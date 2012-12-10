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
						String temp = rs.getString("full_Name");
						int wordsCount = countWords(rs.getString("full_Name"));
						StringBuffer athleteName = new StringBuffer();
						String name;
						if(wordsCount>3){
							String[] words = rs.getString("full_Name").split(" ");							
							for(int i =0; i<words.length; i++){
								if(!words[i].equals("Nickname(s):")){
								athleteName.append(words[i]);
								athleteName.append(" ");}
							}
						name = athleteName.toString();
						}else{
							name = rs.getString("full_Name");
						}
						
						athlete.add(name);
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
	public static int countWords(String s){

	    int counter = 0;

	    boolean word = false;
	    int endOfLine = s.length() - 1;

	    for (int i = 0; i < s.length(); i++) {
	        // if the char is letter, word = true.
	        if (Character.isLetter(s.charAt(i)) == true && i != endOfLine) {
	            word = true;
	            // if char isnt letter and there have been letters before (word
	            // == true), counter goes up.
	        } else if (Character.isLetter(s.charAt(i)) == false && word == true) {
	            counter++;
	            word = false;
	            // last word of String, if it doesnt end with nonLetter it
	            // wouldnt count without this.
	        } else if (Character.isLetter(s.charAt(i)) && i == endOfLine) {
	            counter++;
	        }
	    }
	    return counter;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
