package com.hh.system.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.bean.HhXtData;
import com.hh.system.service.impl.BaseService;
import com.hh.system.service.impl.HhXtDataService;
import com.hh.system.util.MessageException;
import com.hh.system.util.base.BaseServiceAction;
import com.hh.system.util.model.ReturnModel;
import com.hh.system.util.statics.StaticVar;

@SuppressWarnings("serial")
public class ActionData extends BaseServiceAction<HhXtData> {

	public BaseService<HhXtData> getService() {
		return hhXtDataService;
	}

	@Autowired
	private HhXtDataService hhXtDataService;

	public Object queryDataTreeByTypeAndPid() {
		List<HhXtData> hhXtDatas = hhXtDataService.queryMenuListByPid(
				object.getNode(), object.getType());
		return hhXtDatas;
	}

	public Object queryDataTreeByPid() {
		List<HhXtData> hhXtDatas = hhXtDataService.queryDataTreeByPid(object
				.getNode());
		return hhXtDatas;
	}

	public Object queryDataByType() {
		List<HhXtData> hhXtDatas = hhXtDataService.queryDataByType(object
				.getType());
		return hhXtDatas;
	}

	public Object queryAllDataTreeByTypeAndPid() {
		List<HhXtData> hhXtDatas = hhXtDataService.queryAllMenuListByPid(object
				.getType());
		return hhXtDatas;
	}

	public Object findObjectById() {
		HhXtData hhXtData = hhXtDataService.findObjectById(this.object.getId());
		return hhXtData;
	}

	public Object save() {
		try {
			HhXtData hhXtData = hhXtDataService.save(this.object);
			return null;
		} catch (MessageException e) {
			return e;
		}
		
	}

	public void deleteByIds() {
		hhXtDataService.deleteByIds(this.getIds());
	}

}
