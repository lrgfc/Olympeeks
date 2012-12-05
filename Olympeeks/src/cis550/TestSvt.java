package cis550;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class TestSvt
 */
@WebServlet("/TestSvt")
public class TestSvt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestSvt() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("Call Athelete Svt");
		//connect to the DB
			Connection connection = (Connection) this.getServletContext().getAttribute("dbConnection");
			
			// new a json object, to conver the result into json
			Gson gson = new Gson();
			
			//get the country from the jquery
			String country = request.getParameter("country");
			//??? What is this stand for?
			
			if(country.equals("false")){
				if(connection != null){
					String query = "select DISTINCT(country) from athelets order by country";
					
					// hold the country result
					ArrayList<String> countries = new ArrayList<String>();
					try{
						Statement stmt = connection.createStatement();
						
						ResultSet rs = stmt.executeQuery(query);
						
						while(rs.next()){
							countries.add(rs.getString("country"));
						}
						
					}catch(Exception ex){
						ex.printStackTrace();
					}	
					
					response.setContentType("text/plain");
					response.setCharacterEncoding("UTF-8");
					System.out.println("The size of the countreis is " + countries.size());
					String json = gson.toJson(countries);
					response.getWriter().write(json);
				}
			}else{
				String SportsCategory = request.getParameter("sport");
				if(SportsCategory.equals("false")){
					System.out.println(country);
					ArrayList<String> sportsCategorys = new ArrayList<String>();
					String query;
					
					if(connection != null){
						query = "select DISTINCT(sport) from athelets where country = \'" + country + "\'";

						try{
							Statement stmt = connection.createStatement();
							
							ResultSet rs = stmt.executeQuery(query);
							while(rs.next()){
								sportsCategorys.add(rs.getString("sport"));
							}
						}catch(Exception ex){
							ex.printStackTrace();
						}
					System.out.println("The size of the sports is " + sportsCategorys.size());
					String json = gson.toJson(sportsCategorys);
					response.getWriter().write(json);
				}
				}else{
					System.out.println(SportsCategory);
					ArrayList<String> sportsEvents = new ArrayList<String>();
					String hasSportsCategory = request.getParameter("sportsCategory");
					String query;
					if(connection != null){
						query = "select DISTINCT(event) from athelets where country = \'" + country + "\' and sport =\'" + hasSportsCategory;

						try{
							Statement stmt = connection.createStatement();
							
							ResultSet rs = stmt.executeQuery(query);
							while(rs.next()){
								sportsEvents.add(rs.getString("event"));
							}
						}catch(Exception ex){
							ex.printStackTrace();
						}
					System.out.println("The size of the particular sports event is " + sportsEvents.size());
					String json = gson.toJson(sportsEvents);
					response.getWriter().write(json);
				}
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
	}

}
