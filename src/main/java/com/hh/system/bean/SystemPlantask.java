package com.hh.system.bean;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Lob;
import com.hh.hibernate.util.base.*;
import com.hh.hibernate.dao.inf.Order;

@Order
@SuppressWarnings("serial")
@Entity
@Table(name = "SYSTEM_PLANTASK")
public class SystemPlantask extends BaseTwoEntity {
	// name
	private String name;

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// valid
	private int valid;

	@Column(name = "VALID")
	public int getValid() {
		return valid;
	}

	public void setValid(int valid) {
		this.valid = valid;
	}


	// hour
	private int hour;

	@Column(name = "HOUR")
	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	// minute
	private int minute;

	@Column(name = "MINUTE")
	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	// second
	private int second;

	@Column(name = "SECOND")
	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}

	// hourRegister
	private int hourRegister;

	@Column(name = "HOUR_REGISTER")
	public int getHourRegister() {
		return hourRegister;
	}

	public void setHourRegister(int hourRegister) {
		this.hourRegister = hourRegister;
	}

	// minuteRegister
	private int minuteRegister;

	@Column(name = "MINUTE_REGISTER")
	public int getMinuteRegister() {
		return minuteRegister;
	}

	public void setMinuteRegister(int minuteRegister) {
		this.minuteRegister = minuteRegister;
	}

	// secondRegister
	private int secondRegister;

	@Column(name = "SECOND_REGISTER")
	public int getSecondRegister() {
		return secondRegister;
	}

	public void setSecondRegister(int secondRegister) {
		this.secondRegister = secondRegister;
	}

	// formula
	private String formula;

	@Lob
	@Column(name = "FORMULA")
	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

}