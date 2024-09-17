package tarea6.mongoDB;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import org.bson.Document;
import org.bson.conversions.Bson;

/**
 * Esta clase proporciona métodos para gestionar mensajes en una colección MongoDB.
 * Permite realizar operaciones como mostrar todos los mensajes, mostrar mensajes
 * con un número de "me gusta" superior a un valor dado, incrementar el número
 * de "me gusta" de los mensajes de un usuario, borrar mensajes de un usuario y
 * insertar nuevos mensajes en la colección.
 */
public class GestorMensajes {

	private MongoCollection<Document> collection; // Colección MongoDB para gestionar mensajes
	
	/**
     * Constructor de la clase. Inicializa la instancia con la colección especificada.
     * 
     * @param collection La colección MongoDB en la que gestionar los mensajes.
     */
	public GestorMensajes(MongoCollection<Document> collection) {
		this.collection = collection;
	}
	
	/**
     * Muestra todos los mensajes presentes en la colección MongoDB.
     * Cada mensaje se muestra con su texto, número de "me gusta",
     * y la información del usuario que lo escribió (email y ruta de foto).
     */
	public void mostrarTodosLosMensajes() {
		// Sin formatear los datos
		//collection.find().forEach(document -> System.out.println(document.toJson()));
		
		for(Document mensaje : collection.find()) {
			String texto = mensaje.getString("texto");
			int numeroMeGustas = mensaje.getInteger("numero_megustas", 0); // Proporciono un valor por defecto en caso de que el campo no exista
			Document usuario = mensaje.get("usuario", Document.class); // Me aseguro de que el campo 'usuario' también es un documento
			String email = usuario != null ? usuario.getString("email") : "No especificado";
			String rutaFoto = usuario != null ? usuario.getString("rutaFoto") : "No especificada";
			
			// Formateo y muestro el mensaje
			System.out.println("Texto: " + texto);
			System.out.println("Número de 'Me gusta': " + numeroMeGustas);
			System.out.println("Usuario:");
			System.out.println("\tEmail: " + email);
			System.out.println("\tRuta de la foto: " + rutaFoto);
			System.out.println("");
		}
	}
	
	/**
     * Muestra los mensajes que tienen un número de "me gusta" superior a 3.
     * Cada mensaje se muestra con su texto, número de "me gusta",
     * y la información del usuario que lo escribió (email y ruta de foto).
     */
	public void mostrarMensajesConMasLikes() {
		Bson filtro = Filters.gt("numero_megustas", 3);
		FindIterable<Document> mensajes = collection.find(filtro);
		
		// Sin formatear los datos
		//for(Document mensaje : mensajes) {
			//System.out.println(mensaje.toJson());
		//}
		
		for(Document mensaje : mensajes) {
			String texto = mensaje.getString("texto");
			int numeroMeGustas = mensaje.getInteger("numero_megustas", 0); // Valor por defecto en caso de que no exista
			Document usuario = mensaje.get("usuario", Document.class); // Asegurarse de que 'usuario' es un documento
			String email = usuario != null ? usuario.getString("email") : "No especificado";
			String rutaFoto = usuario != null ? usuario.getString("rutaFoto") : "No especificada";
			
			// Formatear y mostrar el mensaje
			System.out.println("Texto: " + texto);
			System.out.println("Número de 'me gusta': " + numeroMeGustas);
			System.out.println("Usuario: ");
			System.out.println("\tEmail: " + email);
			System.out.println("\tRuta de la foto: " + rutaFoto);
			System.out.println("");
		}
	}
	
	/**
     * Incrementa el número de "me gusta" de los mensajes escritos por un usuario específico.
     * 
     * @param email El correo electrónico del usuario cuyos mensajes se deben incrementar.
     */
	public void incrementarLikesPorUsuario(String email) {
		Bson filtro = Filters.eq("usuario.email", email);
		Bson incremento = Updates.inc("numero_megustas", 1);
		UpdateResult resultado = collection.updateMany(filtro, incremento);
		
	    // Comprobar si se modificaron documentos y mostrar un mensaje al usuario
	    if (resultado.getModifiedCount() > 0) {
	        System.out.println(resultado.getModifiedCount() + " mensajes incrementados correctamente.");
	        System.out.println("");
	    } else {
	        System.out.println("No se encontraron mensajes para incrementar 'me gustas' o el email no existe.");
	        System.out.println("");
	    }
	}
	
	/**
     * Borra todos los mensajes escritos por un usuario específico.
     * 
     * @param email El correo electrónico del usuario cuyos mensajes se deben borrar.
     */
	public void borrarMensajesPorUsuario(String email) {
		Bson filtro = Filters.eq("usuario.email", email);
		DeleteResult resultado = collection.deleteMany(filtro);
		
		// Comprobar si se borraron documentos y mostrar un mensaje al usuario
	    if (resultado.getDeletedCount() > 0) {
	        System.out.println(resultado.getDeletedCount() + " mensajes borrados correctamente.");
	        System.out.println("");
	    } else {
	        System.out.println("No se encontraron mensajes para borrar o el email no existe.");
	        System.out.println("");
	    }
	}
	
	/**
     * Inserta un nuevo mensaje en la colección MongoDB con el texto, número de "me gusta",
     * correo electrónico del usuario y ruta de la foto especificados.
     * 
     * @param texto El texto del nuevo mensaje.
     * @param numeroMeGustas El número de "me gusta" del nuevo mensaje.
     * @param email El correo electrónico del usuario que escribe el nuevo mensaje.
     * @param rutaFoto La ruta de la foto asociada al nuevo mensaje.
     */
	public void insertarMensaje(String texto, int numeroMeGustas, String email, String rutaFoto) {
		Document nuevoMensaje = new Document()
				.append("texto", texto)
				.append("numero_megustas", numeroMeGustas)
				.append("usuario", new Document()
						.append("email", email)
						.append("rutaFoto", rutaFoto));
		collection.insertOne(nuevoMensaje);
		System.out.println("Mensaje insertado con éxito");
		System.out.println("");
	}
}
