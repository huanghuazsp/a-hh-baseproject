package com.hh.usersystem.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.service.impl.BaseService;
import com.hh.system.util.base.BaseServiceAction;
import com.hh.usersystem.bean.usersystem.UsGroup;
import com.hh.usersystem.service.impl.UserGroupService;

@SuppressWarnings("serial")
public class ActionUserGroup extends BaseServiceAction<UsGroup> {
	
	@Autowired
	private UserGroupService service;
	public BaseService<UsGroup> getService() {
		return service;
	}
	

}
