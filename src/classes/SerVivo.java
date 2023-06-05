package classes;

import java.io.Serializable;

public class SerVivo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String cod;
	protected String idParque;
	protected String especie;
	protected boolean esAutoctona;

	
	
	// --- Getters and Setters ---
	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

	public String getIdParque() {
		return idParque;
	}

	public void setIdParque(String auxEspecie,int contador) {
		String codigoEspecie = auxEspecie.substring(0, 2).toUpperCase();
		this.idParque = codigoEspecie + "_" + contador;
	}

	public String getEspecie() {
		return especie;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	public boolean isEsAutoctona() {
		return esAutoctona;
	}

	public void setEsAutoctona(boolean esAutoctona) {
		this.esAutoctona = esAutoctona;
	}

	
	public void getDatos() {
		System.out.println("Código: " + this.cod);
        System.out.println("Identificador del ser vivo en el parque: " + this.idParque);
        System.out.println("Especie: " + this.especie);
        System.out.println("Autóctona: " + this.esAutoctona);
	}

	

}
