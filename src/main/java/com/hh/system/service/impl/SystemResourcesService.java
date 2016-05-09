 package com.hh.system.service.impl;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hh.system.bean.SystemResources;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.Json;
import com.hh.system.util.MessageException;
import com.hh.system.util.dto.PageRange;
import com.hh.system.util.dto.PagingData;
import com.hh.system.util.dto.ParamFactory;
import com.hh.system.util.dto.ParamInf;

@Service
public class SystemResourcesService extends BaseService<SystemResources> {
	@Override
	public SystemResources save(SystemResources entity) throws MessageException {
		List<Map<String,Object>> mapList = Json.toMapList(entity.getFiles());
		String text = "";
		for (Map<String, Object> map : mapList) {
			text+=Convert.toString(map.get("text"))+",";
		}
		
		if (text.length()>128) {
			text=text.substring(0, 127);
		}
		entity.setText(text);
		return super.save(entity);
	}
	
	@Override
	public PagingData<SystemResources> queryPagingData(SystemResources entity, PageRange pageRange) {
		ParamInf paramInf = ParamFactory.getParamHb();
		if (Check.isNoEmpty(entity.getType())) {
			paramInf.is("type", entity.getType());
		}
		if (Check.isNoEmpty(entity.getText())) {
			paramInf.like("text", entity.getText());
		}
		return super.queryPagingData(entity, pageRange, paramInf);
	}

}
 