package com.hh.project.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.project.bean.ProjectInfo;
import com.hh.project.bean.ProjectUserInfo;
import com.hh.system.service.impl.BaseService;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.dto.PageRange;
import com.hh.system.util.dto.PagingData;
import com.hh.system.util.dto.ParamFactory;
import com.hh.system.util.dto.ParamInf;
import com.hh.usersystem.service.impl.LoginUserUtilService;

@Service
public class ProjectInfoService extends BaseService<ProjectInfo> {

	@Autowired
	private LoginUserUtilService loginUserUtilService;
	@Autowired
	private ProjectFileService projectFileService;
	@Autowired
	private ProjectModularService projectModularService;
	@Autowired
	private ProjectUserInfoService projectUserInfoService;

	@Override
	public void deleteByIds(String ids) {
		projectFileService.deleteByProperty("projectId", Convert.strToList(ids));
		projectModularService.deleteByProperty("projectId", Convert.strToList(ids));
		projectUserInfoService.deleteByProperty("projectId", Convert.strToList(ids));
		super.deleteByIds(ids);
	}

	@Override
	public PagingData<ProjectInfo> queryPagingData(ProjectInfo entity, PageRange pageRange) {
		ParamInf hqlParamList = ParamFactory.getParamHb();
		hqlParamList.is("vcreate", loginUserUtilService.findUserId());
		hqlParamList.is("manager", loginUserUtilService.findUserId());

		ParamInf hqlParamList2 = ParamFactory.getParamHb().or(hqlParamList);
		if (Check.isNoEmpty(entity.getText())) {
			hqlParamList2.like("text", entity.getText());
		}
		return queryPagingData(pageRange, hqlParamList2);
	}

	public PagingData<Map<String, Object>> queryPartPage(ProjectInfo object, PageRange pageRange) {
		String userId = loginUserUtilService.findUserId();

		String hql = "select a.text as text,a.id as id, a.startDate  as startDate, a.managerText  as managerText"
				+ ", a.client as client " + ", a.money as money, a.vcreate as vcreate, a.manager as manager from "
				+ ProjectInfo.class.getName() + " a , " + ProjectUserInfo.class.getName()
				+ " b  where  a.id=b.projectId and b.user=:userId ";
		String hqlCount = "select count(b) from " + ProjectInfo.class.getName() + " a , "
				+ ProjectUserInfo.class.getName() + " b  where a.id=b.projectId and b.user=:userId";

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		String whereSql = "";
		if (Check.isNoEmpty(object.getText())) {
			paramMap.put("text", "%" + object.getText() + "%");
			whereSql += " and a.text like :text ";
		}

		PagingData<Map<String, Object>> page = dao.queryPagingDataByHql(hql + whereSql + " ORDER BY b.dupdate DESC",
				hqlCount + whereSql, paramMap, pageRange);

		return page;
	}

}
