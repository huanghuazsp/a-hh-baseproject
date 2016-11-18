 package com.hh.project.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.util.base.BaseServiceAction;
import com.hh.project.bean.ProjectUserInfo;
import com.hh.system.service.impl.BaseService;
import com.hh.project.service.impl.ProjectUserInfoService;

@SuppressWarnings("serial")
public class ActionProjectUserInfo extends BaseServiceAction< ProjectUserInfo > {
	@Autowired
	private ProjectUserInfoService projectprojectuserinfoService;
	public BaseService<ProjectUserInfo> getService() {
		return projectprojectuserinfoService;
	}
}
 