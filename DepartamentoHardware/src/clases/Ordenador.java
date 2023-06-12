package clases;

import utilidades.Util;

public class Ordenador extends Hardware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// --- Atributos ---
	private Tipo tipo;
	private int memoriaRAM;
	private int discoDuro;

	// --- Getters y Setters ---
	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public int getMemoriaRAM() {
		return memoriaRAM;
	}

	public void setMemoriaRAM(int memoriaRAM) {
		this.memoriaRAM = memoriaRAM;
	}

	public int getDiscoDuro() {
		return discoDuro;
	}

	public void setDiscoDuro(int discoDuro) {
		this.discoDuro = discoDuro;
	}

	// --- setDatos y getDatos ---

	public void setDatos() {
		super.setDatos();
		char auxTipo = Util.leerChar("¿Es un portátil(P) o sobremesa(S)?", 'P', 'S');
		if (auxTipo == 'P') {
			this.tipo = Tipo.P;
		} else {
			this.tipo = Tipo.S;
		}
		this.memoriaRAM = Util.leerInt("¿Cuánta memoria RAM?");
		this.discoDuro = Util.leerInt("¿Cuánto espacio de disco duro?");
	}
	
	public void setDatos(String id) {
		super.setDatos(id);
		char auxTipo = Util.leerChar("¿Es un portátil(P) o sobremesa(S)?", 'P', 'S');
		if (auxTipo == 'P') {
			this.tipo = Tipo.P;
		} else {
			this.tipo = Tipo.S;
		}
		this.memoriaRAM = Util.leerInt("¿Cuánta memoria RAM?");
		this.discoDuro = Util.leerInt("¿Cuánto espacio de disco duro?");
	}

	public void getDatos() {
		super.getDatos();
		System.out.println("Tipo: " + tipo.getLabel());
		System.out.println("Memoria RAM: " + memoriaRAM);
		System.out.println("Disco duro: " + discoDuro);
	}
	
	// --- toString ---
}
