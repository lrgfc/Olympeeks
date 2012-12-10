package cis550;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.sql.*;

/**
 * Servlet implementation class GraphAthleteSvt
 */
@WebServlet("/GraphAthleteSvt")
public class GraphAthleteSvt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GraphAthleteSvt() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArrayList<Integer> years = new ArrayList<Integer>();
		ArrayList<Integer> ranks = new ArrayList<Integer>();

		String athlete = request.getParameter("athlete");
		String event = request.getParameter("SportsEvents");

		Connection connection = (Connection) this.getServletContext().getAttribute("dbConnection");
		if(connection != null){
			try{
				String query = "select game, rank from athelets where full_Name = \'" + athlete + "\' and event = \"" + 
				event + "\"";
				//Not sure if the sort by game is going to have problems
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(query);

				while(rs.next()){
					String game = rs.getString("game");
					String rank = rs.getString("rank");
					if(isInteger(rank)){
						ranks.add(Integer.parseInt(rank));
						years.add(Integer.parseInt(game.substring(0, Math.min(game.length(), 4))));
					}	
					
				}
			if (!ranks.isEmpty()){
				AthGraphObject graphObject = new AthGraphObject(years,ranks);
				Gson gson = new Gson();
				String json = gson.toJson(graphObject);
				
				response.setContentType("text/plain");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			}else{
				Gson gson = new Gson();
				String json = gson.toJson(0);
				response.setContentType("text/plain");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
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
	public static boolean isInteger(String str) {
		if (str == null) {
			return false;
		}
		int length = str.length();
		if (length == 0) {
			return false;
		}
		int i = 0;
		if (str.charAt(0) == '-') {
			if (length == 1) {
				return false;
			}
			i = 1;
		}
		for (; i < length; i++) {
			char c = str.charAt(i);
			if (c <= '/' || c >= ':') {
				return false;
			}
		}
		return true;
	}
	
}
