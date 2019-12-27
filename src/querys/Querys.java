package querys;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

import entities.Book;
import sqlConn.Con;

public class Querys {
	public Con conn;
	public Statement statement;
	public ResultSet rs;
	public Object[] valuesBook = new Object[7];
	public Scanner sc;
	public String queryAllBooks = "select * from Libro";
	
	public Querys(Con conn) {
		this.conn = conn;
		sc = new Scanner(System.in);
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
					books.add(new Book((int) valuesBook[0], (String) valuesBook[1], (String) valuesBook[3], (int) valuesBook[4], (String) valuesBook[2], (Timestamp) valuesBook[5], (String) valuesBook[6]));
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
					
					book = new Book((int) valuesBook[0], (String) valuesBook[1], (String) valuesBook[3], (int) valuesBook[4], (String) valuesBook[2], (Timestamp) valuesBook[5], (String) valuesBook[6]);
					
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
					
					books.add(new Book((int) valuesBook[0], (String) valuesBook[1], (String) valuesBook[3], (int) valuesBook[4], (String) valuesBook[2], (Timestamp) valuesBook[5], (String) valuesBook[6]));
					
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
	
	public void reservarLibro(int idLibro, int idLibreria) {
		try {
			statement = conn.connection.createStatement();
			rs = statement.executeQuery("select stock from Libreria_Libro where ID_libro = " + idLibro + " and ID_libreria = " + idLibreria);
			if (rs.next() == false) {
				System.out.println("No existe el libro en dicha libreria\n\n");
			} else {
				do {
					if (rs.getInt(1) > 0) {
						System.out.println("Hay stock " + rs.getInt(1));
						int newStock = rs.getInt(1) - 1;
						System.out.println("Introduce el dni del usuario: ");
						String dni = sc.next();
						Statement newStatement = conn.connection.createStatement();
						ResultSet userResultset = newStatement.executeQuery("Select id from Usuario where DNI like '" + dni + "'");
						if (userResultset.next() == false) {
							System.out.println("No existe el usuario con dni: " + dni + "\n\n");
						} else {
							do {
								int id_user = userResultset.getInt(1);
								rs = newStatement.executeQuery("select max(ID) from Reserva");
								rs.next();
								int max = rs.getInt(1) + 1;
								Timestamp now = new Timestamp(System.currentTimeMillis());
								long millis_date = System.currentTimeMillis() + 1000000000l;
								Timestamp fecha_limite = new Timestamp(millis_date);
								//System.out.println("insert into Reserva values(\'" + max + "', \'" + idLibro + "\', \'" + id_user + "\', \'" + idLibreria + "\', \'" + now + "\', \'" + fecha_limite + "\');");
								statement.executeUpdate("insert into Reserva values('" + max + "', '" + idLibro + "', '" + id_user + "', '" + idLibreria + "', '" + now + "', '" + fecha_limite + "');");
								statement.executeUpdate("update Libreria_Libro SET stock = " + newStock + " where ID_Libro = " + idLibro + " and " + idLibreria);
								System.out.println("La reserva se ha realizado, fecha límite: " + fecha_limite.toString());
								break;
							} while(userResultset.next());
						}	
					} else {
						System.out.println("No hay stock disponible para el libro indicado");
					}
				} while(rs.next());
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void venderLibro(int idLibro, int idLibreria) {
		try {
			statement = conn.connection.createStatement();
			rs = statement.executeQuery("select stock, precio from Libreria_Libro where ID_libro = " + idLibro + " and ID_libreria = " + idLibreria);
			if (rs.next() == false) {
				System.out.println("No existe el libro en dicha libreria\n\n");
			} else {
				do {
					if (rs.getInt(1) > 0) {
						System.out.println("Hay stock " + rs.getInt(1));
						int newStock = rs.getInt(1) - 1;
						int valor = rs.getInt(2);
						System.out.println("Introduce el dni del usuario: ");
						String dni = sc.next();
						Statement newStatement = conn.connection.createStatement();
						ResultSet userResultset = newStatement.executeQuery("Select id from Usuario where DNI like '" + dni + "'");
						if (userResultset.next() == false) {
							System.out.println("No existe el usuario con dni: " + dni + "\n\n");
						} else {
							do {
								int id_user = userResultset.getInt(1);
								rs = newStatement.executeQuery("select max(ID) from Venta");
								rs.next();
								int max = rs.getInt(1) + 1;
								Timestamp now = new Timestamp(System.currentTimeMillis());
								//System.out.println("insert into Venta values(\'" + max + "', \'" + idLibro + "\', \'" + id_user + "\', \'" + now + "\', \'" + idLibreria + "\', \'" + valor + "\');");
								statement.executeUpdate("insert into Reserva values('" + max + "', '" + idLibro + "', '" + id_user + "', '" + now + "', '" + idLibreria + "', '" + valor + "');");
								statement.executeUpdate("update Libreria_Libro SET stock = " + newStock + " where ID_Libro = " + idLibro + " and " + idLibreria);
								System.out.println("La venta se ha realizado correctamente");
								break;
							} while(userResultset.next());
						}	
					} else {
						System.out.println("No hay stock disponible para el libro indicado");
					}
				} while(rs.next());
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void prestarLibro(int idLibro, int idLibreria) {
		try {
			statement = conn.connection.createStatement();
			rs = statement.executeQuery("select stock from Libreria_Libro where ID_libro = " + idLibro + " and ID_libreria = " + idLibreria);
			if (rs.next() == false) {
				System.out.println("No existe el libro en dicha libreria\n\n");
			} else {
				do {
					if (rs.getInt(1) > 0) {
						System.out.println("Hay stock " + rs.getInt(1));
						int newStock = rs.getInt(1) - 1;
						System.out.println("Introduce el dni del usuario: ");
						String dni = sc.next();
						Statement newStatement = conn.connection.createStatement();
						ResultSet userResultset = newStatement.executeQuery("Select id from Usuario where DNI like '" + dni + "'");
						if (userResultset.next() == false) {
							System.out.println("No existe el usuario con dni: " + dni + "\n\n");
						} else {
							do {
								int id_user = userResultset.getInt(1);
								rs = newStatement.executeQuery("select max(ID) from Prestamo");
								rs.next();
								int max = rs.getInt(1) + 1;
								Timestamp now = new Timestamp(System.currentTimeMillis());
								long millis_date = System.currentTimeMillis() + 1000000000l;
								Timestamp fecha_limite = new Timestamp(millis_date);
								//System.out.println("insert into Reserva values(\'" + max + "', \'" + idLibro + "\', \'" + id_user + "\', \'" + idLibreria + "\', \'" + now + "\', \'" + fecha_limite + "\');");
								statement.executeUpdate("insert into Prestamo values('" + max + "', '" + idLibro + "', '" + id_user + "', '" + idLibreria + "', '" + now + "', '" + fecha_limite + "');");
								statement.executeUpdate("update Libreria_Libro SET stock = " + newStock + " where ID_Libro = " + idLibro + " and " + idLibreria);
								System.out.println("El prestamo se ha realizado, fecha límite: " + fecha_limite.toString());
								break;
							} while(userResultset.next());
						}	
					} else {
						System.out.println("No hay stock disponible para el libro indicado");
					}
				} while(rs.next());
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Book> librosdeLibreria(int idLibreria) {
		ArrayList<Book> books = new ArrayList<Book>();
		//ResultSet newrs;
		try {
			statement = conn.connection.createStatement();
			Statement newSt = conn.connection.createStatement();
			ResultSet newrs;
			int id;
			rs = statement.executeQuery("select ID_libro from Libreria_Libro where ID_libreria = " + idLibreria);
			while(rs.next()) {
				id = rs.getInt(1);
				 newrs = newSt.executeQuery("select * from Libro where ID = "+ id);
				while(newrs.next()) {
					for(int i = 1; i <= newrs.getMetaData().getColumnCount(); i++) {
						valuesBook[i - 1] = newrs.getObject(i);
					}
					
					books.add(new Book((int) valuesBook[0], (String) valuesBook[1], (String) valuesBook[3], (int) valuesBook[4], (String) valuesBook[2], (Timestamp) valuesBook[5], (String) valuesBook[6]));
				}
			}

		} catch(Exception e) {
			e.printStackTrace();
		}
	
		return books;
	}
	
	public void registrarUsuario() {
		int id, telefono;
		String dni, nombre, apellidos, direccion;
		
		try {
			statement = conn.connection.createStatement();
			rs = statement.executeQuery("select max(ID) from Usuario");
			rs.next();
			id = rs.getInt(1) + 1;
			System.out.println("Introduce el dni: ");
			dni = sc.next();
			System.out.println("Introduce el nombre: ");
			nombre = sc.next();
			sc.nextLine();
			System.out.println("Introduce los apellidos: ");
			apellidos = sc.nextLine();
			System.out.println("Introduce el telefono: ");
			telefono = sc.nextInt();
			sc.nextLine();
			System.out.println("Introduce la dirección: ");
			direccion = sc.nextLine();
			
			//sc.nextLine();
			if(!dni.isEmpty() && !nombre.isEmpty() && !apellidos.isEmpty() && !direccion.isEmpty() && checkDNI(dni) && checkTelefono(telefono)) {
				statement.executeUpdate("insert into Usuario values('" + id + "', '" + dni + "', '" + nombre + "', '" + apellidos + "', '" + telefono + "', '" + direccion + "');");
				System.out.println("Usuario registrado correctamente");
			} else {
				System.out.println("Error en los datos introducidos\n\n");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void listarLibrerias() {
		try {
			statement = conn.connection.createStatement();
			rs = statement.executeQuery("Select * from Libreria");
			System.out.println("\n");
			while(rs.next()) {
				for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					System.out.println(rs.getMetaData().getColumnName(i) + ": " + rs.getObject(i));
				}
				System.out.println("");
				
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("\n\n");
	}
	
	public void cancelarReservasUsuario(String dni) {
		if(dni.length() == 9) {
			try {
				statement = conn.connection.createStatement();
				rs = statement.executeQuery("select ID from Usuario where dni like '" + dni + "'");
				if(rs.next()) {
					int id = rs.getInt(1);
					rs = statement.executeQuery("select ID_libro, ID_libreria from Reserva where ID_user = "+ id);
					while(rs.next()) {
						statement.executeUpdate("update Libreria_libro SET stock = stock + 1 where ID_libro = " + rs.getInt(1) + " and ID_libreria = " + rs.getInt(2));
					}
					statement.executeUpdate("delete from Reserva where ID_user = " + id); 
					System.out.println("Reservas canceladas correctamente");
				} else {
					System.out.println("No se ha encontrado el usuario");
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("El dni no es correcto");
		}
	}
	
	public boolean checkDNI(String dni) {
		return dni.length() == 9 && dniUnico(dni);
	}
	
	public boolean checkTelefono(int telef) {
		return String.valueOf(telef).length() == 9;
	}
	
	public boolean dniUnico(String dni) {
		try {
			rs = statement.executeQuery("select dni from Usuario where DNI like '" + dni +"'");
			if(rs.next()) {
				System.out.println("\nDNI repetido");
				return false;
			} else {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
	}
}
