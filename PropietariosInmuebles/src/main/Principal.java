package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import clases.CiudadInfo;
import clases.Inmueble;
import clases.Local;
import clases.Piso;
import clases.Propietario;
import utilidades.MyObjectOutputStream;
import utilidades.Util;

public class Principal {

	public static void main(String[] args) {
		int opt;
		boolean salir;

		File fileInmuebles = new File("inmuebles.obj");
		File filePropietarios = new File("propietarios.obj");

		do {

			salir = false;

			System.out.println("Menu");
			System.out.println("1. Alta de propietario.");
			System.out.println("2. Alta de inmueble.");
			System.out.println("3. Listado del número de pisos por ciudad");
			System.out.println("4. Listado de inmuebles entre dos precios");
			System.out.println("5. Salir");

			opt = Util.leerInt("Seleccione una opción: ");

			switch (opt) {

			case 1:
				altaPropietario(filePropietarios);
				break;

			case 2:
				if (filePropietarios.exists()) {
					altaInmueble(fileInmuebles, filePropietarios);
				} else {
					System.out.println("Primero debe dar de alta al menos un Propietario.");
				}

				break;

			case 3:
				if (fileInmuebles.exists()) {
					listadoPisosPorCuidad(fileInmuebles);
				} else {
					System.out.println("No existen pisos a " + LocalDate.now());
				}
				break;

			case 4:
				if (fileInmuebles.exists()) {
					listadoPisosEntreDosPrecios(fileInmuebles);
				} else {
					System.out.println("No existen pisos a " + LocalDate.now());
				}
				break;

			case 5:
				System.out.println("Saliendo...");
				salir = true;
				break;
			default:
				break;

			}

		} while (!salir);

	}

