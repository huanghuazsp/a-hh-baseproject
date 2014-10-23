package com.hh.system.bean;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.hh.hibernate.dao.inf.Order;
import com.hh.hibernate.util.base.BaseTreeNodeEntity;

@Entity(name = "SYS_DATA_TYPE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Order
public class SysDataType extends BaseTreeNodeEntity {
	private String type;

	@Column(name = "TYPE_", length = 32)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
