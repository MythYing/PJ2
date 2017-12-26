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
import com.sun.org.apache.bcel.internal.generic.NEW;

import model.Cards;
import model.Common;
import model.Room;
import model.Rules;
import model.VisibleGameData;
import model.Data;
import model.GameResultData;
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
		System.out.println("PID:"+pid);
		String[] cardsStringArray = request.getParameterValues("cardsPlayed[]");
		// 测试传入卡牌
		if(cardsStringArray==null) {
			System.out.println("null");
		}else {
			for (String string : cardsStringArray) {
				System.out.print(string+" ");
			}
			System.out.println();
		}
		Cards cardsPlayed = new Cards(cardsStringArray);
		Data data = new Data();
		// 如果合法
		if(pid==Common.getTurn(pid)) {
			boolean result = Rules.successful(pid, cardsPlayed);
			if (result) {
				// 打出手中的拍牌
				Common.getPlayer(pid).cardsInHand.removeAll(cardsPlayed.cards);
				// 如果非PASS
				if (cardsStringArray!=null) {
					Common.getRoom(pid).maxPlayer=pid;
					Common.getRoom(pid).maxCards=cardsPlayed;
				}
				// 游戏结束判断
				if(Common.getPlayer(pid).cardsInHand.size()==0) {			
					Common.getRoom(pid).hasGotResult=new boolean[3];
					data.status="GameOver";
					RoomDataInform.sendMessageAll(pid, "GameOver");
				}else {
					Common.nextTurn(pid);
					data.status="SuccessfulPlay";
					RoomDataInform.sendMessageAll(pid, "Refresh");
				}
			}else {
				data.status="FailedPlay";
			}
			// 调试输出最大玩家
//			Room room=Common.getRoom(pid);
//			System.out.println("最大玩家："+room.maxPlayer);
			
			Gson gson=new Gson();
			String json = gson.toJson(data);
			System.out.println(json);
			out.print(json);
			out.flush();
		}
	}

}
