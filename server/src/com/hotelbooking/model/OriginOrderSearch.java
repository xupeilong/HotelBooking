package com.hotelbooking.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OrderProcess_searchorder")
public class OriginOrderSearch {

	@Id
	private int id;
	
	private int orderId_id;
	
	private String ordernumber;
	
	private String hotelname;
	
	private String housingname;
	
	private String clientname;
	
	private Date indate;
	
	private Date outdate;
	
	private String orderfromnumber;
	
	private String source;
	
	public OriginOrderSearch() {
	}

	
	
	public OriginOrderSearch(int orderId_id, String ordernumber,
			String hotelname, String housingname, String clientname,
			Date indate, Date outdate, String source) {
		super();
		this.orderId_id = orderId_id;
		this.ordernumber = ordernumber;
		this.hotelname = hotelname;
		this.housingname = housingname;
		this.clientname = clientname;
		this.indate = indate;
		this.outdate = outdate;
		this.orderfromnumber = "None";
		this.source = source;
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

	public String getOrdernumber() {
		return ordernumber;
	}

	public void setOrdernumber(String ordernumber) {
		this.ordernumber = ordernumber;
	}

	public String getHotelname() {
		return hotelname;
	}

	public void setHotelname(String hotelname) {
		this.hotelname = hotelname;
	}

	public String getHousingname() {
		return housingname;
	}

	public void setHousingname(String housingname) {
		this.housingname = housingname;
	}

	public String getClientname() {
		return clientname;
	}

	public void setClientname(String clientname) {
		this.clientname = clientname;
	}

	public Date getIndate() {
		return indate;
	}

	public void setIndate(Date indate) {
		this.indate = indate;
	}

	public Date getOutdate() {
		return outdate;
	}

	public void setOutdate(Date outdate) {
		this.outdate = outdate;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}



	public String getOrderfromnumber() {
		return orderfromnumber;
	}



	public void setOrderfromnumber(String orderfromnumber) {
		this.orderfromnumber = orderfromnumber;
	}
	
	
}
