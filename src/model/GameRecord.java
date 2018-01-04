package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class GameRecord {
	public String myName;
	public int myCarrotsChange;
	public String beginTime;
	public String continueTime;
	public String winner;
	public String[] names=new String[3];
	public String[] icons=new String[3];
	public int[] cardsLeft = new int[3];
	public int[] carrotsChange = new int[3];
	
	public GameRecord(String myName, ResultSet rs) throws SQLException {	
		this.myName=myName;
		// 房间数据
		Timestamp timestamp=rs.getTimestamp("begin_time");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		beginTime=sdf.format(timestamp);
		continueTime=Tools.toTimeString(rs.getInt("continue_time"));
		winner=rs.getString("winner");
		// 用户名
		names[0]=rs.getString("user0");
		names[1]=rs.getString("user1");
		names[2]=rs.getString("user2");
		// 头像
		icons[0]="/PJ2/static/images/icon/" + rs.getString("icon0");
		icons[1]="/PJ2/static/images/icon/" + rs.getString("icon1");
		icons[2]="/PJ2/static/images/icon/" + rs.getString("icon2");
		// 卡片剩余数量
		cardsLeft[0]=rs.getInt("cards_left0");
		cardsLeft[1]=rs.getInt("cards_left1");
		cardsLeft[2]=rs.getInt("cards_left2");
		// 胡萝卜变化
		carrotsChange[0]=rs.getInt("carrots_change0");
		carrotsChange[1]=rs.getInt("carrots_change1");
		carrotsChange[2]=rs.getInt("carrots_change2");	
		for(int i=0;i<3;i++) {
			if (names[i].equals(myName)) {
				myCarrotsChange=carrotsChange[i];
				break;
			}
		}
	}
}
