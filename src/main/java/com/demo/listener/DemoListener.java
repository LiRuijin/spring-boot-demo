/**
 * @创建人：李瑞金
 * @创建日期：2018年2月23日下午9:58:54
 */
package com.demo.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;

import com.demo.filter.DemoFilter;

@WebListener
public class DemoListener implements ServletContextListener {

	private static final Logger logger = Logger.getLogger(DemoListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.info("DemoListener contextDestroyed method");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("DemoListener contextInitialized method");
	}
}
