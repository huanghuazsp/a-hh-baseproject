 package com.hh.oa.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.util.base.BaseServiceAction;
import com.hh.oa.bean.OaMeetingApplyUser;
import com.hh.system.service.impl.BaseService;
import com.hh.oa.service.impl.OaMeetingApplyUserService;

@SuppressWarnings("serial")
public class ActionMeetingApplyUser extends BaseServiceAction< OaMeetingApplyUser > {
	@Autowired
	private OaMeetingApplyUserService oameetingapplyuserService;
	public BaseService<OaMeetingApplyUser> getService() {
		return oameetingapplyuserService;
	}
}
 