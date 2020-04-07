package com.yx.gateway.route;

import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

@Slf4j
@Component
public class CustomerRouteLocator {

    /**
     * 路由规则定义配置DataId
     */
    @Value("${gateway.route.rule.dataId}")
    private String dataId;

    @Autowired
    private NacosConfigProperties properties;

    private JSONArray routeRule;

    @PostConstruct
    private void init() {
        try {
            ConfigService configService = NacosFactory.createConfigService(properties.getServerAddr());
            String source = configService.getConfig(dataId, properties.getGroup(), properties.getTimeout());
            if (StringUtils.isEmpty(source)) {
                return;
            }
            routeRule = JSONArray.parseArray(source);
        } catch (NacosException e) {
            log.error("CustomerRouteLocator.init() error", e);
        }
    }

    /**
     * 路由配置
     *
     * @param builder
     * @return
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        RouteLocatorBuilder.Builder routesBuilder = builder.routes();
        //读取json路由规则，转换为一条条route
        //路由规则：[{"id":"route_id@register_to_st-uer","desc":"所有注册相关接口转发到st-user服务","path":"/api/gl/register/**","uri":"lb://st-user","predicates":{"type":"header","param":[{"name":"auth","value":"123fr"}]}}]
        for (int i = 0, len = routeRule.size(); i < len; i++) {
            JSONObject ruleObj = routeRule.getJSONObject(i);
            //如下代码实现效果：routesBuilder.route("host_route02", r -> r.path("/a/**").uri("http://localhost:2006"))
            routesBuilder.route(ruleObj.getString("id"), r -> {
                // 添加拦截路径
                r.path(ruleObj.getString("path"));
                // 如果有设置order,添加order参数
                if (ruleObj.containsKey("order")) {
                    r.order(ruleObj.getIntValue("order"));
                }
                // 获取路由的URL
                String uri = ruleObj.getString("uri");
                if (ruleObj.containsKey("predicates")) {
                    JSONObject predicatesObj = ruleObj.getJSONObject("predicates");
                    String type = predicatesObj.getString("type");
                    switch (type) {
                        // 基于URL参数的匹配
                        case "query":
                            JSONArray queryParamArray = predicatesObj.getJSONArray("param");
                            for (int j = 0, jlen = queryParamArray.size(); j < jlen; j++) {
                                JSONObject queryParamObj = queryParamArray.getJSONObject(j);
                                r.predicate(this.getQueryPredicate(queryParamObj.getString("name"), queryParamObj.getString("value")));
                            }
                            break;
                        // 基于Header参数的匹配
                        case "header":
                            JSONArray headerParamArray = predicatesObj.getJSONArray("param");
                            for (int j = 0, jlen = headerParamArray.size(); j < jlen; j++) {
                                JSONObject headerParamObj = headerParamArray.getJSONObject(j);
                                r.predicate(this.getHeaderPredicate(headerParamObj.getString("name"), headerParamObj.getString("value")));
                            }
                            break;
                        // 重定向请求
                        case "redirect":
                            break;
                    }
                }
                return r.uri(uri);
            });
        }
        return routesBuilder.build();
    }

    /**
     * 基于param的路由断言匹配
     *     <br> 对表单或者get方式请求生效
     * @param param
     * @param regexp
     * @return
     */
    private Predicate<ServerWebExchange> getQueryPredicate(final String param, final String regexp) {
        return new GatewayPredicate() {
            @Override
            public boolean test(ServerWebExchange exchange) {
                if (StringUtils.isEmpty(regexp)) {
                    return exchange.getRequest().getQueryParams().containsKey(param);
                }
                List<String> values = exchange.getRequest().getQueryParams().get(param);
                if (values == null) {
                    return false;
                }
                for (String value : values) {
                    //正则匹配
                    if (value != null && value.matches(regexp)) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public String toString() {
                return String.format("Query: param=%s regexp=%s", param, regexp);
            }
        };
    }

    /**
     * 基于head的路由断言匹配
     *
     * @param param
     * @param regexp
     * @return
     */
    private Predicate<ServerWebExchange> getHeaderPredicate(final String param, final String regexp) {
        boolean hasRegex = StringUtils.hasText(regexp);
        return new GatewayPredicate() {
            @Override
            public boolean test(ServerWebExchange exchange) {
                List<String> values = exchange.getRequest().getHeaders().getOrDefault(param, Collections.emptyList());
                if (values.isEmpty()) {
                    return false;
                }
                if (hasRegex) {
                    return values.stream().anyMatch(value -> value.matches(regexp));
                }
                return true;
            }

            @Override
            public String toString() {
                return String.format("Header: %s regexp=%s", param, regexp);
            }
        };
    }

}