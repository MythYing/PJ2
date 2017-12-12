package com.test.model;

import java.sql.SQLException;

public class VisibleGameData {
	public int rid;
	public int turn;
	public String[] maxCards;
	
	public String[] cardsInMyHand;
	
	public int myRoomIndex;
	public int myId;
	public int playerLeftId;
	public int playerRightId;
	
	public String myName;
	public String playerLeftName;
	public String playerRightName;
	
	public int playerLeftNumberOfCards;	
	public int playerRightNumberOfCards;
		
	public VisibleGameData(int pid) throws SQLException {
		myRoomIndex=Common.playerPositionMap.get(pid).roomIndex;
		int leftRoomIndex=(myRoomIndex+3-1)%3;
		int rightRoomIndex=(myRoomIndex+3+1)%3;
		
		rid=Common.getRid(pid);
		turn=Common.rooms[rid].turn;
		maxCards=Cards.toStringArray(Common.rooms[rid].maxCards.cards);
		
		myId=pid;
		playerLeftId=Common.rooms[rid].pid[leftRoomIndex];
		playerRightId=Common.rooms[rid].pid[rightRoomIndex];
		
		cardsInMyHand=Cards.toStringArray(Common.getPlayer(myId).cardsInHand);		
			
		// Name
		myName=DB.getUserName(myId);
		playerLeftName=DB.getUserName(playerLeftId);
		playerRightName=DB.getUserName(playerRightId);
		
		playerLeftNumberOfCards=Common.getPlayer(playerLeftId).cardsInHand.size();
		playerRightNumberOfCards=Common.getPlayer(playerRightId).cardsInHand.size();
		
	}
}
