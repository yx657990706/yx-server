package com.yx.common.mvc.filter;

import com.yx.common.mvc.model.ParameterRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 参数处理过滤器
 *
 * 这里负责将所有公共的Header中的参数放到Request对象中
 * 在Controller的方法中可以直接注入Header中的参数
 */
@Slf4j
@Component
@WebFilter(urlPatterns = "/*")
public class ParameterFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        ParameterRequestWrapper requestWrapper = new ParameterRequestWrapper(request);
        // 应用类型
        String appType = request.getHeader("appType");
        if (StringUtils.isEmpty(appType)) {
            appType = request.getHeader("app-type");
        }
        requestWrapper.addParameter("headerAppType", appType);
//        // 设备号
//        String deviceId = request.getHeader("deviceId");
//        if (StringUtils.isEmpty(deviceId)) {
//            deviceId = request.getHeader("device-id");
//        }
//        requestWrapper.addParameter("headerDeviceId", deviceId);
//        // 系统类型
//        String osType = request.getHeader("osType");
//        if (StringUtils.isEmpty(osType)) {
//            osType = request.getHeader("os-type");
//        }
//        requestWrapper.addParameter("headerOsType", osType);
//        // 浏览器类型
//        String userAgent = request.getHeader("User-Agent");
//        requestWrapper.addParameter("headerUserAgent", userAgent);
        // 请求的域名和IP
//        String url = RequestUtil.getInstance().getUrl(request);
//        String ip = RequestUtil.getInstance().getIp(request);
//        requestWrapper.addParameter("requestIp", ip);
//        requestWrapper.addParameter("requestUrl", url);
        // Token
//        String token = request.getHeader("token");
//        requestWrapper.addParameter("headerToken", token);
//        // 用户ID
//        String uid = request.getHeader("uid");
//        requestWrapper.addParameter("headerUid", uid);
        filterChain.doFilter(requestWrapper, servletResponse);
    }

    @Override
    public void destroy() {

    }

}