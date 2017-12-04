package com.test.model;

import java.sql.SQLException;
import java.util.ArrayList;

public class VisibleGameData {
	public int rid;
	
	public int myId;
	public ArrayList<Card> cardsInMyHand;
	
	public int playerLeftId;
	public int playerRightId;
	
	public String playerLeftName;
	public String playerRightName;
	
	public int playerLeftNumberOfCards;	
	public int playerRightNumberOfCards;
	
	public int turn;
	
	public VisibleGameData(int pid) throws SQLException {
		int myRoomIndex=Common.playerPositionMap.get(pid).roomIndex;
		int leftRoomIndex=(myRoomIndex+3-1)%3;
		int rightRoomIndex=(myRoomIndex+3+1)%3;
		
		rid=Common.playerPositionMap.get(pid).rid;
		
		myId=pid;		
		cardsInMyHand=Common.rooms[rid].players[myRoomIndex].cardsInHand;
		
		
		
		playerLeftId=Common.rooms[rid].pid[leftRoomIndex];
		playerRightId=Common.rooms[rid].pid[rightRoomIndex];
		
		// Name
		playerLeftName=DB.getUserName(playerLeftId);
		playerRightName=DB.getUserName(playerRightId);
		
		playerLeftNumberOfCards=Common.rooms[rid].players[leftRoomIndex].cardsInHand.size();
		playerRightNumberOfCards=Common.rooms[rid].players[rightRoomIndex].cardsInHand.size();
		
		turn=Common.rooms[rid].turn;
	}
}
