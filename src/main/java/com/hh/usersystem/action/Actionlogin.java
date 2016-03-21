package com.hh.usersystem.action;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.MessageException;
import com.hh.system.util.Random;
import com.hh.system.util.base.BaseAction;
import com.hh.system.util.date.DateFormat;
import com.hh.system.util.email.JavaMail;
import com.hh.system.util.model.ReturnModel;
import com.hh.system.util.statics.StaticVar;
import com.hh.usersystem.bean.usersystem.UsOrganization;
import com.hh.usersystem.bean.usersystem.UsReg;
import com.hh.usersystem.bean.usersystem.UsUser;
import com.hh.usersystem.service.impl.LoginService;
import com.hh.usersystem.service.impl.UsRegService;
import com.hh.usersystem.service.impl.UserService;
//import com.hh.usersystem.service.impl.ZmsxService;
import com.hh.usersystem.util.app.LoginUser;

@SuppressWarnings("serial")
public class Actionlogin extends BaseAction {
	private UsUser xtYh;
	private boolean jump;
	private String cookie;
	private String type;
	private String message;
	private String desktop;
	private String email;
	private String code;
	@Autowired
	private LoginService loginService;
	@Autowired
	private UserService userService;
	@Autowired
	private UsRegService usRegService;

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

	public Object findCode() {
		Map<String, Object> map = new HashMap<String, Object>();
		UsReg usReg = new UsReg();
		UsReg usReg2 = usRegService.findObjectByProperty("email", email);
		if (usReg2 != null) {
			usReg = usReg2;
		}
		usReg.setEmail(email);
		usReg.setCode(Random.randomCommon(1000, 9999) + "");
		List<String> maiList = new ArrayList<String>();
		maiList.add(email);
		JavaMail se = new JavaMail();
		String msg = "您的注册码为：" + usReg.getCode() + "。";
		se.doSendHtmlEmail(maiList, "注册码获取", msg);
		usRegService.save(usReg);
		map.put("code", 1);
		return map;
	}

	public Object reg() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			xtYh.setNxb(1);
			UsReg usReg2 = usRegService.findObjectByProperty("email", xtYh.getVdzyj());
			if (usReg2==null) {
				map.put("msg", "注册码未获取");
				return map;
			}else{
				if (!Convert.toString(code).equals(usReg2.getCode())) {
					map.put("msg", "注册码不正确");
					return map;
				}
			}
			xtYh.setRoleIds(StaticVar.role_zcyh_id);
			userService.save(xtYh);
			return map;
		} catch (MessageException e) {
			map.put("msg", e.getMessage());
			return map;
		}
	}

	public Object findUserSessionId() {
		String sessionId = xtYh.getId();
		if (Check.isNoEmpty(sessionId)) {
			Set<String> keyset = LoginUser.loginUserSession.keySet();
			for (String key : keyset) {
				HttpSession httpSession = LoginUser.loginUserSession.get(key);
				if (sessionId.equals(httpSession.getId())) {
					UsUser hhXtYh = LoginUser.loginUserMap.get(key);
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", hhXtYh.getId());
					map.put("text", hhXtYh.getText());
					map.put("theme", hhXtYh.getTheme());
					// if (hhXtYh.getOrganization() != null) {
					// Map<String, Organization> organization
					// =hhXtYh.getOrganization();
					UsOrganization dept = hhXtYh.getDept();
					UsOrganization org = hhXtYh.getOrg();
					UsOrganization gw = hhXtYh.getJob();

					if (gw != null) {
						map.put("jobId", gw.getId());
						map.put("jobText", gw.getText());
					}
					if (dept != null) {
						map.put("deptId", dept.getId());
						map.put("deptText", dept.getText());
					}
					if (org != null) {
						map.put("orgId", org.getId());
						map.put("orgText", org.getText());
					}
					// }
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
		Cookie cookie = new Cookie("xtYh.vdlzh", URLEncoder.encode(xtYh
				.getVdlzh()));
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
		UsUser hhXtYh = (UsUser) session.get("loginuser");
		if (hhXtYh != null) {
			userService.update(hhXtYh.getId(), "desktopType", desktop);
			return desktop;
		} else {
			return "login";
		}
	}

	public UsUser getXtYh() {
		return xtYh;
	}

	public void setXtYh(UsUser xtYh) {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
