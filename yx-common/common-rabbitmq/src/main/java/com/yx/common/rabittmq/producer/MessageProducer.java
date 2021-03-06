package com.yx.common.rabittmq.producer;

import com.yx.common.rabittmq.modle.QueueMessge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author jesse
 * @version v1.0
 * @project my-base
 * @Description
 * @encoding UTF-8
 * @date 2018/12/17
 * @time 5:01 PM
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
@Slf4j
@Component
public class MessageProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${report.exchange}")
    private String exchange;

    @Value("${report.routekey}")
    private String routeKey;

    /**
     * 发送队列消息到指定的exchange
     *
     * @param routeKey
     * @param msg
     * @return
     */
    public boolean convertAndSend(String exchange, String routeKey, QueueMessge msg) {
        try {
            rabbitTemplate.convertAndSend(exchange, routeKey, msg);
            return true;
        } catch (Exception e) {
            log.error("===>>MQ异常", e);
            return false;
        }
    }

    /**
     * 定制的send
     * @param msg
     * @return
     */
    public boolean reportSend(QueueMessge msg) {
        try {
            rabbitTemplate.convertAndSend(exchange, routeKey, msg);
            return true;
        } catch (Exception e) {
            log.error("===>>MQ异常", e);
            return false;
        }
    }

    /**
     * 发送延迟队列消息
     *
     * @param queueKey
     * @param msg
     * @param delay    延迟时间（单位：秒）
     * @return
     */
    public boolean convertAndDelaySend(String queueKey, QueueMessge msg, int delay) {
        final int xdelay = delay * 1000;
        try {
            String exchange = "xdelay-letter-exchange";
            //按时间区间分割，避免延迟时间长的排到前面，导致队列后面的数据超时
            if (delay == 0) {//30
                exchange = exchange + "0sec";
            } else if (delay <= 30) {//30
                exchange = exchange + "30sec";
            } else if (delay <= 60) {//60
                exchange = exchange + "60sec";
            } else if (delay <= 300) {//300
                exchange = exchange + "300sec";
            } else if (delay <= 600) {//600
                exchange = exchange + "600sec";
            } else if (delay <= 1800) {//1800
                exchange = exchange + "1800sec";
            } else if (delay <= 3600) {//3600
                exchange = exchange + "3600sec";
            }
            rabbitTemplate.convertAndSend(exchange, queueKey, msg, new MessagePostProcessor() {

                @Override
                public Message postProcessMessage(Message message)
                        throws AmqpException {
                    //设置延时时间
                    message.getMessageProperties().setDelay(xdelay);
                    //设置TTL过期时间
                    message.getMessageProperties().setExpiration(String.valueOf(xdelay));
                    //设置消息持久化
                    message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    return message;
                }
            });
            return true;
        } catch (Exception e) {
            log.error("===>>MQ异常", e);
            return false;
        }

    }
}
