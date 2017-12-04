package com.test.model;

public class Card {
	public String rank;
	public String suit;
	public boolean isLight;
	
	public Card(String rankC,String suitC) {
		rank=rankC;
		suit=suitC;
		isLight=false;
	}
	public Card(String rankC,String suitC,boolean isFirstC) {
		rank=rankC;
		suit=suitC;
		isLight=isFirstC;
	}
}
