package com.yx.common.rabittmq.service;

import com.yx.common.rabittmq.modle.QueueMessge;

/**
 * @Author jesse
 * @Date 3/30/20 6:08 下午
 **/
public interface QueueMessageHandler {
    /**
     * 处理消息业务
     * @param queueMessge
     */
    void process(final QueueMessge queueMessge);
}
