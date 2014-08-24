import java.sql.*;

public class DatabaseClass {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/DBConnection";
	static final String USER = "root";
	static final String PASS = "";

	Connection conn = null;
	Statement stmt = null;

	public boolean connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			System.out.println("Connected to Database...");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean checkUser(int flag, String text) {
		String sql = "SELECT NICK FROM CLIENTS WHERE ";
		if (flag == 0)
			sql = sql + "IP='" + text + "';";
		else
			sql = sql + "NICK='" + text + "';";
		System.out.println(sql);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (!rs.next())
				return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

	public void addUser(String nick, String ip) {

		String sql = "INSERT INTO `clients` (`cid`, `ip`, `nick`) VALUES (NULL, '"
				+ ip + "', '" + nick + "');";
		try {
			if (stmt.executeUpdate(sql) != 1)
				System.out.println("update error");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			conn.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
