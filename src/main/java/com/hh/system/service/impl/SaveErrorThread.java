package com.hh.system.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.hh.system.bean.SysError;
import com.hh.system.util.ExceptionUtil;
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
		SysError sysError = new SysError();
		sysError.setName(e.getClass().getName());
		sysError.setMessage(e.getMessage());
		sysError.setAllMessage(ExceptionUtil.getMessage(e));
		sysError.setCreateUser(userid);
		sysError.setUpdateUser(userid);
		sysError.setOrgid(currOrg);
		try {
			errorService.save(sysError);
		} catch (MessageException e1) {
			e1.printStackTrace();
		}
	}
}
