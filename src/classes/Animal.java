package classes;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.time.Period;

public class Animal extends SerVivo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nombre;
	private LocalDate fechaNac;
	private float peso;
	private List<Revision> revisiones = new ArrayList<Revision>();
	
	public Animal() {
		super();
	}

	// --- Getters and Setters ---
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(LocalDate fechaNac) {
		this.fechaNac = fechaNac;
	}

	public float getPeso() {
		return peso;
	}

	public void setPeso(float peso) {
		this.peso = peso;
	}

	public List<Revision> getRevisiones() {
		return revisiones;
	}

	// Aquí deberé agregar el método para añadir una nueva Revisión
	public void setRevisiones(List<Revision> revisiones) {
		this.revisiones = revisiones;
	}

	public void getDatos() {
		super.getDatos();
		System.out.println("Nombre: " + this.nombre);
		System.out.println("Fecha de nacimiento: " + this.fechaNac);//formateador
		System.out.println("Peso: " + this.peso);
		System.out.println("Revisiones: ");
		// Bucle for-each para printear todas las revisiones
		for (Revision rev:revisiones) {
			System.out.println(rev);
		}
	}

	//Una edad se calcula utilizando un método de Period
	public Period getEdad() {
		return Period.between(this.fechaNac, LocalDate.now());
	}

}
