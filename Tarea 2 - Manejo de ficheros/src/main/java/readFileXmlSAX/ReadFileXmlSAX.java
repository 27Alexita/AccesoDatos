package readFileXmlSAX;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *  Lectura con SAX. Lee y procesa archivos XML utilizando SAX (Simple API for XML).
 * @author Sandra
 */
public class ReadFileXmlSAX {

    // Método que toma una cadena de texto 'filePath' que es la ruta del archivo XML que se va a leer.
    public void readAndPrintXML(String filePath) {
        try {
            // Se instancia 'SAXParserFactory' para crear 'SAXParser'
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser saxParser = spf.newSAXParser();

            // Se crea una instancia anónima de 'DefaultHandler', que es una clase
            // de conveniencia que implementa la interfaz 'org.xml.sax.ContentHandler'.
            // Dentro se sobreescriben los métodos 'startElement' y 'characters' para
            // definir qué hacer cuando se inician elementos específicos y cuando se
            // encuentran caracteres dentro de un elemento.
            DefaultHandler handler = new DefaultHandler() {
                // Declaración de variables booleanas para controlar si estoy dentro de un 
                // elemento en particular u otro.
                boolean titulo, autor, nombre, apellido, editorial, precio = false;

                // Se establecen las variables booleanas en 'true' si el nombre ('qName') coincide
                // con uno de los elementos deseados. Esto indica que los siguientes caracteres 
                // pertenecen a ese elemento.
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

                    if (qName.equalsIgnoreCase("titulo")) {
                        titulo = true;
                    } else if (qName.equalsIgnoreCase("autor")) {
                        autor = true;
                    } else if (qName.equalsIgnoreCase("nombre")) {
                        nombre = true;
                    } else if (qName.equalsIgnoreCase("apellido")) {
                        apellido = true;
                    } else if (qName.equalsIgnoreCase("editorial")) {
                        editorial = true;
                    } else if (qName.equalsIgnoreCase("precio")) {
                        precio = true;
                    }
                }
                
                // Se verifica cuál de las variables booleanas está en 'true' y se imprime
                // el contenido de texto correspondiente para ese elemento. Luego se establece
                // la variable booleana en 'false', para no volver a imprimir el mismo 
                // elemento hasta que se encuentre el inicio de un nuevo elemento.
                public void characters(char ch[], int start, int length) {

                    if (titulo) {
                        System.out.println("Titulo: " + new String(ch, start, length));
                        titulo = false;
                    } else if (autor) {
                        System.out.println("Autor: " + new String(ch, start, length));
                        autor = false;
                    } else if (nombre) {
                        System.out.println("Nombre: " + new String(ch, start, length));
                        nombre = false;
                    } else if (apellido) {
                        System.out.println("Apellidos: " + new String(ch, start, length));
                        apellido = false;
                    } else if (editorial) {
                        System.out.println("Editorial: " + new String(ch, start, length));
                        editorial = false;
                    } else if (precio) {
                        System.out.println("Precio: " + new String(ch, start, length));
                        precio = false;
                    }
                }
            };
            
            // Parseo del archivo XML: 
            // Se llama al método parse, pasando la ruta del archivo y el 'handler'
            // definido para iniciar el procesamiento del XML.
            saxParser.parse(filePath, handler);
        } catch (IOException ex) {
            Logger.getLogger(ReadFileXmlSAX.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (SAXException ex) {
            Logger.getLogger(ReadFileXmlSAX.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (ParserConfigurationException ex){
            Logger.getLogger(ReadFileXmlSAX.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
