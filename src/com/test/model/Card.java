package com.test.model;

public class Card {
	public String rank;
	public String suit;

	public Card() {

	}

	public Card(String rank, String suit) {
		this.rank = rank;
		this.suit = suit;
	}

	public static Card toCard(String cardStr) {
		Card card = new Card();
		int pos = cardStr.indexOf('s');
		card.suit = cardStr.substring(0, pos + 1);
		card.rank = cardStr.substring(pos + 1);
		return card;
	}

	public String toString() {
		return this.suit + this.rank;
	}

	public static String toString(Card card) {
		return card.suit + card.rank;
	}
}
