package com.yx.common.mvc.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class RequestUtil {
	

	public static HttpServletRequest getHttpServletRequest() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		 if(attributes!=null){
	        	return (HttpServletRequest) attributes.getRequest();
		 }
		 return null;
	}
	
    public static String getStringParamFromReq(HttpServletRequest req, String paramName){
    	
    	if(!StringUtils.isEmpty(req.getParameter(paramName))){
    		return req.getParameter(paramName);
    	}else if(!StringUtils.isEmpty((String)req.getAttribute(paramName))) {
    		return (String)req.getAttribute(paramName);
    	} else if(!StringUtils.isEmpty(req.getHeader(paramName))) {
    		return req.getHeader(paramName);
    	}
    	return null;
    	
    	
    }
    
    //获取session 但是在分布式环境下请注意是否打开分布式session共享，一定要使用外部存储存储session
    //不推荐大家使用session存储，此API主要是为了部分不得不用的应用
    public static  HttpSession getSession(HttpServletRequest req){
    	return req.getSession(true);
    }
	
	
    public static String getStringParam(HttpServletRequest req, String paramName){
    	
    	if(!StringUtils.isEmpty(req.getParameter(paramName))){
    		return req.getParameter(paramName);
    	}else if(!StringUtils.isEmpty((String)req.getAttribute(paramName))) {
    		return (String)req.getAttribute(paramName);
    	}else if(!StringUtils.isEmpty((String)req.getSession().getAttribute(paramName))) {
    		return (String)req.getSession().getAttribute(paramName);
    	}else if(!StringUtils.isEmpty(getParamFromCookie(req,paramName))) {
    		return getParamFromCookie(req,paramName);
    	}
    	return null;
    	
    	
    }
    
    public static String getParamFromCookie(HttpServletRequest request, String paramName){
    	Cookie[] cookies = request.getCookies();

		if(cookies!= null ) {
			for (Cookie cookie : cookies) {
				if (paramName.equals(cookie.getName())) {
					return  cookie.getValue();
				}
			}
		}
		return null;
    }
    
    /**
     * 获取访问协议
     * @param request
     * @return
     */
    public static String getScheme(ServletRequest request){
    	String x =null;
    	try{
    		x = ((HttpServletRequest)request).getHeader("X-Forwarded-Proto");
    	}catch(Exception e){
    		log.error("获取X-Forwarded-Proto异常",e);
    	}
    	if(StringUtils.isEmpty(x)){
    		return request.getScheme();
    	}else{
    		return x;
    	}

    }
    
    /**
     * 将cookie封装到Map里面
     */
    public static void setCookie(HttpServletResponse response, String paramName, String value){
    	
    	Cookie cookie = new Cookie(paramName,value);
    	 
    	cookie.setMaxAge(3600);
    	 
    	//设置路径，这个路径即该工程下都可以访问该cookie 如果不设置路径，那么只有设置该cookie路径及其子路径可以访问
    	 
    	cookie.setPath("/");
    	response.addCookie(cookie);
    }
    
    /**
     * 根据名字获取cookie
     * @param request
     * @param name cookie名字
     * @return
     */
    public static Cookie getCookieByName(HttpServletRequest request, String name){
        Map<String,Cookie> cookieMap = readCookieMap(request);
        if(cookieMap.containsKey(name)){
            Cookie cookie = (Cookie)cookieMap.get(name);
            return cookie;
        }else{
            return null;
        }   
    }
     
     
     
    /**
     * 将cookie封装到Map里面
     * @param request
     * @return
     */
    private static Map<String,Cookie> readCookieMap(HttpServletRequest request){
        Map<String,Cookie> cookieMap = null;
        Cookie[] cookies = request.getCookies();
        if(null!=cookies){
			cookieMap = new HashMap<String,Cookie>(cookies.length);
            for(Cookie cookie : cookies){
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }



	/**
	 * 获取端口号
	 * @param request
	 * @return
	 */
	public static String getPort(ServletRequest request){
		String port= String.valueOf(((HttpServletRequest)request).getServerPort());
		if ("80".equals(port)){
			return "";
		}else if ("443".equals(port)){
			return "";
		}else{
			return ":" + port;
		}
	}
}
