package com.hh.project.action;
import org.springframework.beans.factory.annotation.Autowired;
import com.hh.system.util.base.BaseServiceAction;
import com.hh.system.util.dto.ParamFactory;
import com.hh.system.util.dto.ParamInf;
import com.hh.project.bean.ProjectBugLog;
import com.hh.system.service.impl.BaseService;
import com.hh.project.service.impl.ProjectBugLogService;

@SuppressWarnings("serial")
public class ActionBugLog extends BaseServiceAction< ProjectBugLog > {
	@Autowired
	private ProjectBugLogService projectbuglogService;
	public BaseService<ProjectBugLog> getService() {
		return projectbuglogService;
	}
	
	
	public Object queryList() {
		ParamInf hqlParamList = ParamFactory.getParamHb();
		hqlParamList.is("bugId", object.getBugId());
		return projectbuglogService.queryList(hqlParamList);
	}
	
}
 