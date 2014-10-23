package com.hh.system.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.hh.system.service.impl.BeanFactoryHelper;
import com.hh.usersystem.bean.usersystem.HhXtYh;
import com.hh.usersystem.service.impl.LoginUserUtilService;

public class SystemUtil {
	private static LoginUserUtilService loginUserUtilService;
	private static Gson gson = new Gson();

	public static LoginUserUtilService getLoginUserUtilService() {
		if (loginUserUtilService == null) {
			loginUserUtilService = BeanFactoryHelper.getBeanFactory().getBean(
					LoginUserUtilService.class);
		}
		return loginUserUtilService;
	}

	public static String getBaseDoctype() {
		return BaseSystemUtil.getBaseDoctype();
	}

	public static String getLoginJqueryCss() {
		return BaseSystemUtil.getLoginJqueryCss();
	}

	public static String getBaseJs(String... args) {
		String theme = getTheme();
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("operPower", getLoginUserUtilService().getOperPower());
		paramsMap.put("theme", theme);
		return BaseSystemUtil.getBaseJs(paramsMap, args);
	}

	private static String getTheme() {
		HhXtYh hhXtYh = ((HhXtYh) ServletActionContext.getRequest()
				.getSession().getAttribute("loginuser"));
		String theme = "";
		if (Check.isNoEmpty(hhXtYh) && Check.isNoEmpty(hhXtYh.getHhXtZmsx())
				&& Check.isNoEmpty(hhXtYh.getHhXtZmsx().getTheme())) {
			theme = hhXtYh.getHhXtZmsx().getTheme();
		}
		return theme;
	}

	public static String getUser() {
		HhXtYh hhXtYh = getLoginUserUtilService().findLoginUser();
		if (hhXtYh != null) {
			return "<script type=\"text/javascript\">var loginUser="
					+ gson.toJson(hhXtYh) + ";</script>";
		}
		return "";
	}

	public static String getJqueryCss() {
		return BaseSystemUtil.getJqueryCss(getTheme());
	}

}
