package com.hh.usersystem.listener;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.apache.log4j.Logger;

import com.hh.usersystem.bean.usersystem.HhXtYh;
import com.hh.usersystem.util.app.LoginUser;

public class SessionListener implements HttpSessionAttributeListener
// , HttpSessionListener
{
	private static final Logger logger = Logger
			.getLogger(SessionListener.class);

	public void attributeAdded(HttpSessionBindingEvent event) {
		if (event.getName().equals("loginuser")) {
			HhXtYh object = (HhXtYh) event.getValue();
			logger.info(object.getText() + "上线了......");
			LoginUser.put(object.getId(), object, event.getSession());
			logger.info("当前在线数：" + LoginUser.getLoginUserCount());
		}
	}

	public void attributeRemoved(HttpSessionBindingEvent event) {
		if (event.getName().equals("loginuser")) {
			HhXtYh object = (HhXtYh) event.getValue();
			logger.info(object.getText() + "下线了......");
			LoginUser.remove(object.getId());
			logger.info("当前在线数：" + LoginUser.getLoginUserCount());
		}
	}

	public void attributeReplaced(HttpSessionBindingEvent event) {
	}

	// public void sessionCreated(HttpSessionEvent se) {
	// // TODO Auto-generated method stub
	//
	// }

	// public void sessionDestroyed(HttpSessionEvent event) {
	// HhXtYh object = (HhXtYh) event.getSession().getAttribute("loginuser");
	// if (object != null) {
	// System.out.println("===================================销毁!");
	// LoginUser.remove(object.getId());
	// System.out.println("==================================="
	// + LoginUser.getLoginUserCount());
	// } else {
	// System.out.println("===================================不是用户!");
	// }
	//
	// }

}
