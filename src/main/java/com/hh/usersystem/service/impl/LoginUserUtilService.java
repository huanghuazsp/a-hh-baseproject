package com.hh.usersystem.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
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
		if (ActionContext.getContext() == null) {
			return null;
		}
		UsUser hhXtYh = (UsUser) ActionContext.getContext().getSession()
				.get("loginuser");
		return hhXtYh;
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
		Map<String, SysOper> hhXtCzMap = hhXtYh.getHhXtCzMap();
		SysOper hhXtCz = hhXtCzMap.get(action);
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
			return "<script type=\"text/javascript\">" + "var allOperPower="
					+ gson.toJson(SecurityInterceptor.all_manage_page_text_map)
					+ ";"
					+ "var myOperPower="
					+ gson.toJson(hhXtYh.getHhXtCzPageTextMap())
					+ ";"
					+ "var myurl = this.location.pathname.replace(this.contextPath + '/', '');"
					+ "allOperPower = allOperPower[myurl];"
					+ "myOperPower = myOperPower[myurl];"
					+ "var operPower = {};" + "var myOperPowerMap = {};"
					+ "if (allOperPower) {" + "	if (myOperPower) {"
					+ "		for (var i = 0; i < myOperPower.length; i++) {"
					+ "			myOperPowerMap[myOperPower[i]] = true;" + "		}"
					+ "	}" + "	for (var i = 0; i < allOperPower.length; i++) {"
					+ "		if (myOperPowerMap[allOperPower[i]] != true) {"
					+ "			operPower[allOperPower[i]] = true;" + "		}" + "	}"
					+ "	}" + "delete myurl;" + "delete allOperPower;"
					+ "delete myOperPower;" + "delete myOperPowerMap;"
					+ "</script>";
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
