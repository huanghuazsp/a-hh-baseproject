package com.hh.usersystem.bean.usersystem;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.hh.hibernate.dao.inf.Order;
import com.hh.hibernate.util.base.BaseTreeNodeEntity;

@SuppressWarnings("serial")
@Entity
@Table(name = "US_ORGANIZATION")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,region="eternal")
@Order(fields = "order", sorts = "asc")
public class UsOrganization    extends BaseTreeNodeEntity<UsOrganization> implements java.io.Serializable {
	@Override
	public String toString() {
		return this.getId();
	}
//	private String text;
//	private String icon;
//	private String node;
	private String code_;
	private String ms_;
	private String jc_;
	private String zdybm_;
	private int lx_;
	private String pname_;
	private String sjbm_;

//	private Organization bm;// 部门
//	private Organization jg ;// 机构
//	private Organization jt ;// 集团
	private String roleIds;
//	@Transient
//	public Organization getBm() {
//		return bm;
//	}
//
//	public void setBm(Organization bm) {
//		this.bm = bm;
//	}
//
//	@Transient
//	public Organization getJg() {
//		return jg;
//	}
//
//	public void setJg(Organization jg) {
//		this.jg = jg;
//	}
//
//	@Transient
//	public Organization getJt() {
//		return jt;
//	}
//
//	public void setJt(Organization jt) {
//		this.jt = jt;
//	}

//	@Column(length = 64)
//	public String getText() {
//		return text;
//	}
//
//	public void setText(String text) {
//		this.text = text;
//	}
//
//	@Column(length = 128)
//	public String getIcon() {
//		return icon;
//	}
//
//	public void setIcon(String icon) {
//		this.icon = icon;
//	}
//
//	@Column(name = "NODE", length = 128)
//	public String getNode() {
//		return this.node;
//	}
//
//	public void setNode(String node) {
//		this.node = node;
//	}

	@Column(length = 256)
	public String getCode_() {
		return code_;
	}

	public void setCode_(String code_) {
		this.code_ = code_;
	}

	@Column(length = 512)
	public String getMs_() {
		return ms_;
	}

	public void setMs_(String ms_) {
		this.ms_ = ms_;
	}

	@Column(precision = 1, scale = 0)
	public int getLx_() {
		return lx_;
	}

	public void setLx_(int lx_) {
		this.lx_ = lx_;
	}

	@Column(length = 16)
	public String getJc_() {
		return jc_;
	}

	public void setJc_(String jc_) {
		this.jc_ = jc_;
	}

	@Column(length = 32)
	public String getZdybm_() {
		return zdybm_;
	}

	public void setZdybm_(String zdybm_) {
		this.zdybm_ = zdybm_;
	}

	@Transient
	public String getPname_() {
		return pname_;
	}

	public void setPname_(String pname_) {
		this.pname_ = pname_;
	}

	@Transient
	public String getSjbm_() {
		return sjbm_;
	}

	public void setSjbm_(String sjbm_) {
		this.sjbm_ = sjbm_;
	}
	
	@Lob
	@Column(name="ROLE_IDS")
	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
}
