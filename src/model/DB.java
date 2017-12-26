package model;

import java.sql.*;

public class DB {
	private static final String DRIVER = "com.mysql.jdbc.Driver"; //数据库的驱动信息	
	private static final String USERNAME = "pj2"; //数据库的用户名
	private static final String PASSWORD = "pj2pj2pj2"; //数据库的密码
	private static final String URL = "jdbc:mysql://111.230.130.50:3306/pj2";//访问数据库的地址
	
	private static Connection connect(){
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
	
	public  static int register(String name,String password) throws SQLException{
		Connection conn=connect();
		String sql = "insert into user(name,password) values(?,?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1,name);
		ps.setString(2,password);
		int result = ps.executeUpdate();
		return result;
	}
	
	public static String getUserName(int pid) throws SQLException {
		Connection conn=connect();
		String sql = "select name from user where id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, pid);
		ResultSet result = ps.executeQuery();
		result.next();
		return result.getString("name");
	}
	
	public static int getId(String name) throws SQLException  {
		Connection conn=connect();
		String sql = "select id from user where name = ?"; 
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1,name);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			return rs.getInt(1);
		}
		else {
			return -1;
		}
	}
	
	public static String getPassword(int id) throws SQLException  {
		Connection conn=connect();
		String sql = "select password from user where id = ? "; 
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1,id);
		ResultSet rs = ps.executeQuery();  //执行sql并赋值给rs
		rs.next();
		return rs.getString(1);	
	}
	public static String getIcon(int pid) throws SQLException {
		Connection conn=connect();
		String sql = "select file_name from icon where icon.id = (select icon from user where id = ?);";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1,pid);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {	
			return "/PJ2/static/images/icon/"+rs.getString("file_name");
		}else {
			return null;
		}
	}
	public static Info getInfo(int pid) throws SQLException {	
		Connection conn=connect();
		String sql = "select id, name, carrots from user where id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1,pid);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {	
			Info info=new Info();
			info.id=rs.getInt("id");
			info.name=rs.getString("name");
			info.carrots=rs.getInt("carrots");
			info.icon=getIcon(pid);
			return info;
		}
		return null;
	}
	
	
	
	

}
