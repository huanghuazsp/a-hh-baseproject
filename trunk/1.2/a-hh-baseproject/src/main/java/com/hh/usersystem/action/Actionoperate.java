package com.hh.usersystem.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.util.base.BaseServiceAction;
import com.hh.system.util.model.ExtCheckTree;
import com.hh.system.util.model.ExtTree;
import com.hh.usersystem.bean.usersystem.SysOper;
import com.hh.usersystem.service.impl.OperateService;

@SuppressWarnings("serial")
public class Actionoperate extends BaseServiceAction<SysOper> {
	private String roleid;
	@Autowired
	private OperateService operateService;

	public Object queryOperateListByPid() {
		List<ExtTree> hhxtczList = operateService.queryOperateListByPid(object.getMenuId());
		return hhxtczList;
	}

	public Object queryCheckOperateListByPid() {
		System.out.println(request.getParameter("node"));
		List<ExtCheckTree> hhxtczList = operateService
				.queryCheckOperateListByPid(object.getMenuId(), roleid,
						findParam("node"));
		return hhxtczList;
	}

	public Object findObjectById() {
		SysOper hhXtCz = operateService.findObjectById(this.object.getId());
		return hhXtCz;
	}

	public Object save() {
		SysOper hhXtCz = operateService.save(this.object);
		return null;
	}

	public void deleteByIds() {
		operateService.deleteByIds(this.getIds());
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

}
