package com.yx.common.mvc.filter;


import com.yx.common.mvc.utils.RequestUtil;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * CrosFilter : 跨域资源共享过滤器, 该过滤器设置response header, 解决跨域ajax请求报错
 *
 * @author jesse
 * @since 2018-12-15
 */
@Component
@WebFilter(urlPatterns = "/*", filterName = "corsFilter")
@Order(value = 1)
public class CorsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        // 允许指定域进行访问
        String s = RequestUtil.getScheme(req);
        //白名单
        String[] allowDomain = {
                s + "://localhost:8089",
                s + "://localhost:8080",
                s + "://localhost:8888",
                "http://www.baidu111.com"
        };
        Set<String> allowedOrigins = new HashSet<String>(Arrays.asList(allowDomain));
        String originHeader = ((HttpServletRequest) req).getHeader("Origin");
        if (allowedOrigins.contains(originHeader)) {
            response.setHeader("Access-Control-Allow-Origin", originHeader);
        }
        //TODO 测试后移除 本地测试html不起tomcat访问
        response.setHeader("Access-Control-Allow-Origin", originHeader);
//        if (!SystemMode.isProdMode()) {//生产环境不允许*访问
//            response.setHeader("Access-Control-Allow-Origin",originHeader );
//        }
        response.setHeader("Access-Control-Allow-Credentials", "true");
        // 允许的方法
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Cache-Control, token");
        chain.doFilter(req, res);
    }

}
