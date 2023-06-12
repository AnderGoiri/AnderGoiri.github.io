package clases;

import java.io.Serializable;

import utilidades.Util;

public class Deporte implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// --- Atributos ---
	private int code;
	private String descripcion;
	private int precio;
	
	// --- Getters y Setters ---
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getPrecio() {
		return precio;
	}
	public void setPrecio(int precio) {
		this.precio = precio;
	}

	// --- getDatos y setDatos ---
	
	public void getDatos() {
		System.out.println("Código: " + code);
		System.out.println("Descripción: " + descripcion);
		System.out.println("Precio: " + precio);
	}
	
	public void setDatos() {
		this.code = Util.leerInt("Introduce el codigo del deporte: ");
		this.descripcion = Util.introducirCadena("Introduce la descripcion del deporte: ");
		this.precio = Util.leerInt("Introduce el precio al mes del deporte: ");
	}
}
