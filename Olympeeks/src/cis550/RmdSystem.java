package cis550;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

/**
 * Servlet implementation class RmdSystem
 */
@WebServlet("/RmdSystem")
public class RmdSystem extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String username = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RmdSystem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//set the response type, and response json container
		Gson gson = new Gson();
		response.setContentType("test/html;charset=UTF-8");
		//debug
		System.out.println("Call the recommend system!");
		
		try{
			HttpSession session = request.getSession(false);
			username = (String) session.getAttribute("username");
			if(username != null){
				System.out.println("username is " + username);
				String json = gson.toJson(username);
				response.getWriter().write(json);
				System.out.println("json in rmdSystem is " + json);
				ArrayList<String> rmdCountries = new ArrayList<String>();
				rmdCountries = this.recommend();
			}
			
		}catch(Exception ex){
			System.out.println("Opps, cannot find the user");
			String json = gson.toJson("fail");
			response.getWriter().write(json);
			System.out.println("json in rmdSystem is " + json);
		}

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	private ArrayList<String> recommend(){
		ArrayList<String> rmdCountries = new ArrayList<String>();
		//connect to the DB
		Connection connection = (Connection) this.getServletContext().getAttribute("dbConnection");
		//select the top countries clicked by this user
		String query = String.format("select countryname,count(*) from userrmd where username = \'%s\' group by countryname order by count(*) DESC LIMIT 10",username);
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				rmdCountries.add(rs.getString("countryname"));
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		query = "select countryname from totaltrends order by totalclicks LIMIT 20";
		try{
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				String tempCountry = rs.getString("countryname");
				if(!rmdCountries.contains(tempCountry)){
					rmdCountries.add(tempCountry);
				}
			}
			
			System.out.println("The recommend country number is "+rmdCountries.size());
		
		}catch(Exception ex){
			System.out.println("Cannot execute rmd query, main error!");
		}
		return rmdCountries;
	}

}
