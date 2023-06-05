package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import classes.Animal;
import classes.ListadoAuxiliar;
import classes.Revision;
import classes.Planta;
import classes.SerVivo;
import utilidades.MyObjectOutputStream;
import utilidades.Util;

public class Main {

	public static void main(String[] args) {
		int menu;
		boolean salir = false;

		File file = new File("seresVivos.obj");

		do {
			System.out.println("---MENÚ---");
			System.out.println("");
			System.out.println("1. Introducir un nuevo ser vivo");
			System.out.println("2. Introducir revisión médica");
			System.out.println("3. Listar los seres vivos");
			System.out
					.println("4. Mostrar todos los animales con edad inferior a una dada y que tienen alguna vacuna	");
			System.out.println("5.Salir");
			System.out.println("");
			System.out.println("---------");

			menu = Util.leerInt("Seleccione una opción: ");

			switch (menu) {

			case 1:
				addNuevoSerVivo(file);
				break;

			case 2:
				if (file.exists()) {
					// Si existe el fichero
					addNuevaRevision(file);
				} else {
					// Si no existe el fichero
					System.out.println("No existen registros");
				}
				break;

			case 3:
				if (file.exists()) {
					// Si existe el fichero
					listarSeresVivos(file);

				} else {
					// Si no existe el fichero
					System.out.println("No existen registros");
				}
				break;

			case 4:
				if (file.exists()) {
					// Si existe el fichero
					mostrarAnimalconEdadyVacuna(file);
				} else {
					// Si no existe el fichero
					System.out.println("No existen registros");
				}
				break;

			case 5:
				System.out.println("Saliendo del programa...");
				salir = true;
				break;

			default:
				break;
			}
		} while (!salir);
	}

