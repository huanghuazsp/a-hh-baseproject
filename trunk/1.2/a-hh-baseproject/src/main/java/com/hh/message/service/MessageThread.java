package com.hh.message.service;

import com.hh.message.bean.SysMessage;
import com.hh.system.service.impl.BeanFactoryHelper;

public class MessageThread extends Thread {
	private SysMessageService service = BeanFactoryHelper.getBeanFactory().getBean(SysMessageService.class);
	private SysMessage sysMessage;

	public MessageThread(SysMessage sysMessage) {
		this.sysMessage = sysMessage;
	}

	@Override
	public void run() {
		service.save(sysMessage);
	}

}
