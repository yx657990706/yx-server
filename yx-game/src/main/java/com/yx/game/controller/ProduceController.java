package com.yx.game.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.yx.common.rabittmq.enums.MsgEnum;
import com.yx.common.rabittmq.enums.PlatformEnum;
import com.yx.common.rabittmq.enums.UserTypeEnum;
import com.yx.common.rabittmq.modle.QueueMessge;
import com.yx.common.rabittmq.modle.TestReport;
import com.yx.common.rabittmq.producer.MessageProducer;
import com.yx.common.rabittmq.producer.ReportProducer;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@Api(tags = "rabbit测试")
@ApiSort(3)
@RestController
public class ProduceController {

    @Autowired
    private ReportProducer producer;

    @Autowired
    private MessageProducer sender;

    @GetMapping(value = "/addMQData2")
    public String addTestData2() {

        String bank = "{\"name\":\"zhangsna\",\"pwd\":\"123456\"}";
        //队列消息封装
        QueueMessge queueMessge = new QueueMessge();
        queueMessge.setBeanName("queueMessageService01");
        queueMessge.setJsonData(bank);

        //发送消息到rabbitMQ队列
        final boolean b = sender.convertAndSend("test101-report-exchange", "test101-report-routekey", queueMessge);
        log.info("队列推送结果===>>{}", b);
        return "ok";
    }

    @GetMapping(value = "/addMQData")
    public String addTestData() {

        TestReport testReport = new TestReport();
        testReport.setUid(1003);
        testReport.setIp("123.23.0.1");
        testReport.setPlatform(PlatformEnum.IOS);
        testReport.setUuid("yx123456sdfgh");
        testReport.setTimestamp(new Date());
        testReport.setUserName("rrr");
        testReport.setDeviceId("PC端");
        testReport.setFinishTime(new Date());
        testReport.setDomain("werr");
        testReport.setIsFake("1");
        testReport.setParentId(23);
        testReport.setParentName("qqq");
        testReport.setRegDays(30L);
        testReport.setRegMonths(1l);
        testReport.setRegTime(new Date());
        testReport.setRegWeeks(4l);
        testReport.setUserType(UserTypeEnum.MEMBER);
        testReport.setName("你哈");

        log.info("===>>上报时间：{}", System.currentTimeMillis());

        producer.report(MsgEnum.Recharge, testReport);

        return "ok";
    }
}
