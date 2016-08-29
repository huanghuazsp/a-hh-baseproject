package com.hh.usersystem.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.hh.system.util.BeanUtils;
import com.hh.system.util.Check;
import com.hh.usersystem.LoginUserServiceInf;
import com.hh.usersystem.aop.interceptor.SecurityInterceptor;
import com.hh.usersystem.bean.usersystem.SysOper;
import com.hh.usersystem.bean.usersystem.UsUser;
import com.hh.usersystem.bean.usersystem.UsOrganization;
import com.hh.usersystem.util.steady.StaticProperties.OperationLevel;
import com.opensymphony.xwork2.ActionContext;

@Service
public class LoginUserUtilService implements LoginUserServiceInf {
	private static Gson gson = new Gson();

	public UsUser findLoginUser() {
		if (ActionContext.getContext() == null || ActionContext.getContext().getSession()==null) {
			return null;
		}
		Object userObject = ActionContext.getContext().getSession()
				.get("loginuser");
		if (userObject!=null) {
			UsUser hhXtYh = (UsUser)userObject ;
			return hhXtYh;
		}
		return null;
	}
	
	public UsUser findLoginUserSimple() {
		if (ActionContext.getContext() == null) {
			return null;
		}
		UsUser hhXtYh = (UsUser) ActionContext.getContext().getSession()
				.get("loginuser");
		
		UsUser user = new UsUser();
		BeanUtils.copyProperties(user, hhXtYh);
		user.setMenuList(null);
		user.setOperMap(null);
		user.setOperPageTextList(null);
		user.setOperPageTextMap(null);
		user.setOperUrlList(null);
		user.setDesktopQuickList(null);
		return user;
	}

	public String findUserId() {
		if (ActionContext.getContext() == null) {
			return null;
		}
		Object object = ActionContext.getContext().getSession()
				.get("loginuser");
		if (object != null) {
			UsUser hhXtYh = (UsUser) object;
			return hhXtYh.getId();
		}
		return null;
	}

	public String findLoginUserText() {
		UsUser hhXtYh = findLoginUser();
		if (hhXtYh == null) {
			return null;
		} else {
			return hhXtYh.getText();
		}
	}

	public UsOrganization queryDataSecurityOrg(String action) {
		UsUser user = findLoginUser();
		if (user == null) {
			return null;
		}
		UsOrganization organization = null;
		UsUser hhXtYh = findLoginUser();
		Map<String, SysOper> operMap = hhXtYh.getOperMap();
		SysOper hhXtCz = operMap.get(action);
		if (hhXtCz != null) {
			if (OperationLevel.BJG.toString().equals(hhXtCz.getOperLevel())) {
				organization = hhXtYh.getOrg();
			} else if (OperationLevel.BBM.toString().equals(
					hhXtCz.getOperLevel())) {
				organization = hhXtYh.getDept();
			} else if (OperationLevel.BGW.toString().equals(
					hhXtCz.getOperLevel())) {
				organization = hhXtYh.getJob();
			} else {
				organization = null;
			}
			return organization;
		}
		return null;
	}

	public String getOperPower() {
		UsUser hhXtYh = findLoginUser();
		if (hhXtYh != null) {
			HttpServletRequest request = ServletActionContext.getRequest();
			String uri = request.getRequestURI();
			uri = "jsp-"
					+ uri.replace(request.getContextPath(), "")
							.replaceAll("/WEB-INF/CLASSPATH-PAGES/jsp/com/hh/",
									"").replace(".jsp", "")
							.replaceAll("/", "-");

			List<String> allOperPower = SecurityInterceptor.all_manage_page_text_map
					.get(uri);
			List<String> myOperPower = hhXtYh.getOperPageTextMap().get(uri);
			if (myOperPower == null) {
				myOperPower = new ArrayList<String>();
			}

			Map<String, Boolean> myOperPowerMap = new HashMap<String, Boolean>();
			Map<String, Boolean> operPowerMap = new HashMap<String, Boolean>();
			for (String string : myOperPower) {
				myOperPowerMap.put(string, true);
			}
			if (allOperPower!=null) {
				for (String string : allOperPower) {
					if (myOperPowerMap.get(string) ==null || myOperPowerMap.get(string) != true) {
						operPowerMap.put(string, true);
					}
				}
			}
			return "<script type=\"text/javascript\">" + "var operPower="
					+ gson.toJson(operPowerMap) + ";" + "</script>";
		}
		return "";
	}

	@Override
	public String findOrgId() {
		UsUser hhXtYh = findLoginUser();
		if (Check.isNoEmpty(hhXtYh)) {
			UsOrganization organization = hhXtYh.getOrg();
			if (Check.isNoEmpty(organization)) {
				return organization.getId();
			}
		}
		return "";
	}

	@Override
	public String findDeptId() {
		UsUser hhXtYh = findLoginUser();
		if (Check.isNoEmpty(hhXtYh)) {
			UsOrganization organization = hhXtYh.getDept();
			if (Check.isNoEmpty(organization)) {
				return organization.getId();
			}
		}
		return "";
	}

	@Override
	public String findJobId() {
		UsUser hhXtYh = findLoginUser();
		if (Check.isNoEmpty(hhXtYh)) {
			UsOrganization organization = hhXtYh.getJob();
			if (Check.isNoEmpty(organization)) {
				return organization.getId();
			}
		}
		return "";
	}

	@Override
	public String findUserName() {
		UsUser user = findLoginUser();
		if (user != null) {
			return user.getText();
		}
		return "";
	}
}
