package writeDOM;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import car.Car;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.DOMException;


/**
 *  Esta clase creará un archivo XML a partir de una lista de objetos 'Car'.
 * @author Sandra
 */
public class WriteDOM{
    
    // Método que estructura el documento XML a partir de una lista de objetos 'Car'
    // y de una ruta de archivo especificada.
    public void createXML(ArrayList<Car> coches, String filePath){
        try{
            // Creo una nueva instancia de 'DocumentBuilder' a través de
            //  'DocumentBuilderFactory', para crear el documento XML ('Document').
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            Document doc = builder.newDocument();
            
            // Elemento raíz. Crear y añadir al documento utilizando los métodos
            // 'createElement' y 'appendChild'.
            Element rootElement = doc.createElement("Coches");
            doc.appendChild(rootElement);
            
            // Itero a través del 'ArrayList' y, por cada objeto, creo elementos
            // y atributos añadiéndolos al documento 'Document'
            for (Car coche : coches){
                Element cocheElement = doc.createElement("Coche");
                
                Element matricula = doc.createElement("Matricula");
                matricula.appendChild(doc.createTextNode(coche.getMatricula()));
                cocheElement.appendChild(matricula);
                
                Element modelo = doc.createElement("Modelo");
                modelo.appendChild(doc.createTextNode(coche.getModelo()));
                cocheElement.appendChild(modelo);
                
                Element anio = doc.createElement("Anio");
                anio.appendChild(doc.createTextNode(String.valueOf(coche.getAnio())));
                cocheElement.appendChild(anio);
                
                Element numKilometros = doc.createElement("NumeroKms");
                numKilometros.appendChild(doc.createTextNode(String.valueOf(coche.getNumKilometros())));
                cocheElement.appendChild(numKilometros);
                
                rootElement.appendChild(cocheElement);
            }
            // Escribo 'Document' en un archivo XML.
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(doc);
            
            // El resultado se escribe en un archivo llamado 'CochesDOM.xml'
            StreamResult result = new StreamResult(new File(filePath));
            
            // Transforma el DOM a XML y lo escribe al archivo.
            transformer.transform(source, result);
            
        }catch(DOMException ex){
            Logger.getLogger(WriteDOM.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (TransformerException ex) {
            Logger.getLogger(WriteDOM.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(WriteDOM.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
