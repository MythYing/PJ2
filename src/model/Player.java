package model;

import java.util.ArrayList;

public class Player {
	public int pid;
	public ArrayList<Card> cardsInHand;
	
	public Player(int pid) {
		this.pid=pid;
		System.out.println("Player类已创建");
		cardsInHand=new ArrayList<Card>();
	}
}
