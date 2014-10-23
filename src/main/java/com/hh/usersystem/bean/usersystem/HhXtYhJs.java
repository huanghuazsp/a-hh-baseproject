package com.hh.usersystem.bean.usersystem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.hh.hibernate.util.base.BaseTwoEntity;

/**
 * HhXtYhJs entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "HH_XT_YH_JS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class HhXtYhJs extends BaseTwoEntity implements java.io.Serializable {

	private String yhId;
	private String jsId;

	// Constructors

	/** default constructor */
	public HhXtYhJs() {
	}



	@Column(name = "YH_ID", nullable = false, length = 36)
	public String getYhId() {
		return this.yhId;
	}

	public void setYhId(String yhId) {
		this.yhId = yhId;
	}

	@Column(name = "JS_ID", nullable = false, length = 36)
	public String getJsId() {
		return this.jsId;
	}

	public void setJsId(String jsId) {
		this.jsId = jsId;
	}

}