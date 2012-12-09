package cis550;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

/**
 * CountryServlet, select the values for the drop down list in the country topic
 */
@WebServlet("/CountrySvt")
public class CountrySvt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CountrySvt() {
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
				
				System.out.println("The size of the countreis is " + countries.size());
				String json = gson.toJson(countries);
				response.getWriter().write(json);
				
				System.out.print("LOcal directory: " + new File(".").getCanonicalFile());
				
				//use these to create the recommendation table
				/*try {
					 
		 
					File file = new File("//Users//xuwang//Documents//workspace//CIS550Project//temp.txt");
		 
					// if file doesnt exists, then create it
					if (!file.exists()) {
						file.createNewFile();
					}
		 
					FileWriter fw = new FileWriter(file.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(fw);
					for(String temp:countries){
						bw.write(temp + "   INT,\n");
					}
					bw.close();
		 
					System.out.println("Done");
		 
				} catch (IOException e) {
					e.printStackTrace();
				}*/
			}
		}else{
			System.out.println(country);
			
			
			ArrayList<Integer> years = new ArrayList<Integer>();
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
				
			if(connection != null){
				try{
					String hasStartYear = request.getParameter("startyear");
					String query;
					if(hasStartYear.equals("true")){
						int  startYear = Integer.parseInt(request.getParameter("value"));
						query = "select DISTINCT(year) from SummerO where country = \'" + country + "\' and year >="+startYear;
					}else{
						query = "select DISTINCT(year) from SummerO where country = \'" + country + "\'";
					}
					
					Statement stmt = connection.createStatement();
					
					ResultSet rs = stmt.executeQuery(query);
					ResultSetMetaData rsmd = rs.getMetaData();
					
					while(rs.next()){
						int year = rs.getInt("year");
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
	}

}
