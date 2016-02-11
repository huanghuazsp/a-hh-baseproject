package com.hh.system.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.bean.SysDataType;
import com.hh.system.service.impl.BaseService;
import com.hh.system.service.impl.SysDataTypeService;
import com.hh.system.util.Convert;
import com.hh.system.util.base.BaseServiceAction;

@SuppressWarnings("serial")
public class ActionSysDataType extends BaseServiceAction<SysDataType> {

	public BaseService<SysDataType> getService() {
		return sysDataTypeService;
	}

	@Autowired
	private SysDataTypeService sysDataTypeService;

	public Object queryTreeListCode() {
		return sysDataTypeService.queryTreeListCode(this.object,
				Convert.toBoolean(request.getParameter("isNoLeaf")));
	}
	public Object findObjectByCode() {
		return sysDataTypeService.findObjectByProperty("code", object.getId());
	}
	

}
