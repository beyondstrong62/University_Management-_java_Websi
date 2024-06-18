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

public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String url = "jdbc:mysql://localhost:3306/account";
    private static final String user = "root";
    private static final String pass = "";

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		
		String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String cpassword = request.getParameter("cpassword");
        if(password.equals(cpassword)) {
        	try (
        			Connection conn = DriverManager.getConnection(url, user, pass)) {
                String sql = "INSERT INTO credential (name, email, password) VALUES (?, ?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, name);
                    ps.setString(2, email);
                    ps.setString(3, password);
                    int rowsInserted = ps.executeUpdate();
                    if (rowsInserted > 0) {
                    	
                        response.setContentType("text/html");
                        
                        out.println("<html><body>");
                        out.println("<h2>Register successful! Login Now: </h2>");
                        out.println("</body></html>");
                    	RequestDispatcher rd = request.getRequestDispatcher("login.html");
                    	rd.include(request, response);
                    } else {
                    	out.println("<h5 style='color: red';>Error: Registration Failed</h5>");
                    	RequestDispatcher rd = request.getRequestDispatcher("register.html");
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
        	RequestDispatcher rd = request.getRequestDispatcher("register.html");
        	rd.include(request, response);
        	}
	
	}

}
