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

import model.DB;
import model.ShopItem;

/**
 * Servlet implementation class GetShopItem
 */
@WebServlet("/GetShopItem")
public class GetShopItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetShopItem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String type = request.getParameter("type");
		ArrayList<ShopItem> items = null;
			try {
				switch(type) {
				case "icon":
					items=DB.getShopIcons();
					break;
				case "card-skin":
					items=DB.getShopCardSkins();
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
