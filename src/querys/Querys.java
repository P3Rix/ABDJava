package querys;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import entities.Book;
import sqlConn.Con;

public class Querys {
	public Con conn;
	public Statement statement;
	public ResultSet rs;
	public Object[] valuesBook = new Object[8];
	
	public String queryAllBooks = "select * from Libro";
	
	public Querys(Con conn) {
		this.conn = conn;
	}
	
	public ArrayList<Book> getAllBooks() {
		ArrayList<Book> books = new ArrayList<Book>();

		if(conn != null && conn.connection != null) {
			try {
				statement = conn.connection.createStatement();
				rs = statement.executeQuery(queryAllBooks);
				
				while(rs.next()) {
					for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
						valuesBook[i - 1] = rs.getObject(i);
					}
					books.add(new Book((int) valuesBook[0], (String) valuesBook[1], (String) valuesBook[3], (int) valuesBook[4], (int) valuesBook[5], (Timestamp) valuesBook[6], (int) valuesBook[7], (String) valuesBook[2]));
				}
				
				return books;
			} catch(SQLException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}
	
	public Book getBookById(int id) {
		Book book = null;
		
		if(conn != null && conn.connection != null) {
			try {
				statement = conn.connection.createStatement();
				rs = statement.executeQuery("select * from Libro where id = "+ id);
				
				while(rs.next()) {
					for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
						valuesBook[i - 1] = rs.getObject(i);
					}
					
					book = new Book((int) valuesBook[0], (String) valuesBook[1], (String) valuesBook[3], (int) valuesBook[4], (int) valuesBook[5], (Timestamp) valuesBook[6], (int) valuesBook[7], (String) valuesBook[2]);
					
				}
				
				return book;
			} catch(SQLException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}
	
	public ArrayList<Book> getBookByName(String name) {
		ArrayList<Book> books = new ArrayList<Book>();
		
		if(conn != null && conn.connection != null) {
			try {
				statement = conn.connection.createStatement();
				rs = statement.executeQuery("select * from Libro where Nombre like '%"+ name + "%'");
				
				while(rs.next()) {
					for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
						valuesBook[i - 1] = rs.getObject(i);
					}
					
					books.add(new Book((int) valuesBook[0], (String) valuesBook[1], (String) valuesBook[3], (int) valuesBook[4], (int) valuesBook[5], (Timestamp) valuesBook[6], (int) valuesBook[7], (String) valuesBook[2]));
					
				}
				
				return books;
			} catch(SQLException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}
}
