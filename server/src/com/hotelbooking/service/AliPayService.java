package com.hotelbooking.service;

import java.text.DateFormat;
import java.util.Date;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONTokener;

import com.hotelbooking.dao.AliPayLogDAO;
import com.hotelbooking.model.AliPayLog;
import com.hotelbooking.model.Result;
import com.hotelbooking.util.DateFormater;

public class AliPayService {

	public static void saveAlipayLog(Date notifyTime, String notifyType,
			String notifyId, String signType, String sign, String outTradeNo,
			String subject, String paymentType, String tradeNo,
			String tradeStatus, String sellerId, String sellerEmail,
			String buyerId, String buyerEmail, double totalFee, String body,
			Date gmtCreate, Date gmtPayment) 
	{
		AliPayLogDAO aliPayLogDAO = new AliPayLogDAO();
		if (aliPayLogDAO.isTradeNoExists(outTradeNo))
			return;
		AliPayLog aliPayLog = new AliPayLog(notifyTime, notifyType, notifyId,
				signType, sign, outTradeNo, subject, paymentType, tradeNo,
				tradeStatus, sellerId, sellerEmail, buyerId, buyerEmail,
				totalFee, body, gmtCreate, gmtPayment);
		
		System.out.println("***fee " + totalFee);
		JSONTokener tokener = new JSONTokener(body);
		JSONObject obj = (JSONObject) tokener.nextValue();
		Result result = OrderService.placeOrder(obj.getInt("user_id"),
				obj.getString("name"),
				obj.getInt("house_id"),
				obj.getInt("house_num"),
				DateFormater.toDate(obj.getString("checkin_date")),
				DateFormater.toDate(obj.getString("checkout_date")),
						obj.getString("request_massage"),
						tradeNo,
						totalFee);
				
		
		aliPayLogDAO.save(aliPayLog);
		
	}

}
