package cis550;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class GetGraph
 */
@WebServlet("/SportGraphSvt")
public class SportGraphSvt extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SportGraphSvt() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//hold the result;
		ArrayList<String> countries = new ArrayList<String>();
//		ArrayList<Integer> golds = new ArrayList<Integer>();
		ArrayList<Integer> medals = new ArrayList<Integer>();
		
		//get the parameter from the request
		int startyear = Integer.parseInt(request.getParameter("startyear"));
		int endyear = Integer.parseInt(request.getParameter("endyear"));
		String sport = request.getParameter("sport");
		
		//connect to database, select the wanted field
		Connection connection = (Connection) this.getServletContext().getAttribute("dbConnection");
		if(connection != null){
			try{
				//select the years, order by years
				String query = "select country, count(rank) as total from athelets where sport = \'"+sport + 
						"\' and game >= "+ startyear + " and game <= " + endyear + " and rank >= 1 and rank <= 3 group by country";
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				
//				while(rs.next()){
//					int year = rs.getInt("year");
//					years.add(year);
//				}
				
				//select total metals and and countries
//				query = "select gold, total from SummerO where country = \'"+sport + 
//						"\' and year >= "+ startyear + " and year <= " + endyear + " order by year";
//				stmt = connection.createStatement();
//				rs = stmt.executeQuery(query);
				
				while(rs.next()){
					String country = rs.getString("country");
					int medal = rs.getInt("total");
					
					countries.add(country);
					medals.add(medal);
				}
				
				//send the response to the client
				GraphObject graphObject = new GraphObject(medals, countries);
				Gson gson = new Gson();
				String json = gson.toJson(graphObject);
				
				response.setContentType("text/plain");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
				
			}catch(Exception ex){
				ex.printStackTrace();
			}	
		}else{
			System.out.println("Cannot connect to DB");
		}
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
