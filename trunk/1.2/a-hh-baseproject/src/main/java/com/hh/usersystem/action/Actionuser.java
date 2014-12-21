package com.hh.usersystem.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.hh.hibernate.util.dto.HQLParamList;
import com.hh.system.service.impl.BaseService;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.MessageException;
import com.hh.system.util.base.BaseServiceAction;
import com.hh.system.util.model.ExtTree;
import com.hh.system.util.model.ReturnModel;
import com.hh.system.util.statics.StaticVar;
import com.hh.usersystem.bean.usersystem.HhXtYh;
import com.hh.usersystem.service.impl.UserService;

@SuppressWarnings("serial")
public class Actionuser extends BaseServiceAction<HhXtYh> {
	private String orgs;
	private String roles;
	private String groups;
	@Autowired
	private UserService userService;
	private String oldPassword;

	@Override
	public BaseService<HhXtYh> getService() {
		return userService;
	}

	public void queryPagingData() {
		this.returnResult(userService.queryPagingData(object,
				this.getPageRange(),this.getIds(), orgs, roles,groups));
	}

	public void queryPagingDataCombox() {
		if (Check.isNoEmpty(this.object.getId())) {
			this.returnResult(userService.queryItemsByIdsStr(object.getId()));
		} else {
			this.returnResult(userService.queryPagingData(object,
					this.getPageRange(), null,null, null, null));
		}
	}

	public void queryPagingDataList() {
		this.returnResult(userService.queryPagingData(object,
				this.getPageRange(),null, null, null, null));
	}

	public void queryOnLinePagingData() {
		this.getResultMap().putAll(
				userService.queryOnLinePagingData(object, this.getPageRange()));
		this.returnResult();
	}

	public void deleteOnLineByIds() {
		userService.deleteOnLineByIds(this.getIds());
	}

	public void save() {
		try {
			HhXtYh hhXtYh = userService.save(this.object);
			this.getResultMap().put("object", hhXtYh);
		} catch (MessageException e) {
			this.getResultMap().put("returnModel",
					new ReturnModel(e.getMessage()));
		}
		this.returnResult();
	}
	
	public void save2() {
		try {
			HhXtYh hhXtYh = userService.save2(this.object);
			this.getResultMap().put("object", hhXtYh);
		} catch (MessageException e) {
			this.getResultMap().put("returnModel",
					new ReturnModel(e.getMessage()));
		}
		this.returnResult();
	}

	public void updatePassWord() {
		try {
			userService.updatePassWord(this.object, oldPassword);
		} catch (MessageException e) {
			this.getResultMap().put("returnModel",
					new ReturnModel(e.getMessage()));
		}
		this.returnResult();
	}

	public void vdlzhOnly() {
		boolean result = userService.checkVdlzhOnly(object);
		this.returnResult(result);
	}

	public void findObjectById() {
		HhXtYh hhXtYh = userService.findObjectById(this.object.getId());
		this.returnResult(hhXtYh);
	}
	public void findObjectById2() {
		HhXtYh hhXtYh = userService.findObjectById_user(this.object.getId());
		this.returnResult(hhXtYh);
	}

	public void deleteByIds() {
		userService.deleteByIds(this.getIds());
	}

	public void insertYhOrg() {
		userService.insertYhOrg(this.getParamsMap());
	}

	public void deleteYhOrg() {
		userService.deleteYhOrg(this.getParamsMap());
	}

	public void queryCylxrs() {
		List<HhXtYh> hhXtYhs = userService.queryCylxrs();
		this.returnResult(hhXtYhs);
	}

	public void queryCylxrTree() {
		List<ExtTree> hhXtYhs = userService.queryCylxrTree();
		this.returnResult(hhXtYhs);
	}

	public void addCylxr() {
		try {
			userService.addCylxr(this.getParamsMap().get("cylxrid"));
		} catch (MessageException e) {
			this.getResultMap().put("returnModel",
					new ReturnModel(e.getMessage()));
		}
		this.returnResult();
	}

	public void deleteCylxr() {
		try {
			userService.deleteCylxr(this.getParamsMap().get("cylxrid"));
		} catch (MessageException e) {
			this.getResultMap().put("returnModel",
					new ReturnModel(e.getMessage()));
		}
		this.returnResult();
	}

	public void queryUserByOrcCode() {
		List<HhXtYh> hhXtYhList = userService.queryUserByOrcCode(request
				.getParameter("code"));
		this.returnResult(hhXtYhList);
	}

	public void queryUserByRole() {
		List<HhXtYh> hhXtYhList = userService.queryUserByRole(request
				.getParameter("roleId"));
		this.returnResult(hhXtYhList);
	}

	public void queryUserByGroup() {
		List<HhXtYh> hhXtYhList = userService.queryUserByGroup(request
				.getParameter("groupId"));
		this.returnResult(hhXtYhList);
	}
	
	public void findUserTextByIds() {
		String returnTextString = "";
		if (Check.isNoEmpty(object.getId())) {
			StringBuffer texts = new StringBuffer();
			List<HhXtYh> hhXtYhList =	userService.queryList(Restrictions.in(StaticVar.id_r, Convert.strToList(object.getId())));
			for (HhXtYh hhXtYh : hhXtYhList) {
				texts.append(hhXtYh.getText()+",");
			}
			if (Check.isNoEmpty(texts)) {
				returnTextString=texts.substring(0,texts.length()-1);
			}
		}
		Map<String, String> returnMap = new HashMap<String, String>();
		returnMap.put("text", returnTextString);
		returnMap.put("id", object.getId());
		 this.returnResult(returnMap);
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getOrgs() {
		return orgs;
	}

	public void setOrgs(String orgs) {
		this.orgs = orgs;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}

}
