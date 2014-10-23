package com.hh.system.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.bean.SysDataType;
import com.hh.system.service.impl.BaseService;
import com.hh.system.service.impl.SysDataTypeService;
import com.hh.system.util.base.BaseServiceAction;

@SuppressWarnings("serial")
public class ActionSysDataType extends BaseServiceAction<SysDataType> {

	public BaseService<SysDataType> getService() {
		return sysDataTypeService;
	}

	@Autowired
	private SysDataTypeService sysDataTypeService;



}
