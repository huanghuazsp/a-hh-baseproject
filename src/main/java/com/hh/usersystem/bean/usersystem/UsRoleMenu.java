package com.hh.usersystem.bean.usersystem;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.hh.hibernate.util.base.BaseTwoEntity;

/**
 * HhXtJsCd entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "US_ROLE_MENU")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UsRoleMenu extends BaseTwoEntity implements java.io.Serializable {

	// Fields
	private String jsId;

	@Column(name = "JS_ID", nullable = false, length = 36)
	public String getJsId() {
		return this.jsId;
	}

	public void setJsId(String jsId) {
		this.jsId = jsId;
	}
	private String cdId;
	@Column(name = "HHXTCD_ID", length = 36)
	public String getCdId() {
		return this.cdId;
	}

	public void setCdId(String cdId) {
		this.cdId = cdId;
	}

}