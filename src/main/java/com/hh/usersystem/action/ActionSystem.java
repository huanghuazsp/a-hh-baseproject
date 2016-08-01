package com.hh.usersystem.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.service.impl.SystemFileService;
import com.hh.system.util.StaticVar;
import com.hh.system.util.base.BaseAction;
import com.hh.usersystem.LoginUserServiceInf;
import com.hh.usersystem.bean.usersystem.SysMenu;
import com.hh.usersystem.bean.usersystem.UsUser;
import com.hh.usersystem.service.impl.LoginUserUtilService;
import com.hh.usersystem.service.impl.SystemService;

@SuppressWarnings("serial")
public class ActionSystem extends BaseAction {
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private SystemFileService systemFileService;
	
	@Autowired
	private LoginUserUtilService loginUserService;

	public void initMenuAndUser() {
		systemService.initMenuAndUser();
	}
	
	public void fileCopyDeleteTask() {
		systemFileService.fileCopyDeleteTask();
	}
	
	public Object loadDataTime() {
		
		UsUser user = loginUserService.findLoginUser();
		List<String> vsjList = new ArrayList<String>();
		
		vsjList.add("onlineuser");
		vsjList.add("message");
		
		for (SysMenu menu : user.getDesktopQuickList()) {
			vsjList.add(menu.getVsj());
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		Set<String> keySet = StaticVar.loadDataTimeMap.keySet();
		for (String key : keySet) {
			if (vsjList.contains(key)) {
				map.put(key,StaticVar.loadDataTimeMap.get(key).load());
			}
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
