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
		SysMenu rootHhXtCd = new SysMenu("kOvoOgrGUiTq4Iq0CfK","系统管理",
				"com.hh.global.NavigAtionWindow", "/hhcommon/images/extjsico/17460336.png", 0, 0);
		rootHhXtCd.setChildren(new ArrayList<SysMenu>());


		rootHhXtCd.getChildren().add(new SysMenu("OwRQNE6v1UxH11SMDBw", "计划任务",
				"jsp-system-systemplantask-SystemPlantaskList", "/hhcommon/images/extjsico/timer.gif", 0, 1));


		rootHhXtCd.getChildren().add(new SysMenu( "mT0b8fG6qGemkKtnXiA","数据库管理",
				"jsp-database-connect-main", "/hhcommon/images/icons/page/page_white_database.png", 0, 1));


		rootHhXtCd.getChildren().add(new SysMenu( "oAgFhe9tIBMdd2TeZA8","数据字典",
				"jsp-system-data-datalist", "/hhcommon/images/extjsico/17460321.png", 0, 1));

		StaticProperties.hhXtCds.add(rootHhXtCd);


		rootHhXtCd.getChildren().add(new SysMenu("2PfT85tR2RRcqSAdmCS", "系统工具",
				"jsp-system-system-systemmain", "/hhcommon/images/extjsico/application_16x16.gif", 0, 1));


		StaticVar.loadDataTimeMap.put("onlineuser", systemService);

	}
}