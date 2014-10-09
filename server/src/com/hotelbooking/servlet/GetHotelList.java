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
		String sortType = request.getParameter("type");
		int pageNum = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("count"));
		
		String result = HotelService.getHotelsJsonString(sortType, pageNum, pageSize, 0, 0, 0, 0);
		
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(result);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
