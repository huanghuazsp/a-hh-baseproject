package com.hh.system.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hh.system.bean.SysDataType;

@Service
public class SysDataTypeService  extends BaseService<SysDataType>{

	public List<SysDataType> queryTreeListCode(SysDataType baseTreeNodeEntity,
			boolean isNoLeaf) {
		List<SysDataType> sysDataTypes = super.queryTreeList(baseTreeNodeEntity.getNode(), isNoLeaf);
		updateId(sysDataTypes);
		return sysDataTypes;
	}

	private void updateId(List<SysDataType> sysDataTypes ) {
		if (sysDataTypes!=null) {
			for (SysDataType sysDataType : sysDataTypes) {
				sysDataType.setId(sysDataType.getCode());
				updateId(sysDataType.getChildren());
			}
		}
	}

	
}
