 package com.hh.project.action;

import java.util.List;

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
	
	
	public Object queryTreeList() {
		ParamInf hqlParamList = convertTreeParams();
		hqlParamList.is("projectId", object.getProjectId());
		List<ProjectModular> projectModularList = getService().queryTreeList(hqlParamList);
		render(projectModularList);
		return projectModularList;
	}
	
	
	public Object queryTreeListSelect() {
		ParamInf hqlParamList = convertTreeParams();
		hqlParamList.is("projectId", object.getProjectId());
		List<ProjectModular> projectModularList = getService().queryTreeList(hqlParamList);
		return projectModularList;
	}
	
	
	private void render(List<ProjectModular> resourcesTypes) {
		if (resourcesTypes != null) {
			for (ProjectModular projectModular : resourcesTypes) {
				if (projectModular.getType() == 1) {
					projectModular.setIconSkin("folder");
				}else{
					projectModular.setIconSkin("task");
				}
				render(projectModular.getChildren());
			}
		}
	}
}
 