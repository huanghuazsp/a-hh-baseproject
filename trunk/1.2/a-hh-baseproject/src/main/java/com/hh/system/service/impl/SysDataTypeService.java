package com.hh.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.system.bean.SysData;
import com.hh.system.bean.SysDataType;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.MessageException;
import com.hh.system.util.PinYinUtil;
import com.hh.system.util.dto.ParamFactory;
import com.hh.usersystem.bean.usersystem.UsUser;

@Service
public class SysDataTypeService extends BaseService<SysDataType> {

	@Autowired
	private SysDataService sysDataService;

	public List<SysDataType> queryTreeListCode(SysDataType baseTreeNodeEntity) {
		List<SysDataType> sysDataTypes = super.queryTreeList(
				baseTreeNodeEntity.getNode());
		return sysDataTypes;
	}


	protected boolean checkCodeOnly(SysDataType hhXtData) {
		return dao.findWhetherData("select count(o) from "
				+ hhXtData.getClass().getName() + " o "
				+ "where o.code=:code and (o.id!=:id or :id is null)  ",
				hhXtData);
	}

	@Override
	public SysDataType saveTree(SysDataType hhXtData) throws MessageException {
		String msg = "["+hhXtData.getText()+"]";
		if (Check.isEmpty(hhXtData.getNode())) {
			hhXtData.setNode("root");
		}
		if (checkCodeOnly(hhXtData)) {
			throw new MessageException("标识不能重复，请更换！"+msg);
		}
	
		try {
			return super.saveTree(hhXtData);
		} catch (MessageException e) {
			throw new MessageException(e.getMessage()+msg);
		}
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

	public void save(List<Map<String, Object>> mapList) throws MessageException {
		Map<String, String> mapNameIdType = new HashMap<String, String>();
		Map<String, String> mapNameCodeType = new HashMap<String, String>();
		
		Map<String, String> mapNameIdData = new HashMap<String, String>();
		
		for (Map<String, Object> map : mapList) {
			String type = Convert.toString(map.get("类型"));
			String sjmc = Convert.toString(map.get("上级名称"));
			String id = Convert.toString(map.get("标识"));
			String name = Convert.toString(map.get("名称"));
			String code = Convert.toString(map.get("编码"));
			if (Check.isEmpty(code)) {
				code=PinYinUtil.getPinYin(name);
			}
			String node = "root";
			if ("字典".equals(type)) {
				String dataTypeId = Convert.toString(map.get("所属类别"));
				if (Check.isNoEmpty(dataTypeId)) {
					if (mapNameCodeType.containsKey(dataTypeId)) {
						dataTypeId = mapNameCodeType.get(dataTypeId);
					}else {
						List<SysDataType> sysDataTypeList = queryListByProperty(
								"text", dataTypeId);
						if (sysDataTypeList.size() > 0) {
							mapNameIdType.put(sjmc, sysDataTypeList.get(0).getCode());
						} else {
							dataTypeId =null;
						}
					}
				}
				if (Check.isNoEmpty(sjmc)) {
					if (!mapNameIdData.containsKey(sjmc)) {
						List<SysData> sysDataList = sysDataService.queryList(ParamFactory.getParamHb().is("text", sjmc).is("dataTypeId", dataTypeId));
						if (sysDataList.size() > 0) {
							mapNameIdData.put(sjmc, sysDataList.get(0).getId());
						} else {
							mapNameIdData.put(sjmc, "root");
						}
					}
					node = mapNameIdData.get(sjmc);
				}
				
				SysData user = null;
				if (Check.isNoEmpty(id)) {
					user = sysDataService.findObjectById(id);
				} else {
					List<SysData> users = sysDataService.queryList(ParamFactory.getParamHb().is(
							"text", name));
					for (SysData usUser : users) {
						user = usUser;
					}
				}
				if (user == null) {
					user = new SysData();
				}
				user.setText(name);
				user.setNode(node);
				user.setCode(code);
				user.setDataTypeId(dataTypeId);
				sysDataService.saveTree(user);
			}else {
				mapNameCodeType.put(name, code);
				if (Check.isNoEmpty(sjmc)) {
					if (!mapNameIdType.containsKey(sjmc)) {
						List<SysDataType> sysDataTypeList = queryListByProperty(
								"text", sjmc);
						if (sysDataTypeList.size() > 0) {
							mapNameIdType.put(sjmc, sysDataTypeList.get(0).getId());
						} else {
							mapNameIdType.put(sjmc, "root");
						}
					}
					node = mapNameIdType.get(sjmc);
				}
				
				
				SysDataType user = null;
				if (Check.isNoEmpty(id)) {
					user = findObjectById(id);
				} else {
					List<SysDataType> users = queryList(ParamFactory.getParamHb().is(
							"text", name));
					for (SysDataType usUser : users) {
						user = usUser;
					}
				}
				if (user == null) {
					user = new SysDataType();
				}
				user.setText(name);
				user.setNode(node);
				user.setCode(code);
				saveTree(user);
				
			}
			
		}
	}
}
