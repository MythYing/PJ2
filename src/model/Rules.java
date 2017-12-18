package model;

import java.util.ArrayList;
/**
 * DanPai
 * DuiZi
 * LianDui
 * SanDaiEr
 * FeiJi
 * ZhaDan
 * ShunZi
 * SiDaiSan
 * @author Tasdily
 *
 */
public class Rules {
	
	
	// ****************** 主要函数 *****************
	public static boolean successful(int pid, Cards cardsPlayed) { // 执行主体；决定牌能不能出	
		Cards getMaxCards = Common.getMaxCards(pid); //从服务器获取桌上最大的牌
		
		int[] newcard = cardToInt(cardsPlayed.cards); // newcard为选中传过来的牌，将其转为int[] 的card.....49
		int[] MaxCards = cardToInt(getMaxCards.cards);		
		int MaxPlayerId = Common.getMaxPlayer(pid);// 从服务器获取打出最大牌的pid 	
		
		/**
		 * 传来的牌为空(玩家不出牌)
		 */
		if (newcard.length == 0) {
			if (MaxPlayerId == -1) 	// 桌上没牌，说明这是第一个出牌玩家，不能不出牌
				return false;
			else 						// 玩家无牌pass
				return true;
		}

		/**
		 * 传来的牌不为空
		 */
		
		/** 能出牌只有if中的情况： */
		if ( isRegular(newcard) && (pid == MaxPlayerId || isAfford(newcard, MaxCards) || MaxPlayerId == -1) ) {
			return true;
		} 
		else
			return false;
	}
	// **********************************************************************

