package com.hh.system.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.bean.SysError;
import com.hh.system.service.impl.BaseService;
import com.hh.system.service.impl.ErrorService;
import com.hh.system.util.base.BaseServiceAction;

@SuppressWarnings("serial")
public class ActionError extends BaseServiceAction<SysError> {
	@Autowired
	private ErrorService errorService;
	public BaseService<SysError> getService() {
		return errorService;
	}
}
