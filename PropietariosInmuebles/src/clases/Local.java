package clases;

import utilidades.Util;

public class Local extends Inmueble{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// --- Atributos ---
	private String tipo;

	// --- Getters & Setters ---
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	// --- getDatos y setDatos ---
	public void getDatos() {
		super.getDatos();
		System.out.println("Tipo:\t" + tipo);
	}
	
	public void setDatos(int numInmuebles, String dni) {
		super.setDatos(numInmuebles, dni);
		this.tipo = Util.introducirCadena("Introduzca el tipo de local:\t");
	}
	
}
