package com.hh.oa.action;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.oa.bean.Schedule;
import com.hh.oa.service.impl.ScheduleService;
import com.hh.system.service.impl.BaseService;
import com.hh.system.util.base.BaseServiceAction;
import com.hh.system.util.dto.ParamFactory;
import com.hh.usersystem.service.impl.LoginUserUtilService;

@SuppressWarnings("serial")
public class ActionSchedule extends BaseServiceAction<Schedule> {

	private Date startDate;
	private Date endDate;

	public BaseService<Schedule> getService() {
		return scheduleService;
	}

	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private LoginUserUtilService loginUserUtilService;

	@Override
	public void save() {
		this.object.setUserId(loginUserUtilService.findLoginUserId());
		super.save();
	}

	public void queryListByDate() {
		// Date start = DateFormat.strToDate("2013-10", "yyyy-MM");
		// Date end = DateFormat.strToDate("2013-11", "yyyy-MM");
		;
		List<Schedule> schedules = scheduleService.queryList(ParamFactory
				.getParamHb().ge("start", startDate).le("end", endDate)
				.is("userId", loginUserUtilService.findLoginUserId()));
		this.returnResult(schedules);
	}

	public void ok() {
		this.getService().update(this.object.getId(), "isOk",
				this.object.getIsOk());
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

}
