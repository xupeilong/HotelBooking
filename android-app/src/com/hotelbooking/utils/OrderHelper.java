package com.hotelbooking.utils;

import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.alipay.android.app.sdk.AliPay;
import com.hotelbooking.model.Hotel;
import com.hotelbooking.model.House;

public class OrderHelper {
	
	private static final int RQF_PAY = 1;

	private static final int RQF_LOGIN = 2;
	
	public static void order(final Activity activity, final Handler handler, String name, String message, Hotel hotel, House house, int count, Date checkinDate, Date checkoutDate)
	{
		try {
			Log.i("ExternalPartner", "onItemClick");
			String info = getNewOrderInfo(name, message, hotel, house, count, checkinDate, checkoutDate);
			String sign = Rsa.sign(info, Keys.PRIVATE);
			sign = URLEncoder.encode(sign);
			info += "&sign=\"" + sign + "\"&" + getSignType();
			Log.i("ExternalPartner", "start pay");
			// start the pay.

			final String orderInfo = info;
			new Thread() {
				public void run() {
					AliPay alipay = new AliPay(activity, handler);
					
					//设置为沙箱模式，不设置默认为线上环境
					//alipay.setSandBox(true);

					String result = alipay.pay(orderInfo);

					Message msg = new Message();
					msg.what = RQF_PAY;
					msg.obj = result;
					handler.sendMessage(msg);
				}
			}.start();

		} catch (Exception ex) {
			ex.printStackTrace();
			Toast.makeText(activity, "失败",
					Toast.LENGTH_SHORT).show();
		}
	}
	
	private static String getSignType() {
		return "sign_type=\"RSA\"";
	}
	
	private static String getNewOrderInfo(String name, String message, Hotel hotel, House house, int count, Date checkinDate, Date checkoutDate) {
		StringBuilder sb = new StringBuilder();
		sb.append("partner=\"");
		sb.append(Keys.DEFAULT_PARTNER);
		sb.append("\"&out_trade_no=\"");
		sb.append(getOutTradeNo());
		sb.append("\"&subject=\"");
		sb.append(URLEncoder.encode(getSubjectString(hotel, house, count, checkinDate, checkoutDate)));
		sb.append("\"&body=\"");
		sb.append(getBodyString(name, message, hotel, house, count, checkinDate, checkoutDate));
		sb.append("\"&total_fee=\"");
		sb.append(getTotalFee(house, count, checkinDate, checkoutDate));
		sb.append("\"&notify_url=\"");

		// 网址需要做URL编码
		sb.append(URLEncoder.encode(Const.AliPayNofityURL));
		sb.append("\"&service=\"mobile.securitypay.pay");
		sb.append("\"&_input_charset=\"UTF-8");
//		sb.append("\"&return_url=\"");
//		sb.append(URLEncoder.encode("http://m.alipay.com"));
		sb.append("\"&payment_type=\"1");
		sb.append("\"&seller_id=\"");
		sb.append(Keys.DEFAULT_SELLER);

		// 如果show_url值为空，可不传
		// sb.append("\"&show_url=\"");
		sb.append("\"&it_b_pay=\"30m");
		sb.append("\"");

		return new String(sb);
	}
	
	private static String getOutTradeNo()
	{
		Date nowDate = new Date(System.currentTimeMillis());
		return DateFormater.format1(nowDate);
	}
	
	private static String getSubjectString(Hotel hotel, House house, int count,
			Date checkinDate, Date checkoutDate) {
		return hotel.getName() + "," + house.getName() + "," + count + "间,"
				+ DateFormater.getDiffDays(checkinDate, checkoutDate) + "晚,"
				+ DateFormater.format2(checkinDate) + "到" + DateFormater.format2(checkoutDate);
	}
	
	private static String getBodyString(String name, String message, Hotel hotel, House house, int count,
			Date checkinDate, Date checkoutDate)
	{
		JSONObject obj = new JSONObject();
		try {
			obj.put("user_id", Const.currentUser.getId());
			obj.put("name", name);
			obj.put("house_id", house.getBed());
			obj.put("house_num", count);
			obj.put("checkin_date", DateFormater.format2(checkinDate));
			obj.put("checkout_date", DateFormater.format2(checkoutDate));
			obj.put("request_massage", name);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj.toString();
	}
	
	private static String getTotalFee(House house, int count, Date checkinDate, Date checkoutDate)
	{
//		DecimalFormat df = new DecimalFormat(".00");
//		double fee = house.getPrice() * count * DateFormater.getDiffDays(checkinDate, checkoutDate);
//		return df.format(fee);
		
		//for test
		return "0.01";
	}
	
	

	
	public static String getOrderDateString(Date checkinDate, Date checkoutDate)
	{
		String result = "";
		int days = checkoutDate.getDay() - checkinDate.getDay();
		result = result + days + "晚  ";
		result = result + DateFormater.format3(checkinDate) + " - " + DateFormater.format3(checkoutDate);
		return result;
	}
	
	public static String getOrderHouseString(int houseCount, String houseName)
	{
		return houseCount + "间 " + houseName; 
	}
	
	
	
	public static String getStatusString(int statusCode)
	{
		String statusString;
		switch (statusCode) {
		case 2:
			statusString = "待处理";
			break;
		case 1:
			statusString = "已发出预定请求";
			break;
		case 0:
			statusString = "预定成功";
			break;
		case 3:
			statusString = "已取消";
			break;
		case 5:
			statusString = "特殊订单";
			break;
		default:
			statusString = "未知";
			break;
		}
		return statusString;
	}
}
