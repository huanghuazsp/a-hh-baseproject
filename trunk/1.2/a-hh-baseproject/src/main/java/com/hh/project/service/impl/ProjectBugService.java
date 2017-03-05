 package com.hh.project.service.impl;
import com.hh.system.service.impl.BaseService;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.MessageException;
import com.hh.system.util.dto.PageRange;
import com.hh.system.util.dto.PagingData;
import com.hh.system.util.dto.ParamFactory;
import com.hh.system.util.dto.ParamInf;
import com.hh.usersystem.bean.usersystem.UsUser;
import com.hh.usersystem.service.impl.LoginUserUtilService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.project.bean.ProjectBug;

@Service
public class ProjectBugService extends BaseService<ProjectBug> {
	@Autowired
	private LoginUserUtilService loginUserUtilService;
	
	@Autowired
	private ProjectBugLogService projectBugLogService;
	
	@Override
	public PagingData<ProjectBug> queryPagingData(ProjectBug entity, PageRange pageRange) {
		ParamInf hqlParamList = ParamFactory.getParamHb();
		
		if (Check.isNoEmpty(entity.getText())) {
			hqlParamList.like("text", entity.getText());
		}
		
		if (Check.isNoEmpty(entity.getProjectId())) {
			hqlParamList.is("projectId", entity.getProjectId());
		}

		if (Check.isNoEmpty(entity.getModularId())) {
			hqlParamList.is("modularId", entity.getModularId());
		}
		
		if (Check.isNoEmpty(entity.getFindUserText())) {
			hqlParamList.like("findUserText", entity.getFindUserText());
		}
		
		if (Check.isNoEmpty(entity.getProcessingPeopleText())) {
			hqlParamList.like("processingPeopleText", entity.getProcessingPeopleText());
		}
		
		if (Check.isNoEmpty(entity.getSolveUserText())) {
			hqlParamList.like("solveUserText", entity.getSolveUserText());
		}
		
		if (Check.isNoEmpty(entity.getCloseUserText())) {
			hqlParamList.like("closeUserText", entity.getCloseUserText());
		}
		
		if (entity.getState()!=99) {
			if (entity.getState()==90) {
				hqlParamList.nis("state", 9);
			}else if (entity.getState()==91) {
				hqlParamList.in("state", new Integer[]{0,2});
			}else{
				hqlParamList.is("state", entity.getState());
			}
		}
		return super.queryPagingData(pageRange, hqlParamList);
	}

	public void updateState(ProjectBug object) {
		int state = object.getState();
		String describe = object.getDescribe_();
		object = findObjectById(object.getId());
		object.setState(state);
		UsUser user = loginUserUtilService.findLoginUser();
		if (state==1) {
			object.setSolveUser(user.getId());
			object.setSolveUserText(user.getText());
		}else if (state==2) {
			object.setFindUser(user.getId());
			object.setFindUserText(user.getText());
		}else if (state==9) {
			object.setCloseUser(user.getId());
			object.setCloseUserText(user.getText());
		}
		updateEntity(object);
		
		projectBugLogService.saveLog(object.getId(), object.getState(), describe);
	}

	@Override
	public ProjectBug save(ProjectBug entity) throws MessageException {
		UsUser user = loginUserUtilService.findLoginUser();
		entity.setFindUser(user.getId());
		entity.setFindUserText(user.getText());
		return super.save(entity);
	}

	@Override
	public void deleteByIds(String ids) {
		// TODO Auto-generated method stub
		super.deleteByIds(ids);
		
		projectBugLogService.deleteByProperty("bugId", Convert.strToList( ids));
	}
	
	
	
}
 