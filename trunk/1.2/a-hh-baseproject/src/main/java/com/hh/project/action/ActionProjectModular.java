 package com.hh.project.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.util.base.BaseServiceAction;
import com.hh.project.bean.ProjectProjectModular;
import com.hh.system.service.impl.BaseService;
import com.hh.project.service.impl.ProjectProjectModularService;

@SuppressWarnings("serial")
public class ActionProjectModular extends BaseServiceAction< ProjectProjectModular > {
	@Autowired
	private ProjectProjectModularService projectprojectmodularService;
	public BaseService<ProjectProjectModular> getService() {
		return projectprojectmodularService;
	}
}
 