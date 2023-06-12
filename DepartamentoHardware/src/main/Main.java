package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import clases.Departamento;
import clases.Hardware;
import clases.Ordenador;
import clases.Periferico;
import clases.HardwareConDepartamento;
import utilidades.MyObjectOutputStream;
import utilidades.Util;

public class Main {

	public static void main(String[] args) {
		boolean salir;
		int opt;

		File fileTartanga = new File("tartanga.obj");

		do {

			salir = false;

			System.out.println("--- MENU PRINCIPAL --");
			System.out.println("1. Introducir un nuevo departamento");
			System.out.println("2. Añadir hardware");
			System.out.println("3. Mostrar el hardware existente de una marca");
			System.out.println("4. Modificar ubicación de un hardware");
			System.out.println("5. Listado ordenadores");
			System.out.println("6. Salir.");
			System.out.println("--------------------");

			opt = Util.leerInt("Seleccione una opción");

			switch (opt) {
			case (1):
				addNuevoDepartamento(fileTartanga);
				break;

			case (2):
				if (fileTartanga.exists()) {
					addHardware(fileTartanga);
				} else {
					System.out.println("El registro está vacío. Porfavor, seleccione primero la opción 1");
				}
				break;

			case (3):
				if (fileTartanga.exists()) {
					mostrarHardwareMarca(fileTartanga);
				} else {
					System.out.println("El registro está vacío. Porfavor, seleccione primero la opción 1");
				}
				break;

			case (4):
				if (fileTartanga.exists()) {
					modUbicacion(fileTartanga);
				} else {
					System.out.println("El registro está vacío. Porfavor, seleccione primero la opción 1");
				}

				break;

			case (5):
				if (fileTartanga.exists()) {
					mostrarListaUbicaciones(fileTartanga);
				} else {
					System.out.println("El registro está vacío. Porfavor, seleccione primero la opción 1");
				}

				break;

			case (6):
				System.out.println("Saliendo...");
				salir = true;
				break;

			default:
				break;
			}

		} while (!salir);

	}

