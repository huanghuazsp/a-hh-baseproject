package com.hh.usersystem.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.service.impl.BaseService;
import com.hh.system.util.MessageException;
import com.hh.system.util.base.BaseServiceAction;
import com.hh.system.util.model.ExtCheckTree;
import com.hh.system.util.model.ReturnModel;
import com.hh.usersystem.bean.usersystem.SysMenu;
import com.hh.usersystem.service.impl.MenuService;
import com.hh.usersystem.util.steady.StaticProperties;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class Actionmenu extends BaseServiceAction<SysMenu> {
	private String roleid;
	@Autowired
	private MenuService menuService;
	@Override
	public BaseService<SysMenu> getService() {
		return menuService;
	}

	public Object queryPagingData() {
		return menuService.queryPagingData(this.object,
				this.getPageRange());
	}

	public Object findObjectById() {
		SysMenu hhXtCd = menuService.findObjectById(this.object.getId());
		return hhXtCd;
	}

	public Object queryMenuListByPid() {
		List<SysMenu> menuList = menuService.queryMenuListByPid(this.object
				.getNode());
		return menuList;
	}
	
	public Object queryAllMenuList() {
		return StaticProperties.sysMenuList;
	}

	public Object queryMenuAllListByPid() {
		List<ExtCheckTree> menuList = menuService.queryMenuAllListByPid(
				this.object.getNode(), roleid);
		return menuList;
	}

	public void deleteByIds() {
		menuService.deleteByIds(this.getIds());
	}

	public void addZmtb() {
		menuService.addZmtb(this.object.getId());
	}

	public void deleteZmtb() {
		menuService.deleteZmtb(this.object.getId());
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

}
