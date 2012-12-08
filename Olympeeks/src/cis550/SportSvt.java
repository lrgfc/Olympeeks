package cis550;

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
@WebServlet("/SportSvt")
public class SportSvt extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SportSvt() {
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
        
        //get the sport from the jquery
        String sport = request.getParameter("sport");
        
        if(sport.equals("false")){
            if(connection != null){
                String query = "select DISTINCT(sport) from athelets order by sport";
                
                // hold the sport result
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
                
                System.out.println("The size of the sport list is " + sports.size());
                String json = gson.toJson(sports);
                response.getWriter().write(json);
            }
        }else{
            System.out.println(sport);
            
            
            ArrayList<Integer> years = new ArrayList<Integer>();
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
                
            if(connection != null){
                try{
                    String hasStartYear = request.getParameter("startyear");
                    String query;
                    if(hasStartYear.equals("true")){
                        int  startYear = Integer.parseInt(request.getParameter("value"));
                        query = "select DISTINCT(game) from athelets where sport = \'" + sport + "\' and game >="+startYear+" order by game";
                    }else{
                        query = "select DISTINCT(game) from athelets where sport = \'" + sport + "\' order by game";
                    }
                    
                    Statement stmt = connection.createStatement();
                    
                    ResultSet rs = stmt.executeQuery(query);
                    ResultSetMetaData rsmd = rs.getMetaData();
                    
                    while(rs.next()){
                        int year = Integer.parseInt((rs.getString("game")).replaceAll("[\\D]", ""));
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
