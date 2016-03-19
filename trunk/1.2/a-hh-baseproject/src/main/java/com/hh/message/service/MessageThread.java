package com.hh.message.service;

import com.hh.message.bean.SysMessage;
import com.hh.system.service.impl.BeanFactoryHelper;

public class MessageThread extends Thread {
	private SysMessageService service = BeanFactoryHelper.getBeanFactory().getBean(SysMessageService.class);
	private SysMessage sysMessage;
	private int addCylxr;

	public MessageThread(SysMessage sysMessage, int addCylxr) {
		this.addCylxr=addCylxr;
		this.sysMessage = sysMessage;
	}

	@Override
	public void run() {
		service.save(sysMessage,addCylxr);
	}

}
