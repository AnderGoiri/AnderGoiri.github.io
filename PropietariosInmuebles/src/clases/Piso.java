package clases;

import utilidades.Util;

public class Piso extends Inmueble {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// --- Atributos ---
	private int anioConstruccion;
	private int numBanio;
	private int numHabitacion;

	// --- Getters & Setters ---
	public int getAnioConstruccion() {
		return anioConstruccion;
	}

	public void setAnioConstruccion(int anioConstruccion) {
		this.anioConstruccion = anioConstruccion;
	}

	public int getNumBanio() {
		return numBanio;
	}

	public void setNumBanio(int numBanio) {
		this.numBanio = numBanio;
	}

	public int getNumHabitacion() {
		return numHabitacion;
	}

	public void setNumHabitacion(int numHabitacion) {
		this.numHabitacion = numHabitacion;
	}

	// --- getDatos y setDatos ---
	public void getDatos() {
		super.getDatos();
		System.out.println("Año de construcción:\t" + anioConstruccion);
		System.out.println("Nº de baños:\t" + numBanio);
		System.out.println("Nº de habitaciones:\t" + numHabitacion);
	}
	
	public void setDatos(int numInmuebles, String dni) {
		super.setDatos(numInmuebles, dni);
		this.anioConstruccion = Util.leerInt("Introduzca el año de construcción:\t");
		this.numBanio = Util.leerInt("Introduzca el nº de baños");
		this.numHabitacion = Util.leerInt("Ontroduzca el nº de habitaciones");
	}
}
