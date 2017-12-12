package com.test.json;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.test.model.CardsPlayed;

/**
 * Servlet implementation class PlayCards
 */
@WebServlet("/PlayCards")
public class PlayCards extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PlayCards() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 输出流
		PrintWriter out = response.getWriter();
		String pidStr = request.getParameter("pid");
		int pid = Integer.parseInt(pidStr);
		String[] cardsString = request.getParameterValues("cardsPlayed[]");
		CardsPlayed cardsPlayed = new CardsPlayed(cardsString);
		// 如果合法
	}

}
