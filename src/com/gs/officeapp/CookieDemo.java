package com.gs.officeapp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieDemo extends HttpServlet {

	private static final long serialVersionUID = -5877797332321996207L;
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		 
		res.setContentType("text/html");
//		PrintWriter pw = res.getWriter();
 
		Cookie cookie = new Cookie("url","sunderamg dot com");
		cookie.setMaxAge(60 * 60); //1 hour
		
		Cookie c = new Cookie("gstest", "Testing Success");
		res.addCookie(c);
		res.addCookie(cookie);
 
//		pw.println("Cookies created");
		req.getRequestDispatcher("/cookies.jsp").forward(req, res);
	}
	
	/**
	 * HTTP Method Delete is not supported by html forms. So that is handled via a hidden form field set to "true" and delegated
	 * to the doDelete method. Html Forms only support GET and POST. Note that XmlHttpRequest supports all the six
	 * methods, though.
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		if ("true".equalsIgnoreCase(req.getParameter("delete"))) {
			doDelete(req, res); return;
		}
		
		Cookie[] cookies = req.getCookies();
		
		if (cookies != null) {
			System.out.println("No of cookies : " + cookies.length);
			
			for(Cookie cookie : cookies) {
				System.out.println("Name : " + cookie.getName());
				System.out.println("Value : " + cookie.getValue());
				System.out.println("Path : " + cookie.getPath());
				
				if (cookie.getName().equalsIgnoreCase("gstest")) { 
					cookie.setValue("Always Success");
					res.addCookie(cookie);
				}
			}
		}
		
		req.getRequestDispatcher("/cookies.jsp").forward(req, res);
	}
	
	protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		System.out.println("name " + req.getParameter("cookiename"));
		Cookie[] cookies = req.getCookies();
		
		for(Cookie cookie : cookies) {
			if (cookie.getName().equalsIgnoreCase(req.getParameter("cookiename"))) {
				cookie.setMaxAge(0); //Set max age to 0 to delete the cookie!
				res.addCookie(cookie);
				break;
			}
		}
		
		req.getRequestDispatcher("/cookies.jsp").forward(req, res);
	}

}
