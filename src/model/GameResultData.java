package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GameResultData{
	public int myId;
	public String winner;
	public String[] names=new String[3];
	public String[] icons=new String[3];
	public int[] carrotChanges = new int[3];
	
	public GameResultData(int pid) throws SQLException {
		ResultSet rs=DB.getGameResult(pid);
		myId=pid;
		if(rs.next()) {
			// winner
			winner=DB.getUserName(rs.getInt("winner"));
			// 用户名
			names[0]=DB.getUserName(rs.getInt("uid0"));
			names[1]=DB.getUserName(rs.getInt("uid1"));
			names[2]=DB.getUserName(rs.getInt("uid2"));
			// 头像
			icons[0]=DB.getIcon(rs.getInt("uid0"));
			icons[1]=DB.getIcon(rs.getInt("uid1"));
			icons[2]=DB.getIcon(rs.getInt("uid2"));
			// 胡萝卜变化
			carrotChanges[0]=rs.getInt("carrots_change0");
			carrotChanges[1]=rs.getInt("carrots_change1");
			carrotChanges[2]=rs.getInt("carrots_change2");
		}		
	}
}
