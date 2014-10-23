package com.hh.usersystem.bean.usersystem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.hh.hibernate.util.base.BaseOneEntity;

/**
 * HhXtYhJs entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "HH_XT_YH_ORG")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class HhXtYhOrg extends BaseOneEntity implements java.io.Serializable {

	private String yhId;
	private String orgId;

	// Constructors

	/** default constructor */
	public HhXtYhOrg() {
	}



	@Column(name = "YH_ID", nullable = false, length = 36)
	public String getYhId() {
		return this.yhId;
	}

	public void setYhId(String yhId) {
		this.yhId = yhId;
	}


	@Column(name = "ORG_ID", nullable = false, length = 36)
	public String getOrgId() {
		return orgId;
	}



	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	


}