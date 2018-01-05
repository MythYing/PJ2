package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DB;

/**
 * Servlet implementation class UseItem
 */
@WebServlet("/UseItem")
public class UseItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UseItem() {
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
		int item=Integer.valueOf(request.getParameter("item"));
		
		try {
			if (DB.useItem(pid, item)) {
				out.print("Success");
			}else {
				out.print("Failed");
			}
			out.flush();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
