package com.hh.usersystem.bean.usersystem;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.hh.hibernate.dao.inf.Order;
import com.hh.hibernate.util.base.BaseTwoEntity;

/**
 * HhXtYhCdZmtb entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "US_USER_MENU_ZMTB")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Order
public class UsUserMenuZmtb extends BaseTwoEntity {
	private String cdId;
	@Column(name = "HHXTCD_ID", length = 36)
	public String getCdId() {
		return this.cdId;
	}

	public void setCdId(String cdId) {
		this.cdId = cdId;
	}
	private String yhId;


	@Column(name = "YH_ID", length = 36)
	public String getYhId() {
		return this.yhId;
	}

	public void setYhId(String yhId) {
		this.yhId = yhId;
	}

}