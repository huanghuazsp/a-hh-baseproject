package com.hh.system.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.hh.system.bean.HHXtError;
import com.hh.system.util.MessageException;

public class SaveErrorThread extends Thread {
	private ErrorService errorService = BeanFactoryHelper
			.getBeanFactory().getBean(ErrorService.class);;
	private Exception e;
	private String userid;
	private String currOrg;

	public SaveErrorThread(Exception e, String userid, String currOrg) {
		this.e = e;
		this.userid = userid;
		this.currOrg = currOrg;
	}

	@Override
	public void run() {
		saveError();
		super.run();
	}

	private void saveError() {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw, true));
		HHXtError hhXtError = new HHXtError();
		hhXtError.setName(e.getClass().getName());
		hhXtError.setMessage(e.getMessage());
		hhXtError.setAllMessage(sw.toString());
		hhXtError.setVcreate(userid);
		hhXtError.setVupdate(userid);
		hhXtError.setVorgid(currOrg);
		try {
			errorService.save(hhXtError);
		} catch (MessageException e1) {
			e1.printStackTrace();
		}
	}
}
