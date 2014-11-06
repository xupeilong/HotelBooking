

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;


/**
 * Servlet implementation class AliPayNotify
 */
@WebServlet("/AliPayNotify")
public class AliPayNotify extends HttpServlet {
	
	public static final String DEFAULT_PARTNER = "2088411857583121";
	
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
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		BaseDAO baseDAO = new BaseDAO();
		String url = request.getRequestURL().toString();
		String query = ""; 
		Map<String, String[]> p = request.getParameterMap();
		String notifyId = p.get("notify_id")[0];
		for (String name: p.keySet())
			query = query + name + "=" + p.get(name)[0] + "&";
		if (query != null)
			url = url + "?" + query;
		

		String verifyUrl = "https://mapi.alipay.com/gateway.do?service=notify_verify&partner="
				+ DEFAULT_PARTNER
				+ "&notify_id="
				+ notifyId;
		System.out.println("*******************PAY INFO: " + verifyUrl);
		URL rearUrl = new URL(verifyUrl); 
		URLConnection connection = rearUrl.openConnection();
		connection.connect();
		BufferedReader in = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
		System.out.println("*******************PAY INFO: READER:" + in);
        String res = in.readLine();
        System.out.println("*******************PAY INFO: RES: " + res);
		if (res.equals("true"))
		{
	        baseDAO.save(new Request(url));
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
