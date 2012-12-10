package cis550;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

/**
 * Servlet implementation class SignUp
 */
@WebServlet("/SignUp")
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     * 
     * 
     */
    public SignUp() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	Connection conn;
    	ResultSet rs;
    	String username, passwordFirst, passwordSecond, query;
    	Gson gson = new Gson(); 
    	boolean duplicate = false; 
    	response.setContentType("text/html;charset=UTF-8");
    	PrintWriter out = response.getWriter();

    	try{
    		username = request.getParameter("Username");
    		passwordFirst = request.getParameter("PasswordFirst");
    		passwordSecond = request.getParameter("PasswordSecond");
    		query = "select username from admin";
    		conn = (Connection) this.getServletContext().getAttribute("dbConnection");
    		Statement stmt = conn.createStatement();
    		rs = stmt.executeQuery(query);
    		System.out.println("Get into Servlet");
    		while(rs.next()){
    		
    			if (rs.getString("username").equals(username)){
    			String json = gson.toJson("duplicate");
     			response.getWriter().write(json);
     			duplicate = true;
     			System.out.println("Found duplicate!");
    			}
    		}
    		
    		
    		if(duplicate==false){
    			System.out.println("not duplicate!");
    			if(passwordFirst.equals(passwordSecond)){
    				query="insert into admin values (\'" + username + "\', \'" + passwordFirst + "\')"; 
    				System.out.println("Start insert!!!");
    				int a = stmt.executeUpdate(query);
    				HttpSession session = request.getSession(true);				
    				session.setAttribute("username", username); 
    				String json = gson.toJson("Success");
    				response.getWriter().write(json);
    				
    			}else{
    				String json = gson.toJson("NotMatched");
    				System.out.println("Found Not Matched");
         			response.getWriter().write(json);
    			}
    		}

    		
    	}catch(Exception e){
    		throw new ServletException("Login failed", e);
    	}

    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	doPost(request, response);	
		processRequest(request, response);	
	}






	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
}
	}
