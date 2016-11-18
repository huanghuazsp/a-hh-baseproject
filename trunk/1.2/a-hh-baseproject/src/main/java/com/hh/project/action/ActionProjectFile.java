 package com.hh.project.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.util.base.BaseServiceAction;
import com.hh.project.bean.ProjectProjectFile;
import com.hh.system.service.impl.BaseService;
import com.hh.project.service.impl.ProjectProjectFileService;

@SuppressWarnings("serial")
public class ActionProjectFile extends BaseServiceAction< ProjectProjectFile > {
	@Autowired
	private ProjectProjectFileService projectprojectfileService;
	public BaseService<ProjectProjectFile> getService() {
		return projectprojectfileService;
	}
}
 