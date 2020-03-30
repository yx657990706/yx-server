package com.yx.commonrabittmq.consumer;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.yx.commonrabittmq.modle.QueueMessge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author jesse
 * @version v1.0
 * @project my-base
 * @Description
 * @encoding UTF-8
 * @date 2018/12/17
 * @time 5:21 PM
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
@Slf4j
@Component
public class MessgeConsumer {

    @RabbitListener(queues = "${report.queue}")
    @RabbitHandler
    public void dealBiz(QueueMessge queueMessge, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        try {
            log.debug("===>>MessgeConsumer receive msg:{}", JSON.toJSONString(queueMessge));
//            final HashMap<String, Object> content = queueMessge.getContent();
//            final Object o = content.get(QueueMessgeConst.BEAN_NAME_KEY);
//            final Object bean = SpringUtils.getBean(o.toString());
//            DealBizQueueService dealBizQueueService = (DealBizQueueService) bean;
//           if (dealBizQueueService != null) {
//                dealBizQueueService.process(queueMessge);
//            }
            channel.basicAck(tag, false);
            log.debug("===>>MessgeConsumer 消息处理完成");
        } catch (Throwable e) {
            try {
                //false表示拒绝后不再进入队列，此时若绑定了死信队列，则进入死信队列
                channel.basicReject(tag, false);
            } catch (IOException ex) {
                log.error("MessgeConsumer reject error", ex);
            }
            log.error("MessgeConsumer error", e);
        }
    }
}
