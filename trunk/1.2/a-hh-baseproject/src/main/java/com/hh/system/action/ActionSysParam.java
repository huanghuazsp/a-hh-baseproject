package com.hh.system.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.bean.HhSysParam;
import com.hh.system.service.impl.BaseService;
import com.hh.system.service.impl.SysParamService;
import com.hh.system.util.base.BaseServiceAction;

@SuppressWarnings("serial")
public class ActionSysParam extends BaseServiceAction<HhSysParam> {
	@Autowired
	private SysParamService sysParamService;
	public BaseService<HhSysParam> getService() {
		return sysParamService;
	}
}
