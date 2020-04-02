package com.yx.game.dao;

import com.yx.common.mysql.BasicMapper;
import com.yx.game.model.GlGame;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface GlGameMapper extends BasicMapper<GlGame> {
    @Results(id = "glGameResult", value = {
            @Result(property = "gameId", column = "game_id", id = true),
            @Result(property = "gameType", column = "game_type", id = true),
            @Result(property = "channelId", column = "channel_id", id = true),
            @Result(property = "gameName", column = "game_name", id = true),
            @Result(property = "logo", column = "logo", id = true),
            @Result(property = "gameCode", column = "game_code", id = true),
            @Result(property = "createDate", column = "create_date", id = true),
            @Result(property = "lastUpdate", column = "last_update", id = true),
            @Result(property = "merGameName", column = "mer_gamename", id = true)
    })
    @Select("select * from gl_game where game_id=#{gameId} ")
    GlGame findOne(@Param("gameId") Integer gameId);

    /**
     * xml查询测试
     * @return
     */
    GlGame seleectByTest();

    GlGame seleectByTest2();
}