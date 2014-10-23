package com.hh.system.bean;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.hh.hibernate.dao.inf.Order;
import com.hh.hibernate.util.base.BaseTreeNodeEntity;

@Entity(name = "SYS_DATA")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Order
public class SysData extends BaseTreeNodeEntity {
	private String dataTypeId;

	@Column(name = "DATA_TYPE_ID", length = 36)
	public String getDataTypeId() {
		return dataTypeId;
	}

	public void setDataTypeId(String dataTypeId) {
		this.dataTypeId = dataTypeId;
	}

}
