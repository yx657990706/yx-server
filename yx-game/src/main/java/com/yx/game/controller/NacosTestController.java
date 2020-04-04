package com.yx.game.controller;

import com.alibaba.fastjson.JSON;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.yx.common.mvc.model.GlobalResponse;
import com.yx.common.mvc.utils.ResponseUtil;
import com.yx.game.handler.DynamicKeyHandler;
import com.yx.game.model.GlGame;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author jesse
 * @Date 4/4/20 9:57 下午
 **/
@Slf4j
@Api(tags = "nocos测试")
@ApiSort(4)//接口分组排序
@RestController
public class NacosTestController {

    @Autowired
    private DynamicKeyHandler dynamicKeyHandler;

    @GetMapping(value = "/testNacos")
    @ApiOperation(value = "参数动态获取")
    public GlobalResponse printgetsql() {

        Boolean aSwitch = (Boolean)dynamicKeyHandler.getDynamicValueByKey("aSwitch");
        Boolean bSwitch = (Boolean)dynamicKeyHandler.getDynamicValueByKey("bSwitch");
        Boolean IS_PRO = (Boolean)dynamicKeyHandler.getDynamicValueByKey("IS_PRO");
        String url = (String)dynamicKeyHandler.getDynamicValueByKey("url");

        log.info("===>>aSwitch:{}",aSwitch);
        log.info("===>>bSwitch:{}",bSwitch);
        log.info("===>>IS_PRO:{}",IS_PRO);
        log.info("===>>url:{}",url);

        return ResponseUtil.success();
    }
}
