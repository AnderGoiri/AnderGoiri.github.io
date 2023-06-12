package clases;

import java.io.Serializable;

import utilidades.Util;

public class Propietario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// --- Atributos ---
	private String dniPropietario;
	private String nombre;
	private int telefono;

	// --- Getters Y Setters ---
	public String getDniPropietario() {
		return dniPropietario;
	}

	public void setDniPropietario(String dniPropietario) {
		this.dniPropietario = dniPropietario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	// --- getDatos y setDatos ---

	public void getDatos() {
		System.out.println("DNI:\t" + dniPropietario);
		System.out.println("Nombre:\t" + nombre);
		System.out.println("Tlfn:\t" + telefono);
	}

	public void setDatos() {
		this.dniPropietario = Util.introducirCadena("Introduzca el DNI:\t");
		this.nombre = Util.introducirCadena("Introduzca el Nombre:\t");
		this.telefono = Util.leerInt("Introduzca el número de teléfono:\t");
	}
	
	public void setDatos(String dni) {
		this.dniPropietario = dni;
		this.nombre = Util.introducirCadena("Introduzca el Nombre:\t");
		this.telefono = Util.leerInt("Introduzca el número de teléfono:\t");
	}

}
