package com.hotelbooking.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hotelbooking.util.DateFormater;

@Entity
@Table(name = "app_alipay_notify_log")
public class AliPayLog {
	
	@Id
	@GeneratedValue
	int id;
	
	@Column(name = "notify_time")
	private Date notifyTime;
	
	@Column(name = "notify_type")
	private String notifyType;
	
	@Column(name = "notify_id")
	private String notifyId;
	
	@Column(name = "sign_type")
	private String signType;
	
	@Column(name = "sign")
	private String sign;
	
	@Column(name = "out_trade_no")
	private String outTradeNo;
	
	@Column(name = "subject")
	private String subject;
	
	@Column(name = "payment_type")
	private String paymentType;
	
	@Column(name = "trade_no")
	private String tradeNo;
	
	@Column(name = "trade_status")
	private String tradeStatus;
	
	@Column(name = "seller_id")
	private String sellerId;
	
	@Column(name = "seller_email")
	private String sellerEmail;
	
	@Column(name = "buyer_id")
	private String buyerId;
	
	@Column(name = "buyer_email")
	private String buyerEmail;
	
	@Column(name = "total_fee")
	private double totalFee;
	
	@Column(name = "body")
	private String body;
	
	@Column(name = "gmt_create")
	private Date gmtCreate;
	
	@Column(name = "gmt_payment")
	private Date gmtPayment;


	public AliPayLog() {
	}
	
	
	
	
	public AliPayLog(Date notifyTime, String notifyType, String notifyId,
			String signType, String sign, String outTradeNo, String subject,
			String paymentType, String tradeNo, String tradeStatus,
			String sellerId, String sellerEmail, String buyerId,
			String buyerEmail, double totalFee, String body, Date gmtCreate,
			Date gmtPayment) {
		super();
		this.notifyTime = notifyTime;
		this.notifyType = notifyType;
		this.notifyId = notifyId;
		this.signType = signType;
		this.sign = sign;
		this.outTradeNo = outTradeNo;
		this.subject = subject;
		this.paymentType = paymentType;
		this.tradeNo = tradeNo;
		this.tradeStatus = tradeStatus;
		this.sellerId = sellerId;
		this.sellerEmail = sellerEmail;
		this.buyerId = buyerId;
		this.buyerEmail = buyerEmail;
		this.totalFee = totalFee;
		this.body = body;
		this.gmtCreate = gmtCreate;
		this.gmtPayment = gmtPayment;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getNotifyTime() {
		return notifyTime;
	}

	public void setNotifyTime(Date notifyTime) {
		this.notifyTime = notifyTime;
	}

	public String getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}

	public String getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(String notifyId) {
		this.notifyId = notifyId;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerEmail() {
		return sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getBuyerEmail() {
		return buyerEmail;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}


	public double getTotalFee() {
		return totalFee;
	}




	public void setTotalFee(double totalFee) {
		this.totalFee = totalFee;
	}




	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}


	public Date getGmtPayment() {
		return gmtPayment;
	}


	public void setGmtPayment(Date gmtPayment) {
		this.gmtPayment = gmtPayment;
	}

	
}
