package com.yx.game.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @Author jesse
 * @Date 4/4/20 9:41 下午
 *  简易版动态参数集中管理获取
 **/
@Slf4j
@RefreshScope
@Component
public class DynamicKeyHandler {

    @Value("${dynamic.base.isPro:false}")
    private boolean IS_PRO;
    @Value("${dynamickey.test.aaaSwitch:false}")
    private boolean aSwitch;
    @Value("${dynamickey.test.bbbSwitch:false}")
    private boolean bSwitch;
    @Value("${dynamickey.test.url}")
    private String url;

    /**
     * 获取动态key
     * @param key
     * @return
     */
   public Object getDynamicValueByKey(String key){
        switch (key){
            case "aSwitch":
                return this.aSwitch;
            case "bSwitch":
                return this.bSwitch;
            case "IS_PRO":
                return this.IS_PRO;
            case "url":
                return this.url;
            default:
                throw new IllegalStateException("Unexpected value: " + key);
        }
   }
}
