package com.hh.usersystem.action;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.service.impl.SystemFileService;
import com.hh.system.util.StaticVar;
import com.hh.system.util.base.BaseAction;
import com.hh.usersystem.service.impl.SystemService;

@SuppressWarnings("serial")
public class ActionSystem extends BaseAction {
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private SystemFileService systemFileService;

	public void initMenuAndUser() {
		systemService.initMenuAndUser();
	}
	
	public void fileCopyDeleteTask() {
		systemFileService.fileCopyDeleteTask();
	}
	
	public Object loadDataTime() {
		Map<String, Object> map = new HashMap<String, Object>();
		Set<String> keySet = StaticVar.loadDataTimeMap.keySet();
		for (String key : keySet) {
			map.put(key,StaticVar.loadDataTimeMap.get(key).load());
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
