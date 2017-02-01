 package com.hh.project.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.project.bean.ProjectModular;
import com.hh.project.service.impl.ProjectModularService;
import com.hh.system.service.impl.BaseService;
import com.hh.system.util.base.BaseServiceAction;
import com.hh.system.util.dto.ParamInf;

@SuppressWarnings("serial")
public class ActionProjectModular extends BaseServiceAction< ProjectModular > {
	@Autowired
	private ProjectModularService projectprojectmodularService;
	public BaseService<ProjectModular> getService() {
		return projectprojectmodularService;
	}
	
	public Object queryListByProjectId() {
		return projectprojectmodularService.queryListByProjectId(object.getProjectId());
	}
	
	public Object queryTreeList() {
		ParamInf hqlParamList = convertTreeParams();
		hqlParamList.is("projectId", object.getProjectId());
		return getService().queryTreeList(hqlParamList);
	}
}
 