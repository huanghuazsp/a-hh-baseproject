package com.hh.usersystem.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.service.impl.BaseService;
import com.hh.system.service.impl.BaseTreeService;
import com.hh.system.util.Convert;
import com.hh.system.util.MessageException;
import com.hh.system.util.base.BaseServiceAction;
import com.hh.system.util.model.ReturnModel;
import com.hh.usersystem.bean.usersystem.Organization;
import com.hh.usersystem.service.impl.OrganizationService;

@SuppressWarnings("serial")
public class ActionOrg extends BaseServiceAction<Organization> {
	private String orgs;
	private String selectType;

	@Autowired
	private OrganizationService organizationService;

	@Override
	public BaseService<Organization> getService() {
		return organizationService;
	}

	public void queryPagingData() {
		this.returnResult(organizationService.queryPagingData(object,
				this.getPageRange()));
	}

	public void queryOrgListByPidAndLx() {
		List<Organization> organizationList = organizationService
				.queryOrgListByPidAndLx(object, paramsMap.get("node"));
		this.returnResult(organizationList);
	}

	public void queryOrgAndUsersList() {
		this.returnResult(organizationService.queryOrgAndUsersList(object));
	}

	public void queryOrgListByPid() {
		List<Organization> organizationList = organizationService
				.queryOrgListByPid(object, orgs, selectType);
		this.returnResult(organizationList);
	}

	public void findObjectById() {
		Organization hhXtCd = organizationService.findObjectById(this.object
				.getId());
		this.returnResult(hhXtCd);
	}

	public void deleteByIds() {
		organizationService.deleteByIds(this.getIds());
	}

	@Override
	public void queryTreeList() {
		this.returnResult(organizationService.queryTreeList(object,Convert.toBoolean(request.getParameter("isNoLeaf"))));
	}
	
	public void queryTreeListByLx() {
		this.returnResult(organizationService.queryTreeListByLx(object,Convert.toBoolean(request.getParameter("isNoLeaf"))));
	}

	public void save() {
		try {
			Organization hhXtYh = organizationService.save(this.object);
			this.getResultMap().put("object", hhXtYh);
		} catch (MessageException e) {
			this.getResultMap().put("returnModel",
					new ReturnModel(e.getMessage()));
		}
		this.returnResult();
	}

	public String getOrgs() {
		return orgs;
	}

	public void setOrgs(String orgs) {
		this.orgs = orgs;
	}

	public String getSelectType() {
		return selectType;
	}

	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}

}
