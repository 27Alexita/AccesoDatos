package tarea5.hibernate.SegundaMano.consultas;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.persistence.*;

import tarea5.hibernate.SegundaMano.entidades.Articulo;
import tarea5.hibernate.SegundaMano.entidades.Categoria;

/**
 * Clase que contiene métodos para realizar operaciones de consulta y
 * manipulación de entidades Articulo y Categoria en la base de datos.
 *
 * Esta clase utiliza el EntityManager para interactuar con la base de datos a
 * través de JPA, facilitando operaciones como listar artículos y categorías,
 * insertar y modificar artículos, insertar categorías y buscar artículos por
 * palabras clave.
 */
public class Consultas {

	/**
	 * 1. Lista todas las categorías disponibles en la base de datos, ordenadas
	 * alfabéticamente por el nombre de la categoría, y muestra los artículos
	 * asociados a cada una.
	 *
	 * @param em EntityManager para realizar las consultas a la base de datos.
	 */
	public void listarCategorias(EntityManager em) {
		try {
			Query query = em.createQuery("SELECT c FROM Categoria c LEFT JOIN FETCH c.articulos ORDER BY c.categoria");
			List<Categoria> listaCategorias = query.getResultList();
			if (listaCategorias.isEmpty()) {
				System.out.println("No hay categorías.");
			} else {
				System.out.println("\n\nCategorías:");
				for (Categoria categoria : listaCategorias) {
					System.out.println("  --> ID: " + categoria.getId() + "     | " + categoria.getCategoria());
					System.out.println("      Artículos:");
					for (Articulo articulo : categoria.getArticulos()) {
						System.out.println("       \t- " + articulo.getTitulo());
					}
				}
			}
		} catch (PersistenceException e) {
			System.out.println("Error al acceder a la base de datos: " + e.getMessage());
		}
	}

	/**
	 * 2. Lista todos los artículos disponibles en la base de datos, ordenados
	 * alfabéticamente por el título del artículo. También muestra las categorías
	 * asociadas a cada artículo.
	 *
	 * @param em EntityManager para realizar las consultas a la base de datos.
	 */
	public void listarArticulos(EntityManager em) {
		try {
			Query query = em.createQuery("SELECT a FROM Articulo a ORDER BY a.titulo", Articulo.class);
			List<Articulo> listaArticulos = query.getResultList();
			if (listaArticulos.isEmpty()) {
				System.out.println("No hay artículos.");
			} else {
				System.out.println("\n\nArtículos:");
				System.out.printf("%-5s %-25s %-30s %-10s %-15s %-10s %-20s\n", "Id", "Nombre", "Descripción", "Precio",
						"Fecha", "Vendido", "Categoría");
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				for (Articulo articulo : listaArticulos) {
					String vendido = articulo.getVendido() == 0 ? "No" : "Sí"; 
					String fechaFormateada = sdf.format(articulo.getFecha());

					StringBuilder categorias = new StringBuilder();
					articulo.getCategorias().forEach(categoria -> {
						if (categorias.length() > 0)
							categorias.append(", ");
						categorias.append(categoria.getCategoria());
					});

					System.out.printf("%-5d %-25s %-30s %-10.2f %-15s %-10s %-20s\n", articulo.getId(),
							articulo.getTitulo(), articulo.getDescripcion(), articulo.getPrecio().doubleValue(),
							fechaFormateada, vendido, categorias.toString());
				}
			}
		} catch (PersistenceException e) {
			System.out.println("Error al acceder a la base de datos: " + e.getMessage());
		}
	}

