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
		// TODO Auto-generated method stub
			System.out.println("Call me Female Servlet!");
		//connect to the DB
				Connection connection = (Connection) this.getServletContext().getAttribute("dbConnection");
				
				// new a json object, to conver the result into json
				Gson gson = new Gson();
				
				String country = request.getParameter("country");
				System.out.println("Call the femal Servlet!");
				
				if (!country.equals("false")) {
					System.out.println(country);
					
					response.setContentType("text/plain");
					response.setCharacterEncoding("UTF-8");
					
					if (connection != null) {
						try{
						String query = "select country, game, sum(female_num) from female where country = \'" + country +"\' group by country, game";
						
						Statement stmt = connection.createStatement();
						ResultSet rs = stmt.executeQuery(query);
						ResultSetMetaData rsmd = rs.getMetaData();
						
						ArrayList<String> game = new ArrayList<String>();
						ArrayList<Integer> female_num = new ArrayList<Integer>();
						
						
						while(rs.next()) {
							game.add(rs.getString("game"));
							female_num.add(rs.getInt("female_num"));
						}
						
						FemaleGraphObject femaleGraph = new FemaleGraphObject(game, female_num);
						
						String json = gson.toJson(femaleGraph);
						
						
						response.getWriter().write(json);
						
						} catch(Exception e) {
							e.printStackTrace();
						}
					} else {
						System.out.println("Cannot connect to DB.");
					}
					
					
				} else {
					//response.setContentType("text/plain");
					//response.setCharacterEncoding("UTF-8");
					if(connection != null){
						String query = "select DISTINCT(country) from SummerO order by country";
						
						// hold the country result
						ArrayList<String> countries = new ArrayList<String>();
						try{
							Statement stmt = connection.createStatement();
							
							ResultSet rs = stmt.executeQuery(query);
							
							while(rs.next()){
								countries.add(rs.getString("country"));
								System.out.println(rs.getString("country"));
							}
							
						}catch(Exception ex){
							ex.printStackTrace();
						}	
						
						System.out.println("The size of the countreis is " + countries.size());
						String json = gson.toJson(countries);
						response.getWriter().write(json);
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
