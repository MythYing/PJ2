package model;

public class Test {

	public static void main(String[] args) {
		String[] cStrings={"diamonds8","diamonds9","diamonds10"};
		Cards cards=new Cards(cStrings) ;
		for (Card c : cards.cards) {
			System.out.println(c.toString());
		}
		String[] cStrings1={"diamonds8","diamonds9"};
		
		cards.cards.removeAll(Cards.toArrayList(cStrings1));
		
		for (Card c : cards.cards) {
			System.out.println(c.toString());
		}
	}

}