	// 将服务端传来的牌转为int[]类型
	private static int[] cardToInt(ArrayList<Card> cards) {
		if (cards==null) {
			return new int[0];
		}
		int[] card = new int[cards.size()];
		for (int i = 0; i < cards.size(); i++) {			
			card[i] = rankToInt(cards.get(i).rank);
		}
		return card;
	}
	private static int rankToInt(String s) {
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
	
	/*
	 * 判断出牌是否合法
	 */
	private static boolean isRegular(int[] newcard) {		
		return (getCardType(newcard)[0] == "false") ;
	}
	
	/*
	 * 判断自己的牌是否大于上家的牌
	 */
	public static boolean isAfford(int[] newcard, int[] MaxCards) {
		int len = newcard.length;
		int lastlen = MaxCards.length;

		if (len != lastlen && len != 4)
			return false;

		if (len == 4 && lastlen != 4 && bomb(newcard) != 0) {
			return true;
		}
		
		if(getCardType(newcard)[0] == getCardType(MaxCards)[0]) {
			if(getCardType(newcard)[0] =="LianDui") {
				if(newcard.length == MaxCards.length) {
					int cardRank = Integer.parseInt(getCardType(newcard)[1]);
					int MaxCardsRank = Integer.parseInt(getCardType(MaxCards)[1]);
					return (cardRank > MaxCardsRank);
				}
				else {
					return false;
				}
			}
			if(getCardType(newcard)[0] =="ShunZi") {
				if(newcard.length == MaxCards.length) {
					int cardRank = Integer.parseInt(getCardType(newcard)[1]);
					int MaxCardsRank = Integer.parseInt(getCardType(MaxCards)[1]);
					return (cardRank > MaxCardsRank);
				}
				else {
					return false;
				}
			}
			if(getCardType(newcard)[0] =="FeiJI") {
				if(feiJi(newcard)[1] == feiJi(MaxCards)[1]) {
					int cardRank = Integer.parseInt(getCardType(newcard)[1]);
					int MaxCardsRank = Integer.parseInt(getCardType(MaxCards)[1]);
					return (cardRank > MaxCardsRank);
				}
				else {
					return false;
				}
			}
			int cardRank = Integer.parseInt(getCardType(newcard)[1]);
			int MaxCardsRank = Integer.parseInt(getCardType(MaxCards)[1]);
			return (cardRank > MaxCardsRank);
		}
		return false;
	}
	
	/*
	 * 返回牌的类型
	 */
	public static String[] getCardType(int[] x){
		String[] TypeandRank = new String[2];
		if(x.length == 1){
			TypeandRank[0] = "DanPai";
			TypeandRank[1] = String.valueOf(singleCard(x));
			return TypeandRank;
		}
		if(x.length == 2 && onePair(x) !=0){
			TypeandRank[0] = "DuiZi";
			TypeandRank[1] = String.valueOf(onePair(x));
			return TypeandRank;

		}
		if(x.length == 3 && threeKind(x) !=0){
			TypeandRank[0] = "SanDaiEr";
			TypeandRank[1] = String.valueOf(threeKind(x));
			return TypeandRank;
		}
		if(x.length == 4 && bomb(x) !=0){
			TypeandRank[0] = "ZhaDan";
			TypeandRank[1] = String.valueOf(bomb(x));
			return TypeandRank;
		}
		if(x.length >= 4 && x.length%2 == 0 && multiplePair(x) !=0){
			TypeandRank[0] = "LianDui";
			TypeandRank[1] = String.valueOf(multiplePair(x));
			return TypeandRank;
		}
		if(x.length == 4 && sandaiyi(x) !=0){// san dai yi!
			TypeandRank[0] = "SanDaiEr";
			TypeandRank[1] = String.valueOf(sandaiyi(x));
			return TypeandRank;
		}
		if(x.length == 5 && sandaier(x) !=0){//san dai er!
			TypeandRank[0] = "SanDaiEr";
			TypeandRank[1] = String.valueOf(sandaier(x));
			return TypeandRank;
		}
		if(x.length == 5 && sidaiyi(x) !=0){//si dai yi!
			TypeandRank[0] = "SiDaiSan";
			TypeandRank[1] = String.valueOf(sandaier(x));
			return TypeandRank;
		}
		if(x.length == 6 && sidaier(x) !=0){//si dai er!
			TypeandRank[0] = "SiDaiSan";
			TypeandRank[1] = String.valueOf(sandaier(x));
			return TypeandRank;
		}
		if(x.length == 7 && sidaisan(x) !=0){//si dai san!
			TypeandRank[0] = "SiDaiSan";
			TypeandRank[1] = String.valueOf(sandaier(x));
			return TypeandRank;
		}
		if(x.length>=6 && feiJi(x)[1] != 0){
			TypeandRank[0] = "FeiJi";
			TypeandRank[1] = String.valueOf(feiJi(x)[0]);
			return TypeandRank;
		}
		if(x.length >= 5 && straight(x) !=0){
			TypeandRank[0] = "ShunZi";
			TypeandRank[1] = String.valueOf(straight(x));
			return TypeandRank;
		}
		TypeandRank[0] = "false";
		TypeandRank[1] = "false";
		return TypeandRank;
	}
	/**straight 顺子 
	four of a kind 四张相同的牌 
	full house 三张相同和二张相同的牌 
	three of a kind 三张相同的牌 
	two pairs 双对子 
	one pair 一对,对子 */
	public static int singleCard(int[] x) {//单牌，返回牌的大小
		if (x.length != 1)
		return 0;
		return x[0];
	}
	
	public static int onePair(int[] x) {//单对
		for (int i = 1; i < 2; i++) {
			if (x[i - 1] != x[i])
				return 0;
		}
		return x[0];
	}
	public static int threeKind(int[] x) {//三张相同的牌，不带牌
		for (int i = 1; i < 3; i++) {
			if (x[i - 1] != x[i])
				return 0;
		}
		return x[0];
	}
	public static int bomb(int[] x) {//四张相同的牌，炸弹
		for (int i = 1; i < 4; i++) {
			if (x[i - 1] != x[i])
				return 0;
		}
		return x[0];
	}
	/*
	 * 三带一
	 */
	public static int sandaiyi(int[] x) {
		if ( x[1] == x[2] && x[2] == x[3])
			return x[1];
		else if (x[0] == x[1] && x[1] == x[2])
			return x[0];
		return 0;
	}

	/*
	 * 三带二
	 */
	public static int sandaier(int[] x) {
		if (x[2] == x[3] && x[3] == x[4])
			return x[2];
		else if (x[0] == x[1] && x[1] == x[2])
			return x[0];
		else if (x[1] == x[2] && x[2] == x[3])
			return x[1];
		return 0;
	}
	/*
	 * 四带一
	 */
	public static int sidaiyi(int[] x) {
		if ( x[1] == x[2] && x[2] == x[3] && x[3] == x[4])
			return x[1];
		else if (x[0] == x[1] && x[1] == x[2] && x[2] == x[3])
			return x[0];
		return 0;
	}

	/*
	 * 四带二
	 */
	public static int sidaier(int[] x) {
		if (x[2] == x[3] && x[3] == x[4] && x[4] == x[5])
			return x[2];
		else if (x[0] == x[1] && x[1] == x[2] && x[2] == x[3])
			return x[0];
		else if (x[1] == x[2] && x[2] == x[3] && x[3] == x[4])
			return x[1];
		return 0;
	}
	/*
	 * 四带三
	 */
	public static int sidaisan(int[] x) {
		if (x[2] == x[3] && x[3] == x[4] && x[4] == x[5])
			return x[2];
		else if (x[0] == x[1] && x[1] == x[2] && x[2] == x[3])
			return x[0];
		else if (x[1] == x[2] && x[2] == x[3] && x[3] == x[4])
			return x[1];
		else if (x[3] == x[4] && x[4] == x[5] && x[5] == x[6])
			return x[3];
		return 0;
	}
	/*
	 * n个对子相连
	 */
	public static int multiplePair(int[] x) {//连对
		for (int i = 0; i < x.length - 1; i = i + 2) {
			if (x[i] != x[i + 1])
				return 0;
		}
		return x[0];
	}
	/*
	 * n个三相连
	 */
	public static int[] feiJi(int[] x) {
		int[] rankAndType = new int[2];
		if(x.length >= 6 && x.length <=10) {//AAABBB型带四张以内的牌
			for (int j = 0; j <= x.length - 6; j++) {
				if (x[j] == x[j + 1] && x[j + 1] == x[j + 2] && x[j + 2] == (x[j + 3] + 1) && x[j + 3] == x[j + 4] && x[j + 4] == x[j + 5] ) {
					rankAndType[0] = x[j];
					rankAndType[1] = 6;
					return rankAndType;
				}
			}
		}
		if(x.length >= 9 && x.length <=15) {//AAABBBCCC型带六张以内的牌
			for (int j = 0; j <= x.length - 9; j++) {
				for (int i = j; i < j + 9; i = i + 3) {
					if (x[i] != x[i + 1] || x[i + 1] != x[i + 2]) {
						rankAndType[0] = 0;
						rankAndType[1] = 0;
						return rankAndType;
					}
				}
				rankAndType[0] = x[j];
				rankAndType[1] = 9;
				return rankAndType;
			}
		}
		if(x.length >= 12 && x.length <=16) {//AAABBBCCCDDD型带八张以内的牌
			for (int j = 0; j <= x.length - 12; j++) {
				for (int i = j; i < j + 12; i = i + 3) {
					if (x[i] != x[i + 1] || x[i + 1] != x[i + 2]) {
						rankAndType[0] = 0;
					rankAndType[1] = 0;
					return rankAndType;
				}
			}
			rankAndType[0] = x[j];
			rankAndType[1] = 12;
			return rankAndType;
			}
		}
		if(x.length >= 15) {//AAABBBCCCDDDEEE型
			for (int j = 0; j <= x.length - 15; j++) {
				for (int i = j; i < j + 15; i = i + 3) {
					if (x[i] != x[i + 1] || x[i + 1] != x[i + 2]) {
					rankAndType[0] = 0;
					rankAndType[1] = 0;
					return rankAndType;
				}
			}
			rankAndType[0] = x[j];
			rankAndType[1] = 15;
			return rankAndType;
			}
		}
		rankAndType[0] = 0;
		rankAndType[1] = 0;
		return rankAndType;
	}
	/*
	 * 顺子
	 */
	public static int straight(int[] x) {
		for (int i = 1; i < x.length; i++) {
			if (x[i - 1] != x[i] + 1)
				return 0;
		}
		return x[0];
	}
}
