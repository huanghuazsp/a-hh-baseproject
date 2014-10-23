package com.hh.usersystem.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.service.impl.BaseService;
import com.hh.system.util.base.BaseServiceAction;
import com.hh.usersystem.bean.usersystem.HhXtYhCdZmtb;
import com.hh.usersystem.service.impl.ZmtbService;

@SuppressWarnings("serial")
public class ActionZmtb extends BaseServiceAction<HhXtYhCdZmtb> {
	@Autowired
	private ZmtbService service;
	public BaseService<HhXtYhCdZmtb> getService() {
		return service;
	}
	
	public void orderIds() {
		service.orderIds(object.getId());
	}
}
