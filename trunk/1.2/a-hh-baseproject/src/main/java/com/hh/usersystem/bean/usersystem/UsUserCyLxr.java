package com.hh.usersystem.bean.usersystem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.hh.hibernate.dao.inf.Order;
import com.hh.hibernate.util.base.BaseTwoEntity;

@Entity
@Table(name = "US_USER_CYLXR")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Order
public class UsUserCyLxr extends BaseTwoEntity {

	private String yhId;
	private String cylxrId;
	private String cylxrName;
	private String headpic;
	private int type;

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

	@Column(name = "CYLXR_NAME", length = 64)
	public String getCylxrName() {
		return cylxrName;
	}

	public void setCylxrName(String cylxrName) {
		this.cylxrName = cylxrName;
	}

	@Column(name = "HEADPIC")
	public String getHeadpic() {
		return headpic;
	}

	public void setHeadpic(String headpic) {
		this.headpic = headpic;
	}

	@Column(name = "TYPE_")
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}