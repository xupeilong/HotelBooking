package com.hotelbooking.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orderprocess_order")
public class OriginOrder {
	
	@Id
	@GeneratedValue
	private int id;
	
	@Column(name = "housingTypeId_id")
	private int houseId;
	
	@Column(name = "orderSource")
	private String orderSource;
	
	@Column(name = "orderNumber")
	private String orderNumber;
	
	@Column(name = "statusCode")
	private int stateCode;
	
	@Column(name = "statusMsg")
	private String stateMsg;
	
	@Column(name = "roomNum")
	private int roomNum;
	
	@Column(name = "payMoney")
	private int payMoney;
	
	@Column(name = "orderDate")
	private Date orderDate; 
	
	@Column(name = "checkInDate")
	private Date checkInDate;
	
	@Column(name = "checkOutDate")
	private Date checkOutDate;
	
	@Column(name = "customerName")
	private String customerName;
	
	@Column(name = "request")
	private String request;
	
	@Column(name = "everyDayPrice")
	private double everyDayPrice;
	
	@Column(name = "needInvoice")
	private boolean needInvoice;
	
	@Column(name = "bedTypePreference")
	private int bedTypePreference;
	
	@Column(name = "breakfast")
	private int breakfast;
	
	@Column(name = "bedType")
	private String bedType;
	
	@Column(name = "deposit")
	private int deposit;
	
	
	public OriginOrder() {
	}
	
	

	public OriginOrder(int houseId, String orderSource, String orderNumber,
			int stateCode, int roomNum, int payMoney, Date orderDate,
			Date checkInDate, Date checkOutDate, String customerName,
			String request) {
		super();
		this.houseId = houseId;
		this.orderSource = orderSource;
		this.orderNumber = orderNumber;
		this.stateCode = stateCode;
		this.roomNum = roomNum;
		this.payMoney = payMoney;
		this.orderDate = orderDate;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.customerName = customerName;
		this.request = request;
		this.stateMsg = "None";
		this.everyDayPrice = 0;
		this.needInvoice = false;
		this.bedTypePreference = 1;
		this.breakfast = 1;
		this.bedType = "1";
		this.deposit = 1;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHouseId() {
		return houseId;
	}

	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public int getStateCode() {
		return stateCode;
	}

	public void setStateCode(int stateCode) {
		this.stateCode = stateCode;
	}

	public int getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}

	public int getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(int payMoney) {
		this.payMoney = payMoney;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	public Date getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	} 
	
	
}
