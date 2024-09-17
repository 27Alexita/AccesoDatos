package tarea5.hibernate.SegundaMano;

import java.util.Scanner;

import javax.persistence.*;

import tarea5.hibernate.SegundaMano.cargarDatos.InicializacionDatos;
import tarea5.hibernate.SegundaMano.consultas.Consultas;
import tarea5.hibernate.SegundaMano.utilidades.Utilidades;

/**
 * La clase principal de la aplicación que proporciona el menú principal y gestiona las interacciones del usuario.
 * Esta clase se encarga de iniciar la aplicación, mostrar el menú de opciones disponibles,
 * procesar las selecciones del usuario y delegar las acciones a la clase de consultas correspondiente.
 * También inicializa los recursos necesarios para la ejecución del programa, como la conexión a la base de datos
 * y la entrada del usuario.
 */
public class App {
	
	public static void main(String[] args) {

		// Creo fábrica de EntityManagers con la unidad de persistencia "SegundaMano"
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("SegundaMano");
		// Creo EntityManager a partir de la fábrica anterior. Gestiona entidades y transacciones con la base de datos.
		EntityManager em = emf.createEntityManager();
		// Instancia de la clase consultas que contiene los métodos para ejecutar las operaciones
		Consultas consultas = new Consultas();
		boolean continuar = false;
		Scanner scanner = new Scanner(System.in);

		// Inicializo los datos
		InicializacionDatos.cargarDatos(em);

		while (!continuar) {
			// Muestro el menú principal
			Utilidades.mostrarMenu();
			int opcion = Integer.parseInt(scanner.nextLine());
			switch (opcion) {
			case 1:
				// 1. Listar categorías
				consultas.listarCategorias(em);
				break;
			case 2:
				// 2. Listar artículos
				consultas.listarArticulos(em);
				break;
			case 3:
				// 3. Insertar artículo
				consultas.insertarArticulo(em, scanner);
				break;
			case 4:
				// 4. Modificar artículo
				consultas.modificarArticulo(em, scanner);
				break;
			case 5:
				// 5. Insertar categoría
				consultas.insertarCategoria(em, scanner);
				break;
			case 6:
				// 6. Insertar artículo en una categoría
				consultas.insertarArticuloEnCategoria(em, scanner);
				break;
			case 7:
				// 7. Buscar artículo
				consultas.buscarArticulo(em, scanner);
				break;
			case 8:
				// 8. Salir del programa
				Utilidades.salir(em, emf, scanner);
				continuar = true;
				break;
			default:
				System.out.println("\nElija una opción  correcta. (Valores entre 1 y 8).\n");
			}
		}
	}
}
