package model;

public class Test {

	public static void main(String[] args) {
		Card card=new Card("spades8");
		System.out.println(card.rank+"  "+card.suit);
		System.out.println("spades8".lastIndexOf('s'));
	}

}
