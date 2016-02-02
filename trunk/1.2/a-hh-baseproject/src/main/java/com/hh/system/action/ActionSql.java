package com.hh.system.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.bean.SysSql;
import com.hh.system.service.impl.BaseService;
import com.hh.system.service.impl.SqlService;
import com.hh.system.util.base.BaseServiceAction;

@SuppressWarnings("serial")
public class ActionSql extends BaseServiceAction<SysSql> {
	@Autowired
	private SqlService sqlService;
	public BaseService<SysSql> getService() {
		return sqlService;
	}
}
