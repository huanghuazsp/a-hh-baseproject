package com.hh.system.bean;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.hh.hibernate.dao.inf.Order;
import com.hh.hibernate.util.base.BaseEntityTree;

@Entity(name = "SYS_DATA_TYPE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Order(fields = "order", sorts = "asc")
public class SysDataType extends BaseEntityTree {
	private String code;

	@Column(name = "CODE_", length = 1024)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
