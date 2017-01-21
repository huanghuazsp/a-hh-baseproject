package com.hh.oa.service.impl;

import org.springframework.stereotype.Service;

import com.hh.oa.bean.Schedule;
import com.hh.system.service.impl.BaseService;
import com.hh.system.util.MessageException;

@Service
public class ScheduleService extends BaseService<Schedule> {

	@Override
	public Schedule save(Schedule entity) throws MessageException {
		if (entity.getEnd()!=null) {
			if (entity.getEnd().getTime()-entity.getStart().getTime()>60 * 60 * 1000 * 24) {
				entity.setType(1);
			}
		}
		return super.save(entity);
	}
	
	
}
