package tarea4.hibernate.ProyectoInventario;

import javax.persistence.*;

import tarea4.hibernate.ProyectoInventario.CargarDatos.InicializacionApp;
import tarea4.hibernate.ProyectoInventario.Consultas.Consultas;
import tarea4.hibernate.ProyectoInventario.Utilidades.Utilidades;

import java.util.Scanner;

public class App {
	
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoInventario");
	private static Scanner scanner = new Scanner(System.in);
	
    public static void main( String[] args ){
        try {
        	// Precargo los datos en la base de datos
        	InicializacionApp.precargarDatos(emf);
        	
        	// Bucle del menú principal
        	// Flag para manejar el bucle
        	boolean continuar = true;
        	while(continuar) {
        		Utilidades.mostrarMenu();
        		int opcion = Integer.parseInt(scanner.nextLine());
        		
        		switch(opcion) {
        		case 1: 
        			// Listar aulas y sus equipos
        			Consultas.listarAulasYEquipos();
        			break;
        		case 2:
        			// Insertar aula
        			Consultas.insertarAula();
        			break;
        		case 3:
        			// Borrar aula
        			Consultas.borrarAula();
        			break;
        		case 4:
        			// Modificar aula
        			Consultas.modificarAula();
        			break;
        		case 5:
        			// Insertar equipo y añadirlo a un aula
        			Consultas.insertarEquipoYAsignarAula();
        			break;
        		case 6:
        			// Buscar un equipo por su número de serie
        			Consultas.buscarEquipoPorNumeroDeSerie();
        			break;
        		case 7:
        			continuar = false;
        			System.out.println("Programa finalizado.");
        			break;
        			default:
        				System.out.println("Opción no válida. Por favor, inténtelo de nuevo.");
        		}
        	}  	
        } finally {
        	emf.close();
        	scanner.close();
        }
    	
    }
}
