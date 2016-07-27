package com.hh.message.service;

import javax.servlet.ServletException;

import org.directwebremoting.Container;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.event.ScriptSessionEvent;
import org.directwebremoting.event.ScriptSessionListener;
import org.directwebremoting.extend.ScriptSessionManager;
import org.directwebremoting.servlet.DwrServlet;

import com.hh.system.service.impl.BeanFactoryHelper;
import com.hh.system.util.LogUtil;
import com.hh.usersystem.service.impl.LoginUserUtilService;

public class DwrScriptSessionManagerUtil extends DwrServlet {
	private static int init = 0;

	public void init() throws ServletException {
		Container container = ServerContextFactory.get().getContainer();
		ScriptSessionManager manager = container.getBean(ScriptSessionManager.class);
		ScriptSessionListener listener = new ScriptSessionListener() {
			public void sessionCreated(ScriptSessionEvent ev) {
				LogUtil.info("创建 ScriptSession!");
			}

			public void sessionDestroyed(ScriptSessionEvent ev) {
				LogUtil.info("销毁 ScriptSession");
			}
		};
		manager.addScriptSessionListener(listener);
	}

	public static void baseInit() {
		if (init == 0) {
			DwrScriptSessionManagerUtil dwrScriptSessionManagerUtil = new DwrScriptSessionManagerUtil();
			try {
				dwrScriptSessionManagerUtil.init();
			} catch (ServletException e) {
				e.printStackTrace();
			}
			init = 1;
		}
	}
}