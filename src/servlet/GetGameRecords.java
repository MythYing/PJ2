package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.DB;
import model.GameRecord;

/**
 * Servlet implementation class GetGameRecord
 */
@WebServlet("/GetGameRecords")
public class GetGameRecords extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetGameRecords() {
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
		ArrayList<GameRecord> gameRecords=new ArrayList<>();
		
		int pid=(int) request.getSession().getAttribute("pid");
		try {
			ResultSet rs=DB.getGameRecord(pid);
			String myName=DB.getUserName(pid);
			
			while (rs.next()) {
				gameRecords.add(new GameRecord(myName, rs));
			}
			
			Gson gson=new Gson();
			String json=gson.toJson(gameRecords);
			out.print(json);
			out.flush();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

}
