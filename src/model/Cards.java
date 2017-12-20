package model;

import java.util.ArrayList;

public class Cards {
	public String type;
	public ArrayList<Card> cards=new ArrayList<>();
	
	public Cards(String[] cards) {
		this.cards=Cards.toArrayList(cards);
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
}
