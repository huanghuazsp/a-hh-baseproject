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
@Table(name = "HH_XT_YH_GROUP")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class HhXtYhGroup extends BaseTwoEntity implements java.io.Serializable {

	private String yhId;
	private String groupId;

	public HhXtYhGroup() {
	}

	@Column(name = "YH_ID", nullable = false, length = 36)
	public String getYhId() {
		return this.yhId;
	}

	public void setYhId(String yhId) {
		this.yhId = yhId;
	}

	@Column(name = "GROUP_ID", nullable = false, length = 36)
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

}