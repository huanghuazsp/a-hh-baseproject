 package com.hh.project.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.util.base.BaseServiceAction;
import com.hh.project.bean.ProjectProjectUserInfo;
import com.hh.system.service.impl.BaseService;
import com.hh.project.service.impl.ProjectProjectUserInfoService;

@SuppressWarnings("serial")
public class ActionProjectUserInfo extends BaseServiceAction< ProjectProjectUserInfo > {
	@Autowired
	private ProjectProjectUserInfoService projectprojectuserinfoService;
	public BaseService<ProjectProjectUserInfo> getService() {
		return projectprojectuserinfoService;
	}
}
 