	// --- 1. Metodo para agregar un nuevo departamento ---
	private static void addNuevoDepartamento(File fileTartanga) {

		String auxDep = Util.introducirCadena("Ingrese el nombre del departamento:");
		Departamento departamento = encontrarDepartamento(fileTartanga, auxDep);

		if (departamento != null) {
			System.out.println("El departamento ya existe");
			departamento.getDatos();

		} else {

			try {
				FileOutputStream fos;
				ObjectOutputStream oos;

				if (fileTartanga.exists()) {
					fos = new FileOutputStream(fileTartanga, true);
					oos = new MyObjectOutputStream(fos);
				} else {
					fos = new FileOutputStream(fileTartanga);
					oos = new ObjectOutputStream(fos);
				}

				departamento = new Departamento();
				departamento.setNombre(auxDep);

				oos.writeObject(departamento);

				System.out.println("Se guardo el departamento: " + auxDep);

				oos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	private static Departamento encontrarDepartamento(File file, String aux) {

		boolean encontrado = false;

		Departamento departamento = null;

		int tamanioFichero = Util.calculoFichero(file);

		if (file.exists()) {
			try {
				// Abrir el flujo de entrada del fichero al programa
				FileInputStream fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);

				// Recorrer el fichero
				for (int i = 0; i < tamanioFichero && !encontrado; i++) {

					// Departamento auxiliar que se lee del fichero
					Departamento auxDep = (Departamento) ois.readObject();

					// Si el nombre departamento leido coincide con el del departamento enviado
					if (auxDep.getNombre().equals(aux)) {
						departamento = auxDep; // igualar el departamento con el auxiliar
						encontrado = true; // indicar que se ha encontrado el departamento para salir del for
					}
				}
				ois.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return departamento;
	}

	// --- 2. Metodo para agregar un hardware ---
	private static void addHardware(File fileTartanga) {

		String auxDep = Util.introducirCadena("Ingrese el nombre del departamento al que quiere agregar el hardware:");

		Departamento departamento = encontrarDepartamento(fileTartanga, auxDep);

		List<Hardware> listHardware = new ArrayList<>();

		if (departamento == null) {
			System.out.println("El departamento no existe");
		} else {
			System.out.println("El departamento ya existe");
			departamento.getDatos();

			boolean addHardware = Util.esBoolean("¿Quiere añadir nuevo hardware?");

			while (addHardware) {
				char tipoHardware = Util.leerChar("Es un ordenador(O) o un periférico(P)", 'O', 'P');

				Hardware hardware;

				if (tipoHardware == 'O') {
					hardware = new Ordenador();
				} else {
					hardware = new Periferico();
				}

				// Control del ID antes de introducir el resto de datos
				String auxID = Util.introducirCadena("Ingrese el ID del hardware a añadir: ");

				if (!encontrarHardware(fileTartanga, auxID)) {
					hardware.setDatos(auxID);
				} else {
					System.out.println("El hardware ya existe");
				}

				System.out.println("Este es el hardware ha añadido:");
				hardware.getDatos();

				listHardware.add(hardware);

				addHardware = Util.esBoolean("¿Quiere añadir otro hardware?");
			}

			departamento = addHardwareToDepartamento(departamento, listHardware);

			mod(fileTartanga, departamento);
		}
	}

	private static Departamento addHardwareToDepartamento(Departamento departamento, List<Hardware> listHardware) {
		Map<String, Hardware> hardwareCollection;
		
		if (departamento.getHardwareCollection() == null) {
			hardwareCollection = new HashMap<>();
		} else {
			hardwareCollection = departamento.getHardwareCollection();
		}
		
		for (Hardware hardware : listHardware) {
			hardwareCollection.put(hardware.getIdPegatina(), hardware);
		}

		departamento.setHardwareCollection(hardwareCollection);

		return departamento;
	}

	// --- 3. Método para mostrar un hardware ---
	private static void mostrarHardwareMarca(File file) {
		String auxMarca;

		Departamento departamento = new Departamento();
		ArrayList<HardwareConDepartamento> hardwareMarca = new ArrayList<>();

		int tamanioFichero = 0;

		auxMarca = Util.introducirCadena("Introduce marca: ");

		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);

			tamanioFichero = Util.calculoFichero(file);

			for (int i = 0; i < tamanioFichero; i++) {
				departamento = (Departamento) ois.readObject();
				Map<String, Hardware> auxHardware = departamento.getHardwareCollection();

				if(auxHardware != null) {
					for (Hardware h : auxHardware.values()) {
						if (h.getMarca().equals(auxMarca)) {
							hardwareMarca.add(new HardwareConDepartamento(h, departamento));
						}
					}
				}
			}
			ois.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		if (!hardwareMarca.isEmpty()) {
			System.out.println("MARCA\tTIPO/DESC\tFECHA COMPRA\tLUGAR\tDEPARTAMENTO");
			for (HardwareConDepartamento hardware : hardwareMarca) {
				Hardware h = hardware.getHardware();
				Departamento d = hardware.getDepartamento();

				if (h instanceof Ordenador) {
					System.out.println(h.getMarca() + "\t" + ((Ordenador) h).getTipo() + "\t" + h.getFechaCompra()
							+ "\t" + h.getUbicacion() + "\t" + d.getNombre());
				} else if (h instanceof Periferico) {
					System.out.println(h.getMarca() + "\t" + ((Periferico) h).getDescription() + "\t"
							+ h.getFechaCompra() + "\t" + h.getUbicacion() + "\t" + d.getNombre());
				}
			}
		} else {
			System.out.println("No se ha encontrado hardware que contenga esa marca");
		}
	}

	// --- 4. Metodo para modificar la ubicación de un hardware ---
	private static void modUbicacion(File file) {
		String auxID = Util.introducirCadena("Ingrese el ID del hardware:");

		File auxFile = new File("auxTartanga.obj");

		List<Departamento> departamentos = new ArrayList<>();

		int tamanioFichero = 0;
		boolean encontrado = false;
		boolean cambiar = false;

		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(auxFile));

			tamanioFichero = Util.calculoFichero(file);

			for (int i = 0; i < tamanioFichero && !encontrado; i++) {
				Departamento departamentoLeido = (Departamento) ois.readObject();
				Map<String, Hardware> auxHardware = new HashMap<>(departamentoLeido.getHardwareCollection());

				Hardware hardware = auxHardware.get(auxID);

				if (hardware != null) {
					if (hardware instanceof Ordenador) {
						System.out.println(((Ordenador) hardware).getTipo() + " " + hardware.getMarca()
								+ " actualmente en: " + hardware.getUbicacion());
					} else if (hardware instanceof Periferico) {
						System.out.println(((Periferico) hardware).getDescription() + " " + hardware.getMarca()
								+ " actualmente en: " + hardware.getUbicacion());
					}

					encontrado = true;

					cambiar = Util.esBoolean("¿Desea cambiar la ubicación del hardware?");

					if (cambiar) {
						hardware.setUbicacion(Util.introducirCadena("Introduce la nueva ubicación: "));
						auxHardware.put(auxID, hardware);
					} else {
						System.out.println("No se ha realizado cambio");
					}
				}

				departamentoLeido.setHardwareCollection(auxHardware);
				departamentos.add(departamentoLeido);
			}

			ois.close();

			for (Departamento departamento : departamentos) {
				oos.writeObject(departamento);
			}

			oos.close();

			if (file.delete()) {
				System.out.println("El fichero original ha sido eliminado");
				if (auxFile.renameTo(file)) {
					System.out.println("El fichero ha sido modificado");
				} else {
					System.out.println("No se ha podido modificar el fichero");
				}
			} else {
				System.out.println("No se ha podido eliminar el fichero original");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// --- 5. Método para mostrar un hardware ---
	public static void mostrarListaUbicaciones(File file) {
		Map<String, Integer> ubicaciones = new HashMap<>();
		int totalOrdenadores = 0;
		int tamanioFichero = Util.calculoFichero(file);

		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));

			for (int i = 0; i < tamanioFichero; i++) {
				Departamento departamento = (Departamento) ois.readObject();
				Map<String, Hardware> hardwareCollection = departamento.getHardwareCollection();

				if (hardwareCollection != null) {

					for (Hardware hardware : hardwareCollection.values()) {

						if (hardware instanceof Ordenador) {

							String ubicacion = hardware.getUbicacion();
							int numOrdenadores = departamento.getNumOrdenadoresByUbicacion(ubicacion);
							ubicaciones.put(ubicacion, numOrdenadores);
							totalOrdenadores++;
						}
					}
				}
			}
			ois.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		List<Map.Entry<String, Integer>> listaUbicaciones = new ArrayList<>(ubicaciones.entrySet());
		Collections.sort(listaUbicaciones, (a, b) -> b.getValue().compareTo(a.getValue()));

		System.out.println("Lista de Ubicaciones:");

		for (Map.Entry<String, Integer> ubicacion : listaUbicaciones) {
			System.out
					.println("Ubicación: " + ubicacion.getKey() + " - Número de ordenadores: " + ubicacion.getValue());
		}

		System.out.println("Número total de ordenadores en el centro: " + totalOrdenadores);
	}

	// --- Métodos adicionales ---

	private static boolean encontrarHardware(File file, String id) {
		boolean encontrado = false;
		int tamanioFichero = Util.calculoFichero(file);

		try {
			// abrir el flujo de entrada del fichero al programa
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);

			// recorremos el fichero
			for (int i = 0; i < tamanioFichero && !encontrado; i++) {

				Departamento departamento = (Departamento) ois.readObject();

				Map<String, Hardware> hardwareCollection = departamento.getHardwareCollection();

				if (hardwareCollection != null && hardwareCollection.containsKey(id)) {
					encontrado = true;
				}
			}
			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return encontrado;
	}

	public static void mod(File file, Departamento departamento) {
		File auxFile = new File("auxTartanga.obj");
		int tamanioFichero = Util.calculoFichero(file);

		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(auxFile));

			for (int i = 0; i < tamanioFichero; i++) {

				Departamento departamentoLeido = (Departamento) ois.readObject();

				if (departamentoLeido.getNombre().equals(departamento.getNombre())) {
					oos.writeObject(departamento);
				} else {
					oos.writeObject(departamentoLeido);
				}
			}
			ois.close();
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		if (file.delete())
			System.out.println("El fichero ha sido eliminado");
		else
			System.out.println("No se ha podido eliminar el fichero");

		if (auxFile.renameTo(file))
			System.out.println("El fichero ha sido modificado");
		else
			System.out.println("No se ha podido modificar el fichero");

	}
}
