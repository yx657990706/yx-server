package com.yx.commonrabittmq.service;

import com.yx.commonrabittmq.modle.QueueMessge;

/**
 * @Author jesse
 * @Date 3/30/20 6:08 下午
 **/
public interface QueueMessageService {
    /**
     * 处理消息业务
     * @param queueMessge
     */
    void process(final QueueMessge queueMessge);
}
