package com.hotelbooking.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hotelbooking.dao.BaseDAO;
import com.hotelbooking.dao.OrderDAO;
import com.hotelbooking.model.AliRefundNotifyLog;
import com.hotelbooking.model.OriginOrder;
import com.hotelbooking.model.UserOrder;
import com.hotelbooking.util.Const;
import com.hotelbooking.util.DateFormater;

/**
 * Servlet implementation class AliRefundNotity
 */
@WebServlet("/AliRefundNotity")
public class AliRefundNotity extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AliRefundNotity() {
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
		String batch_no = null;
		String success_num = null;
		String result_details = null;
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
			batch_no = request.getParameter("batch_no");
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			success_num = request.getParameter("success_num");
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			result_details = request.getParameter("result_details");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		String verifyUrl = "https://mapi.alipay.com/gateway.do?service=notify_verify&partner="
				+ Const.DEFAULT_PARTNER
				+ "&notify_id="
				+ notifyId;
		System.out.println("*******************REFUND INFO: " + verifyUrl);
		URL rearUrl = new URL(verifyUrl); 
		URLConnection connection = rearUrl.openConnection();
		connection.connect();
		BufferedReader in = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
		System.out.println("*******************REFUND INFO: READER:" + in);
        String res = in.readLine();
        System.out.println("*******************REFUND INFO: RES: " + res);
        if (res.equals("true"))
		{		
			String now = DateFormater.format_refund(new java.sql.Date(System.currentTimeMillis()));
			BaseDAO baseDAO = new BaseDAO();
			baseDAO.save(new AliRefundNotifyLog(batch_no, now, success_num, result_details));
			
			String orderNo = result_details.split("\\^")[0];
			String resultStatus = result_details.split("\\^")[2];
			if (resultStatus.endsWith("SUCCESS"))
			{
				OrderDAO orderDAO = new OrderDAO();
				UserOrder userOrder = orderDAO.getUserOdrByPayNo(orderNo);
				OriginOrder originOrder = userOrder.getOriginOrder();
				originOrder.setStateCode(3);	// cancel
				baseDAO.update(originOrder);
			}
			
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			writer.write("success");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	

}
