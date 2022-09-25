package com.atm.application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
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


public class AccountCreation extends HttpServlet {
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
			con = DriverManager.getConnection(url, un, pwd);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
    }
        
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String Fname = request.getParameter("Fname");
		String Mname = request.getParameter("Mname");
		String Lname = request.getParameter("Lname");
		String mobNum = request.getParameter("mobNum");
		String Email = request.getParameter("Email");
		String userName = request.getParameter("userName");
		String pinNum = request.getParameter("pinNum");
		String date = request.getParameter("dob");
		String gender = request.getParameter("gender");
		
		
		
		String query = "insert into customer (Fname, Mname, Lname, mobNum, Email, userName,"
				+ " pinNum, dob, gender) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, Fname);
			pstmt.setString(2, Mname);
			pstmt.setString(3, Lname);
			pstmt.setString(4, mobNum);
			pstmt.setString(5, Email);
			pstmt.setString(6, userName);
			pstmt.setString(7, pinNum);
			pstmt.setDate(8, Date.valueOf(date));
			pstmt.setString(9, gender);
			int op = pstmt.executeUpdate();
			
			System.out.println(op);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/accountcreated.jsp");
		rd.include(request, response);
	}
	
	@Override
	public void destroy() {
		try {
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
