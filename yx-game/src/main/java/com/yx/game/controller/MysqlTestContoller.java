package com.yx.game.controller;

import com.alibaba.fastjson.JSON;
import com.yx.common.mvc.model.GlobalResponse;
import com.yx.common.mvc.utils.ResponseUtil;
import com.yx.game.dao.GlGameMapper;
import com.yx.game.model.GlGame;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author jesse
 * @Date 4/1/20 9:38 下午
 **/
@Slf4j
@Api(tags = "mysql测试")
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

    @GetMapping(value = "/testmysql2")
    public GlobalResponse printgetsql2() {

        //1.xml查询
        GlGame one = glGameMapper.seleectByTest();
        log.info("===>>game:{}", JSON.toJSONString(one));

        GlGame one2 = glGameMapper.seleectByTest2();
        log.info("===>>game2:{}", JSON.toJSONString(one2));

        //2.查询条件处理
        Condition condition = new Condition(GlGame.class);
        condition.createCriteria()
                .andLessThan("gameId",4);
        //排序
        condition.orderBy("gameId").desc();
        List<GlGame> glGames = glGameMapper.selectByCondition(condition);
        log.info("===>>glGames:{}", JSON.toJSONString(glGames));

        //3、Example查询
        Example example = new Example(GlGame.class);
        example.createCriteria()
                .andCondition("game_id>",5)//自己拼接的字段不会做字段映射处理
                .andLessThan("gameId",8);//会映射为game_id(modle中有定义)

        List<GlGame> glGames2 = glGameMapper.selectByExample(example);
        log.info("===>>glGames2:{}", JSON.toJSONString(glGames2));

        return ResponseUtil.success();
    }

}
