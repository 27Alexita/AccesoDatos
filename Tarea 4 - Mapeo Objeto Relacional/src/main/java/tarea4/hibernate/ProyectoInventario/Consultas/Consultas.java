package tarea4.hibernate.ProyectoInventario.Consultas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.persistence.*;

import tarea4.hibernate.ProyectoInventario.Entidades.Aula;
import tarea4.hibernate.ProyectoInventario.Entidades.Equipo;

public class Consultas {

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoInventario");
	private static Scanner scanner = new Scanner(System.in);
	
	// Método para listar las aulas y los equipos
	public static void listarAulasYEquipos() {
		EntityManager em = emf.createEntityManager();
		try {
	        List<Aula> aulas = em.createQuery("SELECT a FROM Aula a LEFT JOIN FETCH a.equipos", Aula.class).getResultList();
	        if (aulas.isEmpty()) {
	            System.out.println("No hay aulas disponibles.");
	            return;
	        }

	        for (Aula aula : aulas) {
	            System.out.println("Aula: " + aula.getNombre() + " [ID: " + aula.getId() + "]");
	            if (aula.getEquipos() == null || aula.getEquipos().isEmpty()) {
	                System.out.println("  No hay equipos en este aula.");
	            } else {
	                for (Equipo equipo : aula.getEquipos()) {
	                    System.out.println("  Equipo: ");
	                    System.out.println("    Número de Serie: " + equipo.getNumSerie());
	                    System.out.println("    Características: " + equipo.getCaracteristicas());
	                    System.out.println("    Fecha de Alta: " + equipo.getFechaAlta());
	                }
	            }
	            System.out.println(); // Añade una línea en blanco para separar aulas
	        }
	    } finally {
	        em.close();
	    }
	}
	
	// Método para insertar un aula
	public static void insertarAula() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		Aula aula = new Aula();
		System.out.println("Ingrese el nombre del aula: ");
		String nombre = scanner.nextLine();
		aula.setNombre(nombre);
		
		em.persist(aula);
		em.getTransaction().commit();
		
		System.out.println("Aula insertada con éxito.");
		em.close();
	}
	
	// Método para borrar un aula
	public static void borrarAula() {
		EntityManager em = emf.createEntityManager();
		Aula aula = null;
		
		do {
			System.out.println("Ingrese el ID del aula a borrar: ");
			int id = Integer.parseInt(scanner.nextLine());
			aula = em.find(Aula.class, id);
			
			if(aula == null) {
				System.out.println("Aula no encontrada. Por favor, inténtelo de nuevo.");
			}
			
		}while(aula == null);
		
		em.getTransaction().begin();
		em.remove(aula);
		em.getTransaction().commit();
		System.out.println("Aula borrada correctamente");
		
		em.close();
	}
	
	// Método para modificar un aula
	public static void modificarAula() {
		EntityManager em = emf.createEntityManager();
		Aula aula = null;
		
		do {
			try {
	            System.out.print("Ingrese el ID del aula a modificar: ");
	            int id = Integer.parseInt(scanner.nextLine());
	            aula = em.find(Aula.class, id);

	            if(aula == null) {
	                System.out.println("Aula no encontrada. Por favor, inténtelo de nuevo.");
	            }
	        } catch (NumberFormatException e) {
	            System.out.println("Por favor, introduzca un número válido.");
	        }
		} while(aula == null);
		
		System.out.println("Ingrese el nuevo nombre del aula: ");
		String nuevoNombre = scanner.nextLine();
		
		em.getTransaction().begin();
		aula.setNombre(nuevoNombre);
		em.getTransaction().commit();
		System.out.println("Aula modificada con éxito.");
		
		em.close();
	}
	
	// Método para insertar un equipo y añadirlo a un aula
	public static void insertarEquipoYAsignarAula() {
		EntityManager em = emf.createEntityManager();
		try {
			// Listar todas las aulas disponibles
			System.out.println("Aulas disponibles: ");
			List<Aula> aulas = em.createQuery("SELECT a FROM Aula a", Aula.class).getResultList();
			for (Aula aula : aulas) {
				System.out.println("ID: " + aula.getId() + "\nNombre: " + aula.getNombre());
			}
			
			// Solicitar el ID del aula y validar
			Aula aulaSeleccionada = null;
			do {
	            try {
	                System.out.print("Ingrese el ID del aula a la que quiere asignar el equipo: ");
	                int id = Integer.parseInt(scanner.nextLine());
	                aulaSeleccionada = em.find(Aula.class, id);
	                if (aulaSeleccionada == null) {
	                    System.out.println("Aula no encontrada. Por favor, inténtelo de nuevo.");
	                }
	            } catch (NumberFormatException e) {
	                System.out.println("Por favor, introduzca un número válido.");
	            }
	        } while (aulaSeleccionada == null);
			
			// Solicitar datos del equipo
			System.out.println("Ingrese el número de serie del equipo: ");
			String numSerie = scanner.nextLine();
			System.out.println("Ingrese las características del equipo: ");
			String caracteristicas = scanner.nextLine();
			
			// Solicitar la fecha de alta del equipo
	        System.out.print("Ingrese la fecha de alta del equipo (YYYY-MM-DD): ");
	        String fechaAltaStr = scanner.nextLine();
	        Date fechaAlta = null;
	        try {
	            fechaAlta = new SimpleDateFormat("yyyy-MM-dd").parse(fechaAltaStr);
	        } catch (ParseException e) {
	            System.out.println("Formato de fecha no válido. Utilice el formato YYYY-MM-DD.");
	            return; // Salir del método si la fecha no es válida
	        }
	        
			// Crear y persistir el equipo
			em.getTransaction().begin();
			Equipo equipo = new Equipo();
			equipo.setNumSerie(numSerie);
			equipo.setCaracteristicas(caracteristicas);
			equipo.setFechaAlta(fechaAlta); 
			equipo.setAula(aulaSeleccionada); // Asigno el aula seleccionada
			em.persist(equipo);
			em.getTransaction().commit();
			
			System.out.println("Equipo insertado y asignado al aula correctamente.");
		} finally {
			if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();
		}
	}
	
	// Método para buscar un equipo por su número de serie
	public static void buscarEquipoPorNumeroDeSerie() {
		EntityManager em = emf.createEntityManager();
		try {
			System.out.println("Ingrese el número de serie del equipo que desea buscar: ");
			String numSerie = scanner.nextLine();
			
			// Buscar el equipo por su número de serie
			List<Equipo> equipos = em.createQuery("SELECT e FROM Equipo e WHERE e.numSerie = :numSerie", Equipo.class)
					.setParameter("numSerie", numSerie).getResultList();
			
			if(!equipos.isEmpty()) {
				Equipo equipo = equipos.get(0); // Asumo que el número de serie es único
				System.out.println("Equipo encontrado: ");
				System.out.println("\nNúmero de serie: " + equipo.getNumSerie());
				System.out.println("\nCaracterísticas: " + equipo.getCaracteristicas());
				System.out.println("\nFecha de alta: " + equipo.getFechaAlta());
				
				// Mostrar el nombre del aula al que pertenece el equipo
				if(equipo.getAula() != null) {
					System.out.println("Aula: " + equipo.getAula().getNombre());				
				} else {	
					System.out.println("Este equipo no está asignado a ningún aula");
				}
			} else {
				System.out.println("No se encontró un equipo con ese número de serie");
			}
		} finally {
			em.close();
		}
	}
}
