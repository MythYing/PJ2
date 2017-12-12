package com.test.model;

import java.util.ArrayList;

public class Cards {
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
