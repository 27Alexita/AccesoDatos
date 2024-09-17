package readFileXmlJDOM;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;


/**
 *  Lectura con JDOM. Mucho más intuitivo y conveniente en comparación
 *  con las APIs estándar de Java como DOM.
 * @author Sandra
 */
public class ReadFileXmlJDOM {
    
    // Método que toma un String que representa la ruta al archivo XML que se va a procesar.
    public void readAndPrintXML (String filePath){
        // Creo un 'SAXBuilder' que pueda construir un documento JDOM a partir de un archivo XML.
        SAXBuilder saxBuilder = new SAXBuilder();
        
        try{
            // Construyo el documento JDOM con SAXBuilder al parsear el archivo XML proporcionado
            Document document = saxBuilder.build(new File(filePath));
            // Obtengo el elemento raíz del documento XML.
            Element rootElement = document.getRootElement();
            // Obtengo una lista de todos los elementos 'libro'.
            List<Element> libros = rootElement.getChildren("libro");
            
            // Itero sobre cada elemento 'libro' en la lista:
            // - Se extraen los valores de los atributos y textos de elementos hijos como:
            // 'año', 'titulo', 'autor', 'editorial' y 'precio'.
            // - Se imprimen los valores obtenidos en la consola con 'System.out.println'.
            for(Element libro : libros){
                String anio = libro.getAttributeValue("año");
                String titulo = libro.getChildText("titulo");
                List<Element> autores = libro.getChildren("autor");
                
                System.out.println("Año: " + anio);
                System.out.println("Título: " + titulo);
                // Itero sobre los subelementos del elemento 'autor'.
                for (Element autor : autores){
                    String apellido = autor.getChildText("apellido");
                    String nombre = autor.getChildText("nombre");
                    System.out.println("Autor: " + nombre + " " + apellido);
                }
                
                String editorial = libro.getChildText("editorial");
                String precio = libro.getChildText("precio");
                System.out.println("Editorial: " + editorial);
                System.out.println("Precio: " + precio);
                System.out.println("________________________________");
            }    
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(ReadFileXmlJDOM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
