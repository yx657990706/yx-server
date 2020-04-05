package com.yx.game.handler;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @Author jesse
 * @Date 4/4/20 9:41 下午
 *  动态参数集中管理获取
 **/


@Getter
@Setter
@Component
@RefreshScope
@ConfigurationProperties(prefix = "yx.dynamic.value")
public class DynamicValueHandler {

    /**
     * 开关a
     */
    private boolean aSwitch;
    /**
     * 开关b
     */
    private boolean bSwitch;
    /**
     * url
     */
    private String url;
}
