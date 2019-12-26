package entities;

import java.sql.Timestamp;

public class Book {
	
	private int id;
	private String nombre;
	private String editorial;
	private String genero;
	private int edicion;
	private Timestamp fecha_salida;
	private String autor;
	
	public Book(int id, String nombre, String editorial, int edicion, String genero, Timestamp fecha_salida, String autor) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.editorial = editorial;
		this.edicion = edicion;
		this.genero = genero;
		this.fecha_salida = fecha_salida;
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

	public Timestamp getFecha_salida() {
		return fecha_salida;
	}

	public void setFecha_salida(Timestamp fecha_salida) {
		this.fecha_salida = fecha_salida;
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
				+ ", fecha_salida=" + fecha_salida + ", autor="
				+ autor + "]";
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}
	
	
}
