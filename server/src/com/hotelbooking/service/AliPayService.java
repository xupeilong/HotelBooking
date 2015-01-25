package com.hotelbooking.service;

import java.text.DateFormat;
import java.util.Date;

import org.dom4j.Document;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONTokener;

import com.hotelbooking.dao.AliPayLogDAO;
import com.hotelbooking.dao.AlipayWapPreorderDAO;
import com.hotelbooking.model.AliPayLog;
import com.hotelbooking.model.AlipayWapPreorder;
import com.hotelbooking.model.Result;
import com.hotelbooking.util.DateFormater;

public class AliPayService {

	public static void saveAlipayWapPreorder(String body, String outTradeNo)
	{
		AlipayWapPreorderDAO alipayWapPreorderDAO = new AlipayWapPreorderDAO();
		alipayWapPreorderDAO.savePreorder(new AlipayWapPreorder(body, outTradeNo));
	}
	
	public static void placeAlipayWapPreorder(Document notifyData)
	{
		String notifyTimeString = notifyData.selectSingleNode("//notify/notify_time").getText();
		Date notifyTime = DateFormater.toDate(notifyTimeString);
		String notifyType = notifyData.selectSingleNode("//notify/notify_type").getText();
		String notifyId = notifyData.selectSingleNode("//notify/notify_id").getText();
		String signType = "none";
		String sign = "none";
		String outTradeNo = notifyData.selectSingleNode("//notify/out_trade_no").getText();
		String subject = notifyData.selectSingleNode("//notify/subject").getText();
		String paymentType = notifyData.selectSingleNode("//notify/payment_type").getText();
		String tradeNo = notifyData.selectSingleNode("//notify/trade_no").getText();
		String tradeStatus = notifyData.selectSingleNode("//notify/trade_status").getText();
		String sellerId = notifyData.selectSingleNode("//notify/seller_id").getText();
		String sellerEmail = notifyData.selectSingleNode("//notify/seller_email").getText();
		String buyerId = notifyData.selectSingleNode("//notify/buyer_id").getText();
		String buyerEmail = notifyData.selectSingleNode("//notify/buyer_email").getText();
		String totalFeeString = notifyData.selectSingleNode("//notify/total_fee").getText();
		Double totalFee = Double.parseDouble(totalFeeString);
		
		AlipayWapPreorderDAO alipayWapPreorderDAO = new AlipayWapPreorderDAO();
		AlipayWapPreorder preorder = alipayWapPreorderDAO.getByOutTradeNo(outTradeNo);
		if (preorder == null)
			return;
		String body = preorder.getBody();
		
		String gmtCreateString = notifyData.selectSingleNode("//notify/gmt_create").getText();
		Date gmtCreate = DateFormater.toDate(gmtCreateString);
		String gmtPaymentString = notifyData.selectSingleNode("//notify/gmt_payment").getText();
		Date gmtPayment = DateFormater.toDate(gmtPaymentString);
		
		saveAlipayLog(notifyTime, notifyType, notifyId, signType,
				sign, outTradeNo, subject, paymentType, tradeNo, tradeStatus,
				sellerId, sellerEmail, buyerId, buyerEmail, totalFee, body,
				gmtCreate, gmtPayment);
	}
	
	public static void saveAlipayLog(Date notifyTime, String notifyType,
			String notifyId, String signType, String sign, String outTradeNo,
			String subject, String paymentType, String tradeNo,
			String tradeStatus, String sellerId, String sellerEmail,
			String buyerId, String buyerEmail, double totalFee, String body,
			Date gmtCreate, Date gmtPayment) 
	{
		AliPayLogDAO aliPayLogDAO = new AliPayLogDAO();
		if (aliPayLogDAO.isNotifyExists(notifyId))
			return;
		AliPayLog aliPayLog = new AliPayLog(notifyTime, notifyType, notifyId,
				signType, sign, outTradeNo, subject, paymentType, tradeNo,
				tradeStatus, sellerId, sellerEmail, buyerId, buyerEmail,
				totalFee, body, gmtCreate, gmtPayment);
		aliPayLogDAO.save(aliPayLog);
		
		if (tradeStatus.equals("TRADE_SUCCESS"))
		{
			System.out.println("***fee " + totalFee);
			JSONTokener tokener = new JSONTokener(body);
			JSONObject obj = (JSONObject) tokener.nextValue();
			String code = null;
			try {
				code = obj.getString("code");
			} catch (Exception e) {
				// TODO: handle exception
			}
			Result result = OrderService.placeOrder(obj.getInt("user_id"),
					obj.getString("name"),
					obj.getInt("house_id"),
					obj.getInt("house_num"),
					DateFormater.toDate(obj.getString("checkin_date")),
					DateFormater.toDate(obj.getString("checkout_date")),
							obj.getString("request_massage"),
							code,
							tradeNo,
							outTradeNo,
							totalFee);
		}
	}

}
