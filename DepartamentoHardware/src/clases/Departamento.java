package clases;

import java.io.Serializable;
import java.util.Map;

public class Departamento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// --- Atributos ---
	private String nombre;
	private Map<String, Hardware> hardwareCollection;

	// --- Getters y Setters ---
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Map<String, Hardware> getHardwareCollection() {
		return hardwareCollection;
	}

	public void setHardwareCollection(Map<String, Hardware> hardwareCollection) {
		this.hardwareCollection = hardwareCollection;
	}

	public void getDatos() {
		System.out.println("Nombre: " + nombre);
		if (hardwareCollection != null) {
			for (Map.Entry<String, Hardware> piezaHardware : hardwareCollection.entrySet()) {
				System.out.println("ID: " + piezaHardware.getKey() + " - Hardware: ");
				piezaHardware.getValue().getDatos();
			}
		} else {
			System.out.println("Este Departamento no tiene hardware asociado");
		}

	}
	
	
	public int getNumOrdenadoresByUbicacion(String ubicacion) {
	    return DepartamentoHelper.getNumOrdenadoresByUbicacion(this, ubicacion);
	}

}
