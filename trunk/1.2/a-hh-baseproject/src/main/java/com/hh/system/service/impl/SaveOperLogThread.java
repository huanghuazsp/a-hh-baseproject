package com.hh.system.service.impl;

import com.hh.system.bean.SystemOperLog;
import com.hh.system.util.MessageException;

public class SaveOperLogThread extends Thread {
	private SystemOperLogService systemOperLogService = BeanFactoryHelper
			.getBeanFactory().getBean(SystemOperLogService.class);;
	private String userId;
	private String userName;
	private String message;
	private String ipStr;

	public SaveOperLogThread(String ipStr,String userId, String userName, String message) {
		this.userId =userId;
		this.userName = userName;
		this.message = message;
		this.ipStr = ipStr;
	}

	@Override
	public void run() {
		saveError();
		super.run();
	}

	private void saveError() {
		SystemOperLog systemOperLog = new SystemOperLog();
		try {
			systemOperLog.setUserId(userId);
			systemOperLog.setUserName(userName);
			systemOperLog.setMessage(message);
			systemOperLog.setIpStr(ipStr);
			systemOperLogService.save(systemOperLog);
		} catch (MessageException e1) {
			e1.printStackTrace();
		}
	}
}
