package model;

import java.sql.SQLException;

public class GameResultData{
	public int myId;
	public String winner;
	public String[] names=new String[3];
	public int[] carrotChanges = new int[3];
	
	public GameResultData(int pid) {
		myId = pid;
		Room room = Common.getRoom(pid);
		
		try {
			winner=DB.getUserName(room.maxPlayer);
			for(int i=0;i<=2;i++) {
				names[i]=DB.getUserName(room.pid[i]);
				if (room.pid[i]==room.maxPlayer) {
					carrotChanges[i]= (Common.getRoom(pid).players[(i+1)%3].cardsInHand.size()+Common.getRoom(pid).players[(i+2)%3].cardsInHand.size())*100;
				}else {
					carrotChanges[i]=Common.getRoom(pid).players[i].cardsInHand.size()*(-100);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
}
