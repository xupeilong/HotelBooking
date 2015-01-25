package com.hotelbooking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "app_alipay_wap_preorder")
public class AlipayWapPreorder {

	@Id
	@GeneratedValue
	private int id;
	
	@Column(name = "body")
	private String body;
	
	@Column(name= "out_trade_no")
	private String outTradeNo;
	
	public AlipayWapPreorder() {
	}

	public AlipayWapPreorder(String body, String outTradeNo) {
		super();
		this.body = body;
		this.outTradeNo = outTradeNo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	
	
	
}
