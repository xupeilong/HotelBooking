package com.hotelbooking.model;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OrderProcess_process")
public class OriginProcess {
	
	@Id
	private int id;
	
	private int orderId_id;
	
	private int orderStatus;
	
	private double orderPrice;
	
	private double orderTotalPrice;
	
	private String provider;
	
	private String processId;
	
	private Date processTime;
	
	private String hotelBackCode;
	
	private String accountId;
	
	private int incomeStatus;
	
	private int paymentStatus;
	
	private Date accountTime;
	
	private String add_unhandle_person;
	
	private Timestamp add_unhandle_time;
	
	private String unhandle_special_person;
	
	private Timestamp unhandle_special_time;
	
	private String special_send_person;
	
	private Timestamp special_send_time;
	
	private String unhandle_send_person;
	
	private Timestamp unhandle_send_time;
	
	private String send_handle_person;	
	
	private Timestamp send_handle_time;
	
	private double brokerage;
	
	private int predict_wrong;
	
	
	public OriginProcess() {
	}
	
	

	public OriginProcess(int orderId_id) {
		super();
		this.orderId_id = orderId_id;
		this.orderStatus = 2;
		this.orderPrice = -1;
		this.orderTotalPrice = -1;
		this.provider = "None";
		this.processId = "None";
		this.processTime = new Date(System.currentTimeMillis());
		this.hotelBackCode = "None";
		this.accountId = "None";
		this.incomeStatus = 0;
		this.paymentStatus = 0;
		this.accountTime = new Date(System.currentTimeMillis());
		this.add_unhandle_person = "None";
		this.add_unhandle_time = new Timestamp(System.currentTimeMillis());
		this.unhandle_send_person = "None";
		this.unhandle_send_time = new Timestamp(System.currentTimeMillis());
		this.send_handle_person = "None";
		this.send_handle_time = new Timestamp(System.currentTimeMillis());;
		this.brokerage = 0;
		this.predict_wrong = 0;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrderId_id() {
		return orderId_id;
	}

	public void setOrderId_id(int orderId_id) {
		this.orderId_id = orderId_id;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}

	public double getOrderTotalPrice() {
		return orderTotalPrice;
	}

	public void setOrderTotalPrice(double orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public Date getProcessTime() {
		return processTime;
	}

	public void setProcessTime(Date processTime) {
		this.processTime = processTime;
	}

	public String getHotelBackCode() {
		return hotelBackCode;
	}

	public void setHotelBackCode(String hotelBackCode) {
		this.hotelBackCode = hotelBackCode;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public int getIncomeStatus() {
		return incomeStatus;
	}

	public void setIncomeStatus(int incomeStatus) {
		this.incomeStatus = incomeStatus;
	}

	public int getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(int paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Date getAccountTime() {
		return accountTime;
	}

	public void setAccountTime(Date accountTime) {
		this.accountTime = accountTime;
	}



	public String getAdd_unhandle_person() {
		return add_unhandle_person;
	}



	public void setAdd_unhandle_person(String add_unhandle_person) {
		this.add_unhandle_person = add_unhandle_person;
	}





	public String getUnhandle_special_person() {
		return unhandle_special_person;
	}



	public void setUnhandle_special_person(String unhandle_special_person) {
		this.unhandle_special_person = unhandle_special_person;
	}




	public String getSpecial_send_person() {
		return special_send_person;
	}



	public void setSpecial_send_person(String special_send_person) {
		this.special_send_person = special_send_person;
	}





	public String getUnhandle_send_person() {
		return unhandle_send_person;
	}



	public void setUnhandle_send_person(String unhandle_send_person) {
		this.unhandle_send_person = unhandle_send_person;
	}




	public String getSend_handle_person() {
		return send_handle_person;
	}



	public void setSend_handle_person(String send_handle_person) {
		this.send_handle_person = send_handle_person;
	}



	public double getBrokerage() {
		return brokerage;
	}



	public void setBrokerage(double brokerage) {
		this.brokerage = brokerage;
	}






	public int isPredict_wrong() {
		return predict_wrong;
	}



	public void setPredict_wrong(int predict_wrong) {
		this.predict_wrong = predict_wrong;
	}



	public Timestamp getAdd_unhandle_time() {
		return add_unhandle_time;
	}



	public void setAdd_unhandle_time(Timestamp add_unhandle_time) {
		this.add_unhandle_time = add_unhandle_time;
	}



	public Timestamp getUnhandle_special_time() {
		return unhandle_special_time;
	}



	public void setUnhandle_special_time(Timestamp unhandle_special_time) {
		this.unhandle_special_time = unhandle_special_time;
	}



	public Timestamp getSpecial_send_time() {
		return special_send_time;
	}



	public void setSpecial_send_time(Timestamp special_send_time) {
		this.special_send_time = special_send_time;
	}



	public Timestamp getUnhandle_send_time() {
		return unhandle_send_time;
	}



	public void setUnhandle_send_time(Timestamp unhandle_send_time) {
		this.unhandle_send_time = unhandle_send_time;
	}



	public Timestamp getSend_handle_time() {
		return send_handle_time;
	}



	public void setSend_handle_time(Timestamp send_handle_time) {
		this.send_handle_time = send_handle_time;
	}
	
	
	
}
