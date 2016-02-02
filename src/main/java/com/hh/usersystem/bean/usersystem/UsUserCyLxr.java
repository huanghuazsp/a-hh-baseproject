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
@Table(name = "US_USER_CYLXR")
public class UsUserCyLxr extends BaseOneEntity implements java.io.Serializable {

	private String yhId;
	private String cylxrId;

	// Constructors

	/** default constructor */
	public UsUserCyLxr() {
	}



	@Column(name = "YH_ID", nullable = false, length = 36)
	public String getYhId() {
		return this.yhId;
	}

	public void setYhId(String yhId) {
		this.yhId = yhId;
	}


	@Column(name = "CYLXR_ID", nullable = false, length = 36)
	public String getCylxrId() {
		return cylxrId;
	}

	public void setCylxrId(String cylxrId) {
		this.cylxrId = cylxrId;
	}
	


}