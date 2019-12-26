package sqlConn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Con {
	public Connection connection;
	
	
	public Con(String username, String password) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ABD", username, password);
	}
	
	public void disconnect() throws SQLException {
		connection.close();
	}
}
