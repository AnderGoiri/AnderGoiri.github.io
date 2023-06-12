package main;

import utilidades.MyObjectOutputStream;
import utilidades.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.ArrayList;

import clases.Deporte;
import clases.Deportista;

public class Main {

	public static void main(String[] args) {
		int opt;
		boolean salir;

		File fileDeportista = new File("deportista.obj");
		File fileSport = new File("sport.obj");

		do {

			salir = false;

			System.out.println("--- Menu Principal ---");
			System.out.println("1. Altas de deportes ");
			System.out.println("2. Añadir/Modificar deportista");
			System.out.println("3. Listado de los deportes con su número de deportistas e información final");
			System.out.println("4. Salir");

			opt = Util.leerInt("Seleccione una opcion: ");

			switch (opt) {
			case 1:
				addSport(fileSport);
				break;
			case 2:
				addmodDeportista(fileDeportista, fileSport);
				break;
			case 3:
				listSportDeportista(fileDeportista, fileSport);
				break;
			case 4:
				System.out.println("Hasta pronto!");
				salir = true;
				break;
			default:
				break;

			}

		} while (!salir);
	}

	// --- 1. Altas de deportes ---
	private static void addSport(File fileSport) {

		String auxNombreSport = Util.introducirCadena("Ingrese el nombre del deporte: ");

		Deporte sport = null;

		if (fileSport.exists()) {
			sport = comprobarSportEnFichero(fileSport, auxNombreSport);
		}

		if (sport == null) {
			addDeporteNuevo(fileSport, auxNombreSport);
		} else {
			System.out.println("El deporte ya está registrado.");
			sport.getDatos();
		}
	}

	private static Deporte comprobarSportEnFichero(File file, String aux) {
		boolean encontrado = false;
		Deporte sport = null;

		int tamanioFichero = Util.calculoFichero(file);

		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);

