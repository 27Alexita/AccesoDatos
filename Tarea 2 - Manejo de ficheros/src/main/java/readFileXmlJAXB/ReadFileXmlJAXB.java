package readFileXmlJAXB;

import car.CarContainer;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * Lectura con JAXB (Java Architecture for XML Binding). Convierte datos XML
 * en objetos Java y viceversa. Es especialmente útil para trabajar con archivos
 * XML de mayor tamaño y complejidad, ya que automatiza gran parte del proceso.
 * @author Sandra
 */
public class ReadFileXmlJAXB {
    
    public void readAndPrintXML (String filename){
        
        try{
            // Creo una instancia de 'JAXBContext', pasando la clase raíz.
            // Punto de entrada principal para JAXB que proporciona una abstracción
            // para manejar la conversión entre clases Java y documentos XML.
            JAXBContext context = JAXBContext.newInstance(CarContainer.class); // Válido para leer sobre los archivos de la clase Coches, pero no sobre el archivo Libros.xml
            // Creo un 'Unmarshaller'.
            // Es un objeto que se utiliza para convertir datos XML en objetos Java.
            // Se crea a partir del 'JAXBContext'
            Unmarshaller unmarshaller = context.createUnmarshaller();
            // Leo el archivo XML y lo convierto en objetos Java.
            // Le pasaré un 'File', 'InputStream' o 'Reader' que represente el archivo XML.
            CarContainer carContainer = (CarContainer) unmarshaller.unmarshal(new File("Coches.xml"));
        }catch(JAXBException ex){
            Logger.getLogger(ReadFileXmlJAXB.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
