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
import java.sql.SQLException;

public class admissionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String url = "jdbc:mysql://localhost:3306/account";
    private static final String user = "root";
    private static final String pass = "";

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		
		String name = request.getParameter("name");
		String contact = request.getParameter("contact");
		String email = request.getParameter("email");
		String fname = request.getParameter("fname");
		String mname = request.getParameter("mname");
		String state = request.getParameter("state");
        String password = request.getParameter("password");
        String cpassword = request.getParameter("cpassword");
        if(password.equals(cpassword)) {
        	try (
        			Connection conn = DriverManager.getConnection(url, user, pass)) {
                String sql = "INSERT INTO admissionform (name,contact,email,fname,mname,state,password) VALUES (?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, name);
                    ps.setString(2, contact);
                    ps.setString(3, email);
                    ps.setString(4, fname);
                    ps.setString(5, mname);
                    ps.setString(6, state);
                    ps.setString(7, password);
                    int rowsInserted = ps.executeUpdate();
                    if (rowsInserted > 0) {
                    	
                        response.setContentType("text/html");
                        
                        out.println("<h2>Register successful! Apply Now: </h2>");
                    	RequestDispatcher rd = request.getRequestDispatcher("Application.html");
                    	rd.include(request, response);
                    } else {
                    	out.println("<h5 style='color: red';>Error: Application form Failed</h5>");
                    	RequestDispatcher rd = request.getRequestDispatcher("Application.html");
                    	rd.include(request, response);
                    }
                }
            } catch (SQLException e) {
            	out.println("<h4 style='color: red'> Error: "+ e.getMessage()+ " </h4>");
//                e.printStackTrace();
//                throw new ServletException("Database access error", e);
            }
        }
        else {
        	out.println("<h5 style='color: red';>Error: Password Doesn't Match(Mismathed)</h5>");
        	RequestDispatcher rd = request.getRequestDispatcher("Appication.html");
        	rd.include(request, response);
        	}
	
	}

}