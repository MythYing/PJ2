package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.Cards;
import model.Common;
import model.Rules;
import model.StatusType;
import websocket.RoomDataInform;

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
		int pid=(int) request.getSession().getAttribute("pid");
		
		String[] cardsStringArray = request.getParameterValues("cardsPlayed[]");
		Cards cardsPlayed = new Cards(cardsStringArray);
		StatusType status = new StatusType();
		// 如果合法
		if(pid==Common.getTurn(pid)) {
			boolean result = Rules.successful(pid, cardsPlayed);
			if (result) {
				// 打出手中的拍牌
				Common.getPlayer(pid).cardsInHand.removeAll(cardsPlayed.cards);
				Common.getRoom(pid).maxPlayer=pid;
				Common.getRoom(pid).maxCards=cardsPlayed;
				Common.nextTurn(pid);
				// 游戏结束判断
				if(Common.getPlayer(pid).cardsInHand.size()==0) {
					Common.gameOverRelease(pid);			
					status.status="GameOver";
					status.data=pid;
					
				}else {
					status.status="SuccessfulPlay";
					RoomDataInform.sendMessage(pid, "Refresh");
				}
			}else {
				status.status="FailedPlay";
			}
			Gson gson=new Gson();
			String json = gson.toJson(status);
			out.print(json);
			out.flush();
		}
	}

}
