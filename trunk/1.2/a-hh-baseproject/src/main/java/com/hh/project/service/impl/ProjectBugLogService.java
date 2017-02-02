package com.hh.project.service.impl;
import com.hh.system.service.impl.BaseService;
import org.springframework.stereotype.Service;
import com.hh.project.bean.ProjectBugLog;

@Service
public class ProjectBugLogService extends BaseService<ProjectBugLog> {
	
	public void saveLog(String bugId,int type,String describe_){
		ProjectBugLog entity = new ProjectBugLog();
		entity.setBugId(bugId);
		entity.setType(type);
		entity.setDescribe_(describe_);
		save(entity);
	}
}
 