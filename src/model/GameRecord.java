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
		winner=DB.getUserName(rs.getInt("winner"));
		// 用户名
		names[0]=DB.getUserName(rs.getInt("uid0"));
		names[1]=DB.getUserName(rs.getInt("uid1"));
		names[2]=DB.getUserName(rs.getInt("uid2"));
		// 头像
		icons[0]=DB.getIcon(rs.getInt("uid0"));
		icons[1]=DB.getIcon(rs.getInt("uid1"));
		icons[2]=DB.getIcon(rs.getInt("uid2"));
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
			}
		}
	}
}
