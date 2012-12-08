package cis550;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;


import com.google.gson.Gson;

/**
 * Servlet implementation class ValidateLogin
 */
@WebServlet("/ValidateLogin")
public class ValidateLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
    Connection conn;
    ResultSet rs;
    String username, password, query;
    Gson gson = new Gson();


    /**
     * @see HttpServlet#HttpServlet()
     */
    public ValidateLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
	
		try{
			username = request.getParameter("Username");
			password = request.getParameter("Password");		
			query = "select * from admin where username = \'" + username + "\' and password =\'" + password + "\'";
			conn = (Connection) this.getServletContext().getAttribute("dbConnection");
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			if(rs.next()){
			// RequestDispatcher rd = request.getRequestDispatcher("adminScreen.jsp");
			// rd.forward(request, response);
			// out.write(username);	\
			HttpSession session = request.getSession(true);
			session.setAttribute("username", username); 
     		// response.sendRedirect("Olympeeks.html");
     		String json = gson.toJson("Success");
     		response.getWriter().write(json);
			}else{
			// RequestDispatcher rd = request.getRequestDispatcher("Olympeeks.html");
			
			// request.setAttribute("message", "Unknown username/password, try again"); // This sets the ${message}
 		// 	rd.forward(request, response);		
    		// out.println("<p>");
    		// out.println("Unknown username/password, try again");
    		// out.println("</p>");
			// response.sendRedirect("Olympeeks.html");
			String json = gson.toJson("fail");
     		response.getWriter().write(json);
			}
		}catch(Exception e){
			throw new ServletException("Login failed", e);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
		// TODO Auto-generated method stub
	}

	@Override
	public String getServletInfo(){
		return "Short description";
	}

}
