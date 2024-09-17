package tarea5.hibernate.SegundaMano.cargarDatos;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

import tarea5.hibernate.SegundaMano.entidades.Articulo;
import tarea5.hibernate.SegundaMano.entidades.Categoria;
import tarea5.hibernate.SegundaMano.entidades.Usuario;

/**
 * Clase de utilidad para inicializar la base de datos con datos de prueba.
 * Esta clase contiene métodos estáticos para pre-cargar la base de datos con entidades de usuario,
 * categoría y artículo para facilitar las pruebas y la demostración de la aplicación.
 * 
 * Las relaciones entre las entidades se establecen y luego se persisten en la base de datos.
 * Si las relaciones están configuradas para propagar las operaciones en cascada, la persistencia
 * de entidades relacionadas se manejará automáticamente sin la necesidad de llamadas explícitas
 * a persist() para cada entidad.
 */
public class InicializacionDatos {

	public static void cargarDatos(EntityManager em) {
		// Inicio la transacción:
        em.getTransaction().begin();

        // Creo el objeto fecha de tipo Date para las fechas
        Date fecha = new Date(); // Obtiene la fecha

        // Usuarios
        Usuario u1 = new Usuario("Sandra");
        Usuario u2 = new Usuario("Sergio");

        // Categorías
        Categoria c1 = new Categoria("Electrónica");
        Categoria c2 = new Categoria("Libros");

        // Artículos
        Articulo a1 = new Articulo("Laptop Gaming MSI", fecha, new BigDecimal(1200.00), "MSI GL65", (byte) 0);
        Articulo a2 = new Articulo("Smartphone Samsung Galaxy S21", fecha, new BigDecimal(800.00), "Samsung S21", (byte) 0);
        Articulo a3 = new Articulo("El juego del alma", fecha, new BigDecimal(15.50), "Javier Castillo", (byte) 1);
        Articulo a4 = new Articulo("El señor de los anillos", fecha, new BigDecimal(22.75), "J.R.R. Tolkien", (byte) 0);

        // Asigno categorías a los artículos
        a1.addCategoria(c1);
        a2.addCategoria(c2);
        a3.addCategoria(c1);
        a4.addCategoria(c2);

        // Asigno usuarios a los artículos
        a1.addUsuario(u2);
        a2.addUsuario(u1);
        a3.addUsuario(u2);
        a4.addUsuario(u1);

        // Persisto los usuarios y las categorías también si es necesario
        em.persist(u1);
        em.persist(u2);
        em.persist(c1);
        em.persist(c2);

        /*
         * En este caso, al haber añadido CascadeType.PERSIST o CascadeType.ALL, 
         * no sería necesario persistir las Categorías y Usuarios por separado antes
         * de persistir las instancias de Artículo, ya que al persistir Artículo 
         * también persistirán las instancias de Categoría y Usuario asociadas a dicho
         * artículo
         */
        em.persist(a1);
        em.persist(a2);
        em.persist(a3);
        em.persist(a4);

        // Confirmo la transacción
        em.getTransaction().commit();
	}
}
