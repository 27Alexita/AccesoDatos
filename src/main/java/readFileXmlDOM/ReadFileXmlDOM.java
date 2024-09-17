package readFileXmlDOM;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *  Lectura con DOM (Document Object Model), adecuada para archivos XML de tamaño moderado y
 * que permite una manipulación fácil del árbol de nodos XML.
 * @author Sandra
 */
public class ReadFileXmlDOM {
    
    // Método que toma el nombre de un archivo XML ('filename') y lo procesa.
    public void readAndPrintXML(String filename){
    
        try{
            // Creo una nueva instancia de 'DocumentBuilderFactory'
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            // Creo 'DocumentBuilder' para analizar documentos XML.
            DocumentBuilder builder = dbf.newDocumentBuilder();
            // Parseo el documento XML y construyo un árbol DOM.
            Document document = builder.parse(new File(filename));
            
            // Normalizo el documento XML, lo que implica que se combinan nodos de
            // texto adyacentes y elimina nodos de texto vacíos.
            document.getDocumentElement().normalize();
            
            // Para leer elementos específicos, por ejemplo, 'libro'.
            // Obtiene una lista de todos elementos '<libro>' en el documento XML.
            NodeList nodeList = document.getElementsByTagName("libro");
            
            // Itero sobre todos los nodos en 'nodeList'
            for (int i = 0; i < nodeList.getLength(); i++ ){
                Node node = nodeList.item(i);
                
                // Me aseguro de que el nodo es un elemento (y no un nodo de texto, por ejemplo).
                if (node.getNodeType() == Node.ELEMENT_NODE){
                    // Imprimo información del nodo con el método que he creado para imprimirloa.
                    printNodeInfo(node);
                }
            }
            
        }catch(DOMException ex){
            Logger.getLogger(ReadFileXmlDOM.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(ReadFileXmlDOM.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (SAXException ex) {
            Logger.getLogger(ReadFileXmlDOM.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (IOException ex) {
            Logger.getLogger(ReadFileXmlDOM.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
    // Método que toma un nodo (en este caso, elemento 'libro') y procesa sus nodos 'hijos'
    private void printNodeInfo(Node node){
        // Suponiendo que cada libro tiene elementos hijos como 'titulo', 'autor', etc
        // Se obtienen los nodos hijos:
        NodeList childNodes = node.getChildNodes();
        // Itero sobre los nodos hijos.
        for (int j = 0; j < childNodes.getLength(); j++){
            Node childNode = childNodes.item(j);
            
            // Me aseguro de que el nodo hijo es un elemento y no un nodo de texto con espacios en blanco.
            if (childNode.getNodeType() == Node.ELEMENT_NODE){
                System.out.println(childNode.getNodeName() + ": " + childNode.getTextContent());
            }
        }
    }
}
