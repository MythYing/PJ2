package model;

import java.sql.SQLException;

public class VisibleGameData {
	
	public int rid;
	public int myId;
	public int playerLeftId;
	public int playerRightId;
	
	public int turn;
	public String[] maxCards;
	public int maxPlayer;
	
	public String[] cardsInMyHand;
	public int playerLeftNumberOfCards;	
	public int playerRightNumberOfCards;
	
	// 静态信息
	public String myName;
	public String playerLeftName;
	public String playerRightName;
	
	public String myIcon;
	public String playerLeftIcon;
	public String playerRightIcon;
	
	public String myCardSkin;
	public String playerLeftCardSkin;
	public String playerRightCardSkin;
	
	public VisibleGameData(int pid) throws SQLException {
		this(pid, false);
	}
	public VisibleGameData(int pid, boolean isInitial) throws SQLException {
		
		int myRoomIndex=Common.playerPositionMap.get(pid).roomIndex;
		int leftRoomIndex=(myRoomIndex+3-1)%3;
		int rightRoomIndex=(myRoomIndex+3+1)%3;
		
		rid=Common.getRid(pid);
		turn=Common.rooms[rid].turn;
		maxPlayer=Common.rooms[rid].maxPlayer;
		if (maxPlayer!=-1) {
			maxCards=Cards.toStringArray(Common.rooms[rid].maxCards.cards);
		}
		
		myId=pid;
		playerLeftId=Common.rooms[rid].pid[leftRoomIndex];
		playerRightId=Common.rooms[rid].pid[rightRoomIndex];
		
		cardsInMyHand=Cards.toStringArray(Common.getPlayer(myId).cardsInHand);		
		
		playerLeftNumberOfCards=Common.getPlayer(playerLeftId).cardsInHand.size();
		playerRightNumberOfCards=Common.getPlayer(playerRightId).cardsInHand.size();
		
		if (isInitial) {
			// Name
			myName=DB.getUserName(myId);
			playerLeftName=DB.getUserName(playerLeftId);
			playerRightName=DB.getUserName(playerRightId);
			
			myIcon=DB.getIcon(pid);
			playerLeftIcon=DB.getIcon(playerLeftId);
			playerRightIcon=DB.getIcon(playerRightId);
			
			myCardSkin=DB.getCardSkin(pid);
			playerLeftCardSkin=DB.getCardSkin(playerLeftId);
			playerRightCardSkin=DB.getCardSkin(playerRightId);
		}
	}
}
