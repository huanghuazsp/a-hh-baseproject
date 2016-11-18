 package com.hh.project.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.util.base.BaseServiceAction;
import com.hh.project.bean.ProjectProjectInfo;
import com.hh.system.service.impl.BaseService;
import com.hh.project.service.impl.ProjectProjectInfoService;

@SuppressWarnings("serial")
public class ActionProjectInfo extends BaseServiceAction< ProjectProjectInfo > {
	@Autowired
	private ProjectProjectInfoService projectprojectinfoService;
	public BaseService<ProjectProjectInfo> getService() {
		return projectprojectinfoService;
	}
}
 