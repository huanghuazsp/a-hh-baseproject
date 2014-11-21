package com.hh.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.hh.hibernate.util.dto.HQLParamList;
import com.hh.system.bean.HhXtData;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.MessageException;
import com.hh.system.util.dto.PageRange;
import com.hh.system.util.dto.PagingData;

@Service
public class HhXtDataService extends BaseTreeService<HhXtData> {

	
	public PagingData<HhXtData> queryPagingData(HhXtData hhXtData,
			PageRange pageRange) {
		HQLParamList hqlParamList = new HQLParamList();
		hqlParamList.add(Restrictions.eq("node", hhXtData.getNode()));
		return dao.queryPagingData(HhXtData.class, hqlParamList, pageRange);
	}

	public List<HhXtData> queryMenuListByPid(String node, String type) {
		HQLParamList hqlParamList = new HQLParamList().addCondition(
				Restrictions.eq("node", node)).addCondition(
				Restrictions.eq("type", type));
		List<HhXtData> hhXtDatas = treedao.queryTreeList(HhXtData.class, hqlParamList);
		return hhXtDatas;
	}

	public List<HhXtData> queryAllMenuListByPid(String type) {
		HQLParamList hqlParamList = new HQLParamList().addCondition(
				Restrictions.eq("node", "root")).addCondition(
				Restrictions.eq("type", type));
		List<HhXtData> hhXtDatas = dao.queryList(HhXtData.class, hqlParamList);
		for (HhXtData hhXtData : hhXtDatas) {
			addYzNode(hhXtData);
		}

		return hhXtDatas;
	}

	private void addYzNode(HhXtData hhXtData) {
		HQLParamList hqlParamList = new HQLParamList().addCondition(
				Restrictions.eq("node", hhXtData.getId())).addCondition(
				Restrictions.eq("type", hhXtData.getType()));
		List<HhXtData> hhXtDatas = dao.queryList(HhXtData.class, hqlParamList);
		hhXtData.setChildren(hhXtDatas);
		for (HhXtData hhXtData2 : hhXtDatas) {
			addYzNode(hhXtData2);
		}
	}

	public List<HhXtData> queryDataTreeByPid(String node) {
		HQLParamList hqlParamList = new HQLParamList()
				.addCondition(Restrictions.eq("node", node))
				.addCondition(
						Restrictions.not(Restrictions.eq("type", "form_tree")))
				.addCondition(
						Restrictions.not(Restrictions.eq("type",
								"workflow_tree")));
		List<HhXtData> hhXtDatas = treedao.queryTreeList(HhXtData.class, hqlParamList);
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
