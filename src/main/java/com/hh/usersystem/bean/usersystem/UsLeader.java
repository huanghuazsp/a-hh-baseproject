package com.hh.usersystem.bean.usersystem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.hh.hibernate.util.base.BaseOneEntity;


@Entity
@Table(name = "US_LEADER")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UsLeader extends BaseOneEntity {
	private String objectId;
	private String leaderId;
	private int objectType;
	
	private String leaderText;
	private String leaderVdzyj;
	private String leaderVdh;

	private String objectText;
	
	private String node;
	
	@Column(name = "OBJECT_ID", length = 36)
	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	@Column(name = "LEADER_ID", length = 36)
	public String getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}

	@Column(name = "OBJECT_TYPE")
	public int getObjectType() {
		return objectType;
	}

	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}

	@Transient
	public String getLeaderText() {
		return leaderText;
	}

	public void setLeaderText(String leaderText) {
		this.leaderText = leaderText;
	}

	@Transient
	public String getLeaderVdzyj() {
		return leaderVdzyj;
	}

	public void setLeaderVdzyj(String leaderVdzyj) {
		this.leaderVdzyj = leaderVdzyj;
	}

	@Transient
	public String getLeaderVdh() {
		return leaderVdh;
	}

	public void setLeaderVdh(String leaderVdh) {
		this.leaderVdh = leaderVdh;
	}

	@Transient
	public String getObjectText() {
		return objectText;
	}

	public void setObjectText(String objectText) {
		this.objectText = objectText;
	}
	@Transient
	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	
}