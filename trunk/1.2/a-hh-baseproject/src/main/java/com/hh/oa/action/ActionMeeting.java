 package com.hh.oa.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.util.base.BaseServiceAction;
import com.hh.oa.bean.OaMeeting;
import com.hh.system.service.impl.BaseService;
import com.hh.oa.service.impl.OaMeetingService;

@SuppressWarnings("serial")
public class ActionMeeting extends BaseServiceAction< OaMeeting > {
	@Autowired
	private OaMeetingService oameetingService;
	public BaseService<OaMeeting> getService() {
		return oameetingService;
	}
}
 