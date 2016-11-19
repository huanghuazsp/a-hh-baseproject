 package com.hh.project.service.impl;
import com.hh.system.service.impl.BaseService;
import com.hh.system.util.dto.PageRange;
import com.hh.system.util.dto.PagingData;
import com.hh.system.util.dto.ParamFactory;
import com.hh.system.util.dto.ParamInf;

import org.springframework.stereotype.Service;

import com.hh.project.bean.ProjectModular;

@Service
public class ProjectModularService extends BaseService<ProjectModular> {

	@Override
	public PagingData<ProjectModular> queryPagingData(ProjectModular entity, PageRange pageRange) {
		ParamInf hqlParamList = ParamFactory.getParamHb();
		hqlParamList.is("projectId", entity.getProjectId());
		return queryPagingData(pageRange, hqlParamList);
	}
	
	
}
 