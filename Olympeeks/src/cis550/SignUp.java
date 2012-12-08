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
	Connection conn;
	ResultSet rs;
	String username, passwordFirst, passwordSecond, query;
	Gson gson = new Gson(); 
	boolean duplicate = false; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUp() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

			while(rs.next()){
			
				if (rs.getString("username").equals(username)){
				String json = gson.toJson("duplicate");
     			response.getWriter().write(json);
     			duplicate = true;	
				}
			}

			if(duplicate==false){
				if(passwordFirst.equals(passwordSecond)){
					query="insert into admin values (\'" + username + "\', \'" + passwordFirst + "\')"; 
					int a = stmt.executeUpdate(query);
					HttpSession session = request.getSession(true);
					session.setAttribute("username", username); 
					String json = gson.toJson("Success");
					response.getWriter().write(json);
					
				}else{
					String json = gson.toJson("NotMatched");
	     			response.getWriter().write(json);
				}
			}

			
		}catch(Exception e){
			throw new ServletException("Login failed", e);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
