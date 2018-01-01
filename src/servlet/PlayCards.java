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
import model.DB;
import model.Room;
import model.Rules;
import model.Data;
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
					Room room=Common.getRoom(pid);
					long endTime=System.currentTimeMillis();
					int[] cardsLeft=new int[3];
					int[] carrotsChange=new int[3];
					for(int i=0;i<=2;i++) {
						cardsLeft[i]=room.players[i].cardsInHand.size();
					}
					for(int i=0;i<=2;i++) {
						if (room.pid[i]==pid) {
							carrotsChange[i]= (cardsLeft[(i+1)%3]+cardsLeft[(i+2)%3])*100;
						}else {
							carrotsChange[i]=cardsLeft[i]*(-100);
						}
					}
					try {
						DB.insertRecord(Common.getRid(pid), room.beginTime, (int)(endTime-room.beginTime)/1000, pid, room.pid[0], room.pid[1], room.pid[2], cardsLeft[0], cardsLeft[1], cardsLeft[2], carrotsChange[0], carrotsChange[1], carrotsChange[2]);
						for(int i=0;i<2;i++) {
							DB.changeCarrots(room.pid[i], carrotsChange[i]);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					data.status="GameOver";
					RoomDataInform.sendMessageAll(pid, "GameOver");
					Common.gameOverRelease(pid);
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
