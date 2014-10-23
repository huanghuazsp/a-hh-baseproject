package com.hh.system.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hh.hibernate.dao.inf.Order;
import com.hh.hibernate.util.base.BaseTwoEntity;

@SuppressWarnings("serial")
@Entity
@Order
@Table(name = "HH_XT_SQL")
public class HHXtSql extends BaseTwoEntity {
	private long elapsedTime;
	private String sql;
	private String createUserName;
	
	@Column(name="ELAPSED_TIME")
	public long getElapsedTime() {
		return elapsedTime;
	}
	public void setElapsedTime(long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}
	@Lob
	@Column(name="SQL_")
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	
	@Transient
	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
}
