package com.hh.usersystem.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.service.impl.BaseService;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.MessageException;
import com.hh.system.util.base.BaseServiceAction;
import com.hh.system.util.dto.ParamFactory;
import com.hh.system.util.model.ReturnModel;
import com.hh.system.util.statics.StaticVar;
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

	public Object queryPagingData() {
		return organizationService.queryPagingData(object,
				this.getPageRange());
	}

	public Object queryOrgListByPidAndLx() {
		List<Organization> organizationList = organizationService
				.queryOrgListByPidAndLx(object, paramsMap.get("node"));
		return organizationList;
	}

	public Object queryOrgAndUsersList() {
		return organizationService.queryOrgAndUsersList(object);
	}

	public Object queryOrgListByPid() {
		List<Organization> organizationList = organizationService
				.queryOrgListByPid(object, orgs, selectType);
		return organizationList;
	}

	public Object findObjectById() {
		Organization hhXtCd = organizationService.findObjectById(this.object
				.getId());
		return hhXtCd;
	}

	public void deleteByIds() {
		organizationService.deleteByIds(this.getIds());
	}

	@Override
	public Object queryTreeList() {
		return organizationService.queryTreeList(object,
				Convert.toBoolean(request.getParameter("isNoLeaf")));
	}

	public Object queryTreeListByLx() {
		return organizationService.queryTreeListByLx(object,
				Convert.toBoolean(request.getParameter("isNoLeaf")));
	}

	public Object save() {
		try {
			Organization hhXtYh = organizationService.save(this.object);
			return null;
		} catch (MessageException e) {
			return new ReturnModel(e.getMessage());
		}
		
	}

	public Object findOrgTextByIds() {
		String returnTextString = "";
		if (Check.isNoEmpty(object.getId())) {
			StringBuffer texts = new StringBuffer();
			List<Organization> organizationList = organizationService
					.queryList(ParamFactory.getParamHb().in(StaticVar.entityId,
							Convert.strToList(object.getId())));
			for (Organization organization : organizationList) {
				texts.append(organization.getText() + ",");
			}
			if (Check.isNoEmpty(texts)) {
				returnTextString = texts.substring(0, texts.length() - 1);
			}
		}
		Map<String, String> returnMap = new HashMap<String, String>();
		returnMap.put("text", returnTextString);
		returnMap.put("id", object.getId());
		return returnMap;
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
