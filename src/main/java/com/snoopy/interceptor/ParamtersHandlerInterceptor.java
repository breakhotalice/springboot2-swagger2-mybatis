package com.snoopy.interceptor;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.snoopy.result.ActionResult;

/**
 * ClassName: OpenHandlerInterceptor <br/>
 * Function: 拦截参数异常. <br/>
 * date: 2018年7月19日 下午4:36:08 <br/>
 * 
 * @author LiHaiqing
 */
public class ParamtersHandlerInterceptor extends HandlerInterceptorAdapter {

    private static final Logger   logger              = LoggerFactory.getLogger(ParamtersHandlerInterceptor.class);

    protected static final String APPLICATION_CHARSET = "application/json; charset=utf-8";

    protected static final String UTF_8               = "UTF-8";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        try {
            Map<String, String[]> requestParameters = request.getParameterMap();
            if (handler instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
                for (MethodParameter methodParameter : methodParameters) {
                    // 方法参数名
                    String parameterName = methodParameter.getParameterName();
                    // 方法参数类型
                    Class<?> parameterType = methodParameter.getParameterType();
                    // 根据方法参数名获取传入参数值
                    String[] values = requestParameters.get(parameterName);
                    if (values != null) {
                        String simpleName = parameterType.getSimpleName();
                        String name = parameterType.getName();
                        for (String value : values) {
                            if ("int".equals(simpleName) || "java.lang.Integer".equals(name)) {
                                Integer.valueOf(value);
                            } else if ("byte".equals(simpleName) || "java.lang.Byte".equals(name)) {
                                Byte.valueOf(value);
                            } else if ("float".equals(simpleName) || "java.lang.Float".equals(name)) {
                                Float.valueOf(value);
                            } else if ("double".equals(simpleName) || "java.lang.Double".equals(name)) {
                                Double.valueOf(value);
                            } else if ("long".equals(simpleName) || "java.lang.Long".equals(name)) {
                                Long.valueOf(value);
                            } else if ("boolean".equals(simpleName) || "java.lang.Boolean".equals(name)) {
                                Boolean.valueOf(value);
                            } else if ("char".equals(simpleName) || "java.lang.Character".equals(name)) {
                            } else if ("short".equals(simpleName) || "java.lang.Short".equals(name)) {
                                Short.valueOf(value);
                            } else if ("java.util.Date".equals(name)) {
                            } else {

                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.debug(String.format("参数类型转换异常：%s", e));
            ErrorMsg(response, e);
            return false;
        }
        return true;
    }

    /**
     * 异常. <br/>
     * date: 2018年7月23日 上午11:20:39.<br/>
     * 
     * @author LiHaiqing
     * @param e
     */
    public static void ErrorMsg(HttpServletResponse response, Exception e) throws Exception {
        PrintWriter out = response.getWriter();
        response.setCharacterEncoding(UTF_8);
        response.setContentType(APPLICATION_CHARSET);
        ActionResult<String> result = new ActionResult<>("-100", "参数异常", e.getMessage());
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
        out.write(jsonObject.toString());
    }
}
