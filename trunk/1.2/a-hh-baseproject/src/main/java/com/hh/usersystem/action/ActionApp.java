package com.hh.usersystem.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.util.base.BaseAction;
import com.hh.usersystem.bean.usersystem.SysMenu;
import com.hh.usersystem.bean.usersystem.UsOrganization;
import com.hh.usersystem.bean.usersystem.UsUser;
import com.hh.usersystem.service.impl.LoginUserUtilService;
import com.hh.usersystem.service.impl.OrganizationService;

@SuppressWarnings("serial")
public class ActionApp extends BaseAction {
	@Autowired
	private LoginUserUtilService loginUserUtilService;
	private String currOrgId;
	private String node;
	private String action;
	@Autowired
	private OrganizationService organizationService;

	public Object queryCurrOrgTree() {
		List<UsOrganization> organizationList = organizationService
				.queryCurrOrgTree(node, action);
		return organizationList;
	}
	public Object queryZmtb() {
		UsUser hhXtYh = loginUserUtilService.findLoginUser();
		List<SysMenu> sysMenus = hhXtYh.getDesktopQuickList();
		return sysMenus;
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
