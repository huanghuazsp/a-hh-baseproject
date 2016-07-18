 package com.hh.system.service.impl;
import com.hh.system.service.impl.BaseService;
import com.hh.system.util.dto.ParamFactory;
import com.hh.usersystem.LoginUserServiceInf;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.system.bean.SystemResourcesType;

@Service
public class SystemResourcesTypeService extends BaseService<SystemResourcesType> {

	@Autowired
	private LoginUserServiceInf userService;
	@Override
	public List<SystemResourcesType> queryTreeList(String node) {
		return super.queryTreeList(node,ParamFactory.getParamHb().is("vcreate", userService.findUserId()));
	}
	
	
}
 