 package com.hh.project.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.util.base.BaseServiceAction;
import com.hh.project.bean.ProjectBug;
import com.hh.system.service.impl.BaseService;
import com.hh.project.service.impl.ProjectBugService;

@SuppressWarnings("serial")
public class ActionBug extends BaseServiceAction< ProjectBug > {
	@Autowired
	private ProjectBugService projectbugService;
	public BaseService<ProjectBug> getService() {
		return projectbugService;
	}
	
	public void updateState() {
		projectbugService.updateState(object);
	}
	
}
 