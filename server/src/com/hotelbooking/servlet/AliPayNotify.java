package com.hotelbooking.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hotelbooking.service.AliPayService;
import com.hotelbooking.util.DateFormater;

/**
 * Servlet implementation class AliPayNotify
 */
@WebServlet("/AliPayNotify")
public class AliPayNotify extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AliPayNotify() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Date notifyTime = null;
		String notifyType = null;
		String notifyId = null;
		String signType = null;
		String sign = null;
		String outTradeNo = null;
		String subject = null;
		String paymentType = null;
		String tradeNo = null;
		String tradeStatus = null;
		String sellerId = null;
		String sellerEmail = null;
		String buyerId = null;
		String buyerEmail = null;
		double totalFee = 0;
		String body = null;
		Date gmtCreate = null;
		Date gmtPayment = null;
		
		try {
			notifyTime = DateFormater.toDate(request.getParameter("notify_time"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			notifyType = request.getParameter("notify_type");
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			notifyId = request.getParameter("notify_id");
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			signType = request.getParameter("sign_type");
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			sign = request.getParameter("sign");
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			outTradeNo = request.getParameter("out_trade_no");
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			subject = request.getParameter("subject");
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			paymentType = request.getParameter("payment_type");
		} catch (Exception e) {
		}
		try {
			tradeNo = request.getParameter("trade_no");
		} catch (Exception e) {
		}
		try {
			tradeStatus = request.getParameter("trade_status");
		} catch (Exception e) {
		}
		try {
			sellerId = request.getParameter("seller_id");
		} catch (Exception e) {
		}
		try {
			sellerEmail = request.getParameter("seller_email");
		} catch (Exception e) {
		}
		try {
			buyerId = request.getParameter("buyer_id");
		} catch (Exception e) {
		}
		try {
			buyerEmail = request.getParameter("buyer_email");
		} catch (Exception e) {
		}
		try {
			totalFee = Double.parseDouble(request.getParameter("total_fee"));
		} catch (Exception e) {
		}
		try {
			body = request.getParameter("body");
		} catch (Exception e) {
		}
		try {
			gmtCreate = DateFormater.toDate(request.getParameter("gmt_create"));
		} catch (Exception e) {
		}
		try {
			gmtPayment = DateFormater.toDate(request.getParameter("gmt_payment"));
		} catch (Exception e) {
		}
		
		AliPayService.saveAlipayLog(notifyTime, notifyType, notifyId, signType,
				sign, outTradeNo, subject, paymentType, tradeNo, tradeStatus,
				sellerId, sellerEmail, buyerId, buyerEmail, totalFee, body,
				gmtCreate, gmtPayment);

		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write("success");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
