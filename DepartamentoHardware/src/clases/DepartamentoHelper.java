package clases;

import java.util.Map;

public class DepartamentoHelper {

	public static int getNumOrdenadoresByUbicacion(Departamento departamento, String ubicacion) {
		int count = 0;
		Map<String, Hardware> hardwareCollection = departamento.getHardwareCollection();
		if (hardwareCollection != null) {
			for (Hardware hardware : hardwareCollection.values()) {
				if (hardware instanceof Ordenador && hardware.getUbicacion().equals(ubicacion)) {
					count++;
				}
			}
		}
		return count;
	}
}
