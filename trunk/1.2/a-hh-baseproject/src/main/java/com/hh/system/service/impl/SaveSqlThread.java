package com.hh.system.service.impl;

import com.hh.system.bean.SysSql;
import com.hh.system.util.MessageException;

public class SaveSqlThread extends Thread {
	private SqlService service = BeanFactoryHelper.getBeanFactory().getBean(
			SqlService.class);
	private String userid;
	private String currOrg;
	private long elapsedTime;
	private String sql;

	public SaveSqlThread(String sql, long elapsedTime, String userid,
			String currOrg) {
		this.sql = sql;
		this.elapsedTime = elapsedTime;
		this.userid = userid;
		this.currOrg = currOrg;
	}

	@Override
	public void run() {
		save();
		super.run();
	}

	private void save() {
		SysSql sqlObject = new SysSql();
		sqlObject.setElapsedTime(elapsedTime);
		sqlObject.setSql(sql);
		sqlObject.setVcreate(userid);
		sqlObject.setVupdate(userid);
		sqlObject.setVorgid(currOrg);
		try {
			service.save(sqlObject);
		} catch (MessageException e1) {
			e1.printStackTrace();
		}
	}
}
