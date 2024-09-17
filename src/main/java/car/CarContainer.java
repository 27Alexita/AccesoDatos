package car;

import java.io.Serializable; // Para poder serializar objetos de esta clase.
import java.util.List; // Para trabajar con listas.
import javax.xml.bind.annotation.XmlRootElement; // Anotaciones necesarias para la serialización de objetos en formato XML.

/**
 *  Clase contenedora para crear una lista de la clase 'Car':
 * @author Sandra
 */

@XmlRootElement(name = "coches") // Elemento raíz para serializar la clase en XML.
public class CarContainer implements Serializable{
    
    // Declaración de la variable contenedora. Atributo privado que declara una lista de objetos 'Car'.
    private List<Car> cochesList;
    
    // Constructores. Toda clase contenedora necesita un constructor vacío.
    public CarContainer() {
    }
    
    // Getter and setter
    public List<Car> getCochesList() {
        return cochesList;
    }

    public void setCochesList(List<Car> cochesList) {
        this.cochesList = cochesList;
    }
}
