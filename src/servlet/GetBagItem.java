package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.BagItem;
import model.DB;
import model.ShopItem;

/**
 * Servlet implementation class GetBagItem
 */
@WebServlet("/GetBagItem")
public class GetBagItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetBagItem() {
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
		String type = request.getParameter("type");
		ArrayList<BagItem> items = null;
			try {
				switch(type) {
				case "icon":
					items=DB.getBagIcons(pid);
					break;
				case "card-skin":
					items=DB.getBagCardSkins(pid);
					break;
				default:
					break;
				}
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		Gson gson=new Gson();
		String json=gson.toJson(items);
		out.print(json);
		out.flush();
	}

}
