package com.hotelbooking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "app_order")
public class UserOrder {
	
	@Id
	private int id;
	
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "origin_order_id")
	private int originOrderId;
	
	private String ali_pay_trade_no;
	
	private double total_fee;
	
	@Transient
	private OriginOrder originOrder;
	
	public UserOrder() {
	}
	

	
	public UserOrder(int userId, int originOrderId, String ali_pay_trade_no,
			double total_fee) {
		super();
		this.userId = userId;
		this.originOrderId = originOrderId;
		this.ali_pay_trade_no = ali_pay_trade_no;
		this.total_fee = total_fee;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getOriginOrderId() {
		return originOrderId;
	}

	public void setOriginOrderId(int originOrderId) {
		originOrderId = originOrderId;
	}

	public OriginOrder getOriginOrder() {
		return originOrder;
	}

	public void setOriginOrder(OriginOrder originOrder) {
		this.originOrder = originOrder;
	}

	public String getAli_pay_trade_no() {
		return ali_pay_trade_no;
	}

	public void setAli_pay_trade_no(String ali_pay_trade_no) {
		this.ali_pay_trade_no = ali_pay_trade_no;
	}

	public double getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(double total_fee) {
		this.total_fee = total_fee;
	}
	
	
	
	
}
