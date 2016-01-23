package com.hh.usersystem.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.hh.usersystem.LoginUserServiceInf;
import com.hh.usersystem.aop.interceptor.SecurityInterceptor;
import com.hh.usersystem.bean.usersystem.HhXtCz;
import com.hh.usersystem.bean.usersystem.HhXtYh;
import com.hh.usersystem.bean.usersystem.Organization;
import com.hh.usersystem.util.steady.StaticProperties.OperationLevel;
import com.opensymphony.xwork2.ActionContext;

@Service
public class LoginUserUtilService implements LoginUserServiceInf {
	private static Gson gson = new Gson();
	public HhXtYh findLoginUser() {
		if(ActionContext.getContext()==null){
			return null;
		}
		HhXtYh hhXtYh = (HhXtYh) ActionContext.getContext().getSession()
				.get("loginuser");
		return hhXtYh;
	}

	public String findLoginUserId() {
		if(ActionContext.getContext()==null){
			return null;
		}
		Object object = ActionContext.getContext().getSession()
				.get("loginuser");
		if (object != null) {
			HhXtYh hhXtYh = (HhXtYh) object;
			return hhXtYh.getId();
		}
		return null;
	}
	
	public String findLoginUserText() {
		HhXtYh hhXtYh = findLoginUser();
		if(hhXtYh==null){
			return null;
		}else{
			return hhXtYh.getText();
		}
	}

	public String findLoginUserOrgId() {
		HhXtYh user = findLoginUser();
		if (user==null) {
			return "";
		}
		if (user.getOrganization()!=null) {
			Map<String, Organization> organization =user.getOrganization();
			if (organization.get("base")!=null) {
				return organization.get("base").getId();
			}else{
				return "";
			}
		} else {
			return "";
		}
	}

	public Map<String, Organization> findLoginUserOrg() {
		HhXtYh user = findLoginUser();
		if (user==null) {
			return null;
		}
		return user.getOrganization();
	}

	public Organization queryDataSecurityOrg(String action) {
		HhXtYh user = findLoginUser();
		if (user==null) {
			return null;
		}
		if (user.getOrganization()!=null) {
			Map<String, Organization> organizationMap =user.getOrganization();
			Organization organization =null;
			HhXtYh hhXtYh = findLoginUser();
			List<HhXtCz> hhXtCzList = hhXtYh.getHhXtCzList();
			for (HhXtCz hhXtCz : hhXtCzList) {
				if (action.equals(hhXtCz.getVurl())) {
					if (OperationLevel.BBM.toString().equals(
							hhXtCz.getOperLevel())) {
						organization = organizationMap.get("bm");
					} else if (OperationLevel.BJG.toString().equals(
							hhXtCz.getOperLevel())) {
						organization =  organizationMap.get("jg");
					} else if (OperationLevel.BJT.toString().equals(
							hhXtCz.getOperLevel())) {
						organization =  organizationMap.get("jt");
					} else if (OperationLevel.BGW.toString().equals(
							hhXtCz.getOperLevel())) {
						organization =  organizationMap.get("gw");
					} else {
						organization = null;
					}
					return organization;
				}
			}
			return null;
		} else {
			return null;
		}
	}

	public String getOperPower() {
		HhXtYh hhXtYh = findLoginUser();
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
}
