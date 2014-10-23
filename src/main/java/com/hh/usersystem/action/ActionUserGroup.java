package com.hh.usersystem.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.service.impl.BaseService;
import com.hh.system.util.base.BaseServiceAction;
import com.hh.usersystem.bean.usersystem.UserGroup;
import com.hh.usersystem.service.impl.UserGroupService;

@SuppressWarnings("serial")
public class ActionUserGroup extends BaseServiceAction<UserGroup> {
	
	@Autowired
	private UserGroupService service;
	public BaseService<UserGroup> getService() {
		return service;
	}
	

}
