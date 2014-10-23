package com.hotelbooking.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hotelbooking.model.Result;
import com.hotelbooking.service.OrderService;
import com.hotelbooking.service.UserService;
import com.hotelbooking.util.DateFormater;

/**
 * Servlet implementation class Order
 */
@WebServlet("/Order")
public class Order extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Order() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int userId = Integer.parseInt(request.getParameter("user_id"));
		String name = request.getParameter("name");
		int houseId = Integer.parseInt(request.getParameter("house_id"));
		int houseNumber = Integer.parseInt(request.getParameter("house_num"));
		String checkInDateString = request.getParameter("checkin_date");
		String checkOutDateString = request.getParameter("checkout_date");
		String requestMessage = request.getParameter("request_massage");
		Date checkInDate = DateFormater.toDate(checkInDateString);
		Date checkOutDate = DateFormater.toDate(checkOutDateString);
		
		Result result = OrderService.placeOrder(userId, name, houseId, houseNumber, checkInDate, checkOutDate, requestMessage);
		
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(result.toJsonString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
