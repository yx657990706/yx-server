package com.yx.forehead.gateway.handler;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.yx.common.base.enums.EnumResponseCode;
import com.yx.common.base.restful.GlobalResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

/**
 * 自定义限流响应处理器
 */
public class CustomerBlockRequestHandler implements BlockRequestHandler {

    @Override
    public Mono<ServerResponse> handleRequest(ServerWebExchange exchange, Throwable t) {
        GlobalResponse result = new GlobalResponse(EnumResponseCode.TOOMANY_REQUEST.getCode(), EnumResponseCode.TOOMANY_REQUEST.getMsg());
        Mono<ServerResponse> body = ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(result));
        return body;
    }

}