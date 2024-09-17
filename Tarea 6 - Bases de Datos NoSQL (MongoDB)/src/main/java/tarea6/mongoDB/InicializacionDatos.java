package tarea6.mongoDB;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.Arrays;
import java.util.List;

/**
 * Esta clase proporciona métodos para inicializar datos en una colección MongoDB.
 * Permite verificar si la colección ya contiene documentos y, si está vacía,
 * inserta datos de semilla en la colección.
 * 
 * Los datos de semilla consisten en una lista de documentos, cada uno con un texto,
 * número de "me gusta" e información de usuario (email y ruta de foto).
 */
public class InicializacionDatos {
	
	private MongoCollection<Document> collection; // Colección MongoDB para inicializar
	
	/**
     * Constructor de la clase. Inicializa la instancia con la colección especificada.
     * 
     * @param collection La colección MongoDB en la que inicializar los datos.
     */
	public InicializacionDatos(MongoCollection<Document> collection) {
		this.collection = collection;
	}

	/**
     * Inserta datos de semilla en la colección MongoDB, si la colección está vacía.
     * Los datos de semilla consisten en una lista de documentos con texto, número de "me gusta",
     * y información de usuario (email y ruta de foto).
     * 
     * Si la colección ya contiene documentos, no se insertarán datos de semilla.
     */
	public void seedData() {
	    // Verifica si la colección ya tiene documentos.
	    if (collection.countDocuments() == 0) {
	        // Documentos que se insertarán.
	        List<Document> documentos = List.of(
	            new Document("texto", "A ver si hoy sale el sol")
	                .append("numero_megustas", 5)
	                .append("usuario", new Document("email", "pepe@gmail.com")
	                        .append("rutaFoto", "34deg48d.jpg")),
	            new Document("texto", "Parece que va a estar nublado todo el día")
	                .append("numero_megustas", 0)
	                .append("usuario", new Document("email", "dolores@gmail.com")
	                        .append("rutaFoto", "45345oj34.jpg")),
	            new Document("texto", "Pues menos mal que estamos en verano")
	                .append("numero_megustas", 57)
	                .append("usuario", new Document("email", "pepe@gmail.com")
	                        .append("rutaFoto", "34deg48d.jpg"))
	        );
	        
	        // Inserta los documentos en la colección.
	        collection.insertMany(documentos);
	        System.out.println("Datos de semilla insertados correctamente.");
	        System.out.println("");
	    } else {
	        System.out.println("La colección ya contiene datos.");
	        System.out.println("");
	    }
	}
}
