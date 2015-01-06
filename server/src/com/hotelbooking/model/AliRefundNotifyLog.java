package com.hotelbooking.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "app_refund_notify_log")
public class AliRefundNotifyLog {

	@Id
	private int id;
	
	private String refund_batch_no;
	
	private String refund_date;
	
	private String refund_result;
	
	private String detail_data;
	
	public AliRefundNotifyLog() {
	}
	
	

	public AliRefundNotifyLog(String refund_batch_no, String refund_date,
			String refund_result, String detail_data) {
		super();
		this.refund_batch_no = refund_batch_no;
		this.refund_date = refund_date;
		this.refund_result = refund_result;
		this.detail_data = detail_data;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getRefund_batch_no() {
		return refund_batch_no;
	}



	public void setRefund_batch_no(String refund_batch_no) {
		this.refund_batch_no = refund_batch_no;
	}



	public String getRefund_date() {
		return refund_date;
	}

	public void setRefund_date(String refund_date) {
		this.refund_date = refund_date;
	}

	public String getRefund_result() {
		return refund_result;
	}

	public void setRefund_result(String refund_result) {
		this.refund_result = refund_result;
	}

	public String getDetail_data() {
		return detail_data;
	}

	public void setDetail_data(String detail_data) {
		this.detail_data = detail_data;
	}
	
	
}
