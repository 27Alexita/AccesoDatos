package tarea5.hibernate.SegundaMano.utilidades;

import java.util.Scanner;

import javax.persistence.*;

/**
 * Clase de utilidades que proporciona métodos estáticos para operaciones comunes,
 * como mostrar el menú principal y cerrar recursos de manera segura.
 * 
 * Esta clase facilita la interacción con el usuario al proporcionar un menú consistente
 * y manejar el cierre ordenado de la aplicación.
 */
public class Utilidades {

	// Método que muestra el menú en el main
	public static void mostrarMenu() {
		System.out.println("\n | MENÚ PRINCIPAL |");
		System.out.println("1. Listar categorías");
		System.out.println("2. Listar artículos");
		System.out.println("3. Insertar artículo");
		System.out.println("4. Modificar artículo");
		System.out.println("5. Insertar categoría");
		System.out.println("6. Insertar artículo en una categoría");
		System.out.println("7. Buscar artículo");
		System.out.println("8. Salir");
		System.out.println(" -> Seleccione una opción: ");
	}
	
	// Método que cierrar de forma segura todos los recursos
	public static void salir(EntityManager em, EntityManagerFactory emf, Scanner scanner) {
		System.out.println("\nPrograma finalizado.\nHasta pronto.");

        // Cierro el Scanner antes de intentar cerrar el EntityManager.
        if (scanner != null) {
            scanner.close();
        }

        // Ahora cierro el EntityManager si aún está abierto.
        if (em != null && em.isOpen()) {
            em.close();
        }
        
        // Finalmente, cierro el EntityManagerFactory si aún está abierto.
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
