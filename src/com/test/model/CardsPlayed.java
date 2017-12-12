package com.test.model;

import java.util.ArrayList;

public class CardsPlayed {
	public String type;
	public ArrayList<Card> cards=new ArrayList<>();
	
	public CardsPlayed(String[] cardsPlayed) {
		this.cards=Cards.toArrayList(cardsPlayed);
	}
}