	// --- 4.
	private static void listadoPisosEntreDosPrecios(File fileInmuebles) {
		boolean encontrado = false;

		float precioMinimo = Util.leerFloat("Introduce el precio minimo: ");
		float precioMaximo = Util.leerFloat("Introduce el precio máximo: ");

		try {
			FileInputStream fis = new FileInputStream(fileInmuebles);
			ObjectInputStream ois = new ObjectInputStream(fis);

			while (fis.available() > 0) {
				Inmueble inmueble = (Inmueble) ois.readObject();
				double precio = inmueble.getPrecio();

				if (precio >= precioMinimo && precio <= precioMaximo) {
					encontrado = true;
					String tipoInmueble = (inmueble instanceof Piso) ? "Piso" : "Local";
					String ubicacion = inmueble.getCiudad();
					if(inmueble instanceof Piso) {
						System.out.println(tipoInmueble + " " + inmueble.getPrecio() + " en " + ubicacion.toUpperCase()+" "+((Piso)inmueble).getNumHabitacion()+" habitaciones");
					} else {
						System.out.println(tipoInmueble + " " + inmueble.getPrecio() + " en " + ubicacion.toUpperCase()+" "+((Local)inmueble).getTipo());
					}		
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

		if (!encontrado) {
			System.out.println("No se encontraron inmuebles en el rango de precios especificado.");
		}

	}

	// --- 3.
	private static void listadoPisosPorCuidad(File fileInmuebles) {
		List<Piso> auxListPisos = new ArrayList<>();
		List<CiudadInfo> ciudades = new ArrayList<>();

		int tamanioFile = Util.calculoFichero(fileInmuebles);

		FileInputStream fis;
		ObjectInputStream ois;
		try {
			fis = new FileInputStream(fileInmuebles);
			ois = new ObjectInputStream(fis);

			for (int i = 0; i < tamanioFile; i++) {
				
				Inmueble auxInmueble = (Inmueble) ois.readObject();
				
				if(auxInmueble instanceof Piso) {
					 Piso auxPiso = (Piso) auxInmueble;
				     auxListPisos.add(auxPiso);
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

		for (Piso piso : auxListPisos) {
			String ciudad = piso.getCiudad();
			int precio = piso.getPrecio();

			boolean encontrado = false;

			CiudadInfo ciudadInfo = null;
			for (CiudadInfo info : ciudades) {
				if (info.getCiudad().equalsIgnoreCase(ciudad) && !encontrado) {
					ciudadInfo = info;
					encontrado = true;
				}
			}

			if (ciudadInfo != null) {
				ciudadInfo.incrementarNumPisos();
				ciudadInfo.sumarPrecio(precio);
			} else {
				ciudadInfo = new CiudadInfo(ciudad, 1, precio);
				ciudades.add(ciudadInfo);
			}
		}

		for (CiudadInfo info : ciudades) {
			info.calcularPrecioMedio();
		}

		Collections.sort(ciudades, Collections.reverseOrder());

		if (ciudades.isEmpty()) {
			System.out.println("No existen pisos a " + LocalDate.now());
		} else {
			System.out.println("Listado a " + LocalDate.now());
			System.out.println("CIUDAD\tNUM.PISOS\tPRECIOMEDIO");
			for (CiudadInfo info : ciudades) {
				System.out.println(info.getCiudad() + "\t" + info.getNumPisos() + "\t" + info.getPrecioMedio());
			}
		}
	}

	// --- 2.
	private static void altaInmueble(File fileInmuebles, File filePropietarios) {
		Propietario propietario = new Propietario();
		Inmueble inmueble;

		boolean encontrado = false;

		String auxDni = Util.introducirCadena("Introduzca el DNI del propietario:\t");

		encontrado = comprobarPropietario(filePropietarios, auxDni);

		if (!encontrado) {
			System.out.println("Este propietario no está dado de alta.");
		} else {
			propietario = devolverPropietario(filePropietarios, auxDni);
			System.out.println("Estos son los datos del propietario:");
			propietario.getDatos();

			int numInmuebles;

			if (!fileInmuebles.exists()) {
				numInmuebles = 1;
			} else {
				numInmuebles = Util.calculoFichero(fileInmuebles) + 1;
			}

			char tipoInmueble = Util.leerChar("¿Es un piso(P) o un local(L)?", 'P', 'L');
			if (tipoInmueble == 'P') {
				inmueble = new Piso();
			} else {
				inmueble = new Local();
			}

			inmueble.setDatos(numInmuebles, propietario.getDniPropietario());
			addInmueble(fileInmuebles, inmueble);
			System.out.println("El inmueble se ha dado de alta con código: " + inmueble.getCod());
		}
	}

	private static Propietario devolverPropietario(File filePropietarios, String auxDni) {
		Propietario propietarioEncontrado = new Propietario();
		List<Propietario> listaPropietarios = new ArrayList<>();
		int tamanioFile = Util.calculoFichero(filePropietarios);
		try {
			FileInputStream fis = new FileInputStream(filePropietarios);
			ObjectInputStream ois = new ObjectInputStream(fis);

			for (int i = 0; i < tamanioFile; i++) {
				Propietario auxPropietario = (Propietario) ois.readObject();
				listaPropietarios.add(auxPropietario);
			}

			Iterator<Propietario> iter = listaPropietarios.iterator();
			while (iter.hasNext()) {
				Propietario propietario = iter.next();
				if (propietario.getDniPropietario().equalsIgnoreCase(auxDni)) {
					propietarioEncontrado = propietario;
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
		return propietarioEncontrado;
	}

	private static void addInmueble(File fileInmueble, Inmueble inmueble) {

		FileOutputStream fos;
		ObjectOutputStream oos;

		try {
			if (fileInmueble.exists()) {
				fos = new FileOutputStream(fileInmueble, true);
				oos = new MyObjectOutputStream(fos);
			} else {
				fos = new FileOutputStream(fileInmueble);
				oos = new ObjectOutputStream(fos);
			}
			oos.writeObject(inmueble);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// --- 1.
	private static void altaPropietario(File filePropietarios) {

		Propietario propietario = new Propietario();

		boolean encontrado = false;

		String auxDni = Util.introducirCadena("Introduzca el DNI:\t");

		if (filePropietarios.exists()) {
			encontrado = comprobarPropietario(filePropietarios, auxDni);
		}

		if (encontrado) {
			System.out.println("El propietario ya está registrado.");
		} else {
			propietario.setDatos(auxDni);
			addProp(filePropietarios, propietario);
		}
	}

	private static void addProp(File filePropietarios, Propietario propietario) {
		try {
			FileOutputStream fos;
			ObjectOutputStream oos;

			if (filePropietarios.exists()) {
				fos = new FileOutputStream(filePropietarios, true);
				oos = new MyObjectOutputStream(fos);
			} else {
				fos = new FileOutputStream(filePropietarios);
				oos = new ObjectOutputStream(fos);
			}
			oos.writeObject(propietario);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static boolean comprobarPropietario(File filePropietarios, String auxDni) {
		List<Propietario> listaPropietarios = new ArrayList<>();
		int tamanioFile = Util.calculoFichero(filePropietarios);
		boolean encontrado = false;

		try {
			FileInputStream fis = new FileInputStream(filePropietarios);
			ObjectInputStream ois = new ObjectInputStream(fis);

			for (int i = 0; i < tamanioFile; i++) {
				Propietario auxPropietario = (Propietario) ois.readObject();
				listaPropietarios.add(auxPropietario);
			}

			Iterator<Propietario> iter = listaPropietarios.iterator();
			while (iter.hasNext()) {
				Propietario propietario = iter.next();
				if (propietario.getDniPropietario().equalsIgnoreCase(auxDni)) {
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
}
