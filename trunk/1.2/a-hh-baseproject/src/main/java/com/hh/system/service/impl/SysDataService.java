package com.hh.system.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hh.system.bean.SysData;
import com.hh.system.bean.SysDataType;
import com.hh.system.util.Check;
import com.hh.system.util.MessageException;
import com.hh.system.util.dto.ParamInf;

@Service
public class SysDataService  extends BaseService<SysData> {
	protected boolean checkTextOnly(SysData hhXtData) {
		return dao
				.findWhetherData(
						"select count(o) from "
								+ hhXtData.getClass().getName()
								+ " o "
								+ "where o.text=:text and (o.id!=:id or :id is null) and node = :node and dataTypeId=:dataTypeId",
						hhXtData);
	}
	
	public List<SysData> queryTreeListCode(SysData baseTreeNodeEntity,
			boolean isNoLeaf, ParamInf paramInf) {
		List<SysData> sysDataTypes = super.queryTreeList(baseTreeNodeEntity.getNode(), isNoLeaf, paramInf);
		updateId(sysDataTypes);
		return sysDataTypes;
	}

	private void updateId(List<SysData> sysDataTypes ) {
		if (sysDataTypes!=null) {
			for (SysData sysDataType : sysDataTypes) {
				sysDataType.setId(sysDataType.getCode());
				updateId(sysDataType.getChildren());
			}
		}
	}
	
	protected boolean checkCodeOnly(SysData hhXtData) {
		return dao
				.findWhetherData(
						"select count(o) from "
								+ hhXtData.getClass().getName()
								+ " o "
								+ "where o.code=:code and (o.id!=:id or :id is null)  and dataTypeId=:dataTypeId ",
						hhXtData);
	}
	@Override
	public SysData saveTree(SysData hhXtData) throws MessageException {
		if (Check.isEmpty(hhXtData.getNode())) {
			hhXtData.setNode("root");
		}
		if (checkCodeOnly(hhXtData)) {
			throw new MessageException("标识不能重复，请更换！");
		}
		return super.saveTree(hhXtData);
	}
	
}
