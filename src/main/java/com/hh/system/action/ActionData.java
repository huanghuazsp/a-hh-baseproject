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

	public void queryDataTreeByTypeAndPid() {
		List<HhXtData> hhXtDatas = hhXtDataService.queryMenuListByPid(
				object.getNode(), object.getType());
		this.returnResult(hhXtDatas);
	}

	public void queryDataTreeByPid() {
		List<HhXtData> hhXtDatas = hhXtDataService.queryDataTreeByPid(object
				.getNode());
		this.returnResult(hhXtDatas);
	}

	public void queryDataByType() {
		List<HhXtData> hhXtDatas = hhXtDataService.queryDataByType(object
				.getType());
		this.returnResult(hhXtDatas);
	}

	public void queryAllDataTreeByTypeAndPid() {
		List<HhXtData> hhXtDatas = hhXtDataService.queryAllMenuListByPid(object
				.getType());
		this.returnResult(hhXtDatas);
	}

	public void findObjectById() {
		HhXtData hhXtData = hhXtDataService.findObjectById(this.object.getId());
		this.returnResult(hhXtData);
	}

	public void save() {
		try {
			HhXtData hhXtData = hhXtDataService.save(this.object);
			this.getResultMap().put("object", hhXtData);
		} catch (MessageException e) {
			this.getResultMap().put("returnModel",
					new ReturnModel(e.getMessage()));
		}
		this.returnResult();
	}

	public void deleteByIds() {
		hhXtDataService.deleteByIds(this.getIds());
	}

}