	/**
	 * 3. Permite al usuario insertar un nuevo artículo en la base de datos,
	 * solicitando los datos necesarios a través del terminal.
	 *
	 * @param em      EntityManager para acceder y modificar la base de datos.
	 * @param scanner Scanner para leer la entrada del usuario desde el terminal.
	 */
	public void insertarArticulo(EntityManager em, Scanner scanner) {
		try {
			System.out.println("Datos del nuevo artículo:");
			// Solicitar nombre
			System.out.print("Nombre: ");
			String titulo = scanner.nextLine();
			// Solicitar descripción
			System.out.print("Descripción: ");
			String descripcion = scanner.nextLine();
			// Solicitar precio
			System.out.print("Precio: ");
			BigDecimal precio = scanner.nextBigDecimal();
			scanner.nextLine(); // Consumo el newline
			// Solicitar fecha
			System.out.print("Fecha (dd/MM/yyyy): ");
			String fechaStr = scanner.nextLine();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date fecha = null;
			try {
				fecha = dateFormat.parse(fechaStr);
			} catch (ParseException e) {
				System.out.println("Formato de fecha incorrecto. Debe ser dd/MM/yyyy. Artículo no insertado.");
				return;
			}
			
			// Solicitar el estado de la venta
			System.out.print("¿Está vendido? (0 - NO / 1 - SÍ): ");
			Byte vendido = scanner.nextByte();
			scanner.nextLine(); // Consumo el newline

			// Añado el artículo a la base de datos
			Articulo articulo = new Articulo();
			articulo.setTitulo(titulo);
			articulo.setDescripcion(descripcion);
			articulo.setPrecio(precio);
			articulo.setFecha(fecha);
			articulo.setVendido(vendido);

			em.getTransaction().begin(); // Comienza la transación
			em.persist(articulo); // Persiste los datos
			em.getTransaction().commit(); // Termina la transacción

			System.out.println("Artículo insertado correctamente.");
		} catch (InputMismatchException e) {
			System.out.println("Error: El formato de entrada es incorrecto.");
		} catch (PersistenceException e) {
			System.out.println("Error al acceder a la base de datos: " + e.getMessage());
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
	}

	/**
	 * 4. Permite al usuario modificar los datos de un artículo existente en la base
	 * de datos, identificado por su ID, solicitando los nuevos datos a través del
	 * terminal.
	 *
	 * @param em      EntityManager para realizar las operaciones de búsqueda y
	 *                actualización.
	 * @param scanner Scanner para leer la entrada del usuario.
	 */
	public void modificarArticulo(EntityManager em, Scanner scanner) {
		try {
			// Solicita el ID del artículo
			System.out.print("ID del artículo a modificar: ");
			int id = Integer.parseInt(scanner.nextLine());

			Articulo articulo = em.find(Articulo.class, id);
			if (articulo != null) {
				System.out.println("Modificando artículo: " + articulo.getTitulo());
				// Solicitar y actualizar título
				System.out.print("Nuevo nombre (deje en blanco para no modificar): ");
				String titulo = scanner.nextLine();
				if (!titulo.isEmpty()) {
					articulo.setTitulo(titulo);
				}

				// Solicitar y actualizar descripción
				System.out.print("Nueva descripción (deje en blanco para no modificar): ");
				String descripcion = scanner.nextLine();
				if (!descripcion.isEmpty()) {
					articulo.setDescripcion(descripcion);
				}

				// Solicitar y actualizar precio
				System.out.print("Nuevo precio (0 para no modificar): ");
				BigDecimal precio;
				try {
					precio = scanner.nextBigDecimal();
				} catch (InputMismatchException ex) {
					System.out.println("Debe ingresar un número válido para el precio.");
					scanner.next(); // consumir la entrada incorrecta
					return;
				}
				scanner.nextLine(); // Consumir el final de la línea después del número

				// Solicitar y actualizar fecha
				System.out.print("Nueva fecha (dd/MM/yyyy, deje en blanco para no modificar): ");
				String fechaStr = scanner.nextLine();
				if (!fechaStr.isEmpty()) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					try {
						Date fecha = dateFormat.parse(fechaStr);
						articulo.setFecha(fecha);
					} catch (ParseException ex) {
						System.out.println("Formato de fecha incorrecto. Debe ser dd/MM/yyyy.");
						return;
					}
				}

				// Solicitar y actualizar el estado de venta
				System.out.print("¿Está vendido? (0 - NO / 1 - SÍ / deje en blanco para no modificar): ");
				String vendidoStr = scanner.nextLine();
				if (!vendidoStr.isEmpty()) {
				    try {
				        byte vendido = Byte.parseByte(vendidoStr);
				        if (vendido == 0 || vendido == 1) {
				            articulo.setVendido(vendido);
				        } else {
				            System.out.println("Entrada no válida. Por favor, introduzca 0 para no o 1 para sí.");
				        }
				    } catch (NumberFormatException e) {
				        System.out.println("Error: La entrada debe ser 0, 1 o dejarse en blanco.");
				    }
				}
				em.getTransaction().begin(); // Comienza la transacción
				em.merge(articulo); // Persiste actualizando el estado del artículo
				em.getTransaction().commit(); // Finaliza la transacción

				System.out.println("Artículo modificado correctamente.");
			} else {
				System.out.println("No se encontró el artículo con ID: " + id);
			}
		} catch (PersistenceException e) {
			System.out.println("Error al acceder a la base de datos: " + e.getMessage());
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
	}

	/**
	 * 5. Permite al usuario insertar una nueva categoría en la base de datos,
	 * solicitando el nombre de la categoría a través del terminal.
	 *
	 * @param em      EntityManager para acceder y modificar la base de datos.
	 * @param scanner Scanner para leer la entrada del usuario.
	 */
	public void insertarCategoria(EntityManager em, Scanner scanner) {
		try {
			System.out.println("Nueva categoría:");
			// Solicitud nuevo nombre categoría
			System.out.print("Nombre: ");
			String nombreCategoria = scanner.nextLine(); // NextLine para permitir nombres de categorías con espacios
			// Añado la categoría a la base de datos
			Categoria categoria = new Categoria(nombreCategoria);
			em.getTransaction().begin(); // Comienza la transacción
			em.persist(categoria); // Persisten los datos
			em.getTransaction().commit(); // No es necesario llamar a flush() antes de commit, commit ya lo hace
											// automáticamente.

			System.out.println("Categoría '" + categoria.getCategoria() + "' creada correctamente.");
		} catch (NoSuchElementException | PersistenceException e) {
			System.out.println("ERROR 1: " + e.getMessage());
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		} catch (Exception e) {
			System.out.println("ERROR 2: " + e.getMessage());
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
	}

	/**
	 * 6. Asocia un artículo existente con una categoría existente, ambos
	 * identificados por sus IDs, modificando la relación en la base de datos.
	 *
	 * @param em      EntityManager para realizar la operación.
	 * @param scanner Scanner para leer los IDs del artículo y la categoría.
	 */
	public void insertarArticuloEnCategoria(EntityManager em, Scanner scanner) {
		try {
			// Solicitud del ID del artículo
			System.out.print("ID del artículo: ");
			int idArticulo = Integer.parseInt(scanner.nextLine());
			// Solicitud del ID de la categoría
			System.out.print("ID de la categoría: ");
			int idCategoria = Integer.parseInt(scanner.nextLine());
			
			em.getTransaction().begin(); // Comienza la transacción
			
			// Busco el artículo y la categoría según su ID
			Articulo articulo = em.find(Articulo.class, idArticulo);
			Categoria categoria = em.find(Categoria.class, idCategoria);
			
			// Compruebo si ambos existen
			if (articulo != null && categoria != null) {
				
				categoria.getArticulos().add(articulo);
				// Añado la categoría a la lista de categorías del artículo
				List<Categoria> categoriasDelArticulo = articulo.getCategorias();
				if (categoriasDelArticulo == null) {
				    categoriasDelArticulo = new ArrayList<>();
				    articulo.setCategorias(categoriasDelArticulo);
				}
				categoriasDelArticulo.add(categoria);
				
				// Fuerzo la actualización del contexto de persistencia
	            em.flush();

	            // Refresco la entidad categoría para asegurar que la lista de artículos es actualizada
	            em.refresh(categoria);

	            // Finaliza la transacción
	            em.getTransaction().commit();

	            // Verifico el estado de la lista de artículos de la categoría
	            System.out.println("La categoría '" + categoria.getCategoria() + "' tiene los siguientes artículos:");
	            for (Articulo art : categoria.getArticulos()) {
	                System.out.println("\t- " + art.getTitulo());
	            }

				System.out.println("Artículo añadido a la categoría correctamente.");
			} else {
				System.out.println("Artículo o categoría no encontrados.");
			}
		} catch (PersistenceException e) {
			System.out.println("Error al acceder a la base de datos: " + e.getMessage());
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
	}

	/**
	 * 7. Busca artículos que contienen una palabra clave dada en su título o
	 * descripción, utilizando una NamedQuery definida en la entidad Articulo.
	 *
	 * @param em      EntityManager para realizar la consulta.
	 * @param scanner Scanner para leer la palabra clave del usuario.
	 */
	public void buscarArticulo(EntityManager em, Scanner scanner) {
		try {
			// Solicitud de la palabra clave
			System.out.print("Palabra clave para buscar en los artículos: ");
			String palabraClave = scanner.nextLine();

			// Uso la NamedQuery definida en la clase Articulo
			List<Articulo> articulos = em.createNamedQuery("Articulo.findByPalabra", Articulo.class)
					.setParameter("palabra", "%" + palabraClave + "%").getResultList();

			if (articulos.isEmpty()) {
				System.out.println("No se encontraron artículos con la palabra clave proporcionada.");
			} else {
				System.out.println("Artículos encontrados:");
				for (Articulo articulo : articulos) {
					System.out.println(
							articulo.getId() + ": " + articulo.getTitulo() + " - " + articulo.getDescripcion());
				}
			}
		} catch (PersistenceException e) {
			System.out.println("Error al acceder a la base de datos: " + e.getMessage());
		}
	}
}
