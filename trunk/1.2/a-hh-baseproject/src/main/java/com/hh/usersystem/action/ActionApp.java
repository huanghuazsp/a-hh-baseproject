package com.hh.usersystem.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.util.Convert;
import com.hh.system.util.base.BaseAction;
import com.hh.usersystem.bean.usersystem.HhXtCd;
import com.hh.usersystem.bean.usersystem.HhXtYh;
import com.hh.usersystem.bean.usersystem.Organization;
import com.hh.usersystem.service.impl.LoginService;
import com.hh.usersystem.service.impl.LoginUserUtilService;
import com.hh.usersystem.service.impl.OrganizationService;
import com.hh.usersystem.service.impl.UserService;
//import com.hh.usersystem.service.impl.ZmsxService;
import com.hh.usersystem.service.impl.ZmtbService;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
public class ActionApp extends BaseAction {
	@Autowired
	private LoginUserUtilService loginUserUtilService;
	private String currOrgId;
	@Autowired
	private UserService userService;
	private String node;
	private String action;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private ZmtbService zmtbService;

	@Autowired
	private LoginService loginService;
	
	public Object queryLoginOrgList() {
		return loginUserUtilService.findLoginUser()
				.getOrganizationList();
	}

	public Object changeOrg() {
		HhXtYh hhXtYh = loginUserUtilService.findLoginUser();
		List<Organization> organizations = hhXtYh.getOrganizationList();
		Organization organization1 = new Organization();

		for (Organization organization : organizations) {
			if (organization.getId().equals(currOrgId)) {
				hhXtYh.setOrganization(loginService.addGwJtJgBm(organization));
				ActionContext.getContext().getSession()
						.put("loginuser", hhXtYh);
				organization1 = organization;
				if ("on".equals(request.getParameter("remember")) || Convert.toInt(request.getParameter("remember"))==1) {
					userService.updateDefaultOrg(hhXtYh.getId(),
							organization1.getId());
				} else {
					userService.updateDefaultOrg(hhXtYh.getId(), "");
				}
				break;
			}
		}
		return organization1;
	}

	public Object queryCurrOrgTree() {
		List<Organization> organizationList = organizationService
				.queryCurrOrgTree(node, action);
		return organizationList;
	}
	
	public Object queryZmtb() {
		HhXtYh hhXtYh = loginUserUtilService.findLoginUser();
		return zmtbService.queryZmtbByUserId(hhXtYh.getId());
	}

	public String getCurrOrgId() {
		return currOrgId;
	}

	public void setCurrOrgId(String currOrgId) {
		this.currOrgId = currOrgId;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
