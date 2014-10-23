package com.hh.usersystem.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.service.impl.BaseService;
import com.hh.system.util.MessageException;
import com.hh.system.util.base.BaseServiceAction;
import com.hh.system.util.dto.ExtCheckTree;
import com.hh.system.util.model.ReturnModel;
import com.hh.usersystem.bean.usersystem.HhXtCd;
import com.hh.usersystem.service.impl.MenuService;
import com.hh.usersystem.util.steady.StaticProperties;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class Actionmenu extends BaseServiceAction<HhXtCd> {
	private String roleid;
	@Autowired
	private MenuService menuService;
	@Override
	public BaseService<HhXtCd> getService() {
		return menuService;
	}

	public void queryPagingData() {
		this.returnResult(menuService.queryPagingData(this.object,
				this.getPageRange()));
	}

	public void findObjectById() {
		HhXtCd hhXtCd = menuService.findObjectById(this.object.getId());
		this.returnResult(hhXtCd);
	}

	public void queryMenuListByPid() {
		List<HhXtCd> hhxtcdList = menuService.queryMenuListByPid(this.object
				.getNode());
		this.returnResult(hhxtcdList);
	}
	
	public void queryAllMenuList() {
		this.returnResult(StaticProperties.hhXtCds);
	}

	public void queryMenuAllListByPid() {
		List<ExtCheckTree> hhxtcdList = menuService.queryMenuAllListByPid(
				this.object.getNode(), roleid);
		this.returnResult(hhxtcdList);
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
