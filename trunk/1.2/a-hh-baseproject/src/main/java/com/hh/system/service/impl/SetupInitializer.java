package com.hh.system.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.system.bean.SystemPlantask;

@Service
public class SetupInitializer {
	@Autowired
	private SystemPlantaskService taskConfigManager;

	@PostConstruct
	public void initialize() {
		List<SystemPlantask> taskConfigList = this.taskConfigManager
				.queryAllList();
		for (SystemPlantask taskConfig : taskConfigList) {
			this.taskConfigManager.addTask(taskConfig);
		}
	}
}