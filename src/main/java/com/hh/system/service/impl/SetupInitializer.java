package com.hh.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.system.bean.SystemPlantask;
import com.hh.system.util.StaticVar;
import com.hh.usersystem.bean.usersystem.SysMenu;
import com.hh.usersystem.service.impl.SystemService;
import com.hh.usersystem.util.steady.StaticProperties;

@Service
public class SetupInitializer {
	@Autowired
	private SystemPlantaskService taskConfigManager;
	@Autowired
	private SystemService systemService;
	@PostConstruct
	public void initialize() {
		List<SystemPlantask> taskConfigList = this.taskConfigManager
				.queryAllList();
		for (SystemPlantask taskConfig : taskConfigList) {
			this.taskConfigManager.addTask(taskConfig);
		}
		menu();
	}
	
	
	public void menu() {
		SysMenu rootHhXtCd = new SysMenu("b77305ce-b345-46d7-8735-6968f616ad62", "系统管理",
				"com.hh.global.NavigAtionWindow", "/hhcommon/images/extjsico/17460336.png", 0, 0);
		rootHhXtCd.setChildren(new ArrayList<SysMenu>());


		rootHhXtCd.getChildren().add(new SysMenu("d45831d2-b887-40f1-9a3f-450298ac3f92", "计划任务",
				"jsp-system-systemplantask-SystemPlantaskList", "/hhcommon/images/extjsico/timer.gif", 0, 1));


		rootHhXtCd.getChildren().add(new SysMenu("7cc2cd43-d297-419d-9386-8129f887b2b3", "数据库管理",
				"jsp-database-connect-main", "/hhcommon/images/icons/page/page_white_database.png", 0, 1));


		rootHhXtCd.getChildren().add(new SysMenu("5c88da68-d911-4372-b421-80b308b0c34e", "数据字典",
				"jsp-system-data-datalist", "/hhcommon/images/extjsico/17460321.png", 0, 1));

		StaticProperties.hhXtCds.add(rootHhXtCd);


		rootHhXtCd.getChildren().add(new SysMenu("bc55cc4f-7a56-4089-affb-899f92e6c511", "系统工具",
				"jsp-system-system-systemmain", "/hhcommon/images/extjsico/application_16x16.gif", 0, 1));


		StaticVar.loadDataTimeMap.put("onlineuser", systemService);

	}
}