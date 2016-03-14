package com.hh.usersystem.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.service.inf.LoadDataTime;
import com.hh.system.util.StaticProperties;
import com.hh.system.util.base.BaseAction;
import com.hh.usersystem.service.impl.SystemService;

@SuppressWarnings("serial")
public class ActionSystem extends BaseAction {
	@Autowired
	private SystemService systemService;

	public void initMenuAndUser() {
		systemService.initMenuAndUser();
	}
	
	public Object loadDataTime() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<LoadDataTime> loadDataTimeList = StaticProperties.loadDataTimeList;
		for (LoadDataTime loadDataTime : loadDataTimeList) {
			map.putAll(loadDataTime.load());
		}
		return map;
	}
	
	public Object queryCacheListPage() {
		return systemService.queryCacheListPage();
	}
	
	public void deleteCacheByIds() {
		systemService.deleteCacheByIds(this.getIds());
	}
}
