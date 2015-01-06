package com.hotelbooking.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hotelbooking.dao.BaseDAO;
import com.hotelbooking.dao.BaseDAO;
import com.hotelbooking.dao.HotelDAO;
import com.hotelbooking.model.Hotel;
import com.hotelbooking.service.HotelService;

/**
 * Servlet implementation class GetHotelList
 */
@WebServlet("/GetHotelList")
public class GetHotelList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetHotelList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int pageNum = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("count"));
		int latitude = 0;
		int longitude = 0;
		String keyword = "";
		String cityName = "";
		int lowPrice = 0;
		int highPrice = 0;
		try {
			latitude = Integer.parseInt(request.getParameter("latitude"));
			longitude = Integer.parseInt(request.getParameter("longitude"));
		} catch (Exception e) {
			latitude = 0;
			longitude = 0;
		}
		
		try {
			cityName = request.getParameter("city");
			System.out.println("***param: city=" + cityName);
		} catch (Exception e) {
			cityName = "";
		}
		
		try {
			keyword = request.getParameter("keyword");
		} catch (Exception e) {
			keyword = "";
		}
		
		try {
			lowPrice = Integer.parseInt(request.getParameter("low"));
			highPrice = Integer.parseInt(request.getParameter("high"));
		} catch (Exception e) {
			lowPrice = 0;
			highPrice = 0;
		}
		
		String result = HotelService.getHotelsJsonString(pageNum, pageSize,
				latitude, longitude, cityName, keyword, lowPrice, highPrice);

		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(result);
		System.out.println("over");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
