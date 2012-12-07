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
@WebServlet("/FemaleGraphSvt")
public class FemaleGraphSvt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FemaleGraphSvt() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		//hold the result;
		ArrayList<String> years = new ArrayList<String>();
		// total and female number pairs
		ArrayList<ArrayList<Integer>> pairs = new ArrayList<ArrayList<Integer>>();
		
		//get the parameter from the request
		String startyear = request.getParameter("startyear");
		String endyear = request.getParameter("endyear");
		String country = request.getParameter("country");
		
		//connect to database, select the wanted field
		Connection connection = (Connection) this.getServletContext().getAttribute("dbConnection");
		if(connection != null){
			try{
				//select the years, order by years
				String query = "select Distinct(year) from FemalePercent where country = \'"+country + 
						"\' and year >= \'"+ startyear + "\' and year <= \'" + endyear + "\' order by year";
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				
				while(rs.next()){
					String year = rs.getString("year");
					years.add(year);
				}
				
				//select gold metals and total metals order by years
				query = "select numOfFemale, numOfMale from FemalePercent where country = \'"+country + 
						"\' and year >= \'"+ startyear + "\' and year <= \'" + endyear + "\' order by year";
				System.out.println(query);
				stmt = connection.createStatement();
				rs = stmt.executeQuery(query);
				
				while(rs.next()){
					ArrayList<Integer> temp = new ArrayList<Integer>();
					temp.add(rs.getInt("numOfFemale"));
					temp.add(rs.getInt("numOfMale"));
					pairs.add(temp);
				}
				
				//send the response to the client
				FemaleGraphObject graphObject = new FemaleGraphObject(years,pairs);
				Gson gson = new Gson();
				String json = gson.toJson(graphObject);
				
				response.setContentType("text/plain");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
				//debug
				System.out.println("the size of years is " + years.size());
				System.out.println(json);
				
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