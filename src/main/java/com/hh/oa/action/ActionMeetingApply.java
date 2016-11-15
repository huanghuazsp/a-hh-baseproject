 package com.hh.oa.action;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.util.Check;
import com.hh.system.util.base.BaseServiceAction;
import com.hh.system.util.dto.ParamFactory;
import com.hh.oa.bean.OaMeetingApply;
import com.hh.oa.bean.Schedule;
import com.hh.system.service.impl.BaseService;
import com.hh.usersystem.service.impl.LoginUserUtilService;
import com.hh.oa.service.impl.OaMeetingApplyService;

@SuppressWarnings("serial")
public class ActionMeetingApply extends BaseServiceAction< OaMeetingApply > {
	
	private Date startDate;
	private Date endDate;

	private String meetingId;
	
	
	@Autowired
	private OaMeetingApplyService oameetingapplyService;
	public BaseService<OaMeetingApply> getService() {
		return oameetingapplyService;
	}
	
	
	
	public Object queryListByDate() {
		List<OaMeetingApply> schedules = oameetingapplyService
				.queryList(ParamFactory.getParamHb().ge("startDate", startDate).le("endDate", endDate).is("meetingId", meetingId));
		return schedules;
	}
	
	
	
	
	
	
	
	
	
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}
	
}
 