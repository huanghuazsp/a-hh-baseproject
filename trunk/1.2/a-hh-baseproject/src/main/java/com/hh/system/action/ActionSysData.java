package com.hh.system.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.bean.SysData;
import com.hh.system.service.impl.BaseService;
import com.hh.system.service.impl.SysDataService;
import com.hh.system.util.Convert;
import com.hh.system.util.base.BaseServiceAction;
import com.hh.system.util.dto.ParamFactory;

@SuppressWarnings("serial")
public class ActionSysData extends BaseServiceAction<SysData> {

	public BaseService<SysData> getService() {
		return sysDataService;
	}

	@Autowired
	private SysDataService sysDataService;

	@Override
	public void queryTreeList() {
		this.returnResult(sysDataService.queryTreeList(
				object.getNode(),
				Convert.toBoolean(request.getParameter("isNoLeaf")),
				ParamFactory.getParamHb().is("dataTypeId",
						object.getDataTypeId())));
	}

}
