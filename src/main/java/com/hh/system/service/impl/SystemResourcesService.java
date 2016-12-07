package com.hh.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.system.bean.SystemFile;
import com.hh.system.bean.SystemResources;
import com.hh.system.service.inf.IFileOper;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.Json;
import com.hh.system.util.MessageException;
import com.hh.system.util.dto.PageRange;
import com.hh.system.util.dto.PagingData;
import com.hh.system.util.dto.ParamFactory;
import com.hh.system.util.dto.ParamInf;
import com.hh.usersystem.LoginUserServiceInf;

@Service
public class SystemResourcesService extends BaseService<SystemResources> implements IFileOper {
	@Autowired
	private LoginUserServiceInf userService;

	@Override
	public SystemResources save(SystemResources entity) throws MessageException {
		List<Map<String, Object>> mapList = Json.toMapList(entity.getFiles());
		if (Check.isEmpty(entity.getText())) {
			String text = "";
			for (Map<String, Object> map : mapList) {
				text += Convert.toString(map.get("text")) + ",";
			}

			if (Check.isNoEmpty(text)) {
				text = text.substring(0, text.length() - 1);
			}

			if (text.length() > 128) {
				text = text.substring(0, 127);
			}
			entity.setText(text);
		}

		return super.save(entity);
	}

	public PagingData<SystemResources> queryPagingData(SystemResources entity, PageRange pageRange) {
		return queryPagingData(entity, pageRange, 0);
	}

	public PagingData<SystemResources> querySharePagingData(SystemResources entity, PageRange pageRange) {
		entity.setState(1);
		return queryPagingData(entity, pageRange, 1);
	}

	public PagingData<SystemResources> queryPagingData(SystemResources entity, PageRange pageRange, int share) {
		ParamInf paramInf = ParamFactory.getParamHb();
		if (Check.isNoEmpty(entity.getType())) {
			paramInf.is("type", entity.getType());
		}
		if (Check.isNoEmpty(entity.getText())) {
			paramInf.like("text", entity.getText());
		}

		if (entity.getState() == 1 || entity.getState() == 0) {
			paramInf.is("state", entity.getState());
		}

		if (share != 1) {
			paramInf.is("createUser", userService.findUserId());
		}

		return super.queryPagingData( pageRange, paramInf);
	}

	@Override
	public void fileOper(SystemFile systemFile) {
		int count = findCount(ParamFactory.getParamHb()
				.or(ParamFactory.getParamHb().like("files", systemFile.getId()).is("img", systemFile.getId())));
		if (count == 0) {
			systemFile.setStatus(1);
		}
	}

	public void doSetState(String ids, int state) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", Convert.strToList(ids));
		map.put("state", state);
		dao.updateEntity("update " + SystemResources.class.getName() + " o set o.state=:state where o.id in :id", map);
	}

}
