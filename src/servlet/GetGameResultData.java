package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.Common;
import model.GameResultData;

/**
 * Servlet implementation class GetGameResultData
 */
@WebServlet("/GetGameResultData")
public class GetGameResultData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetGameResultData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		int pid=(int) request.getSession().getAttribute("pid");
		GameResultData gameResultData;
		try {
			gameResultData = new GameResultData(pid);
			Gson gson=new Gson();
			String json=gson.toJson(gameResultData);
			out.print(json);
			out.flush();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

}
