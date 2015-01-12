package com.hotelbooking.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "app_code")
public class Code {

	@Id
	@GeneratedValue
	private int id;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "is_used")
	private int isUsed;
	
	@Column(name = "used_time")
	private Timestamp usedTime;
	
	@Column(name = "cut_fee")
	private double cutFee;
	
	public Code() {
	}

	public Code(int id, String code, double cutFee) {
		super();
		this.id = id;
		this.code = code;
		this.isUsed = 0;
		this.cutFee = cutFee;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(int isUsed) {
		this.isUsed = isUsed;
	}

	public Timestamp getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(Timestamp usedTime) {
		this.usedTime = usedTime;
	}

	public double getCutFee() {
		return cutFee;
	}

	public void setCutFee(double cutFee) {
		this.cutFee = cutFee;
	}
	
	
	
	
	
}
