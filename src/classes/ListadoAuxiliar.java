package classes;

public class ListadoAuxiliar {

	// ---Attributes---
	//codigo del animal
	private String codigoAnimal;
	
	//especie
	private String especie;
	
	//n.revisiones
	private int nRevisiones;
	
	//edad
	private int edad;
	
	//n.vacunas
	private int nVacunas;
	
	
	// ---Getters & Setters---
	public String getCodigoAnimal() {
		return codigoAnimal;
	}

	public void setCodigoAnimal(String codigoAnimal) {
		this.codigoAnimal = codigoAnimal;
	}

	public String getEspecie() {
		return especie;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	public int getnRevisiones() {
		return nRevisiones;
	}

	public void setnRevisiones(int nRevisiones) {
		this.nRevisiones = nRevisiones;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public int getnVacunas() {
		return nVacunas;
	}

	public void setnVacunas(int nVacunas) {
		this.nVacunas = nVacunas;
	}

	@Override
	public String toString() {
		return "ListadoAuxiliar [codigoAnimal=" + codigoAnimal + ", especie=" + especie + ", nRevisiones=" + nRevisiones
				+ ", edad=" + edad + ", nVacunas=" + nVacunas + "]";
	}
}