			for (int i = 0; i < tamanioFichero && !encontrado; i++) {

				Deporte auxSport = (Deporte) ois.readObject();

				if (auxSport.getDescripcion().equalsIgnoreCase(aux)) {
					encontrado = true;
					sport = auxSport;
				}
			}
			ois.close();

		} catch (FileNotFoundException e) {
			System.out.println("El fichero no existe.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error al leer el fichero");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Clase no encontrada");
			e.printStackTrace();
		}
		return sport;
	}

	private static void addDeporteNuevo(File fileSport, String auxNombreSport) {
		try {

			FileOutputStream fos;
			ObjectOutputStream oos;

			if (fileSport.exists()) {
				fos = new FileOutputStream(fileSport, true);
				oos = new MyObjectOutputStream(fos);
			} else {
				fos = new FileOutputStream(fileSport);
				oos = new ObjectOutputStream(fos);
			}

			Deporte sport = new Deporte();

			sport.setPrecio(Util.leerInt("Ingrese el precio mensual del deporte: "));

			if (fileSport.exists()) {
				sport.setCode(Util.calculoFichero(fileSport) + 1);
			} else {
				sport.setCode(1);
			}

			sport.setDescripcion(auxNombreSport);

			oos.writeObject(sport);

			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// --- 2. ---
	private static void addmodDeportista(File fileDeportista, File fileSport) {
		String auxDNI = Util.introducirCadena("Ingrese el DNI del deportista: ");

		Deportista deportista = null;

		if (fileDeportista.exists()) {
			deportista = comprobarDeporstistaEnFichero(fileDeportista, auxDNI);
		}

		boolean nuevoDeportista = false;

		if (deportista == null) {

			deportista = new Deportista();
			deportista.setDni(auxDNI);
			deportista.setNombre(Util.introducirCadena("Ingrese el nombre del deportista: "));
			nuevoDeportista = true;

		} else {
			System.out.println("DNI:\t" + deportista.getDni());
			System.out.println("Nombre:\t" + deportista.getNombre());
			if (fileSport.exists()) {
				List<Deporte> deportes = obtenerDeportes(fileSport);
				if (deportes.size() > 0) {
					System.out.println("Listado de deportes a los que " + deportista.getNombre() + " está apuntado: ");
					if (deportista.getListaDeportes() != null) {
						for (Deporte deporte : deportes) {
							if (deportista.getListaDeportes().contains(deporte.getCode())) {
								System.out.println(deporte.getDescripcion());
							}
						}
					}
				}
			} else {
				System.out.println(deportista.getNombre() + " no está apuntado a ningún deporte.");
			}
		}

		boolean addDeporte = Util
				.esBoolean("¿Desea " + (nuevoDeportista ? "apuntarse a" : "añadir") + " un nuevo deporte? (S/N)");

		while (addDeporte) {
			String auxSportNombre = Util.introducirCadena("Ingrese el nombre del deporte al que se quiere "
					+ (nuevoDeportista ? "apuntar" : "añadir") + ": ");

			Deporte sport = null;

			if (fileSport.exists()) {
				sport = comprobarSportEnFichero(fileSport, auxSportNombre);
			}

			if (sport == null) {
				System.out.println("El deporte " + auxSportNombre + " no está dado de alta.");
			} else {
				System.out.println("El precio mensual de " + auxSportNombre + " es: " + sport.getPrecio());

				boolean apuntarse = Util.esBoolean(
						"¿Desea " + (nuevoDeportista ? "apuntarse a" : "añadir") + auxSportNombre + "? (S/N)");

				if (apuntarse) {
					deportista = apuntarseDeporte(deportista, sport);
					System.out.println("Se ha " + (nuevoDeportista ? "apuntado" : "añadido") + " al deporte "
							+ sport.getDescripcion());
				}
			}

			addDeporte = Util.esBoolean("¿Desea añadir otro deporte? (S/N)");
		}

		File auxFile = new File("auxFile.obj");
		int tamanioFichero = Util.calculoFichero(fileDeportista);

		try {
			FileOutputStream fos = new FileOutputStream(auxFile);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			FileInputStream fis = new FileInputStream(fileDeportista);
			ObjectInputStream ois = new ObjectInputStream(fis);

			for (int i = 0; i < tamanioFichero; i++) {
				Deportista auxDeportista = (Deportista) ois.readObject();
				if (auxDeportista.getDni().equalsIgnoreCase(deportista.getDni())) {
					auxDeportista = deportista;
				}
				oos.writeObject(deportista);
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
	}

	private static Deportista apuntarseDeporte(Deportista deportista, Deporte sport) {

		List<Integer> listDeportistaSports;

		if (deportista.getListaDeportes() != null) {
			listDeportistaSports = deportista.getListaDeportes();
		} else {
			listDeportistaSports = new ArrayList<Integer>();
		}

		listDeportistaSports.add(sport.getCode());
		deportista.setListaDeportes(listDeportistaSports);
		System.out.println("Se ha apuntado al deporte " + sport.getDescripcion());

		return deportista;
	}

	private static Deportista comprobarDeporstistaEnFichero(File file, String aux) {
		boolean encontrado = false;
		Deportista deportista = null;

		int tamanioFichero = Util.calculoFichero(file);

		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);

			for (int i = 0; i < tamanioFichero && !encontrado; i++) {

				Deportista auxDeportista = (Deportista) ois.readObject();

				if (auxDeportista.getDni().equalsIgnoreCase(aux)) {
					encontrado = true;
					deportista = auxDeportista;
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
		return deportista;
	}

	// --- 3. Listado de los deportes ---
	private static void listSportDeportista(File fileDeportista, File fileSport) {

		List<Deporte> deportes = obtenerDeportes(fileSport);

		// Ordenar los deportes por número de deportistas en orden descendente
		deportes.sort((d1, d2) -> {
			int numDeportistas1 = contarDeportistas(d1.getCode(), fileDeportista);
			int numDeportistas2 = contarDeportistas(d2.getCode(), fileDeportista);
			return Integer.compare(numDeportistas2, numDeportistas1);
		});

		// Mostrar los deportes y su información final gestionar cabecera
		if (deportes.size() > 0) {
			System.out.println("Listado TOP deportes:");
			System.out.println("DEPORTE\t\tNºDEPORTISTAS");
		}
		for (Deporte deporte : deportes) {
			int numDeportistas = contarDeportistas(deporte.getCode(), fileDeportista);
			System.out.println(deporte.getDescripcion() + "\t\t" + numDeportistas);
		}
		System.out.println("------------------------");
		System.out.println("Total deportes con deportista: " + deportes.size());
		System.out.println("Media de deportistas por deporte (con deportistas): "
				+ Util.calculoFichero(fileDeportista) / deportes.size());
	}

	private static List<Deporte> obtenerDeportes(File fileSport) {
		List<Deporte> deportes = new ArrayList<>();
		int tamanioFichero = Util.calculoFichero(fileSport);

		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileSport));

			for (int i = 0; i < tamanioFichero; i++) {
				Deporte deporte = (Deporte) ois.readObject();
				deportes.add(deporte);
			}
			ois.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return deportes;
	}

	private static int contarDeportistas(int codigoDeporte, File fileDeportista) {
		int contador = 0;
		int tamanioFichero = Util.calculoFichero(fileDeportista);

		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileDeportista));

			for (int i = 0; i < tamanioFichero; i++) {

				Deportista deportista = (Deportista) ois.readObject();

				List<Integer> deportes = deportista.getListaDeportes();
				if (deportes != null) {
					if (deportes.contains(codigoDeporte)) {
						contador++;
					}
				}
			}
			ois.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return contador;
	}

}
