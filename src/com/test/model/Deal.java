package com.test.model;

import java.util.ArrayList;
import java.util.Collections;

public class Deal {
//	public static void main(String[] args) {
//		Player[] p = new Player[3];
//		// 对象数组的初始化
//		for(int i=0;i<3;i++) {
//			p[i]=new Player();
//		}
//		
//		int turn=deal(p);
//		
//		for(int j=0;j<=2;j++) {
//			System.out.println("玩家"+j+"手中的牌：");
//			for(int i=0;i<=15;i++) {
//				System.out.println(p[j].cardsInHand.get(i).suit+p[j].cardsInHand.get(i).rank);
//			}
//		}
//		
//		
//	}
//	
	public static int deal(Player[] p) {
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
		int lightCard = (int) (Math.random() * 48);

		cards.get(lightCard).isLight = true;

		// System.out.println(lightCard);
		// System.out.println(cards.get(lightCard).suit+cards.get(lightCard).rank+cards.get(lightCard).isLight);
		
		// 发牌
		int firstOuts=-1;
		for (int i = 0; i < 48; i++) {
			// System.out.println("发牌-->玩家"+i % 3);
			if (cards.get(i).isLight) {
				System.out.println("****************"+cards.get(i).suit + cards.get(i).rank + "给了playerInRoomIndex" + i % 3+"，该用户第一个出牌");
				firstOuts=i % 3;
			}
			p[i % 3].cardsInHand.add(cards.get(i));
		}
		
		for(int i=0;i<=2;i++) {
			Collections.sort(p[i].cardsInHand, new CardComparator());
		}
		
		return firstOuts;

	}

}
