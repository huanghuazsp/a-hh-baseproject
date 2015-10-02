 package com.hh.system.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.util.base.BaseServiceAction;
import com.hh.system.bean.SystemPlantask;
import com.hh.system.service.impl.BaseService;
import com.hh.system.service.impl.SystemPlantaskService;

@SuppressWarnings("serial")
public class ActionSystemPlantask extends BaseServiceAction< SystemPlantask > {
	@Autowired
	private SystemPlantaskService systemplantaskService;
	public BaseService<SystemPlantask> getService() {
		return systemplantaskService;
	}
}
 