package com.test.model;

import java.util.Comparator;

public class CardComparator implements Comparator<Card>{
	public int compare(Card c1,Card c2) {
		int c1Rank=rankToInt(c1.rank);
		int c2Rank=rankToInt(c2.rank);
		return c1Rank-c2Rank;
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
}
