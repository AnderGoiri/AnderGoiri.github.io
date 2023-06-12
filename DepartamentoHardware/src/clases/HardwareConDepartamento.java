package clases;

public class HardwareConDepartamento {
	private Hardware hardware;
	private Departamento departamento;

	public HardwareConDepartamento(Hardware hardware, Departamento departamento) {
		this.hardware = hardware;
		this.departamento = departamento;
	}

	public Hardware getHardware() {
		return hardware;
	}

	public Departamento getDepartamento() {
		return departamento;
	}
}