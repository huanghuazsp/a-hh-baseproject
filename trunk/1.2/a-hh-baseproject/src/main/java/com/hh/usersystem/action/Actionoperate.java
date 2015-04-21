package com.hh.usersystem.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.util.base.BaseServiceAction;
import com.hh.system.util.model.ExtCheckTree;
import com.hh.system.util.model.ExtTree;
import com.hh.usersystem.bean.usersystem.HhXtCz;
import com.hh.usersystem.service.impl.OperateService;

@SuppressWarnings("serial")
public class Actionoperate extends BaseServiceAction<HhXtCz> {
	private String roleid;
	@Autowired
	private OperateService operateService;

	public void queryOperateListByPid() {
		List<ExtTree> hhxtczList = operateService.queryOperateListByPid(object
				.getVpid());
		this.returnResult(hhxtczList);
	}

	public void queryCheckOperateListByPid() {
		System.out.println(request.getParameter("node"));
		List<ExtCheckTree> hhxtczList = operateService
				.queryCheckOperateListByPid(object.getVpid(), roleid,
						findParam("node"));
		this.returnResult(hhxtczList);
	}

	public void findObjectById() {
		HhXtCz hhXtCz = operateService.findObjectById(this.object.getId());
		this.returnResult(hhXtCz);
	}

	public void save() {
		HhXtCz hhXtCz = operateService.save(this.object);
		this.getResultMap().put("object", hhXtCz);
		this.returnResult();
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
