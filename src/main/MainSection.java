package main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import entities.Book;
import querys.Querys;
import sqlConn.Con;

public class MainSection {
	
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		System.out.println("Introduce el nombre de usuario");
		String username = sc.nextLine();
		System.out.println("Introduce la contraseña");
		String password = sc.nextLine();
		
		int selection;
		int bookId;
		int libreriaId;
		ArrayList<Book> books = new ArrayList<Book>();
		Con con;
		try {
			con = new Con(username, password);
			Querys querys = new Querys(con);
			
			while(con.connection != null) {
				System.out.println("Que deseas hacer:\n1.Ver todos los libros\n2.Buscar un libro\n3.Reservar un libro\n4.Comprar un libro\n5.Prestar libro\n6.Listar Librerias\n7.Registrar usuario\n8.Listar libros de una libreria\n9.Cancelar reservas de un usuario\n10.Desconectar");
				selection = sc.nextInt();
				switch(selection) {
					case 1:
							books = querys.getAllBooks();
							for(Book book: books) {
								System.out.print("Id: "+ book.getId() + " Nombre: "+ book.getNombre()); //hay más atributos poner según
								System.out.println("\n");
							}
							break;
					case 2:
						System.out.println("Introduce el nombre del libro: ");
						String name = sc.next();
						books = querys.getBookByName(name);
						for(Book book: books) {
							System.out.print("Id: "+ book.getId() + " Nombre: "+ book.getNombre()); //hay más atributos poner según
							System.out.println("\n");
						}
						break;
					case 3:
						System.out.println("Introduce el id del libro");
						bookId = sc.nextInt();
						System.out.println("Introduce el id de la libreria");
						libreriaId = sc.nextInt();
						if(bookId > 0 && libreriaId > 0) {
							querys.reservarLibro(bookId, libreriaId);
						} else {
							System.out.println("Valores no permitidos");
						}
						break;
					case 4:
						System.out.println("Introduce el id del libro");
						bookId = sc.nextInt();
						System.out.println("Introduce el id de la libreria");
						libreriaId = sc.nextInt();
						if(bookId > 0 && libreriaId > 0) {
							querys.venderLibro(bookId, libreriaId);
						} else {
							System.out.println("Valores no permitidos");
						}
						break;
					case 5:
						System.out.println("Introduce el id del libro");
						bookId = sc.nextInt();
						System.out.println("Introduce el id de la libreria");
						libreriaId = sc.nextInt();
						if(bookId > 0 && libreriaId > 0) {
							querys.prestarLibro(bookId, libreriaId);
						} else {
							System.out.println("Valores no permitidos");
						}
						break;
					
					case 6:
						querys.listarLibrerias();
						break;
					case 7:
						querys.registrarUsuario();
						break;
					case 8:
						System.out.println("Introduce el id de la libreria:");
						int id_libreria = sc.nextInt();
						books = querys.librosdeLibreria(id_libreria);
						System.out.println("\nLibros: \n");
						for(Book book: books) {
							System.out.print("Id: "+ book.getId() + " Nombre: "+ book.getNombre()); //hay más atributos poner según
							System.out.println("\n");
						}
						break;
					case 9:
						System.out.println("Introduce el dni: ");
						String dni = sc.next();
						querys.cancelarReservasUsuario(dni);
						break;
					case 10:
						con.disconnect();
						con.connection = null;
						System.out.println("Gracias por usar el sistema");
						break;
					default:
						break;
				}
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
