package cis550;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

/**
 * Servlet implementation class GetGraph
 */
@WebServlet("/GraphSvt")
public class GraphSvt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GraphSvt() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//hold the result;
		ArrayList<Integer> years = new ArrayList<Integer>();
		ArrayList<Integer> golds = new ArrayList<Integer>();
		ArrayList<Integer> totals = new ArrayList<Integer>();
		
		//get the parameter from the request
		int startyear = Integer.parseInt(request.getParameter("startyear"));
		int endyear = Integer.parseInt(request.getParameter("endyear"));
		String country = request.getParameter("country");
		
		//connect to database, select the wanted field
		Connection connection = (Connection) this.getServletContext().getAttribute("dbConnection");
		if(connection != null){
			try{
				//select the years, order by years
				String query = "select Distinct(year) from SummerO where country = \'"+country + 
						"\' and year >= "+ startyear + " and year <= " + endyear + " order by year";
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				
				while(rs.next()){
					int year = rs.getInt("year");
					years.add(year);
				}
				
				//select gold metals and total metals order by years
				query = "select gold, total from SummerO where country = \'"+country + 
						"\' and year >= "+ startyear + " and year <= " + endyear + " order by year";
				stmt = connection.createStatement();
				rs = stmt.executeQuery(query);
				
				while(rs.next()){
					int gold = rs.getInt("gold");
					int total = rs.getInt("total");
					
					golds.add(gold);
					totals.add(total);
				}
				
				//close the result;
				rs.close();
				//close the stmt;
				stmt.close();
				
				//send the response to the client
				GraphObject graphObject = new GraphObject(years,golds,totals);
				Gson gson = new Gson();
				String json = gson.toJson(graphObject);
				
				response.setContentType("text/plain");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
				
				//get the session of the user, if the user logs in, we'll keep track of his manipulation
				try{
					HttpSession session = request.getSession(false);
					String username = (String) session.getAttribute("username");
					String clicktime = (String) request.getParameter("clicktime");
					System.out.println("The clicktime is "+clicktime);
					System.out.println("The user name is "+username);
					
					//update the user information
					String updating = String.format("insert into userrmd values(\'%s\', \'%s\', \'%s\')", username,country, clicktime);
					Statement stmt_updating = connection.createStatement();
					try{
						stmt_updating.executeUpdate(updating);
					}catch(Exception ex){
						ex.printStackTrace();
					}
					
				}catch(Exception ex){
					System.out.println("Opps, this user has not logged in!");
				}
				
			}catch(Exception ex){
				ex.printStackTrace();
			}	
		}else{
			System.out.println("Cannot connect to DB");
		}
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
