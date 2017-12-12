package com.test.model;

public class Test {

	public static void main(String[] args) {
		Card a=Card.toCard("clubs10");
		System.out.println(a.rank);
		System.out.println(a.suit);
		
	}

}
