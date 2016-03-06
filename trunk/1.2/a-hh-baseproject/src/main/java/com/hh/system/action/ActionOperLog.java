 package com.hh.system.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.util.base.BaseServiceAction;
import com.hh.system.bean.SystemOperLog;
import com.hh.system.service.impl.BaseService;
import com.hh.system.service.impl.SystemOperLogService;

@SuppressWarnings("serial")
public class ActionOperLog extends BaseServiceAction< SystemOperLog > {
	@Autowired
	private SystemOperLogService systemoperlogService;
	public BaseService<SystemOperLog> getService() {
		return systemoperlogService;
	}
}
 