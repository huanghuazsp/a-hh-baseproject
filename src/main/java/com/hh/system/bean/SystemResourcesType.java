 package com.hh.system.bean;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Lob;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.hh.hibernate.util.base.*;
import com.hh.hibernate.dao.inf.Order;
@Entity
@Table(name="SYS_RESOURCES_TYPE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Order(fields = "order", sorts = "asc")
public class SystemResourcesType  extends BaseEntityTree<SystemResourcesType>{
	private int state;
	@Column(name="STATE_",columnDefinition = "int default 0")
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}