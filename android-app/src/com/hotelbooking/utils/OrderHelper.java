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

import com.alipay.sdk.app.PayTask;
import com.hotelbooking.OrderConfirmActivity;
import com.hotelbooking.model.Hotel;
import com.hotelbooking.model.House;

public class OrderHelper {
	
	private static final int RQF_PAY = 1;

	private static final int RQF_LOGIN = 2;
	
	private static final int SDK_PAY_FLAG = 1;

	private static final int SDK_CHECK_FLAG = 2;
	
	public static void orderByPage(Context context, 
			String name, String message, Hotel hotel, House house,
			int count, Date checkinDate, Date checkoutDate)
	{
	}
	
	public static void order(final Activity activity, final Handler handler,
			String name, String message, Hotel hotel, House house,
			int count, Date checkinDate, Date checkoutDate)
	{
		try {
			Log.i("ExternalPartner", "onItemClick");
			String info = getNewOrderInfo(name, message, hotel, house, count, checkinDate, checkoutDate);
			String sign = SignUtils.sign(info, Keys.PRIVATE);
			sign = URLEncoder.encode(sign, "UTF-8");

			final String payInfo = info + "&sign=\"" + sign + "\"&"
					+ getSignType();
			new Thread() {
				public void run() {
					
					PayTask alipay = new PayTask(activity);
					// 调用支付接口
					String result = alipay.pay(payInfo);

					Message msg = new Message();
					msg.what = SDK_PAY_FLAG;
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
		
		// 合作者身份ID
		String orderInfo = "partner=" + "\"" + Keys.DEFAULT_PARTNER + "\"";

		// 卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + Keys.DEFAULT_SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + getSubjectString(hotel, house, count, checkinDate, checkoutDate) + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + getBodyString(name, message, hotel, house, count, checkinDate, checkoutDate) + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + getTotalFee(house, count, checkinDate, checkoutDate) + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + Const.AliPayNofityURL
				+ "\"";

		// 接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
//		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
		
//		StringBuilder sb = new StringBuilder();
//		sb.append("partner=\"");
//		sb.append(Keys.DEFAULT_PARTNER);
//		sb.append("\"&out_trade_no=\"");
//		sb.append(getOutTradeNo());
//		sb.append("\"&subject=\"");
//		sb.append(URLEncoder.encode(getSubjectString(hotel, house, count, checkinDate, checkoutDate)));
//		sb.append("\"&body=\"");
//		sb.append(getBodyString(name, message, hotel, house, count, checkinDate, checkoutDate));
//		sb.append("\"&total_fee=\"");
//		sb.append(getTotalFee(house, count, checkinDate, checkoutDate));
//		sb.append("\"&notify_url=\"");
//
//		// 网址需要做URL编码
//		sb.append(URLEncoder.encode(Const.AliPayNofityURL));
//		sb.append("\"&service=\"mobile.securitypay.pay");
//		sb.append("\"&_input_charset=\"UTF-8");
////		sb.append("\"&return_url=\"");
////		sb.append(URLEncoder.encode("http://m.alipay.com"));
//		sb.append("\"&payment_type=\"1");
//		sb.append("\"&seller_id=\"");
//		sb.append(Keys.DEFAULT_SELLER);
//
//		// 如果show_url值为空，可不传
//		// sb.append("\"&show_url=\"");
//		sb.append("\"&it_b_pay=\"30m");
//		sb.append("\"");
//
//		return new String(sb);
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
			obj.put("house_id", house.getId());
			obj.put("house_num", count);
			obj.put("checkin_date", DateFormater.format2(checkinDate));
			obj.put("checkout_date", DateFormater.format2(checkoutDate));
			obj.put("request_massage", message);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj.toString();
	}
	
	private static String getTotalFee(House house, int count, Date checkinDate, Date checkoutDate)
	{
		DecimalFormat df = new DecimalFormat("0.00");
//		double fee = house.getPrice() * count * DateFormater.getDiffDays(checkinDate, checkoutDate);
		// for test
		double fee = 0.01 * count * DateFormater.getDiffDays(checkinDate, checkoutDate);
		return df.format(fee);
		
	}
	
	

	
	public static String getOrderDateString(Date checkinDate, Date checkoutDate)
	{
		String result = "";
		int days = DateFormater.getDiffDays(checkinDate, checkoutDate);
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
			statusString = "酒店确认中";
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
