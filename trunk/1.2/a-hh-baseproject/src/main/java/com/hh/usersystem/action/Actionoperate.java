package com.hh.usersystem.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.util.base.BaseAction;
import com.hh.system.util.model.ExtCheckTree;
import com.hh.system.util.model.ExtTree;
import com.hh.system.util.model.ReturnModel;
import com.hh.usersystem.bean.usersystem.HhXtCz;
import com.hh.usersystem.service.impl.OperateService;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class Actionoperate extends BaseAction implements ModelDriven<HhXtCz> {
	private HhXtCz hhXtCz = new HhXtCz();
	private String node;
	private String roleid;
	@Autowired
	private OperateService operateService;

	public HhXtCz getModel() {
		return hhXtCz;
	}

	public void queryOperateListByPid() {
		List<ExtTree> hhxtczList = operateService.queryOperateListByPid(hhXtCz
				.getVpid());
		this.returnResult(hhxtczList);
	}

	public void queryCheckOperateListByPid() {
		List<ExtCheckTree> hhxtczList = operateService
				.queryCheckOperateListByPid(hhXtCz.getVpid(), roleid, node);
		this.returnResult(hhxtczList);
	}

	public void findObjectById() {
		HhXtCz hhXtCz = operateService.findObjectById(this.hhXtCz.getId());
		this.returnResult(hhXtCz);
	}

	public void save() {
		HhXtCz hhXtCz = operateService.save(this.hhXtCz);
		this.getResultMap().put("object", hhXtCz);
		this.returnResult();
	}

	public void deleteByIds() {
		operateService.deleteByIds(this.getIds());
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

}
