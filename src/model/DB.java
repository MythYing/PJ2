package model;

import java.sql.*;
import java.util.ArrayList;

import com.sun.crypto.provider.RSACipher;

public class DB {
	private static final String DRIVER = "com.mysql.jdbc.Driver"; // 数据库的驱动信息
	private static final String USERNAME = "pj2"; // 数据库的用户名
	private static final String PASSWORD = "pj2pj2pj2"; // 数据库的密码
	private static final String URL = "jdbc:mysql://111.230.130.50:3306/pj2";// 访问数据库的地址

	private static Connection connect() {
		// 注册驱动
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// 连接数据库
		try {
			Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static int register(String name, String password) throws SQLException {
		Connection conn = connect();
		String sql = "call register(?, ?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, name);
		ps.setString(2, password);
		int result = ps.executeUpdate();
		return result;
	}

	public static String getUserName(int pid) throws SQLException {
		Connection conn = connect();
		String sql = "select name from user where id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, pid);
		ResultSet result = ps.executeQuery();
		result.next();
		return result.getString("name");
	}

	public static int getId(String name) throws SQLException {
		Connection conn = connect();
		String sql = "select id from user where name = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, name);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			return rs.getInt("id");
		} else {
			return -1;
		}
	}

	public static String getPassword(int id) throws SQLException {
		Connection conn = connect();
		String sql = "select password from user where id = ? ";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery(); // 执行sql并赋值给rs
		rs.next();
		return rs.getString("password");
	}

	public static String getIcon(int pid) throws SQLException {
		Connection conn = connect();
		String sql = "select file_name from icon where icon.id = (select icon from user where id = ?);";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, pid);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			return "/PJ2/static/images/icon/" + rs.getString("file_name");
		} else {
			return null;
		}
	}

	public static Info getInfo(int pid) throws SQLException {
		Connection conn = connect();
		String sql = "select id, name, carrots from user where id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, pid);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			Info info = new Info();
			info.id = rs.getInt("id");
			info.name = rs.getString("name");
			info.carrots = rs.getInt("carrots");
			info.icon = getIcon(pid);
			return info;
		}
		return null;
	}

	// 插入一局游戏记录
	public static int insertRecord(int rid, long begin_time, int continue_time, int winner, int uid0, int uid1,
			int uid2, int cards_left0, int cards_left1, int cards_left2, int carrots_change0, int carrots_change1,
			int carrots_change2) throws SQLException {
		Connection conn = connect();
		String sql = "insert into game_record(rid, begin_time, continue_time, winner, uid0, uid1, uid2, cards_left0, cards_left1, cards_left2, carrots_change0, carrots_change1, carrots_change2) values(?,?,?,?,?,?,?,?,?,?,?,?,?);";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, rid);
		ps.setTimestamp(2, new Timestamp(begin_time));
		ps.setInt(3, continue_time);
		ps.setInt(4, winner);
		ps.setInt(5, uid0);
		ps.setInt(6, uid1);
		ps.setInt(7, uid2);
		ps.setInt(8, cards_left0);
		ps.setInt(9, cards_left1);
		ps.setInt(10, cards_left2);
		ps.setInt(11, carrots_change0);
		ps.setInt(12, carrots_change1);
		ps.setInt(13, carrots_change2);
		int result = ps.executeUpdate();
		return result;
	}

	// 获取用户id的游戏记录
	public static ResultSet getGameRecord(int pid) throws SQLException {
		Connection conn = connect();
		String sql = "call getGameRecord(?);";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, pid);
		ResultSet rs = ps.executeQuery(); // 执行sql并赋值给rs
		return rs;
	}

	public static ResultSet getGameResult(int pid) throws SQLException {
		Connection conn = connect();
		String sql = "call getGameResult(?); ";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, pid);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public static String getCardSkin(int pid) throws SQLException {
		Connection conn = connect();
		String sql = "select file_name from card_skin where card_skin.id = (select card_skin from user where id = ?);";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, pid);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			return "/PJ2/static/images/card_skin/" + rs.getString("file_name");
		} else {
			return null;
		}
	}
	//获取id的积分
	public static int getCarrots(int pid) throws SQLException  {
		Connection conn = connect();
		String sql = "select carrots from user where id = ? ";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, pid);
		ResultSet rs = ps.executeQuery(); 
		rs.next();
		return rs.getInt("carrots");
	}
	// 改变积分
	public static int getItemPrice(int item) throws SQLException  {
		Connection conn = connect();
		String sql = "select price from shop where id = ? ";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, item);
		ResultSet rs = ps.executeQuery(); 
		rs.next();
		return rs.getInt("price");
	}
	public static int changeCarrots(int pid, int quantity) throws SQLException {
		Connection conn = connect();
		String sql = "call changeCarrots(?, ?);";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, pid);
		ps.setInt(2, quantity);
		int result = ps.executeUpdate();
		return result;
	}
	
	public static ArrayList<ShopItem> getShopIcons() throws SQLException {
		Connection conn = connect();
		String sql = "select shop.id id, icon.name name, icon.file_name file_name, price from icon, shop where type=1 and icon.id=item_id;";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		ArrayList<ShopItem> icons=new ArrayList<>();
		while (rs.next()) {
			icons.add(new ShopItem(rs.getInt("id"), rs.getString("name"), "/PJ2/static/images/icon/"+rs.getString("file_name"), rs.getInt("price")));
		}
		return icons;
	}
	public static ArrayList<ShopItem> getShopCardSkins() throws SQLException {
		Connection conn = connect();
		String sql = "select shop.id id, card_skin.name name, card_skin.file_name file_name, price from card_skin, shop where type=2 and card_skin.id=item_id;";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		ArrayList<ShopItem> cardSkins=new ArrayList<>();
		while (rs.next()) {
			cardSkins.add(new ShopItem(rs.getInt("id"), rs.getString("name"), "/PJ2/static/images/card_skin/"+rs.getString("file_name"), rs.getInt("price")));
		}
		return cardSkins;
	}
	public static int addItemToBag(int pid, int item) throws SQLException {
		Connection conn = connect();
		String sql = "call addItemToBag(?, ?);";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, pid);
		ps.setInt(2, item);
		int result = ps.executeUpdate();
		return result;
	}
	public static ArrayList<BagItem> getBagIcons(int pid) throws SQLException {
		Connection conn = connect();
		String sql = "select bag.id id, icon.name name, icon.file_name file_name from icon, bag where type=1 and icon.id=item_id and uid=?;";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, pid);
		ResultSet rs = ps.executeQuery();
		ArrayList<BagItem> icons=new ArrayList<>();
		while (rs.next()) {
			icons.add(new BagItem(rs.getInt("id"), rs.getString("name"), "/PJ2/static/images/icon/"+rs.getString("file_name")));
		}
		return icons;
	}
	public static ArrayList<BagItem> getBagCardSkins(int pid) throws SQLException {
		Connection conn = connect();
		String sql = "select bag.id id, card_skin.name name, card_skin.file_name file_name from card_skin, bag where type=2 and card_skin.id=item_id and uid=?;";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, pid);
		ResultSet rs = ps.executeQuery();
		ArrayList<BagItem> cardSkins=new ArrayList<>();
		while (rs.next()) {
			cardSkins.add(new BagItem(rs.getInt("id"), rs.getString("name"), "/PJ2/static/images/card_skin/"+rs.getString("file_name")));
		}
		return cardSkins;
	}
	
	
	public static boolean hasItem(int pid, int item) throws SQLException{
		Connection conn = connect();
		String sql = "call hasItem(?, ?);";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, pid);
		ps.setInt(2, item);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			return true;
		}else {
			return false;
		}
	}
	
	public static boolean useItem(int pid, int item) throws SQLException{
		Connection conn = connect();
		String sql = "select * from bag where uid=? and id=?;";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, pid);
		ps.setInt(2, item);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			int type=rs.getInt("type");
			int itemId=rs.getInt("item_id");
			switch (type) {
			case 1:
				setIcon(pid, itemId);
				break;
			case 2:
				setCardSkin(pid, itemId);
				break;
			default:
				break;
			}
			return true;
		}else {
			return false;
		}
	}
	
	public static int setIcon(int pid,int itemId) throws SQLException {
		Connection conn = connect();
		String sql = "update user set icon = ? where id = ? ";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, itemId);
		ps.setInt(2, pid);
		return ps.executeUpdate();	
	}
	
	public static int setCardSkin(int pid,int itemId) throws SQLException {
		Connection conn = connect();
		String sql = "update user set card_skin = ? where id = ? ";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, itemId);
		ps.setInt(2, pid);
		return ps.executeUpdate();	
	}
	
	public static boolean hasName(String name) throws SQLException {
		Connection conn = connect();
		String sql = "select id from user where name = ? ";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, name);
		ResultSet rs = ps.executeQuery();
		return rs.next();
	}


}
