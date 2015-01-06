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
					// ����֧���ӿ�
					String result = alipay.pay(payInfo);

					Message msg = new Message();
					msg.what = SDK_PAY_FLAG;
					msg.obj = result;
					handler.sendMessage(msg);
					
				}
			}.start();

		} catch (Exception ex) {
			ex.printStackTrace();
			Toast.makeText(activity, "ʧ��",
					Toast.LENGTH_SHORT).show();
		}
	}
	
	private static String getSignType() {
		return "sign_type=\"RSA\"";
	}
	
	
	private static String getNewOrderInfo(String name, String message, Hotel hotel, House house, int count, Date checkinDate, Date checkoutDate) {
		
		// ���������ID
		String orderInfo = "partner=" + "\"" + Keys.DEFAULT_PARTNER + "\"";

		// ����֧�����˺�
		orderInfo += "&seller_id=" + "\"" + Keys.DEFAULT_SELLER + "\"";

		// �̻���վΨһ������
		orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

		// ��Ʒ����
		orderInfo += "&subject=" + "\"" + getSubjectString(hotel, house, count, checkinDate, checkoutDate) + "\"";

		// ��Ʒ����
		orderInfo += "&body=" + "\"" + getBodyString(name, message, hotel, house, count, checkinDate, checkoutDate) + "\"";

		// ��Ʒ���
		orderInfo += "&total_fee=" + "\"" + getTotalFee(house, count, checkinDate, checkoutDate) + "\"";

		// �������첽֪ͨҳ��·��
		orderInfo += "&notify_url=" + "\"" + Const.AliPayNofityURL
				+ "\"";

		// �ӿ����ƣ� �̶�ֵ
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// ֧�����ͣ� �̶�ֵ
		orderInfo += "&payment_type=\"1\"";

		// �������룬 �̶�ֵ
		orderInfo += "&_input_charset=\"utf-8\"";

		// ����δ����׵ĳ�ʱʱ��
		// Ĭ��30���ӣ�һ����ʱ���ñʽ��׾ͻ��Զ����رա�
		// ȡֵ��Χ��1m��15d��
		// m-���ӣ�h-Сʱ��d-�죬1c-���죨���۽��׺�ʱ����������0��رգ���
		// �ò�����ֵ������С���㣬��1.5h����ת��Ϊ90m��
		orderInfo += "&it_b_pay=\"30m\"";

		// ֧��������������󣬵�ǰҳ����ת���̻�ָ��ҳ���·�����ɿ�
//		orderInfo += "&return_url=\"m.alipay.com\"";

		// �������п�֧���������ô˲���������ǩ���� �̶�ֵ
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
//		// ��ַ��Ҫ��URL����
//		sb.append(URLEncoder.encode(Const.AliPayNofityURL));
//		sb.append("\"&service=\"mobile.securitypay.pay");
//		sb.append("\"&_input_charset=\"UTF-8");
////		sb.append("\"&return_url=\"");
////		sb.append(URLEncoder.encode("http://m.alipay.com"));
//		sb.append("\"&payment_type=\"1");
//		sb.append("\"&seller_id=\"");
//		sb.append(Keys.DEFAULT_SELLER);
//
//		// ���show_urlֵΪ�գ��ɲ���
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
		return hotel.getName() + "," + house.getName() + "," + count + "��,"
				+ DateFormater.getDiffDays(checkinDate, checkoutDate) + "��,"
				+ DateFormater.format2(checkinDate) + "��" + DateFormater.format2(checkoutDate);
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
		result = result + days + "��  ";
		result = result + DateFormater.format3(checkinDate) + " - " + DateFormater.format3(checkoutDate);
		return result;
	}
	
	public static String getOrderHouseString(int houseCount, String houseName)
	{
		return houseCount + "�� " + houseName; 
	}
	
	
	
	public static String getStatusString(int statusCode)
	{
		String statusString;
		switch (statusCode) {
		case 2:
			statusString = "������";
			break;
		case 1:
			statusString = "�Ƶ�ȷ����";
			break;
		case 0:
			statusString = "Ԥ���ɹ�";
			break;
		case 3:
			statusString = "��ȡ��";
			break;
		case 5:
			statusString = "���ⶩ��";
			break;
		default:
			statusString = "δ֪";
			break;
		}
		return statusString;
	}
}
