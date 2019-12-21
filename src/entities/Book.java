package entities;

import java.sql.Timestamp;

public class Book {
	
	private int id;
	private String nombre;
	private String editorial;
	private int edicion;
	private int id_libreria;
	private Timestamp fecha_salida;
	private int stock;
	private String autor;
	
	public Book(int id, String nombre, String editorial, int edicion, int id_libreria, Timestamp fecha_salida,
			int stock, String autor) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.editorial = editorial;
		this.edicion = edicion;
		this.id_libreria = id_libreria;
		this.fecha_salida = fecha_salida;
		this.stock = stock;
		this.autor = autor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	public int getEdicion() {
		return edicion;
	}

	public void setEdicion(int edicion) {
		this.edicion = edicion;
	}

	public int getId_libreria() {
		return id_libreria;
	}

	public void setId_libreria(int id_libreria) {
		this.id_libreria = id_libreria;
	}

	public Timestamp getFecha_salida() {
		return fecha_salida;
	}

	public void setFecha_salida(Timestamp fecha_salida) {
		this.fecha_salida = fecha_salida;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", nombre=" + nombre + ", editorial=" + editorial + ", edicion=" + edicion
				+ ", id_libreria=" + id_libreria + ", fecha_salida=" + fecha_salida + ", stock=" + stock + ", autor="
				+ autor + "]";
	}
	
	
}
