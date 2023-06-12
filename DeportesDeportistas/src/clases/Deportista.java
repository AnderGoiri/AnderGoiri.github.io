package clases;

import java.util.Iterator;
import java.util.List;

import utilidades.Util;

public class Deportista extends Persona {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// --- Atributos ---
	private List<Integer> listaDeportes;

	// --- Getters y Setters ---
	public List<Integer> getListaDeportes() {
		return listaDeportes;
	}

	public void setListaDeportes(List<Integer> listaDeportes) {
		this.listaDeportes = listaDeportes;
	}

	// --- getDatos y setDatos ---
	public void getDatos() {
		super.getDatos();

		Iterator<Integer> iterador = listaDeportes.iterator();
		while (iterador.hasNext()) {
			Integer codDeporte = iterador.next();
			System.out.println(codDeporte);
		}
	}

	public void setDatos() {
		super.setDatos();
		boolean continuar = true;
		while (continuar) {
			int codDep = Util.leerInt("Introduce el codigo de la deporte: ");
			listaDeportes.add(codDep);
			continuar = Util.esBoolean("¿Desea continuar?");
		}
	}
	
	public void setDatos(String dni) {
		super.setDatos(dni);
		boolean continuar = true;
		while (continuar) {
			int codDep = Util.leerInt("Introduce el codigo de la deporte: ");
			listaDeportes.add(codDep);
			continuar = Util.esBoolean("¿Desea continuar?");
		}
	}

}
