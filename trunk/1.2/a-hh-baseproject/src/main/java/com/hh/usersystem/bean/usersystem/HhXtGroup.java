package com.hh.usersystem.bean.usersystem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.hh.hibernate.dao.inf.Order;
import com.hh.hibernate.util.base.BaseTreeNodeEntity;
import com.hh.hibernate.util.base.BaseTwoEntity;

@Entity
@Table(name = "HH_XT_GROUP")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Order
public class HhXtGroup  extends BaseTreeNodeEntity<HhXtGroup> {
//	private String text;
	private String remark;

	private String users;
	
//	@Column(length = 64)
//	public String getText() {
//		return text;
//	}
//
//	public void setText(String text) {
//		this.text = text;
//	}

	@Column(length = 1024)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	@Transient
	public String getUsers() {
		return users;
	}

	public void setUsers(String users) {
		this.users = users;
	}
	
	

}
