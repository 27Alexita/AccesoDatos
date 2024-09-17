package writeJAXB;

import car.Car;
import car.CarContainer;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;


/**
 * Esta clase creará un archivo XML utilizando la API JAXB (Java Architecture for XML Binding)
 * a partir de una lista de objetos 'Car'.
 * @author Sandra
 */
public class WriteJAXB {
    
    // Este método toma una lista de objetos 'Car' y una ruta de archivo 'filePath'
    // para crear un archivo XML.
    public void createXML(List<Car> coches, String filePath){
        try{
            // Configuración JAXB:
            // Se crea un contexto JAXB para la clase 'CarContainer'. Necesario para que JAXB
            // conozca la estructura de los objetos que se van a serializar.
            JAXBContext context = JAXBContext.newInstance(CarContainer.class);
            // Crea un objeto 'Marshaller' que se usará para convertir el objeto Java en XML.
            Marshaller marshaller = context.createMarshaller();
            // Configura el 'Marshaller' para formatear la salida XML de manera legible.
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            
            // Creación y configuración del contenedor:
            // Crea un nuevo objeto 'CarContainer'
            CarContainer cochesWrapper = new CarContainer();
            // Establece la lista de coches en el contenedor.
            cochesWrapper.setCochesList(coches);
            
            // Marshalling (Serialización):
            // Convierte el objeto 'CarContainer', que contiene la lista de coches, en XML
            // y lo escribe en un archivo en la ruta especificada por 'filePath'.
            marshaller.marshal(cochesWrapper, new File(filePath));

        }catch(JAXBException ex){
            Logger.getLogger(WriteJAXB.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }  
}
