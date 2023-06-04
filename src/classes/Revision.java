package classes;

import java.io.Serializable;
import java.time.LocalDate;

import utilidades.Util;

public class Revision implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LocalDate fechaRevision;
	private String motivo;
	private String detalle;

	// --- Getters and Setters ---
	public LocalDate getFechaRevision() {
		return fechaRevision;
	}

	public void setFechaRevision() {
		boolean fechaCorrecta = false;
		char decision = Util.leerChar("¿Fecha de hoy (H) o una posterior (P)?", 'H', 'P');

		if (decision == 'H') {
			this.fechaRevision = LocalDate.now();
			System.out.println("Fecha de hoy: " + this.fechaRevision);
		}
		if (decision == 'P') {
			this.fechaRevision = Util.leerFechaDMA("Fecha a posterior (dd/mm/aaaa): ");

			do {
				if (this.fechaRevision.isBefore(LocalDate.now()) || this.fechaRevision.isEqual(LocalDate.now())) {
					System.out.println("La fecha tiene que ser posterior a la fecha de hoy: " + LocalDate.now());
					System.out.println("Por favor ingrese una fecha posterior a la fecha de hoy: " + LocalDate.now());
					this.fechaRevision = Util.leerFechaDMA("Fecha a posterior (dd/mm/aaaa): ");
				} else {
					System.out.println("Fecha de revisión pendiente: " + this.fechaRevision);
					fechaCorrecta = true;
				}
			} while (!fechaCorrecta);
		}
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
}