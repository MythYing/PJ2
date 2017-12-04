package com.test.model;

public class Room{
	public int[] pid=new int[3];
	public Player[] players= new Player[3];
	public int turn;
	
	public Room(int[] waitingPlayers) {
		System.out.println("Room类已创建");
		
		for(int i=0;i<3;i++) {
			pid[i]=waitingPlayers[i];
			players[i]=new Player(waitingPlayers[i]);
		}
		int newTurn=Deal.deal(players);
		turn=newTurn;
		System.out.println("pid="+waitingPlayers[turn]+"第一个出牌");
//		玩家手中的牌
//		for(int j=0;j<=2;j++) {
//			System.out.println("玩家"+j+"手中的牌：");
//			for(int i=0;i<=15;i++) {
//				System.out.println(p[j].cardsInHand.get(i).suit+p[j].cardsInHand.get(i).rank);
//			}
//		}
	}
	
	
}
