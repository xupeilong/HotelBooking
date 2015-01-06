package com.hotelbooking.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hotelbooking.util.DateFormater;

@Entity
@Table(name = "OrderProcess_order")
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
	
	private String orderFromNumber;
	
	private int incidentalExpenses;
	
	@Column(name = "statusCode")
	private int stateCode;
	
	private int qunarStatusCode;
	
	@Column(name = "statusMsg")
	private String stateMsg;
	
	private int payTypeCode;
	
	private String payTypeMsg;
	
	@Column(name = "roomNum")
	private int roomNum;
	
	@Column(name = "payMoney")
	private int payMoney;
	
	private String cityName;
	
	@Column(name = "orderDate")
	private Date orderDate; 
	
	@Column(name = "checkInDate")
	private Date checkInDate;
	
	@Column(name = "checkOutDate")
	private Date checkOutDate;
	
	private int extendStay;
	
	@Column(name = "customerName")
	private String customerName;
	
	private String contactName;
	
	private String contactPhone;
	
	private String contactEmail;
	
	@Column(name = "request")
	private String request;
	
	private String remark;
	
	private String logs;
	
	private String customerIp;
	
	@Column(name = "everyDayPrice")
	private double everyDayPrice;
	
	private String everyDayPriceStr;
	
	private String arriveTime;
	
	@Column(name = "needInvoice")
	private boolean needInvoice;
	
	private int invoiceType;
	
	private String invoiceContent;
	
	private String invoiceHead;
	
	private String invoiceContactName;
	
	private String invoicePhone;
	
	private String invoiceProvince;
	
	private String invoiceCity;
	
	private String invoiceArea;
	
	private String invoiceAddress;
	
	private String invoiceDispatch;
	
	private int invoiceFee;
	
	@Column(name = "bedTypePreference")
	private int bedTypePreference;
	
	private int promotionCode;
	
	private int redPacketMoney;
	
	private int cashBackMoney;
	
	@Column(name = "breakfast")
	private int breakfast;
	
	private int isVouch;
	
	private int vouchMoney;
	
	private int changeRule;
	
	private Date lastCancelTime;
	
	@Column(name = "bedType")
	private String bedType;
	
	@Column(name = "deposit")
	private int deposit;
	
	private String contactPhoneKey;
	
	
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
		this.orderFromNumber = "None";
		this.incidentalExpenses = 0;
		this.stateCode = stateCode;
		this.qunarStatusCode = -1;
		this.stateMsg = "None";
		this.payTypeCode = 0;
		this.payTypeMsg = "";
		this.roomNum = roomNum;
		this.payMoney = payMoney;
		this.cityName = "";
		this.orderDate = orderDate;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.extendStay = 0;
		this.customerName = customerName;
		this.contactName = "";
		this.contactPhone = "";
		this.contactEmail = "";
		this.request = request;
		this.remark = "";
		this.logs = "";
		this.customerIp = "";
		this.everyDayPrice = 0;
		this.everyDayPriceStr = "";
		this.arriveTime = DateFormater.format2(checkInDate);
		this.needInvoice = false;
		this.invoiceType = 0;
		this.invoiceContent = "";
		this.invoiceHead = "";
		this.invoiceContactName = "";
		this.invoicePhone = "";
		this.invoiceProvince = "";
		this.invoiceCity = "";
		this.invoiceArea = "";
		this.invoiceAddress = "";
		this.invoiceDispatch = "";
		this.invoiceFee = 0;
		this.bedTypePreference = 1;
		this.promotionCode = 0;
		this.redPacketMoney = 0;
		this.cashBackMoney = 0;
		this.breakfast = 1;
		this.isVouch = 0;
		this.vouchMoney = 0;
		this.changeRule = 0;
		this.lastCancelTime = checkInDate;
		this.bedType = "1";
		this.deposit = 1;
		this.contactPhoneKey = "";
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



	public String getOrderFromNumber() {
		return orderFromNumber;
	}



	public void setOrderFromNumber(String orderFromNumber) {
		this.orderFromNumber = orderFromNumber;
	}



	public int getIncidentalExpenses() {
		return incidentalExpenses;
	}



	public void setIncidentalExpenses(int incidentalExpenses) {
		this.incidentalExpenses = incidentalExpenses;
	}



	public int getQunarStatusCode() {
		return qunarStatusCode;
	}



	public void setQunarStatusCode(int qunarStatusCode) {
		this.qunarStatusCode = qunarStatusCode;
	}



	public String getStateMsg() {
		return stateMsg;
	}



	public void setStateMsg(String stateMsg) {
		this.stateMsg = stateMsg;
	}



	public int getPayTypeCode() {
		return payTypeCode;
	}



	public void setPayTypeCode(int payTypeCode) {
		this.payTypeCode = payTypeCode;
	}



	public String getPayTypeMsg() {
		return payTypeMsg;
	}



	public void setPayTypeMsg(String payTypeMsg) {
		this.payTypeMsg = payTypeMsg;
	}



	public String getCityName() {
		return cityName;
	}



	public void setCityName(String cityName) {
		this.cityName = cityName;
	}



	public int getExtendStay() {
		return extendStay;
	}



	public void setExtendStay(int extendStay) {
		this.extendStay = extendStay;
	}



	public String getContactName() {
		return contactName;
	}



	public void setContactName(String contactName) {
		this.contactName = contactName;
	}



	public String getContactPhone() {
		return contactPhone;
	}



	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}



	public String getContactEmail() {
		return contactEmail;
	}



	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}



	public String getRemark() {
		return remark;
	}



	public void setRemark(String remark) {
		this.remark = remark;
	}



	public String getLogs() {
		return logs;
	}



	public void setLogs(String logs) {
		this.logs = logs;
	}



	public String getCustomerIp() {
		return customerIp;
	}



	public void setCustomerIp(String customerIp) {
		this.customerIp = customerIp;
	}



	public double getEveryDayPrice() {
		return everyDayPrice;
	}



	public void setEveryDayPrice(double everyDayPrice) {
		this.everyDayPrice = everyDayPrice;
	}



	public String getEveryDayPriceStr() {
		return everyDayPriceStr;
	}



	public void setEveryDayPriceStr(String everyDayPriceStr) {
		this.everyDayPriceStr = everyDayPriceStr;
	}



	public String getArriveTime() {
		return arriveTime;
	}



	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}



	public boolean isNeedInvoice() {
		return needInvoice;
	}



	public void setNeedInvoice(boolean needInvoice) {
		this.needInvoice = needInvoice;
	}



	public int getInvoiceType() {
		return invoiceType;
	}



	public void setInvoiceType(int invoiceType) {
		this.invoiceType = invoiceType;
	}



	public String getInvoiceContent() {
		return invoiceContent;
	}



	public void setInvoiceContent(String invoiceContent) {
		this.invoiceContent = invoiceContent;
	}



	public String getInvoiceHead() {
		return invoiceHead;
	}



	public void setInvoiceHead(String invoiceHead) {
		this.invoiceHead = invoiceHead;
	}



	public String getInvoiceContactName() {
		return invoiceContactName;
	}



	public void setInvoiceContactName(String invoiceContactName) {
		this.invoiceContactName = invoiceContactName;
	}



	public String getInvoicePhone() {
		return invoicePhone;
	}



	public void setInvoicePhone(String invoicePhone) {
		this.invoicePhone = invoicePhone;
	}



	public String getInvoiceProvince() {
		return invoiceProvince;
	}



	public void setInvoiceProvince(String invoiceProvince) {
		this.invoiceProvince = invoiceProvince;
	}



	public String getInvoiceCity() {
		return invoiceCity;
	}



	public void setInvoiceCity(String invoiceCity) {
		this.invoiceCity = invoiceCity;
	}



	public String getInvoiceArea() {
		return invoiceArea;
	}



	public void setInvoiceArea(String invoiceArea) {
		this.invoiceArea = invoiceArea;
	}



	public String getInvoiceAddress() {
		return invoiceAddress;
	}



	public void setInvoiceAddress(String invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
	}



	public String getInvoiceDispatch() {
		return invoiceDispatch;
	}



	public void setInvoiceDispatch(String invoiceDispatch) {
		this.invoiceDispatch = invoiceDispatch;
	}



	public int getInvoiceFee() {
		return invoiceFee;
	}



	public void setInvoiceFee(int invoiceFee) {
		this.invoiceFee = invoiceFee;
	}



	public int getBedTypePreference() {
		return bedTypePreference;
	}



	public void setBedTypePreference(int bedTypePreference) {
		this.bedTypePreference = bedTypePreference;
	}



	public int getPromotionCode() {
		return promotionCode;
	}



	public void setPromotionCode(int promotionCode) {
		this.promotionCode = promotionCode;
	}



	public int getRedPacketMoney() {
		return redPacketMoney;
	}



	public void setRedPacketMoney(int redPacketMoney) {
		this.redPacketMoney = redPacketMoney;
	}



	public int getCashBackMoney() {
		return cashBackMoney;
	}



	public void setCashBackMoney(int cashBackMoney) {
		this.cashBackMoney = cashBackMoney;
	}



	public int getBreakfast() {
		return breakfast;
	}



	public void setBreakfast(int breakfast) {
		this.breakfast = breakfast;
	}



	public int getIsVouch() {
		return isVouch;
	}



	public void setIsVouch(int isVouch) {
		this.isVouch = isVouch;
	}



	public int getVouchMoney() {
		return vouchMoney;
	}



	public void setVouchMoney(int vouchMoney) {
		this.vouchMoney = vouchMoney;
	}



	public int getChangeRule() {
		return changeRule;
	}



	public void setChangeRule(int changeRule) {
		this.changeRule = changeRule;
	}



	public Date getLastCancelTime() {
		return lastCancelTime;
	}



	public void setLastCancelTime(Date lastCancelTime) {
		this.lastCancelTime = lastCancelTime;
	}



	public String getBedType() {
		return bedType;
	}



	public void setBedType(String bedType) {
		this.bedType = bedType;
	}



	public int getDeposit() {
		return deposit;
	}



	public void setDeposit(int deposit) {
		this.deposit = deposit;
	}



	public String getContactPhoneKey() {
		return contactPhoneKey;
	}



	public void setContactPhoneKey(String contactPhoneKey) {
		this.contactPhoneKey = contactPhoneKey;
	} 
	
	
}
