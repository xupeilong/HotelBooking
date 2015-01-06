package com.hotelbooking.servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Date;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hotelbooking.dao.AliRefundLogDAO;
import com.hotelbooking.dao.OrderDAO;
import com.hotelbooking.model.AliRefundLog;
import com.hotelbooking.model.UserOrder;
import com.hotelbooking.util.Const;
import com.hotelbooking.util.DateFormater;
import com.hotelbooking.util.Rsa;

/**
 * Servlet implementation class RefundService
 */
@WebServlet("/RefundService")
public class RefundService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RefundService() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int orderId = Integer.parseInt(request.getParameter("order_id"));
		String callback = request.getParameter("callback");
		OrderDAO orderDAO = new OrderDAO();
		UserOrder userOrder = orderDAO.getUserOdrByOriginOrdId(orderId);
		String batch_no = getTodayNewBatchNoString(userOrder.getAli_pay_trade_no() + "^" + userOrder.getTotal_fee() + "^" + "refund");
		String refund_date = DateFormater.format_refund(new Date(System.currentTimeMillis()));
		String paramsForSign = 
				 "_input_charset=" + "utf-8"
				+ "&batch_no=" + batch_no
				+ "&batch_num=" + "1"
				+ "&detail_data=" + userOrder.getAli_pay_trade_no() + "^" + userOrder.getTotal_fee() + "^" + "refund"
				+ "&notify_url=" + callback
				+ "&partner=" + Const.DEFAULT_PARTNER
				+ "&refund_date=" + refund_date
				+ "&seller_user_id=" + Const.DEFAULT_PARTNER
				+ "&service=" + "refund_fastpay_by_platform_pwd";
		String params =   
				 "_input_charset=" + "utf-8"
				+ "&batch_no=" + batch_no
				+ "&batch_num=" + "1"
				+ "&detail_data=" + userOrder.getAli_pay_trade_no() + "^" + userOrder.getTotal_fee() + "^" + "refund"
				+ "&notify_url=" + URLEncoder.encode(callback)
				+ "&partner=" + Const.DEFAULT_PARTNER
				+ "&refund_date=" + refund_date
				+ "&seller_user_id=" + Const.DEFAULT_PARTNER
				+ "&service=" + "refund_fastpay_by_platform_pwd";
		System.out.println(paramsForSign);
		System.out.println(params);
		String signType = "&sign_type=" + "RSA";
		String sign = "&sign=" + URLEncoder.encode(Rsa.sign(paramsForSign, Const.PRIVATE));
		params = params + signType + sign;
		String url = "https://mapi.alipay.com/gateway.do?" + params;
		response.sendRedirect(url);
	}

	private String getTodayNewBatchNoString(String detail_data)
	{
		Date today = new Date(System.currentTimeMillis());
		String todayString = DateFormater.format_batch_no_part(today);
		AliRefundLogDAO aliRefundLogDAO = new AliRefundLogDAO();
		int no = aliRefundLogDAO.getTodayNewBatchNo();
		aliRefundLogDAO.save(new AliRefundLog(detail_data, no, todayString));
		String t = "000" + no;
		int l = t.length();
		return todayString + t.substring(l - 3, l);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
