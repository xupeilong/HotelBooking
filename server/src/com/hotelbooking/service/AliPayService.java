package com.hotelbooking.service;

import java.util.Date;

import com.hotelbooking.dao.AliPayLogDAO;
import com.hotelbooking.model.AliPayLog;

public class AliPayService {

	public static void saveAlipayLog(Date notifyTime, String notifyType,
			String notifyId, String signType, String sign, String outTradeNo,
			String subject, String paymentType, String tradeNo,
			String tradeStatus, String sellerId, String sellerEmail,
			String buyerId, String buyerEmail, double totalFee, String body,
			Date gmtCreate, Date gmtPayment) 
	{

		AliPayLog aliPayLog = new AliPayLog(notifyTime, notifyType, notifyId,
				signType, sign, outTradeNo, subject, paymentType, tradeNo,
				tradeStatus, sellerId, sellerEmail, buyerId, buyerEmail,
				totalFee, body, gmtCreate, gmtPayment);
		AliPayLogDAO aliPayLogDAO = new AliPayLogDAO();
		aliPayLogDAO.save(aliPayLog);
		
	}

}
