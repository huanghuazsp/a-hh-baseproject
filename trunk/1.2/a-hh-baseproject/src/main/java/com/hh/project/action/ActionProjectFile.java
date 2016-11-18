 package com.hh.project.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.util.base.BaseServiceAction;
import com.hh.project.bean.ProjectFile;
import com.hh.system.service.impl.BaseService;
import com.hh.project.service.impl.ProjectFileService;

@SuppressWarnings("serial")
public class ActionProjectFile extends BaseServiceAction< ProjectFile > {
	@Autowired
	private ProjectFileService projectprojectfileService;
	public BaseService<ProjectFile> getService() {
		return projectprojectfileService;
	}
}
 