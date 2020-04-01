package com.yx.game.controller;

import com.alibaba.fastjson.JSON;
import com.yx.common.mvc.model.GlobalResponse;
import com.yx.common.mvc.utils.ResponseUtil;
import com.yx.common.redis.enums.EnumRegion;
import com.yx.game.dao.GlGameMapper;
import com.yx.game.model.GlGame;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author jesse
 * @Date 4/1/20 9:38 下午
 **/
@Slf4j
@RestController
public class MysqlTestContoller {

    @Resource
    private GlGameMapper glGameMapper;

    @GetMapping(value = "/testmysql")
    public GlobalResponse printgetsql() {

        GlGame one = glGameMapper.findOne(20065);
        log.info("===>>{}", JSON.toJSONString(one));
        List<GlGame> glGames = glGameMapper.selectByIds("1,2,3");
        log.info("===>>glGames:{}", JSON.toJSONString(glGames));
        return ResponseUtil.success();
    }

}
