package clases;

import java.io.Serializable;
import java.time.LocalDate;

import utilidades.Util;

public class Hardware implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// --- Atributos ---
	protected String idPegatina;
	protected String marca;
	protected String ubicacion;
	protected LocalDate fechaCompra;
	
	// --- Getters y Setters ---
	public String getIdPegatina() {
		return idPegatina;
	}
	public void setIdPegatina(String id) {
		this.idPegatina = id;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	public LocalDate getFechaCompra() {
		return fechaCompra;
	}
	public void setFechaCompra(LocalDate fechaCompra) {
		this.fechaCompra = fechaCompra;
	} 
	
	// --- setDatos & getDatos ---
	
	public void setDatos() {
		this.idPegatina = Util.introducirCadena("Ingrese el id del hardware:");
		this.marca = Util.introducirCadena("Ingrese el marca del hardware:");
		this.ubicacion=Util.introducirCadena("Ingrese la ubicación del hardware:");
		this.fechaCompra = Util.leerFechaAMD("Ingrese la fecha de compra del hardware(aaaa/MM/DD):");
	}
	
	public void setDatos(String id) {
		this.idPegatina = id;
		this.marca = Util.introducirCadena("Ingrese el marca del hardware:");
		this.ubicacion=Util.introducirCadena("Ingrese la ubicación del hardware:");
		this.fechaCompra = Util.leerFechaAMD("Ingrese la fecha de compra del hardware:");
	}
	
	public void getDatos() {
		System.out.println("Id: " + getIdPegatina());
        System.out.println("Marca: " + getMarca());
        System.out.println("Ubicación: " + getUbicacion());
        System.out.println("Fecha de compra: " + getFechaCompra());
	}
	
}
