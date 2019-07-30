package com.niit.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.niit.util.User;

@WebServlet(name = "Login", urlPatterns = { "/Login" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(LoginServlet.class);
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Write code to retrive data from  rquest parameter
		String errorMsg=null;
		
		// Write code to do validation for null input or required validation
		if(errorMsg != null){
			
			PrintWriter out= response.getWriter();
			out.println("<font color=red>"+errorMsg+"</font>");
			
			// Create object of RequestDispatcher 
			// Redirect it to /Login.html 
			
		}else{
		
		Connection con = (Connection) getServletContext().getAttribute("DBConnection");
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// complete prepareStatement 
			ps = con.prepareStatement("");
			ps.setString(1, email);
			ps.setString(2, password);
			
			
			// execute ps to get rs
			
			if(rs != null){
				rs.next();
				// Create user object from rs.
				
				User user = new User();
				// code goes here 
				logger.info("User found with details="+user);
				
				// Create HttpSession Object 
				
				session.setAttribute("User", user);
				response.sendRedirect("home.jsp");;
			}else{
				
				// Create Request dispatcher to point to /login.html
				
				PrintWriter out= response.getWriter();
				logger.error("User not found with email="+email);
				out.println("<font color=red>No user found with given email id, please register first.</font>");
				rd.include(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Database connection problem");
			throw new ServletException("DB Connection problem.");
		}finally{
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				logger.error("SQLException in closing PreparedStatement or ResultSet");;
			}
			
		}
		}
	}

}
