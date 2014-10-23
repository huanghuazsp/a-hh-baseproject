package com.hh.message.task;

import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.hh.message.service.SysMessageService;
import com.hh.system.service.impl.BeanFactoryHelper;
import com.hh.system.util.date.DateFormat;

public class SysMessageTask extends TimerTask {
	private SysMessageService sysMessageService = null;
	private static final Logger logger = Logger.getLogger(SysMessageTask.class);

	public SysMessageService getSysMessageService() {
		if (sysMessageService == null) {
			sysMessageService = BeanFactoryHelper.getBeanFactory().getBean(
					SysMessageService.class);
		}
		return sysMessageService;
	}

	@Override
	public void run() {
		logger.info(DateFormat.getDate() + "：清空消息提醒数据！！");
		getSysMessageService().deleteReadData();
	}

}
