package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Cards {
	public String type;
	public ArrayList<Card> cards=new ArrayList<>();
	
	public Cards(String[] cards) {
		this.cards=Cards.toArrayList(cards);
		Collections.sort(this.cards, new CardComparator());
		// 牌型...
		this.type=Rules.getCardType(this.cards);
		System.out.println(type);
	}
	
	public static ArrayList<Card> toArrayList(String[] cardsStringArray) {
		ArrayList<Card> cardsArrayList=new ArrayList<>();
		if (cardsStringArray!=null) {
			for (String card : cardsStringArray) {
				cardsArrayList.add(Card.toCard(card));
			}
		}
		return cardsArrayList;
	}
	
	public static String[] toStringArray(ArrayList<Card> cardsArrayList) {
		int size=cardsArrayList.size();
		String[] cardsStringArray=new String[size];
		for(int i=0;i<size;i++) {
			cardsStringArray[i]=Card.toString(cardsArrayList.get(i));
		}
		return cardsStringArray;
	}
	class CardComparator implements Comparator<Card>{
		@Override
		public int compare(Card c1, Card c2) {
			int c1Rank=Card.rankToInt(c1.rank);
			int c2Rank=Card.rankToInt(c2.rank);
			return c1Rank-c2Rank;
		}
	}

	
}
