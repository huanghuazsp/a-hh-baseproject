package com.hh.system.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hh.system.bean.SysData;
import com.hh.system.bean.SysDataType;
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
}
