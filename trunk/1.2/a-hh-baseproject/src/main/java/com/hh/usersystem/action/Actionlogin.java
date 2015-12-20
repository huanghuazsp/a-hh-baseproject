package com.hh.usersystem.action;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.util.Check;
import com.hh.system.util.base.BaseAction;
import com.hh.system.util.model.ReturnModel;
import com.hh.usersystem.bean.usersystem.HhXtYh;
import com.hh.usersystem.bean.usersystem.Organization;
import com.hh.usersystem.service.impl.LoginService;
import com.hh.usersystem.service.impl.UserService;
//import com.hh.usersystem.service.impl.ZmsxService;
import com.hh.usersystem.util.app.LoginUser;

@SuppressWarnings("serial")
public class Actionlogin extends BaseAction {
	private HhXtYh xtYh;
	private boolean jump;
	private String cookie;
	private String type;
	private String message;
	private String desktop;
	@Autowired
	private LoginService loginService;
	@Autowired
	private UserService userService;

	public Object login() {
		if (xtYh == null) {
			this.setMessage("请输入用户名和密码！！");
			return "login";
		}
		ReturnModel returnModel = loginService.savefindLogin(xtYh);
		if ("jsp".equals(type)) {
			if (Check.isEmpty(returnModel.getHref())) {
				this.setMessage(returnModel.getMsg());
				return "login";
			} else {
				addCookie();
				if (Check.isEmpty(xtYh.getDesktopType())) {
					return "desktop2";
				} else {
					return xtYh.getDesktopType();
				}

			}
		} else {
			if (!Check.isEmpty(returnModel.getHref())) {
				addCookie();
			}
		}
		if (jump) {
			return returnModel;
		} else {
			if (Check.isEmpty(returnModel.getHref())) {
				return returnModel;
			}
		}
		return null;
	}

	public Object findUserSessionId() {
		String sessionId = xtYh.getId();
		if (Check.isNoEmpty(sessionId)) {
			Set<String> keyset = LoginUser.loginUserSession.keySet();
			for (String key : keyset) {
				HttpSession httpSession = LoginUser.loginUserSession.get(key);
				if (sessionId.equals(httpSession.getId())) {
					HhXtYh hhXtYh = LoginUser.loginUserMap.get(key);
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", hhXtYh.getId());
					map.put("text", hhXtYh.getText());
					map.put("theme", hhXtYh.getTheme());
					if (hhXtYh.getOrganization() != null) {
						Organization organization =hhXtYh.getOrganization();
						Organization dept = organization.getBm();
						Organization org = organization.getJg();
						map.put("jobId", organization.getId());
						map.put("jobText", organization.getText());
						if (dept != null) {
							map.put("deptId", dept.getId());
							map.put("deptText", dept.getText());
						}
						if (org != null) {
							map.put("orgId", org.getId());
							map.put("orgText", org.getText());
						}
					}
					return map;
				}
			}
		}
		return false;
	}

	private void addCookie() {
		int maxAge = 0;
		// if (Check.isNoEmpty(cookie)) {
		maxAge = 60 * 60 * 24 * 30;
		// }
		Cookie cookie = new Cookie("xtYh.vdlzh", xtYh.getVdlzh());
		cookie.setMaxAge(maxAge); // cookie 保存30天
		response.addCookie(cookie);
		// cookie = new Cookie("xtYh.vmm", xtYh.getVmm());
		// cookie.setMaxAge(maxAge); // cookie 保存30天
		// response.addCookie(cookie);
		// cookie = new Cookie("xtYh.cookie", "checked");
		// cookie.setMaxAge(maxAge); // cookie 保存30天
		// response.addCookie(cookie);
	}

	public String logout() {
		session.remove("loginuser");
		return "login";
	}

	public String updateDesktopType() {
		HhXtYh hhXtYh = (HhXtYh) session.get("loginuser");
		if (hhXtYh != null) {
			userService.update(hhXtYh.getId(), "desktopType", desktop);
			return desktop;
		} else {
			return "login";
		}
	}

	public HhXtYh getXtYh() {
		return xtYh;
	}

	public void setXtYh(HhXtYh xtYh) {
		this.xtYh = xtYh;
	}

	public boolean isJump() {
		return jump;
	}

	public void setJump(boolean jump) {
		this.jump = jump;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public String getDesktop() {
		return desktop;
	}

	public void setDesktop(String desktop) {
		this.desktop = desktop;
	}

}
