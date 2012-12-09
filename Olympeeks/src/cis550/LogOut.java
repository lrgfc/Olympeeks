package cis550;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

/**
 * Servlet implementation class LogOut
 */
@WebServlet("/LogOut")
public class LogOut extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogOut() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		 HttpSession session = request.getSession();
		 
	        //
	        // Invalidate the session and removes any attribute related to it
	        //
	        session.invalidate();
	 
	        //
	        // Get an HttpSession related to this request, if no session exist don't
	        // create a new one. This is just a check to see after invalidation the
	        // session will be null.
	        //
	        session = request.getSession(false);
	        if(session == null){
	        	Gson gson = new Gson();
				String json = gson.toJson("Log out succeed");
				
				response.setContentType("text/plain");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
	       
	        }else{
	        	Gson gson = new Gson();
				String json = gson.toJson("Log out fail");
				
				response.setContentType("text/plain");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
	        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}
