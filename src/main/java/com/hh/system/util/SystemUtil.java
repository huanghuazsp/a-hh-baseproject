package com.hh.system.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.hh.system.bean.SysData;
import com.hh.system.service.impl.BeanFactoryHelper;
import com.hh.system.service.impl.SysDataService;
import com.hh.system.service.impl.SystemFileService;
import com.hh.usersystem.bean.usersystem.UsUser;
import com.hh.usersystem.service.impl.LoginUserUtilService;

public class SystemUtil {
	private static LoginUserUtilService loginUserUtilService;
	private static SysDataService sysDataService;
	private static Gson gson = new Gson();

	public static LoginUserUtilService getLoginUserUtilService() {
		if (loginUserUtilService == null) {
			loginUserUtilService = BeanFactoryHelper.getBeanFactory().getBean(LoginUserUtilService.class);
		}
		return loginUserUtilService;
	}

	public static SysDataService getSysDataService() {
		if (sysDataService == null) {
			sysDataService = BeanFactoryHelper.getBeanFactory().getBean(SysDataService.class);
		}
		return sysDataService;
	}

	static SystemFileService systemFileService = null;

	public static SystemFileService getSystemFileService() {
		if (systemFileService != null) {
			return systemFileService;
		}
		systemFileService = BeanFactoryHelper.getBean(SystemFileService.class);
		return systemFileService;
	}

	public static String getBaseDoctype() {
		return BaseSystemUtil.getBaseDoctype();
	}

	public static String getBaseJs(String... args) {
		String theme = getTheme();
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("operPower", getLoginUserUtilService().getOperPower());
		paramsMap.put("theme", theme);
		return "<link rel=\"shortcut icon\" href=\"" + SysParam.sysParam.getSysIcon2() + "\" />"
				+ BaseSystemUtil.getBaseJs(paramsMap, args);
	}

	public static String getTheme() {
		UsUser hhXtYh = ((UsUser) ServletActionContext.getRequest().getSession().getAttribute("loginuser"));
		String theme = "";
		if (Check.isNoEmpty(hhXtYh) && Check.isNoEmpty(hhXtYh) && Check.isNoEmpty(hhXtYh.getTheme())) {
			theme = hhXtYh.getTheme();
		}
		return theme;
	}

	public static String getUser() {
		UsUser hhXtYh = getLoginUserUtilService().findLoginUserSimple();
		if (hhXtYh != null) {
			return "<script type=\"text/javascript\">var loginUser=" + gson.toJson(hhXtYh) + ";</script>";
		}
		return "";
	}

	public static String getJqueryCss() {
		return BaseSystemUtil.getJqueryCss(getTheme());
	}

	private static LoginUserUtilService userService = BeanFactoryHelper.getBean(LoginUserUtilService.class);

	public static String getMobileHead(String quickMenu) {
		UsUser hhXtYh = userService.findLoginUser();
		String returnstr = hhXtYh.getMobileHead();
		if (Check.isNoEmpty(quickMenu)) {
			List<Map<String, Object>> mapList = Json.toMapList(quickMenu);
			StringBuffer str = new StringBuffer();
			str.append(
					"<div id='rightpanel' data-position='right' data-role='panel' data-display='overlay'	data-theme='b'>");

			str.append("<ul data-role='listview'  data-filter='false' data-inset='true'>");
			for (Map<String, Object> map : mapList) {
				str.append("<li  data-icon='false'><a data-ajax='false' href='" + map.get("url") + "'>");
				if (Check.isNoEmpty(map.get("img"))) {
					str.append("<img src='" + map.get("img") + "' alt='" + map.get("text") + "'	class='ui-li-icon'>");
				}
				str.append(map.get("text") + "</a></li>");
			}

			str.append("</ul>");
			str.append("</div>");
			returnstr = returnstr.replace("right_panel", str);
			returnstr = returnstr.replace("right_tool",
					"<a href='#rightpanel'   data-role='button' data-position='fixed' data-icon='bars'	data-iconpos='notext'></a>");
		} else {
			returnstr = returnstr.replace("right_panel", "");
			returnstr = returnstr.replace("right_tool", "");
		}
		return returnstr;

	}

	public static String getMobileDown() {
		return "</div>";
	}

	public static String getJsonDataByCode(String code) {
		List<SysData> sysDatas = getSysDataService().queryListByCode(code);
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		for (SysData sysData : sysDatas) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", sysData.getCode());
			map.put("text", sysData.getText());
			mapList.add(map);
		}
		return gson.toJson(mapList).replaceAll("\"", "'");
	}

	public static String getMobileBaseJs(Map<String, String> paramsMap, String... args) {
		return BaseSystemUtil.getMobileBaseJs(paramsMap, args);
	}

	public static String getMobileBaseJs(String... args) {
		return BaseSystemUtil.getMobileBaseJs(args);
	}

	public static String getKey(String... args) {
		return BaseSystemUtil.getKey(args);
	}

}
