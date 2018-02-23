/**
 * @创建人：李瑞金
 * @创建日期：2018年2月23日下午10:08:53
 */
package com.demo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.apache.log4j.Logger;

@WebFilter(urlPatterns = "/*", filterName = "indexFilter")
public class Demo1Filter implements Filter {
    private static final Logger logger = Logger.getLogger(Demo1Filter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    	logger.info("init IndexFilter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    	logger.info("doFilter IndexFilter");
        filterChain.doFilter(servletRequest,servletResponse);

    }

    @Override
    public void destroy() {

    }
}