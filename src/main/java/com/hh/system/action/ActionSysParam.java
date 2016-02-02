package com.hh.system.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.bean.SysParams;
import com.hh.system.service.impl.BaseService;
import com.hh.system.service.impl.SysParamService;
import com.hh.system.util.base.BaseServiceAction;

@SuppressWarnings("serial")
public class ActionSysParam extends BaseServiceAction<SysParams> {
	@Autowired
	private SysParamService sysParamService;
	public BaseService<SysParams> getService() {
		return sysParamService;
	}
}
