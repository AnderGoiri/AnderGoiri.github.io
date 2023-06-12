package clases;

import utilidades.Util;

public class Periferico extends Hardware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// --- Atributos ---
	private String description;

	// --- Getters y Setters ---
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	// --- setDatos y getDatos ---
	public void setDatos() {
		super.setDatos();
		this.description = Util.introducirCadena("Descripción: ");
	}
	
	public void setDatos(String id) {
		super.setDatos(id);
		this.description = Util.introducirCadena("Descripción: ");
	}
	
	public void getDatos() {
        super.getDatos();
        System.out.println("Descripción: " + description);
    }
	
}
