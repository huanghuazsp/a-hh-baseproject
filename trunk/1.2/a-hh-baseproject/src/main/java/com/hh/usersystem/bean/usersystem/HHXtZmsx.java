package com.hh.usersystem.bean.usersystem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.hh.hibernate.util.base.BaseOneEntity;
import com.hh.usersystem.util.steady.StaticProperties;

/**
 * HhXtJs entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "HH_XT_ZMSX")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class HHXtZmsx extends BaseOneEntity {

	private String vzmbj = StaticProperties.HHXT_USERSYSTEM_ZMBJ;
	private int pageSize = 15;
	private String defaultOrgId;
	private String theme;
	private String desktopType;

	@Column(length = 64)
	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	@Column(name = "page_size")
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getDefaultOrgId() {
		return defaultOrgId;
	}

	public void setDefaultOrgId(String defaultOrgId) {
		this.defaultOrgId = defaultOrgId;
	}

	public String getVzmbj() {
		return vzmbj;
	}

	public void setVzmbj(String vzmbj) {
		this.vzmbj = vzmbj;
	}

	@Column(name="DESKTOP_TYPE",length=16)
	public String getDesktopType() {
		return desktopType;
	}

	public void setDesktopType(String desktopType) {
		this.desktopType = desktopType;
	}

}