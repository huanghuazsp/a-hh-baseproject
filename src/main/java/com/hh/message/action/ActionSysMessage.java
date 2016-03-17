package com.hh.message.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.message.bean.SysMessage;
import com.hh.message.service.SysMessageService;
import com.hh.system.service.impl.BaseService;
import com.hh.system.util.base.BaseServiceAction;

@SuppressWarnings("serial")
public class ActionSysMessage extends BaseServiceAction<SysMessage> {
	@Autowired
	private SysMessageService service;

	public BaseService<SysMessage> getService() {
		return service;
	}

	
	public Object queryMyPagingDataBySendObjectId() {
		return service.queryMyPagingDataBySendObjectId(object,
				this.getPageRange());
	}
	
}
