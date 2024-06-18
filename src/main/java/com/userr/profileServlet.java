package com.userr;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class profileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String url = "jdbc:mysql://localhost:3306/account";
    private static final String user = "root";
    private static final String pass = "";
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		String name = request.getParameter("name");
        String password = request.getParameter("password");
        
        try {
        	Connection conn = DriverManager.getConnection(url, user, pass);
        	String sql = "select name, password from admissionform where name=? and password=?";
        	PreparedStatement ps = conn.prepareStatement(sql);
        	ps.setString(1,name);
        	ps.setString(2,password);
        	ResultSet rs = ps.executeQuery();
        	if(rs.next()) {
        		RequestDispatcher rd = request.getRequestDispatcher("profileShow.html");
        		rd.forward(request, response);
        	}
        	else {
        		out.println("<h3> Credential Invalid - Try Again</h3>");
        		RequestDispatcher rd = request.getRequestDispatcher("profile.html");
        		rd.include(request, response);
        	}
        	
        }
        catch(Exception e) {
        	
        	
        }
	}

}
