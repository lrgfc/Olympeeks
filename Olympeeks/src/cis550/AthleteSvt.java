package cis550;

import java.io.IOException;
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

import com.google.gson.Gson;

/**
 * Servlet implementation class AthleteSvt
 */
@WebServlet(value = {"/AthleteSvt"})
public class AthleteSvt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AthleteSvt() {
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
				
				
				if(country.equals("false")){
					if(connection != null){
						String query = "select DISTINCT(country) from SummerO order by country";
						
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
						response.getWriter().print(json);
					}
				}else{
					String selSportsCategory = request.getParameter("SportsCategory");
					if(selSportsCategory.equals("false")){
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
						String selSportsEvent = request.getParameter("SportsEvents");
						if(selSportsEvent.equals("false")){
							System.out.println(selSportsCategory);
							ArrayList<String> sportsEvents = new ArrayList<String>();
						// String hasSportsCategory = request.getParameter("sportsCategory");
							String query;
							if(connection != null){
								query = "select DISTINCT(event) from athelets where country = \'" + country + "\' and sport =\'" + selSportsCategory + "\'";
							try{
								Statement stmt = connection.createStatement();
								
								ResultSet rs = stmt.executeQuery(query);
								while(rs.next()){
									sportsEvents.add(rs.getString("event"));
								}
							}catch(Exception ex){
								ex.printStackTrace();
							}
							}
						System.out.println("The size of the particular sports event is " + sportsEvents.size());
						String json = gson.toJson(sportsEvents);
						response.getWriter().write(json);
						}else{
							String selyear = request.getParameter("year");
							if(selyear.equals("false")){
							System.out.println(selSportsEvent);
							List<Integer> years = new ArrayList<Integer>();
							String query;
						// String hasSportsCategory = request.getParameter("sportsCategory");
					
							if(connection != null){
								query = "select DISTINCT(game) from athelets where country = \'" + country + "\' and sport =\'" + selSportsCategory + "\' and event = \"" + selSportsEvent + "\"";
							try{
								Statement stmt = connection.createStatement();
								
								ResultSet rs = stmt.executeQuery(query);
								while(rs.next()){
									String game = rs.getString("game");
									years.add(Integer.parseInt(game.substring(0, Math.min(game.length(), 4))));
								}
							}catch(Exception ex){
								ex.printStackTrace();
							}
							Collections.sort(years);
						// System.out.println("The size of the particular sports event is " + sportsEvents.size());
						String json = gson.toJson(years);
						response.getWriter().write(json);
							}
						}else{
							ArrayList<String> athletes = new ArrayList<String>();
							String query;
							if(connection != null){
							query = "select DISTINCT(full_Name) from athelets where country = \'" + country + "\' and sport =\'" + selSportsCategory + "\' and event = \"" + selSportsEvent + "\"" +
									" and game LIKE \'" + selyear + "%\'";
							try{
								Statement stmt = connection.createStatement();
								
								ResultSet rs = stmt.executeQuery(query);
								while(rs.next()){
										
									athletes.add(rs.getString("full_Name"));
								}
							}catch(Exception ex){
								ex.printStackTrace();	
							}
							String json = gson.toJson(athletes);
							response.getWriter().write(json);
						}
						
					}
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
