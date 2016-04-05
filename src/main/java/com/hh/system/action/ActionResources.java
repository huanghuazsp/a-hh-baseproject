 package com.hh.system.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.util.base.BaseServiceAction;
import com.hh.system.bean.SystemResources;
import com.hh.system.service.impl.BaseService;
import com.hh.system.service.impl.SystemResourcesService;

@SuppressWarnings("serial")
public class ActionResources extends BaseServiceAction< SystemResources > {
	@Autowired
	private SystemResourcesService systemresourcesService;
	public BaseService<SystemResources> getService() {
		return systemresourcesService;
	}
}
 