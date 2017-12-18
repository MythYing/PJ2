package model;

import java.util.ArrayList;

public class Cards {
	public String type;
	public ArrayList<Card> cards=new ArrayList<>();
	
	public Cards(String[] cards) {
		this.cards=Cards.toArrayList(cards);
		// 牌型...
	}
	
	public static ArrayList<Card> toArrayList(String[] cardsStringArray) {
		ArrayList<Card> cardsArrayList=new ArrayList<>();
		for (String card : cardsStringArray) {
			cardsArrayList.add(Card.toCard(card));
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
}
