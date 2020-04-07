package com.yx.gateway.filter;

import com.yx.common.redis.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * token全局过滤器
 */
@Order(value = 5)
@Component
public class AuthTokenFilter implements GlobalFilter {

    @Autowired
    private RedisService redisService;

    /**
     * 排除签名验证的API
     */
    @Value("#{'${forehead.auth.skip_check_url_list}'.split(',')}")
    private static List<String> SIGN_EXCLUDE_LIST;
    private static final String AUTHORIZE_TOKEN = "token";
    /**
     * 路径匹配验证器
     */
    private static PathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse response = exchange.getResponse();
        ServerHttpRequest request = exchange.getRequest();
        // 获取请求的路径
        String requestPath = request.getURI().getRawPath();
        boolean skip = this.checkSkip(requestPath);
        if (skip) {
            return chain.filter(exchange);
        }
        //获取请求头中的token信息
        HttpHeaders headers = request.getHeaders();
        String token = headers.getFirst(AUTHORIZE_TOKEN);
        if (StringUtils.isBlank(token)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        //token校验
        if (!validToken(token)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        return chain.filter(exchange);
    }

    /**
     * 是否跳过验签
     *
     * @param requestPath
     * @return
     */
    private boolean checkSkip(final String requestPath) {
        if(CollectionUtils.isEmpty(SIGN_EXCLUDE_LIST)){
            return true;
        }
        //完全匹配
        if (SIGN_EXCLUDE_LIST.contains(requestPath)) {
            return true;
        }
        //路径匹配
        for (String excludePath : SIGN_EXCLUDE_LIST) {
            if (PATH_MATCHER.match(excludePath, requestPath)) {
                return true;
            }
        }
        return false;
    }

    /**
     * token验证
     * @param token
     * @return
     */
    private boolean validToken(String token) {
        //TODO 解析token
//        String uid = "123";

        //TODO redis查询authToken
//        String authToken = "QWER";
//        if (StringUtils.isBlank(authToken) || !token.endsWith(authToken)) {
//            return false;
//        }
        return true;
    }
}