	// ---1. ---
	private static void addNuevoSerVivo(File file) {
		boolean existe = false;
		SerVivo auxSerVivo = null;
		int numSerVivo = 0;

		List<Revision> revisionInicial = new ArrayList<>();

		String auxCode = Util.introducirCadena("Introduzca el código del ser vivo: ");

		// Comprobar si existe ya el ser vivo en el fichero
		if (file.exists()) {

			try {
				// Abrimos el flujo de entrada del fichero al programa
				FileInputStream fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);

				// Leer el fichero

				numSerVivo = Util.calculoFichero(file);

				for (int i = 0; i < numSerVivo && !existe; i++) {
					auxSerVivo = (SerVivo) ois.readObject(); // asi solo leo un objeto
					existe = (auxSerVivo.getCod()).equals(auxCode) ? true : false;

					if (existe) {
						System.out.println("El ser vivo ya existe en el fichero");
						auxSerVivo.getDatos();
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

		if (!existe) {
			// Abrimos el flujo de salida del porgrama al fichero
			try {

				ObjectOutputStream oos = (file.exists()) ? new MyObjectOutputStream(new FileOutputStream(file, true))
						: new ObjectOutputStream(new FileOutputStream(file));

				char tipo = Util.leerChar("¿Es Planta (P) o Animal (A)?", 'P', 'A');

				if (tipo == 'P') {
					auxSerVivo = new Planta();
				} else if (tipo == 'A') {
					auxSerVivo = new Animal();
				}

				auxSerVivo.setEspecie(Util.introducirCadena("Especie:"));

				String auxEspecie = auxSerVivo.getEspecie();
				int contEspecie = calcularCantidadEspecie(auxEspecie, file);
				auxSerVivo.setIdParque(auxEspecie, contEspecie);

				auxSerVivo.setEsAutoctona(Util.esBoolean("¿Es Autoctona?"));

				if (auxSerVivo instanceof Planta) {
					((Planta) auxSerVivo).setfRiego(Util.leerFloat("Introduzca la frecuencia de riego:"));
					((Planta) auxSerVivo).setLocation(Util.introducirCadena("Introduzca la ubicación:"));
				}

				if (auxSerVivo instanceof Animal) {
					((Animal) auxSerVivo).setNombre(Util.introducirCadena("Introduzca el nombre:"));
					((Animal) auxSerVivo)
							.setFechaNac(Util.leerFechaDMA("Introduza la fecha de nacimiento(dd/mm/aaaa):"));
					((Animal) auxSerVivo).setPeso(Util.leerFloat("Introduzca el peso:"));

					Revision revision = new Revision();
					revision.setFechaRevision();
					revision.setMotivo(Util.introducirCadena("Introduzca el motivo:"));
					revision.setDetalle(Util.introducirCadena("Introduzca el detalle:"));

					revisionInicial.add(revision);

					((Animal) auxSerVivo).setRevisiones(revisionInicial);
				}

				oos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private static int calcularCantidadEspecie(String auxEspecie, File file) {
		int contador = 1;
		ArrayList<SerVivo> servivoList = new ArrayList<>();

		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));

			servivoList = fileToArrayList(file);

			for (SerVivo serVivo : servivoList) {
				if (serVivo.getEspecie().equals(auxEspecie)) {
					contador++;
				}
			}
			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return contador;
	}

	private static ArrayList<SerVivo> fileToArrayList(File file) {
		ArrayList<SerVivo> seresVivos = new ArrayList<>();

		if (file.exists()) {
			FileInputStream fis = null;
			ObjectInputStream ois = null;

			int numSeresVivos = Util.calculoFichero(file);

			try {
				fis = new FileInputStream(file);
				ois = new ObjectInputStream(fis);

				for (int i = 0; i < numSeresVivos; i++) {
					seresVivos.add((SerVivo) ois.readObject());
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			try {
				ois.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return seresVivos;
	}

	// ---2. ---
	private static void addNuevaRevision(File file) {
		SerVivo auxSerVivo = null;
		boolean continuar = true;
		boolean encontrado = false;
		int numSeresVivos = 0;

		String auxIdentificador = Util.introducirCadena("Introduzca el código del ser vivo: ");

		// Comprobar si existe el animal en el fichero
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));

			numSeresVivos = Util.calculoFichero(file);

			for (int i = 0; i < numSeresVivos && !encontrado; i++) {
				auxSerVivo = (SerVivo) ois.readObject();
				if (auxSerVivo.getIdParque().equals(auxIdentificador)) {
					encontrado = true;
				}
			}

			if (!encontrado)
				System.out.println("El ser vivo no existe en el fichero");

			if (encontrado && auxSerVivo instanceof Animal) {
				System.out.println("El animal ya existe en el fichero");

				// Mostrar toda su información
				auxSerVivo.getDatos();

				do {
					((Animal) auxSerVivo).addRevision();

					continuar = Util.esBoolean("¿Desea continuar? (S/N)");
				} while (continuar);

			}
			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		if (encontrado) {

			File auxFile = new File("auxSeresVivos.obj");

			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));

				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(auxFile));

				numSeresVivos = Util.calculoFichero(file);

				for (int i = 0; i < numSeresVivos; i++) {

					SerVivo serVivoLeido = (SerVivo) ois.readObject();

					if (serVivoLeido.getIdParque().equals(auxSerVivo.getIdParque())) {
						oos.writeObject(auxSerVivo);
					} else {
						oos.writeObject(serVivoLeido);
					}
				}
				oos.close();
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			if (file.delete()) {
				System.out.println("El fichero se ha borrado");
			} else {
				System.out.println("No se ha podido borrar el fichero");
			}

			if (auxFile.renameTo(file)) {
				System.out.println("El fichero se ha cambiado");
			} else {
				System.out.println("No se ha podido renombrar el fichero");
			}

		}

	}

	// ---3. ---
	private static void listarSeresVivos(File file) {
		SerVivo auxSerVivo = null;
		ArrayList<SerVivo> servivoList = new ArrayList<>();

		servivoList = fileToArrayList(file);

		Iterator<SerVivo> iterador = servivoList.iterator();
		while (iterador.hasNext()) {
			auxSerVivo = iterador.next();
			auxSerVivo.getDatos();
		}

	}

	// ---4. ---
	private static void mostrarAnimalconEdadyVacuna(File file) {
		int tamanioFichero = 0;
		int edadlimite = 0;
		Animal auxAnimal = null;
		ArrayList<ListadoAuxiliar> listAuxiliar = new ArrayList<>();

		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));

			tamanioFichero = Util.calculoFichero(file);
			edadlimite = Util.leerInt("Introduzca la edad máxima (en años) que ha de tener el animal: ");

			for (int i = 0; i < tamanioFichero; i++) {
				auxAnimal = (Animal) ois.readObject();
				if (auxAnimal.calcularEdad(auxAnimal.getFechaNac()) < edadlimite && auxAnimal.tieneVacuna(auxAnimal)) {
					ListadoAuxiliar aux = new ListadoAuxiliar();
					aux.setCodigoAnimal(auxAnimal.getIdParque());
					aux.setEspecie(auxAnimal.getEspecie());
					aux.setnRevisiones(auxAnimal.getRevisiones().size());
					aux.setEdad(auxAnimal.calcularEdad(auxAnimal.getFechaNac()));
					aux.setnVacunas(auxAnimal.cantidadVacunas(auxAnimal));

					listAuxiliar.add(aux);
				}
			}

			if (listAuxiliar.size() > 0) {
				System.out.println("Cabeceras");
				for (int i = 0; i < listAuxiliar.size(); i++) {
					System.out.println(listAuxiliar.get(i).toString());
				}
			} else {
				System.out.println("No se han encontrado animales vacunados que tengan una edad inferior a" + edadlimite
						+ "año. ");
			}

			ois.close();
		} catch (

		FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	/*
	 * private static void mostrarAnimalconEdadyVacuna(File file) { Animal auxAnimal
	 * = null; ArrayList<SerVivo> animalesList = new ArrayList<>();
	 * ArrayList<ListadoAuxiliar> listAuxiliar = new ArrayList<>(); int edadlimite =
	 * 0;
	 * 
	 * // Alternativa
	 * 
	 * animalesList = fileToArrayList(file);
	 * 
	 * edadlimite = Util.
	 * leerInt("Introduzca la edad máxima (en años) que ha de tener el animal: ");
	 * 
	 * Iterator<SerVivo> iterador = animalesList.iterator(); while
	 * (iterador.hasNext()) { auxAnimal = (Animal) iterador.next(); if
	 * (auxAnimal.calcularEdad(auxAnimal.getFechaNac()) < edadlimite &&
	 * auxAnimal.tieneVacuna(auxAnimal)) { ListadoAuxiliar aux = new
	 * ListadoAuxiliar();
	 * 
	 * // sacar la info necesaria del animal
	 * aux.setCodigoAnimal(auxAnimal.getIdParque());
	 * aux.setEspecie(auxAnimal.getEspecie());
	 * aux.setnRevisiones(auxAnimal.getRevisiones().size());
	 * aux.setEdad(auxAnimal.calcularEdad(auxAnimal.getFechaNac()));
	 * aux.setnVacunas(auxAnimal.cantidadVacunas(auxAnimal));
	 * 
	 * // añadir a la lista listAuxiliar.add(aux);
	 * 
	 * } } if (listAuxiliar.size() > 0) { System.out.println("Cabeceras"); for (int
	 * i = 0; i < listAuxiliar.size(); i++) {
	 * System.out.println(listAuxiliar.get(i).toString()); } } else {
	 * System.out.println(
	 * "No se han encontrado animales vacunados que tengan una edad inferior a" +
	 * edadlimite + "año. "); }
	 * 
	 * }
	 */

}
