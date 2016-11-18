 package com.hh.project.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.util.base.BaseServiceAction;
import com.hh.project.bean.ProjectModular;
import com.hh.system.service.impl.BaseService;
import com.hh.project.service.impl.ProjectModularService;

@SuppressWarnings("serial")
public class ActionProjectModular extends BaseServiceAction< ProjectModular > {
	@Autowired
	private ProjectModularService projectprojectmodularService;
	public BaseService<ProjectModular> getService() {
		return projectprojectmodularService;
	}
}
 