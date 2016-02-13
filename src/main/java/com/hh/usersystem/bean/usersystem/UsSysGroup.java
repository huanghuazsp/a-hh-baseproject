package com.hh.usersystem.bean.usersystem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.hh.hibernate.dao.inf.Order;
import com.hh.hibernate.util.base.BaseTreeNodeEntity;
import com.hh.hibernate.util.base.BaseTwoEntity;

@Entity
@Table(name = "US_SYS_GROUP")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Order(fields = "order", sorts = "asc")
public class UsSysGroup  extends BaseTreeNodeEntity<UsSysGroup> {
	private String remark;
	private String users;
	
	private String type;

	@Column(length = 1024)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	@Lob
	@Column(name="USERS")
	public String getUsers() {
		return users;
	}

	public void setUsers(String users) {
		this.users = users;
	}

	@Transient
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
