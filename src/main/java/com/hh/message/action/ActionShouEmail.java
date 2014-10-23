package com.hh.message.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.message.bean.SysShouEmail;
import com.hh.message.service.SysShouEmailService;
import com.hh.system.service.impl.BaseService;
import com.hh.system.util.base.BaseServiceAction;

@SuppressWarnings("serial")
public class ActionShouEmail extends BaseServiceAction<SysShouEmail> {
	@Autowired
	private SysShouEmailService sysShouEmailService;
	public BaseService<SysShouEmail> getService() {
		return sysShouEmailService;
	}
}
