package com.yx.common.rabittmq.producer;

import com.yx.common.rabittmq.enums.MsgEnum;
import com.yx.common.rabittmq.modle.BaseReport;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class ReportProducer implements MessagePostProcessor {

    @Value("${report.pool-size:2}")
    private int poolSize;

    @Value("${report.max-retry:10}")
    private int maxRetry;

    @Value("${report.exchange}")
    private String exchange;

    @Value("${report.routekey}")
    private String routeKey;

    private String contentType = "application/json";

    // rabbitmq
    private RabbitTemplate rabbitTemplate;

    // sender pool
    private ExecutorService senderPool;

    // events queue
    private BlockingQueue<Event> events;

    // delivery mode
    private MessageDeliveryMode deliveryMode = MessageDeliveryMode.PERSISTENT;

    // retry timer
    private final Timer retryTimer = new Timer("global-report-retry", true);

    /**
     * Producer
     * @param rabbitTemplate RabbitTemplate
     */
    @Autowired
    public ReportProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostConstruct
    private void init() {
        // init event query
        this.events = createEventQueue();
        this.senderPool = Executors.newCachedThreadPool();
        for (int i = 0; i < this.poolSize; i++) {
            this.senderPool.submit(new EventSender());
        }
        this.registerShutdown();
        log.info("!!!new producer!!!");
        log.info("producer pool size:{}", this.poolSize);
        log.info("producer max retry:{}", this.maxRetry);
    }

    /**
     * shutdown
     */
    protected void registerShutdown() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("report queue shutdown...");
            ReportProducer.this.senderPool.shutdown();
        }));
    }

    /**
     * create event queue
     */
    private BlockingQueue<Event> createEventQueue() {
        return new LinkedBlockingDeque<>();
    }

    /**
     * report 消息发送MQ
     *
     * @param msgType Event type
     * @param msgBody Event data
     */
    public void report(MsgEnum msgType, BaseReport msgBody) {
        try {
            log.info("发送消息:msg:{}", msgBody.getMsg());
            this.packMessage(msgType, msgBody);
            this.rabbitTemplate.convertAndSend(this.exchange, this.routeKey, msgBody.getMsg());
        } catch (AmqpException e) {
            this.events.add(new Event(msgBody.getMsg()));
        } catch (Exception e) {
            log.warn("report msg failed,retry:{}", e);
        }
    }

    /**
     * 消息封装
     *
     * @param msgType Event type
     * @param msgBody Event data
     * @throws IllegalAccessException exception
     */
    private void packMessage(MsgEnum msgType, BaseReport msgBody) throws IllegalAccessException {
        int eventId = msgType.value();
        msgBody.set("event", eventId);

        // 时间戳参数不能为空
        if (null == msgBody.get("timestamp")) {
            msgBody.set("timestamp", new Date());
        }
        // index shard
        if (null == msgBody.get("index")) {
            Date ts = (Date) msgBody.get("timestamp");
            msgBody.set("index", DateFormatUtils.format(ts, "yyyy.MM.dd"));
        }
        // document id
        // 执行更新增量更新时作为主键
        if (null == msgBody.get("uuid")) {
            msgBody.set("uuid", UUID.randomUUID().toString().replace("-",""));
        }
        // 事件参数
        Class clazz = msgBody.getClass();
        Field[] fields = clazz.getDeclaredFields();
        if (fields.length == 0) {
            return;
        }
        String dataKey = String.valueOf(eventId);
        Map<String, Object> data = new HashMap<>();
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldKey = field.getName();
            Object fieldValue = field.get(msgBody);
            if (null == fieldValue) {
                continue;
            }
            data.put(fieldKey, fieldValue);
        }
        msgBody.set(dataKey, data);
    }


    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
        message.getMessageProperties().setTimestamp(new Date());
        message.getMessageProperties().setContentType(ReportProducer.this.contentType);
        message.getMessageProperties().setMessageId(UUID.randomUUID().toString());
        message.getMessageProperties().setDeliveryMode(ReportProducer.this.deliveryMode);
        return message;
    }

    /**
     * event sender async
     */
    private class EventSender implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    final Event event = ReportProducer.this.events.take();
                    Object message = event.getEvent();
                    try {
                        // try send message
                        log.info("重试发送消息:msg:{}", message);
                        String exchange = ReportProducer.this.exchange;
                        String routingKey = ReportProducer.this.routeKey;
                        ReportProducer.this.rabbitTemplate.convertAndSend(exchange, routingKey, message, ReportProducer.this);
                    } catch (AmqpException e) {
                        int retries = event.incrementRetries();
                        if (retries < ReportProducer.this.maxRetry) {
                            log.warn("retry:{}", retries);
                            // add to schedule queue
                            ReportProducer.this.retryTimer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    ReportProducer.this.events.add(event);
                                }
                            }, (long) (Math.pow(retries, Math.log(retries)) * 1000));
                        } else {
                            log.error("send message failed:{},msg:{},retry:{}", e, message.toString(), ReportProducer.this.maxRetry);
                        }
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * event declare
     */
    private static class Event {

        private final Object event;

        private final AtomicInteger retries = new AtomicInteger(0);

        private Event(Object event) {
            this.event = event;
        }

        private Object getEvent() {
            return this.event;
        }

        private int incrementRetries() {
            return this.retries.incrementAndGet();
        }
    }
}