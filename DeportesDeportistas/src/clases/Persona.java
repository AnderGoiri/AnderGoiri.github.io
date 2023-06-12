package clases;

import java.io.Serializable;

import utilidades.Util;

public class Persona implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// --- Atributos ---
	protected String dni;
	protected String nombre;
	
	// --- Getters y Setters ---
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	// --- getDatos y setDatos ---
	public void getDatos() {
		System.out.println("DNI: " + dni);
        System.out.println("Nombre: " + nombre);
	}
	
	public  void setDatos() {
		this.dni = Util.introducirCadena("DNI: ");
		this.nombre = Util.introducirCadena("Nombre: ");
	}
	
	public  void setDatos(String dni) {
		this.dni = dni;
		this.nombre = Util.introducirCadena("Nombre: ");
	}
	

}
