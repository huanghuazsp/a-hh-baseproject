package com.hh.system.lis;

import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.log4j.Logger;

import com.hh.system.service.impl.BeanFactoryHelper;
import com.hh.system.service.impl.DataInitializationService;
import com.hh.system.util.date.UtilDateConvert;

public class ServletContextListenerSetUp implements ServletContextListener {
	static {
		ConvertUtils.register(new UtilDateConvert(), java.util.Date.class);
	}
	private static final Logger logger = Logger.getLogger(ServletContextListenerSetUp.class);
	public void contextInitialized(ServletContextEvent sce) {
	
		DataInitializationService dataInitializationService = BeanFactoryHelper
				.getBeanFactory().getBean(DataInitializationService.class);
		try {
			dataInitializationService.dataInitialization();
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("服务器启动了.....");
	}

	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("服务器关闭了.....");
	}

}
