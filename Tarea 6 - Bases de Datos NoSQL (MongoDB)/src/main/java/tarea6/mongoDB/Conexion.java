package tarea6.mongoDB;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

/**
 * Esta clase proporciona una conexión a una base de datos MongoDB.
 * Permite establecer una conexión con la base de datos especificada
 * y obtener una colección específica de la base de datos para su uso.
 * También proporciona un método para cerrar la conexión una vez que ya no es necesaria.
 * 
 * La conexión se crea utilizando una URL de conexión estándar de MongoDB,
 * especificando el nombre de host y el puerto por defecto (localhost:27017).
 * 
 */
public class Conexion {
	
	private MongoClient mongoClient;
	private MongoDatabase database;
	
	/**
     * Constructor de la clase. Crea una nueva conexión a MongoDB
     * utilizando la URL de conexión proporcionada y selecciona la base de datos especificada.
     * 
     * @param databaseName El nombre de la base de datos a la que conectar.
     */
	public Conexion (String databaseName) {
		// Creo una nueva conexión a MongoDB
		ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");
		mongoClient = MongoClients.create(connectionString);
		
		// Selecciono la base de datos proporcionada
		database = mongoClient.getDatabase(databaseName);
	}
	
	/**
     * Obtiene una colección específica de la base de datos.
     * 
     * @param collectionName El nombre de la colección a obtener.
     * @return La colección especificada.
     */
	public MongoCollection<Document> getCollection(String collectionName) {
        return database.getCollection(collectionName);
    }
	
	/**
     * Obtiene la base de datos actualmente seleccionada.
     * 
     * @return La base de datos actualmente seleccionada.
     */
	public MongoDatabase getDatabase() {
		return database;
	}
	
	/**
     * Cierra la conexión con la base de datos.
     * Si la conexión no es nula, se cierra para liberar recursos.
     */
	public void close() {
		if(mongoClient != null) {
			mongoClient.close();
		} 
	}
}
