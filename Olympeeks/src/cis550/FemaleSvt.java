package cis550;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;

import com.google.gson.Gson;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * Servlet implementation class FemaleSvt
 */
@WebServlet("/FemaleSvt")
public class FemaleSvt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FemaleSvt() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//connect to the DB
		Connection connection = (Connection) this.getServletContext().getAttribute("dbConnection");
		
		// new a json object, to conver the result into json
		Gson gson = new Gson();
		
		//get the country from the jquery
		String country = request.getParameter("country");
		System.out.println(country);
		
		if(country.equals("false")){
			if(connection != null){
				String query = "select DISTINCT(country) from FemalePercent order by country";
				
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
				
				System.out.println("The size of the countreis is " + countries.size());
				String json = gson.toJson(countries);
				response.getWriter().write(json);
			}
		}else{
			System.out.println(country);
			
			
			ArrayList<String> years = new ArrayList<String>();
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
				
			if(connection != null){
				try{
					String hasStartYear = request.getParameter("startyear");
					String query;
					if(hasStartYear.equals("true")){
						String  startYear = request.getParameter("value");
						query = "select DISTINCT(year) from FemalePercent where country = \'" + country + "\' and year >= \'"+startYear + "\'";
					}else{
						query = "select DISTINCT(year) from FemalePercent where country = \'" + country + "\'";
					}
					
					Statement stmt = connection.createStatement();
					
					ResultSet rs = stmt.executeQuery(query);
					ResultSetMetaData rsmd = rs.getMetaData();
					
					while(rs.next()){
						String year = rs.getString("year");
						years.add(year);
					}
				
				}catch(Exception ex){
					ex.printStackTrace();
				}
				
				String json = gson.toJson(years);
				response.getWriter().write(json);
			}else{
				System.out.println("Cannot open the database");
			}
			
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Call Female Post Servlet!");
	}

}
