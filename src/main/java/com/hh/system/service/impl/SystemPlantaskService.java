package com.hh.system.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;

import org.springframework.stereotype.Service;

import com.hh.cache.impl.CacheFactory;
import com.hh.system.bean.SystemPlantask;
import com.hh.system.util.MessageException;
import com.hh.system.util.PlanTask;

@Service
public class SystemPlantaskService extends BaseService<SystemPlantask> {

	public void test(HashMap<String, Object> map) throws Exception {
		System.out.println("定时任务：==============="+new Date());
	}
	
	@Override
	public SystemPlantask save(SystemPlantask entity) throws MessageException {
		SystemPlantask SystemPlantask = super.save(entity);
		removeTask(entity.getId());
		addTask(entity);
		return SystemPlantask;
	}

	public void addTask(SystemPlantask taskConfig) {
		if (taskConfig.getValid() == 1) {
			long time = taskConfig.getSecond() * 1000 + taskConfig.getMinute()
					* 60 * 1000 + taskConfig.getHour() * 60 * 60 * 1000;
			Timer timer = new Timer();
			if ((taskConfig.getHourRegister() != 0)
					|| (taskConfig.getMinuteRegister() != 0)
					|| (taskConfig.getSecondRegister() != 0)) {
				Calendar calendar = Calendar.getInstance();
				calendar.set(11, taskConfig.getHourRegister());
				calendar.set(12, taskConfig.getMinuteRegister());
				calendar.set(13, taskConfig.getSecondRegister());
				timer.schedule(new PlanTask(taskConfig), calendar.getTime(),
						time);
			} else {
				timer.schedule(new PlanTask(taskConfig), 1000L, time);
			}
			CacheFactory.getCache(Object.class, SystemPlantask.class.getName())
					.put(taskConfig.getId(), timer);
		}
	}

	private void removeTask(String id) {
		Object object = CacheFactory.getCache(Object.class,
				SystemPlantask.class.getName()).get(id);
		if (object != null) {
			Timer timer = (Timer) object;
			timer.cancel();
		}
	}
}
