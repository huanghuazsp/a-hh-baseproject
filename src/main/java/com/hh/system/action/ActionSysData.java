package com.hh.system.action;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.hh.hibernate.util.dto.HQLParamList;
import com.hh.system.bean.SysData;
import com.hh.system.service.impl.BaseService;
import com.hh.system.service.impl.SysDataService;
import com.hh.system.util.Convert;
import com.hh.system.util.base.BaseServiceAction;

@SuppressWarnings("serial")
public class ActionSysData extends BaseServiceAction<SysData> {

	public BaseService<SysData> getService() {
		return sysDataService;
	}

	@Autowired
	private SysDataService sysDataService;

	@Override
	public void queryTreeList() {
		this.returnResult(sysDataService.queryTreeList(object.getNode(),
				Convert.toBoolean(request.getParameter("isNoLeaf")),
				new HQLParamList().addCondition(Restrictions.eq("dataTypeId",
						object.getDataTypeId()))));
	}

}
