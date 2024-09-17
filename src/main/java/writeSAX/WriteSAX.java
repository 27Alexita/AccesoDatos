package writeSAX;

import car.Car;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;


/**
 *  Esta clase escribe un archivo XML utilizando la API de StAX
 * (Streaming API for XML), una alternativa a SAX (Simple API for XML).
 * Procesa una lista de objetos 'Car' y los escribe en un archivo XML.
 * @author Sandra
 */
public class WriteSAX {
    
    // Método que toma una lista de objetos 'Car' y una ruta de archivo 'filePath'
    // para crear un archivo XML.
    public void createXML(List<Car> coches, String filename){
        
        try{
            // Inicialización de StAX.
            // XMLOutputFactory crea una fábrica para construir escritores de flujos XML.
            XMLOutputFactory xof = XMLOutputFactory.newInstance();
            // Creo un 'XMLStreamWriter' para escribir en el archivo especificado 'filename'.
            XMLStreamWriter xsw = xof.createXMLStreamWriter(new FileOutputStream(filename));
            
            // Escritura del documento XML
            // Inicio documento. Declaración estándar XML.
            xsw.writeStartDocument("UTF-8","1.0");
            xsw.writeCharacters("\n"); // Salto de línea
            // Comienzo elemento raíz.
            xsw.writeStartElement("coches");
            xsw.writeCharacters("\n"); // Salto de línea
            
            // Se recorren todos los objetos 'Car':
            for(Car coche : coches){
                xsw.writeCharacters("  "); // Indentación
                xsw.writeStartElement("coche"); // Comienzo etiqueta coche.
                xsw.writeAttribute("matricula", coche.getMatricula()); // Atributo en la etiqueta coche.
                xsw.writeCharacters("\n"); // Salto de línea
                
                xsw.writeCharacters("    "); // Indentación
                xsw.writeStartElement("modelo"); // Comienzo etiqueta modelo.
                xsw.writeCharacters(coche.getModelo()); 
                xsw.writeEndElement(); // Fin etiqueta modelo
                xsw.writeCharacters("\n"); // Salto de línea
                
                xsw.writeCharacters("    "); // Indentación
                xsw.writeStartElement("anio"); // Comienzo etiqueta anio
                xsw.writeCharacters(String.valueOf((char) coche.getAnio()));
                xsw.writeEndElement(); // Fin etiqueta anio
                xsw.writeCharacters("\n"); // Salto de línea
                
                xsw.writeCharacters("    "); // Indentación
                xsw.writeStartElement("numeroKilometros"); // Comienzo etiqueta numeroKilometros
                xsw.writeCharacters(String.valueOf(coche.getNumKilometros()));
                xsw.writeEndElement(); // Fin etiqueta numeroKilometros
                xsw.writeCharacters("\n"); // Salto de línea
                
                xsw.writeCharacters("  "); // Indentación
                xsw.writeEndElement(); // Fin etiqueta coche
                xsw.writeCharacters("\n"); // Salto de línea
            }
            
            xsw.writeEndElement(); // Fin etiqueta coches
            xsw.writeCharacters("\n"); // Salto de línea
            xsw.writeEndDocument(); // Fin del documento XML
            
            xsw.flush(); // Vacía el búffer
            xsw.close(); // Cierra el escritor
            
        } catch (XMLStreamException ex) {
            Logger.getLogger(WriteSAX.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WriteSAX.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
