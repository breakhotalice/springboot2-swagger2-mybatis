package com.snoopy.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * ClassName: ParamtersFilter <br/>
 * Function: 过滤器. <br/>
 * date: 2018年7月19日 下午3:36:30 <br/>
 * 
 * @author LiHaiqing
 */
@Component
@WebFilter(filterName = "paramtersFilter", urlPatterns = "/*")
public class ParamtersFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(ParamtersFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        logger.info("参数过滤");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
