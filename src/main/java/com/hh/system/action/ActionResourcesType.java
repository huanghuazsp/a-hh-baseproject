 package com.hh.system.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.util.base.BaseServiceAction;
import com.hh.system.bean.SystemResourcesType;
import com.hh.system.service.impl.BaseService;
import com.hh.system.service.impl.SystemResourcesTypeService;

@SuppressWarnings("serial")
public class ActionResourcesType extends BaseServiceAction< SystemResourcesType > {
	@Autowired
	private SystemResourcesTypeService systemresourcestypeService;
	public BaseService<SystemResourcesType> getService() {
		return systemresourcestypeService;
	}
	
	public void doSetState() {
		systemresourcestypeService.doSetState(this.getIds(),object.getState());
	}

	@Override
	public Object queryTreeList() {
		return systemresourcestypeService.queryTreeList(object);
	}
	
	
}
 