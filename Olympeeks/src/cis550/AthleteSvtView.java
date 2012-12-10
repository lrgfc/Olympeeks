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
 * Servlet implementation class AthleteSvtView
 */
@WebServlet("/AthleteSvtView")
public class AthleteSvtView extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AthleteSvtView() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Call AtheleteView Svt");
		//connect to the DB
	Connection connection = (Connection) this.getServletContext().getAttribute("dbConnection");
	
	// new a json object, to conver the result into json
	Gson gson = new Gson();
	
	//get the country from the jquery
	String sportscategory = request.getParameter("sportsCategory");
	
	
	if(sportscategory.equals("false")){
		if(connection != null){
			String query = "select DISTINCT(sport) from athelets order by sport";
			
			// hold the country result
			ArrayList<String> sports = new ArrayList<String>();
			try{
				Statement stmt = connection.createStatement();
				
				ResultSet rs = stmt.executeQuery(query);
				
				while(rs.next()){
					sports.add(rs.getString("sport"));
				}
				
			}catch(Exception ex){
				ex.printStackTrace();
			}	
			
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			System.out.println("The size of the sports is " + sports.size());
			String json = gson.toJson(sports);
			response.getWriter().print(json);
		}
	}else{
		String startyear = request.getParameter("startyear");
		if(startyear.equals("false")){
			System.out.println(startyear);
			ArrayList<Integer> startyears = new ArrayList<Integer>();
			String query;
			
			if(connection != null){
				query = "select DISTINCT(game) from athelets where sport = \'" + sportscategory + "\' order by game";

				try{
					Statement stmt = connection.createStatement();
					
					ResultSet rs = stmt.executeQuery(query);
					while(rs.next()){
						String game = rs.getString("game");
						startyears.add(Integer.parseInt(game.substring(0, Math.min(game.length(), 4))));
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}
			System.out.println("The size of the start years is " + startyears.size());
			String json = gson.toJson(startyears);
			response.getWriter().write(json);
		}
		}else{
			String endyear = request.getParameter("SportsEvents");
	
				ArrayList<Integer> endyears= new ArrayList<Integer>();
			// String hasSportsCategory = request.getParameter("sportsCategory");
				String query;
				if(connection != null){
					query = "select DISTINCT(game) from athelets where sport = \'" + sportscategory + "\' and game > \'" + startyear + "\' order by game";
				try{
					Statement stmt = connection.createStatement();
					
					ResultSet rs = stmt.executeQuery(query);
					while(rs.next()){
						String game2 = rs.getString("game");
						endyears.add(Integer.parseInt(game2.substring(0, Math.min(game2.length(), 4))));
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}
				}
			System.out.println("The size of the end years is " + endyears.size());
			String json = gson.toJson(endyears);
			response.getWriter().write(json);
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
