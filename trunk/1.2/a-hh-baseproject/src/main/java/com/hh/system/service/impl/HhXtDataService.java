package com.hh.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.hh.system.bean.HhXtData;
import com.hh.system.util.dto.PageRange;
import com.hh.system.util.dto.PagingData;
import com.hh.system.util.dto.ParamFactory;
import com.hh.system.util.dto.ParamInf;

@Service
public class HhXtDataService extends BaseService<HhXtData> {

	public PagingData<HhXtData> queryPagingData(HhXtData hhXtData,
			PageRange pageRange) {
		ParamInf hqlParamList = ParamFactory.getParamHb();
		hqlParamList.is("node", hhXtData.getNode());
		return dao.queryPagingData(HhXtData.class, hqlParamList, pageRange);
	}

	public List<HhXtData> queryMenuListByPid(String node, String type) {
		ParamInf hqlParamList = ParamFactory.getParamHb()
				.is("node", node).is("type", type);
		List<HhXtData> hhXtDatas = dao.queryTreeList(HhXtData.class,
				hqlParamList);
		return hhXtDatas;
	}

	public List<HhXtData> queryAllMenuListByPid(String type) {
		ParamInf hqlParamList = ParamFactory.getParamHb()
				.is("node", "root").is("type", type);
		List<HhXtData> hhXtDatas = dao.queryList(HhXtData.class, hqlParamList);
		for (HhXtData hhXtData : hhXtDatas) {
			addYzNode(hhXtData);
		}

		return hhXtDatas;
	}

	private void addYzNode(HhXtData hhXtData) {
		ParamInf hqlParamList = ParamFactory.getParamHb()
				.is("node", hhXtData.getId()).is("type", hhXtData.getType());
		List<HhXtData> hhXtDatas = dao.queryList(HhXtData.class, hqlParamList);
		hhXtData.setChildren(hhXtDatas);
		for (HhXtData hhXtData2 : hhXtDatas) {
			addYzNode(hhXtData2);
		}
	}

	public List<HhXtData> queryDataTreeByPid(String node) {
		ParamInf hqlParamList = ParamFactory.getParamHb()
				.is("node", node).
				nis("type", "form_tree")
				.nis("type",
								"workflow_tree");
		List<HhXtData> hhXtDatas = dao.queryTreeList(HhXtData.class,
				hqlParamList);
		return hhXtDatas;
	}

	public List<HhXtData> queryDataByType(String type) {
		HhXtData hhXtData = dao.findEntity(HhXtData.class,
				Restrictions.eq("type", type));
		if (hhXtData == null) {
			return new ArrayList<HhXtData>();
		}
		return this.queryDataTreeByPid(hhXtData.getId());
	}
}
