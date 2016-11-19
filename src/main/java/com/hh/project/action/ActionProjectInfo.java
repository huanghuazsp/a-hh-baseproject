 package com.hh.project.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.util.base.BaseServiceAction;
import com.hh.project.bean.ProjectInfo;
import com.hh.system.service.impl.BaseService;
import com.hh.project.service.impl.ProjectInfoService;

@SuppressWarnings("serial")
public class ActionProjectInfo extends BaseServiceAction< ProjectInfo > {
	@Autowired
	private ProjectInfoService projectprojectinfoService;
	public BaseService<ProjectInfo> getService() {
		return projectprojectinfoService;
	}
	
	
	public Object queryPartPage() {
		return projectprojectinfoService.queryPartPage(object,
				this.getPageRange());
	}
}
 