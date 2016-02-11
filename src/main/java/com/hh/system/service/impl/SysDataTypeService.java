package com.hh.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.system.bean.SysDataType;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.MessageException;

@Service
public class SysDataTypeService extends BaseService<SysDataType> {
	
	@Autowired
	private SysDataService sysDataService;

	public List<SysDataType> queryTreeListCode(SysDataType baseTreeNodeEntity,
			boolean isNoLeaf) {
		List<SysDataType> sysDataTypes = super.queryTreeList(
				baseTreeNodeEntity.getNode(), isNoLeaf);
		updateId(sysDataTypes);
		return sysDataTypes;
	}

	private void updateId(List<SysDataType> sysDataTypes) {
		if (sysDataTypes != null) {
			for (SysDataType sysDataType : sysDataTypes) {
				sysDataType.setId(sysDataType.getCode());
				updateId(sysDataType.getChildren());
			}
		}
	}

	protected boolean checkCodeOnly(SysDataType hhXtData) {
		return dao.findWhetherData("select count(o) from "
				+ hhXtData.getClass().getName() + " o "
				+ "where o.code=:code and (o.id!=:id or :id is null)  ",
				hhXtData);
	}

	@Override
	public SysDataType saveTree(SysDataType hhXtData) throws MessageException {
		if (Check.isEmpty(hhXtData.getNode())) {
			hhXtData.setNode("root");
		}
		if (checkCodeOnly(hhXtData)) {
			throw new MessageException("标识不能重复，请更换！");
		}
		return super.saveTree(hhXtData);
	}

	@Override
	public List<String> deleteTreeByIds(String ids) {
		List<String> idList = Convert.strToList(ids);
		List<String> deleteIdList = new ArrayList<String>();
		deleteYzNode(idList, deleteIdList);
		if (!Check.isEmpty(deleteIdList)) {
			List<String> codeList = new ArrayList<String>();
			List<SysDataType> sysDataTypes = queryListByIds(deleteIdList);
			for (SysDataType sysDataType : sysDataTypes) {
				codeList.add(sysDataType.getCode());
			}
			sysDataService.deleteByProperty("dataTypeId", codeList);
			dao.deleteEntity(this.getGenericType(0), "id", deleteIdList);
		}
		return deleteIdList;
	}

}
