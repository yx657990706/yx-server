package com.yx.commonrabittmq.service.impl;

import com.alibaba.fastjson.JSON;
import com.yx.commonrabittmq.modle.QueueMessge;
import com.yx.commonrabittmq.service.QueueMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @Author jesse
 * @Date 3/30/20 6:09 下午
 **/
@Slf4j
@Service("queueMessageService01")
public class QueueMessageServiceImpl implements QueueMessageService {
    @Override
    public void process(QueueMessge queueMessge) {
        try {
            //获取队列中的数据
            final HashMap<String, Object> content = queueMessge.getMsg();
//            final String openid = (String) content.get(QueueMessgeConst.OPENID_KEY);
            final String result = (String) content.get("jsonData");

            //将json转为对象进行业务处理

            log.debug("===>>消费者QueueMessageServiceImpl接收消息:{}",result);
        } catch (Exception e) {
            log.error("===>>QueueMessageServiceImpl队列处理异常msg:{}", JSON.toJSONString(queueMessge),e);
        }
    }
}