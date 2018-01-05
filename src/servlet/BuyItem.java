package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DB;

/**
 * Servlet implementation class Buy
 */
@WebServlet("/BuyItem")
public class BuyItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuyItem() {
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
			int myCarrots = DB.getCarrots(pid);
			int price=DB.getItemPrice(item);
			if (myCarrots>=price && (!DB.hasItem(pid, item))) {
				DB.changeCarrots(pid, -price);
				DB.addItemToBag(pid, item);
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
