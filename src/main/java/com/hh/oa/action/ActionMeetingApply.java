package com.hh.oa.action;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.util.Check;
import com.hh.system.util.MessageException;
import com.hh.system.util.base.BaseServiceAction;
import com.hh.system.util.dto.PageRange;
import com.hh.system.util.dto.ParamFactory;
import com.hh.oa.bean.OaMeetingApply;
import com.hh.oa.bean.OaNotice;
import com.hh.oa.bean.Schedule;
import com.hh.system.service.impl.BaseService;
import com.hh.usersystem.service.impl.LoginUserUtilService;
import com.hh.oa.service.impl.OaMeetingApplyService;

@SuppressWarnings("serial")
public class ActionMeetingApply extends BaseServiceAction<OaMeetingApply> {

	@Autowired
	private OaMeetingApplyService oameetingapplyService;

	public BaseService<OaMeetingApply> getService() {
		return oameetingapplyService;
	}

	public Object queryListByDate() {
		List<OaMeetingApply> schedules = oameetingapplyService
				.queryList(ParamFactory.getParamHb()
						.gt("start", object.getStart())
						.lt("end", object.getEnd())
						.is("meetingId", object.getMeetingId()));
		return schedules;
	}

	public Object updateDate() {
		try {
			oameetingapplyService.updateDate(this.object);
			return null;
		} catch (MessageException e) {
			return e;
		}
	}

	public Object findReadObjectById() {
		OaMeetingApply object = oameetingapplyService
				.findReadObjectById(this.object.getId());
		return object;
	}

}
