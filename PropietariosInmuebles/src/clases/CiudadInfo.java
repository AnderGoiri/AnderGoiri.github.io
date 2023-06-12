package clases;

public class CiudadInfo implements Comparable<CiudadInfo> {

	private String ciudad;
	private int numPisos;
	private float precioMedio;
	private float precioAcumulado;

	public CiudadInfo(String ciudad2, int i, int precio) {
		this.ciudad = ciudad2;
		this.numPisos = i;
		this.precioAcumulado = precio;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public int getNumPisos() {
		return numPisos;
	}

	public void setNumPisos(int numPisos) {
		this.numPisos = numPisos;
	}

	public float getPrecioMedio() {
		return precioMedio;
	}

	public void setPrecioMedio(float precioMedio) {
		this.precioMedio = precioMedio;
	}

	public float getPrecioAcumulado() {
		return precioAcumulado;
	}

	public void setPrecioAcumulado(float precioAcumulado) {
		this.precioAcumulado = precioAcumulado;
	}

	public void incrementarNumPisos() {
		numPisos++;
	}

	public void sumarPrecio(float precio) {
		precioAcumulado += precio;
	}

	public void calcularPrecioMedio() {
		if (numPisos > 0) {
			precioMedio = precioAcumulado / numPisos;
		}
	}

	@Override
	public int compareTo(CiudadInfo otraCiudad) {
		return Integer.compare(otraCiudad.numPisos, this.numPisos);
	}

}
