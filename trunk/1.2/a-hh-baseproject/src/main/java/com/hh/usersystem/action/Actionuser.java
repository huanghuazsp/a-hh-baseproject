package com.hh.usersystem.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.service.impl.BaseService;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.MessageException;
import com.hh.system.util.base.BaseServiceAction;
import com.hh.system.util.dto.ParamFactory;
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

	public Object queryPagingData() {
		return userService.queryPagingData(object,
				this.getPageRange(), this.getIds(), orgs, roles, groups);
	}

	public Object queryPagingDataCombox() {
		if (Check.isNoEmpty(this.object.getId())) {
			return userService.queryItemsByIdsStr(object.getId());
		} else {
			return userService.queryPagingData(object,
					this.getPageRange(), null, null, null, null);
		}
	}

	public Object queryPagingDataList() {
		return userService.queryPagingData(object,
				this.getPageRange(), null, null, null, null);
	}

	public Object queryOnLinePagingData() {
		return userService.queryOnLinePagingData(object, this.getPageRange());
	}

	public void deleteOnLineByIds() {
		userService.deleteOnLineByIds(this.getIds());
	}

	public Object save() {
		try {
			HhXtYh hhXtYh = userService.save(this.object);
			return null;
		} catch (MessageException e) {
			return e;
		}
	}

	public Object save2() {
		try {
			HhXtYh hhXtYh = userService.save2(this.object);
			return null;
		} catch (MessageException e) {
			return e;
		}
	}

	public Object updatePassWord() {
		try {
			userService.updatePassWord(this.object, oldPassword);
			return null;
		} catch (MessageException e) {
			return e;
		}
	}

	public Object vdlzhOnly() {
		boolean result = userService.checkVdlzhOnly(object);
		return result;
	}

	public Object findObjectById() {
		HhXtYh hhXtYh = userService.findObjectById(this.object.getId());
		return hhXtYh;
	}

	public Object findObjectById2() {
		HhXtYh hhXtYh = userService.findObjectById_user(this.object.getId());
		return hhXtYh;
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

	public Object queryCylxrs() {
		List<HhXtYh> hhXtYhs = userService.queryCylxrs();
		return hhXtYhs;
	}

	public Object queryCylxrTree() {
		List<ExtTree> hhXtYhs = userService.queryCylxrTree();
		return hhXtYhs;
	}

	public Object addCylxr() {
		try {
			userService.addCylxr(this.getParamsMap().get("cylxrid"));
			return null;
		} catch (MessageException e) {
			return e;
		}
	}

	public Object deleteCylxr() {
		try {
			userService.deleteCylxr(this.getParamsMap().get("cylxrid"));
			return null;
		} catch (MessageException e) {
			return e;
		}
	}

	public Object queryUserByOrcCode() {
		List<HhXtYh> hhXtYhList = userService.queryUserByOrcCode(request
				.getParameter("code"));
		return hhXtYhList;
	}

	public Object queryUserByRole() {
		List<HhXtYh> hhXtYhList = userService.queryUserByRole(request
				.getParameter("roleId"));
		return hhXtYhList;
	}

	public Object queryUserByGroup() {
		List<HhXtYh> hhXtYhList = userService.queryUserByGroup(request
				.getParameter("groupId"));
		return hhXtYhList;
	}

	public Object findUserTextByIds() {
		String returnTextString = "";
		if (Check.isNoEmpty(object.getId())) {
			StringBuffer texts = new StringBuffer();

			List<HhXtYh> hhXtYhList = userService.queryList(ParamFactory
					.getParamHb().in(StaticVar.entityId,
							Convert.strToList(object.getId())));
			for (HhXtYh hhXtYh : hhXtYhList) {
				texts.append(hhXtYh.getText() + ",");
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

	public Object findUserListByIds() {
		List<HhXtYh> hhXtYhList = userService.queryList(ParamFactory
				.getParamHb().in(StaticVar.entityId,
						Convert.strToList(object.getId())));
		return hhXtYhList;
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
