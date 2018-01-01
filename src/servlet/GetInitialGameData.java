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

import model.VisibleGameData;

/**
 * Servlet implementation class GetInitialGameData
 */
@WebServlet("/GetInitialGameData")
public class GetInitialGameData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetInitialGameData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		int pid=(int) request.getSession().getAttribute("pid");
		
		VisibleGameData data=null;
		try {
			data = new VisibleGameData(pid, true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Gson gson=new Gson();
		String json=gson.toJson(data);
		out.print(json);
		out.flush();
		
	}

}
