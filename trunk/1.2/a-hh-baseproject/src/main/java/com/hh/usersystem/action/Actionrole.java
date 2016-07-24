package com.hh.usersystem.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.service.impl.BaseService;
import com.hh.system.util.base.BaseServiceAction;
import com.hh.usersystem.bean.usersystem.UsRole;
import com.hh.usersystem.service.impl.RoleService;

@SuppressWarnings("serial")
public class Actionrole  extends BaseServiceAction<UsRole> {
	private String roles;
	private String menuIds;
	
	private String czid_operLevel;
	
	@Autowired
	private RoleService roleService;
	public BaseService<UsRole> getService() {
		return roleService;
	}

	public Object queryAllRoleList() {
		List<UsRole> roleList = roleService.queryAllRoleList();
		return roleList;
	}

	public Object queryPagingData() {
		return roleService.queryPagingData(this.object, roles, this.getPageRange());
	}

	public void deleteByIds() {
		roleService.deleteByIds(this.getIds());
	}

	public void insertOrdeleteMenu() {
		roleService.insertOrdeleteMenu(this.getParamsMap());
	}
	
	public void saveJsMenu() {
		roleService.saveJsMenu(this.object,menuIds);
	}
	
	public void saveJsOper() {
		roleService.saveJsOper(menuIds,roles,czid_operLevel);
	}

	public void insertOrdeleteOperate() {
		roleService.insertOrdeleteOperate(this.getParamsMap());
	}
	
	public void updateOperateLevel() {
		roleService.updateOperateLevel(this.getParamsMap());
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}


	public String getCzid_operLevel() {
		return czid_operLevel;
	}

	public void setCzid_operLevel(String czid_operLevel) {
		this.czid_operLevel = czid_operLevel;
	}

	
}
