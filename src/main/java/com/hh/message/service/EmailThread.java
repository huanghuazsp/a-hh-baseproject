package com.hh.message.service;

import com.hh.system.service.impl.BeanFactoryHelper;
import com.hh.system.util.Json;

public class EmailThread extends Thread {
	private EmailService service = BeanFactoryHelper.getBeanFactory().getBean(
			EmailService.class);
	static MessageService messageService = new MessageService();
	private String userId;

	public EmailThread(String userId) {
		this.userId=userId;
	}

	@Override
	public void run() {
		messageService.sendMessageAuto(Json.toStr(service.load2(userId)));
	}

}
