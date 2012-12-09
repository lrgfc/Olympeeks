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
 * Servlet implementation class GraphAthleteSvtView
 */
@WebServlet("/GraphAthleteSvtView")
public class GraphAthleteSvtView extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GraphAthleteSvtView() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<String> athlete = new ArrayList<String>();
		ArrayList<Integer> golds = new ArrayList<Integer>();

		String sport = request.getParameter("SportsCategory");
		String startyear= request.getParameter("startyear");
		String endyear = request.getParameter("endyear");
		Connection connection = (Connection) this.getServletContext().getAttribute("dbConnection");
		if(connection != null){
			try{
				String query = "select full_Name, count(*) as gold from athelets where sport = \'" + sport + "\' and game >= \'" + startyear + 
						"\'  and game <= \'" + endyear + "\' and medal = 'Gold' group by full_Name order by count(*) DESC";
				//Not sure if the sort by game is going to have problems
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				int count = 1;
				while(rs.next() && count <=8){					
						athlete.add(rs.getString("full_Name"));
						golds.add(Integer.parseInt(rs.getString("gold")));
							count = count+1;
					}	
					

			if (!golds.isEmpty()){
				AthGraphObjectView graphObject = new AthGraphObjectView(athlete,golds);
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

}
