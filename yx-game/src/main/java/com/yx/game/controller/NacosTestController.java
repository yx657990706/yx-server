package com.yx.game.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.yx.common.mvc.model.GlobalResponse;
import com.yx.common.mvc.utils.ResponseUtil;
import com.yx.game.handler.DynamicValueHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author jesse
 * @Date 4/4/20 9:57 下午
 **/
@Slf4j
@Api(tags = "nocos测试")
@ApiSort(4)//接口分组排序
@RestController
public class NacosTestController {

    @Resource
    private DynamicValueHandler dynamicValueHandler;

    @GetMapping(value = "/testNacos")
    @ApiOperation(value = "参数动态获取")
    public GlobalResponse printgetsql() {

        log.info("===>>aSwitch2:{}", dynamicValueHandler.isASwitch());
        log.info("===>>bSwitch2:{}", dynamicValueHandler.isBSwitch());
        log.info("===>>url:{}", dynamicValueHandler.getUrl());

        return ResponseUtil.success();
    }
}
