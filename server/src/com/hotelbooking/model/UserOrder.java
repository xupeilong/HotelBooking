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
	
	@Transient
	private OriginOrder originOrder;
	
	public UserOrder() {
	}
	
	public UserOrder(int userId, int originOrderId) {
		this.userId = userId;
		this.originOrderId = originOrderId;
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
	
	
	
	
}
