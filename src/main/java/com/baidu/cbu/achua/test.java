package com.baidu.cbu.achua;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class test
 */
@WebServlet("/test")
public class test extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public test() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String httpMethod = request.getParameter("httpMethod");
		String host = request.getParameter("host");
		String URI = request.getParameter("URI");
		String headers = request.getParameter("headers");
		String queryString = request.getParameter("queryString");
		String body = request.getParameter("body");
		int expiresInSeconds = Integer.parseInt(request.getParameter("expiresInSeconds"));
		String accessKey = request.getParameter("accessKey");
		String secretKey = request.getParameter("secretKey");
		String date = request.getParameter("date");
		String option = request.getParameter("option");
		boolean run = "check".equals(option);
		Date ddate = new Date();

		PrintWriter out = response.getWriter();

		if (!"now".equals(date) && date != null && !"".equals(date)) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				ddate = formatter.parse(date);
			} catch (ParseException e) {
				out.println("Wrong time format");
				return;
			}
		}

		// InterfaceTest.interfaceInvoke(httpMethod, host, URI, queryString,
		// body, accessKey, secretKey, expiresInSeconds,
		// ddate, out, run);
		InterfaceTest.interfaceInvoke2(httpMethod, host, URI, headers, queryString, body, accessKey, secretKey,
				expiresInSeconds, ddate, out, run);
	}
}
