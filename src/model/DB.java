package model;

import java.sql.*;

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
		String sql = "insert into user(name,password) values(?,?)";
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
		String sql = "select * from game_record where uid0 = ? or uid1 = ? or uid2 = ? ";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, pid);
		ps.setInt(2, pid);
		ps.setInt(3, pid);
		ResultSet rs = ps.executeQuery(); // 执行sql并赋值给rs
		return rs;
	}

	public static ResultSet getGameResult(int pid) throws SQLException {
		Connection conn = connect();
		String sql = "select * from game_record where uid0 = ? or uid1 = ? or uid2 = ? order by gid desc limit 0, 1; ";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, pid);
		ps.setInt(2, pid);
		ps.setInt(3, pid);
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

	public static int changeCarrots(int pid, int change) throws SQLException {
		Connection conn = connect();
		String sql1 = "select carrots from user where id = ?;";
		PreparedStatement ps1 = conn.prepareStatement(sql1);
		ps1.setInt(1, pid);
		ResultSet rs1 = ps1.executeQuery();
		rs1.next();
		int carrots = rs1.getInt("carrots");
		carrots += change;

		String sql = "update user set carrots = ? where id = ?;";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, carrots);
		ps.setInt(2, pid);
		int result = ps.executeUpdate();
		return result;
	}
}
