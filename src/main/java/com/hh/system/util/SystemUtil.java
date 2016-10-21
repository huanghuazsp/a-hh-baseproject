package com.hh.system.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.hh.system.service.impl.BeanFactoryHelper;
import com.hh.system.util.request.Request;
import com.hh.usersystem.bean.usersystem.SysMenu;
import com.hh.usersystem.bean.usersystem.UsUser;
import com.hh.usersystem.service.impl.LoginUserUtilService;
import com.hh.usersystem.service.impl.MenuService;

public class SystemUtil {
	private static LoginUserUtilService loginUserUtilService;
	private static Gson gson = new Gson();

	public static LoginUserUtilService getLoginUserUtilService() {
		if (loginUserUtilService == null) {
			loginUserUtilService = BeanFactoryHelper.getBeanFactory().getBean(LoginUserUtilService.class);
		}
		return loginUserUtilService;
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
	private static MenuService MenuService = BeanFactoryHelper.getBean(MenuService.class);

	public static String getMobileHead(String quickMenu) {
		String returnstr = "";
		if (Request.getSession() != null) {
			String mobileHead = Convert.toString(Request.getSession().get("mobileHead"));
			if (Check.isEmpty(mobileHead)) {
				UsUser hhXtYh = userService.findLoginUser();
				StringBuffer str = new StringBuffer();
				str.append("<div id='page' data-role='page'	data-theme='a'>");
				str.append("<script type='text/javascript'>	$(function() {	$('[data-role=collapsible]').eq(0).find('a').click();	});</script>");
				str.append("<div id='leftpanel' data-role='panel' data-display='overlay'	data-theme='b'>");
				
				str.append("<ul data-role='listview'  data-filter='false' data-inset='true'>");
				str.append("<li data-icon='home' style='text-align:center;'><a data-ajax='false' href='webapp-desktop-mobiledesktop'  >主页</a></li>");
				str.append("</ul>");
				
				str.append("<div data-role='collapsible-set'>");
				List<SysMenu> menuList = hhXtYh.getMenuList();
				for (SysMenu hhXtCd : menuList) {
					if ("root".equals(hhXtCd.getNode())) {
						List<SysMenu> submenuList = MenuService.getSubNodes(hhXtCd.getId(), menuList);
						if (submenuList.size() > 0) {
							str.append("<div data-role='collapsible'>");
							str.append("<h2>");
							str.append("<img src='" + hhXtCd.getIcon() + "' alt='" + hhXtCd.getText()
									+ "' class='ui-li-icon'>&nbsp;" + hhXtCd.getText() + "");
							str.append("</h2>");
							str.append("<ul data-role='listview'>");
							for (SysMenu subhhXtCd : submenuList) {
								str.append("<li><a data-ajax='false' href='" + subhhXtCd.getMobileUrl()
										+ "'><img	src='" + subhhXtCd.getIcon() + "' alt='" + hhXtCd.getText()
										+ "'	class='ui-li-icon'>" + subhhXtCd.getText() + "</a></li>");
							}
							str.append("</ul>");
							str.append("</div>");

						}
					}
				}

				str.append("</div>");
				str.append("</div>");

				str.append("<div data-role='header'>");
				str.append(
						"<a href='#leftpanel' data-role='button' data-position='fixed' data-icon='home'	data-iconpos='notext'></a>");
				str.append("<h1>" + SysParam.sysParam.getSysName() + "</h1>");

				str.append("right_tool");

				str.append("</div>");
				str.append("right_panel");

				Request.getSession().put("mobileHead", str.toString());
				returnstr = str.toString();
			} else {
				returnstr = mobileHead;
			}
		}

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

}
