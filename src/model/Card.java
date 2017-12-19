package model;

public class Card implements Comparable<Card>{
	public String rank;
	public String suit;

	public Card() {

	}
	
	public Card(String cardStr) {
		int pos = cardStr.indexOf('s');
		this.suit = cardStr.substring(0, pos + 1);
		this.rank = cardStr.substring(pos + 1);	
	}
	public Card(String suit, String rank) {
		this.suit = suit;
		this.rank = rank;	
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

	@Override
	public int compareTo(Card c) {
		int c1Rank=rankToInt(this.rank);
		int c2Rank=rankToInt(c.rank);
		return c2Rank-c1Rank;
	}
	
	public static int rankToInt(String s) {
		switch (s) {
		case "3":
			return 1;
		case "4":
			return 2;
		case "5":
			return 3;
		case "6":
			return 4;
		case "7":
			return 5;
		case "8":
			return 6;
		case "9":
			return 7;
		case "10":
			return 8;
		case "J":
			return 9;
		case "Q":
			return 10;
		case "K":
			return 11;
		case "A":
			return 12;
		case "2":
			return 13;
		default:
			return 0;
		}
	}
	@Override
	public boolean equals(Object o) {  
		if(o == null) {  
			return false;  
		} 
		if(getClass() != o.getClass()) {  
			return false;  
		}
		Card card=(Card)o;
		if (this.suit.equals(card.suit) && this.rank.equals(card.rank)) {
			return true;
		}else {
			return false;
		}
		 
	}
}
