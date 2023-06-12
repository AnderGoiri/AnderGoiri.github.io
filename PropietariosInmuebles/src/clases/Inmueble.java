package clases;

import java.io.Serializable;

import utilidades.Util;

public class Inmueble implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// --- Atributos ---
	protected static final String nombreAgencia = "Inmuebles Erandio";
	protected String cod;
	protected String ciudad;
	protected int precio;
	protected String dniPropietario;
	
	
	// --- Getters & Setters ---
	public String getCod() {
		return cod;
	}
	public void setCod(String cod) {
		this.cod = cod;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public int getPrecio() {
		return precio;
	}
	public void setPrecio(int precio) {
		this.precio = precio;
	}
	public String getDniPropietario() {
		return dniPropietario;
	}
	public void setDniPropietario(String dniPropietario) {
		this.dniPropietario = dniPropietario;
	}
	
	// --- getDatos y setDatos ---
	
	public void getDatos() {
		System.out.println(nombreAgencia);
		System.out.println("Código:\t" + cod);
		System.out.println("Ciudad:\t" + ciudad);
		System.out.println("Precio:\t" + precio);
		System.out.println("DNI del Propietario:\t" +dniPropietario);
	}
	
	public void setDatos(int numInmuebles, String dni) {
		this.ciudad = Util.introducirCadena("Introduzca la Ciudad:\t");
		this.cod = this.ciudad.substring(0,2).toUpperCase()+"-"+numInmuebles;
		this.precio = Util.leerInt("Introduzca el precio:\t");
		this.dniPropietario = dni;
	}
	
}
