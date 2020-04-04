package com.yx.game.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "gl_game")
@Setter
@Getter
public class GlGame implements Serializable {
	
	private static final long serialVersionUID = 5199692580550320944L;

	/**
     * 游戏ID
     */
    @Id
    @Column(name = "game_id")
    @ApiModelProperty(value = "游戏id")
    private Integer gameId;

    /**
     * 游戏类型：0彩票，1真人，2体育，3老虎机，4捕鱼，5电竞
     */
    @Column(name = "game_type")
    @ApiModelProperty(value = "游戏类型：0彩票，1真人，2体育，3老虎机，4捕鱼，5电竞")
    private Integer gameType;

    /**
     * 游戏子类型：0其他，1电动老虎机，2经典老虎机，3刮刮乐，4棋牌游戏，5街机游戏
     */
    @Column(name = "sub_type")
    @ApiModelProperty(value = "游戏子类型：0其他，1电动老虎机，2经典老虎机，3刮刮乐，4棋牌游戏，5街机游戏")
    private Integer subType;

    /**
     * 渠道ID
     */
    @Column(name = "channel_id")
    private Integer channelId;

    /**
     * 游戏名称
     */
    @Column(name = "game_name")
    private String gameName;

    /**
     * 游戏LOGO
     */
    private String logo;

    /**
     * 游戏编码
     */
    @Column(name = "game_code")
    private String gameCode;

    /**
     * 是否热门：0否，1是
     */
    private Integer hot;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private String createDate;

    /**
     * 最后修改人
     */
    private String operator;

    /**
     * 最后修改时间
     */
    @Column(name = "last_update")
    private Date lastUpdate;

    /**
     * 0正常，1已关闭，2已删除
     */
    private Integer status;

    /**
     * 厂商游戏名称
     */
    @Column(name = "mer_gamename")
    private String merGameName;

    /**
     * 奖池游戏 0无，1有
     */
    @Column(name = "jackpot")
    private Integer jackpot;

    @Column(name = "line_num")
    private Integer lineNum;
}