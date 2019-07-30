package com.niit.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

@WebServlet(name = "Register", urlPatterns = { "/Register" })
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(RegisterServlet.class);
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*Retrieve all details from request object
		 * 
		 * 
		 * 
		 */
		String errorMsg = null;
		if(email == null || email.equals("")){
			errorMsg = "Email ID can't be null or empty.";
		}
		if(password == null || password.equals("")){
			errorMsg = "Password can't be null or empty.";
		}
		if(name == null || name.equals("")){
			errorMsg = "Name can't be null or empty.";
		}
		if(country == null || country.equals("")){
			errorMsg = "Country can't be null or empty.";
		}
		
		if(errorMsg != null){
			
			PrintWriter out= response.getWriter();
			out.println("<font color=red>"+errorMsg+"</font>");
			
			// Redirect to /register.html using Request Dispatcher.
			
		}else{
		
		Connection con = (Connection) getServletContext().getAttribute("DBConnection");
		PreparedStatement ps = null;
		try {
			
			// Sql statement to insert Records 
			ps = con.prepareStatement("");
			// Set all data to ps
			
			ps.execute();
			
			logger.info("User registered with email="+email);
			
			//forward to login page to login
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
			PrintWriter out= response.getWriter();
			out.println("<font color=green>Registration successful, please login below.</font>");
			rd.include(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Database connection problem");
			throw new ServletException("DB Connection problem.");
		}finally{
			try {
				ps.close();
			} catch (SQLException e) {
				logger.error("SQLException in closing PreparedStatement");
			}
		}
		}
		
	}

}
