package com.test.model;

import java.util.ArrayList;
import java.util.Collections;

public class Room{
	public int[] pid=new int[3];
	public Player[] players= new Player[3];
	public Card lightCard;
	public int turn = -1;
	public CardsPlayed maxCards;
	
	public Room(int[] p) {
		System.out.println("Room类已创建");
		
		for(int i=0;i<3;i++) {
			this.pid[i]=p[i];
			this.players[i]=new Player(p[i]);
		}
		deal(players);
		System.out.println("pid="+p[turn]+"第一个出牌");
	}
	// 发牌
	public void deal(Player[] p) {
		ArrayList<Card> cards = new ArrayList<Card>();
		String[] ranks = { "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A", "2" };
		String[] suits = { "diamonds", "clubs", "hearts", "spades" };

		for (int i = 0; i < 52; i++) {
			cards.add(new Card(ranks[i % 13], suits[i / 13]));
			// System.out.println(i+" "+cards.get(i).rank+" "+cards.get(i).suit);
		}
		// 去掉三张“2”，一张“A”，剩48张牌
		cards.remove(51);
		cards.remove(50);
		cards.remove(38);
		cards.remove(25);

		// 洗牌
		Collections.shuffle(cards);
		// 一张亮牌
		int lightCardIndex = (int) (Math.random() * 48);
		// 亮的牌
		this.lightCard = cards.get(lightCardIndex);
		
		// 发牌
		for (int i = 0; i < 48; i++) {
			// System.out.println("发牌-->玩家"+i % 3);
			if (i==lightCardIndex) {
				System.out.println("****************"+cards.get(i).suit + cards.get(i).rank + "给了playerInRoomIndex" + i % 3+"，该用户第一个出牌");
				this.turn =i % 3;
			}
			p[i % 3].cardsInHand.add(cards.get(i));
		}
		
		for(int i=0;i<=2;i++) {
			Collections.sort(p[i].cardsInHand, new CardComparator());
		}

	}
	
	
}
