package com.hotelbooking.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "app_refund_log")
public class AliRefundLog {

	@Id
	private int id;
	
	private String detail_data;
	private int refund_batch_no;
	private String refund_date;
	
	public AliRefundLog() {
	}
	
	

	public AliRefundLog(String detail_data, int refund_batch_no,
			String refund_date) {
		super();
		this.detail_data = detail_data;
		this.refund_batch_no = refund_batch_no;
		this.refund_date = refund_date;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDetail_data() {
		return detail_data;
	}

	public void setDetail_data(String detail_data) {
		this.detail_data = detail_data;
	}

	public int getRefund_batch_no() {
		return refund_batch_no;
	}

	public void setRefund_batch_no(int refund_batch_no) {
		this.refund_batch_no = refund_batch_no;
	}

	public String getRefund_date() {
		return refund_date;
	}

	public void setRefund_date(String refund_date) {
		this.refund_date = refund_date;
	}
	
	
}
