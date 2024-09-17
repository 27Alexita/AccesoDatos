package tarea6.mongoDB;

import com.mongodb.client.MongoCollection;

import java.util.Scanner;

import org.bson.Document;

/**
 * Esta clase representa la aplicación principal que gestiona mensajes en una red social.
 * Permite interactuar con la base de datos MongoDB para realizar diversas operaciones,
 * como mostrar mensajes, incrementar "me gustas", borrar mensajes y añadir nuevos mensajes.
 * El programa se ejecuta desde el método {@code main}.
 * 
 * @author Sandra
 */
public class App {

	/**
     * Método principal de la aplicación.
     * 
     * @param args Los argumentos de la línea de comandos (no se utilizan).
     */
    public static void main( String[] args ){
        
    	// Creo la conexión con la base de datos
    	Conexion conexion = new Conexion("RedSocial");
    	// Establezco la colección
    	MongoCollection<Document> mensajesCollection = conexion.getCollection("mensajes");
    	// Instancia de la clase GestorMensajes
    	GestorMensajes mensajes = new GestorMensajes(mensajesCollection);
    	
    	// Inicializar y sembrar datos en la base de datos
    	InicializacionDatos inicializacionDatos = new InicializacionDatos(mensajesCollection);
    	inicializacionDatos.seedData();
    	
    	// Entrada de datos
    	Scanner scanner = new Scanner(System.in);
    	
    	// Flag para el bucle del programa principal
    	boolean continuar = false;
    	
    	// Instancia de la clase Utilidades
    	Utilidades utilidades = new Utilidades();
    	
    	
    	// Comienzo del programa principal
    	try {
    		while(!continuar) {
    			utilidades.mostrarMenu();// Muestro el menú principal
    			int opcion = Integer.parseInt(scanner.nextLine()); // Para elegir las opciones del switch
    			System.out.println("");
    			switch(opcion) {
    			case 1:
    				// 1. Mostrar todos los mensajes (se deben mostrar todos los datos de los mensajes y sus usuarios)
    				mensajes.mostrarTodosLosMensajes();
    				break;
    			case 2:
    				// 2. Mostrar los mensajes con número de me gustas > 3
    				mensajes.mostrarMensajesConMasLikes();
    				break;
    			case 3:
    				// 3. Incrementar en uno el número de me gustas de los mensajes escritos por pepe@gmail.com
    				System.out.println("Ingresa el email del usuario para incrementar sus 'me gustas': ");
    				String emailIncrementar = scanner.nextLine();
    				mensajes.incrementarLikesPorUsuario(emailIncrementar);
    				break;
    			case 4: 
    				// 4. Borrar los mensajes escritos por dolores@gmail.com
    				System.out.println("Ingresa el email del usuario para borrar sus mensajes: ");
    				String emailBorrar = scanner.nextLine();
    				mensajes.borrarMensajesPorUsuario(emailBorrar);
    				break;
    			case 5:
    				// 5. Insertar un mensaje solicitando el texto, número de me gustas, email y rutaFoto
    				System.out.println("Ingresa el texto del mensaje: ");
    				String texto = scanner.nextLine();
    				
    				System.out.println("Ingresa el número de 'me gusta': ");
    				int numeroMeGustas;
    				try {
    					numeroMeGustas = Integer.parseInt(scanner.nextLine());
    				} catch (NumberFormatException ex) {
    					System.out.println("Número no válido. Se usará 0 como valor por defecto.");
    					numeroMeGustas = 0;
    				}
    				
    				System.out.println("Ingresa el email del usuario: ");
    				String email = scanner.nextLine();
    				
    				System.out.println("Ingresa la ruta de la foto: ");
    				String rutaFoto = scanner.nextLine();
    				
    				mensajes.insertarMensaje(texto, numeroMeGustas, email, rutaFoto);
    				break;
    			case 6:
    				// 6. Salir del programa
    				continuar = true;
    				System.out.println("Programa finalizado. \n¡Hasta nueva orden!");
    				break;
    			default:
    				System.out.println("Opción no válida. Por favor intente nuevamente.");
                    break;
    			}
    		}
    	} catch (Exception ex){
    		ex.printStackTrace();
    	} finally {
    		conexion.close();
    		scanner.close();
    	}
    }
}
