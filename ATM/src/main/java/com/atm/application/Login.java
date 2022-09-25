package com.atm.application;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String url = null;
	String un = null;
	String pwd = null;
	Connection con;
	PreparedStatement pstmt;
	ResultSet res;
       
    
    @Override
    public void init(ServletConfig config) throws ServletException {
    	ServletContext scon = config.getServletContext();
    	url = scon.getInitParameter("url");
    	un = scon.getInitParameter("un");
    	pwd = scon.getInitParameter("pwd");
    	
    	
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url,un, pwd);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
    	
    }
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String query ="select * from customer where userName=? and pinNum=?";
		String userName = request.getParameter("userName");
		String pin = request.getParameter("pinNum");
		int pinNum = Integer.parseInt(pin);
		try {
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, userName);
			pstmt.setInt(2, pinNum);
			res = pstmt.executeQuery();
			
			
			if(res.next()) {
				RequestDispatcher rd = request.getRequestDispatcher("url of logged in html");
				rd.include(request, response);
			} else {
				PrintWriter writer = response.getWriter();
				response.setContentType("text/html");
				writer.println("<h3>Invalid credentials. Try again!</h3>");
				RequestDispatcher rd = request.getRequestDispatcher("index.html");
				rd.forward(request, response);
				writer.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void destroy() {
		try {
			res.close();
			pstmt.close();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
