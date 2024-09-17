package writeJDOM;

import car.Car;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * Esta clase está diseñada para crear archivos XML a partir de una lista 
 * de objetos 'Car', utilizando la biblioteca JDOM.
 * @author Sandra
 */
public class WriteJDOM {
    
    // Método que toma una lista de objetos 'Car' y una ruta de archivo 'filePath'
    // para crear un archivo XML.
    public void createXML(ArrayList<Car> coches, String filePath){
        try{
            // Creo el elemento raíz.
            Element rootElement = new Element("Coches");
            // Creo un nuevo documento JDOM utilizando el elemento raíz.
            Document doc = new Document(rootElement);
            
            // Construcción del archivo XML.
            // Itero sobre la lista 'coches', crea un elemento <coche>, para 
            // cada objeto 'Car' y subelementos para cada propiedad 
            //('matricula','modelo','anio','numKilometros'), añadiendo el 
            // contenido de cada propiedad de 'Car'.
            // 'rootElement.addContent(cocheElement);' añade cada elemento 
            // 'cocheElement' al elemento raíz.
            for (Car coche : coches) {
                Element cocheElement = new Element("Coche");
                Element matricula = new Element("Matricula").setText(coche.getMatricula());
                cocheElement.addContent(matricula);
                Element modelo = new Element("Modelo").setText(coche.getModelo());
                cocheElement.addContent(modelo);
                Element anio = new Element("Anio").setText(String.valueOf(coche.getAnio()));
                cocheElement.addContent(anio);
                Element numKilometros = new Element("NumeroKms").setText(String.valueOf(coche.getNumKilometros()));
                cocheElement.addContent(numKilometros);
                rootElement.addContent(cocheElement);
            }

            // Escritura del archivo XML.
            // Creo un objeto 'XMLOutputter' para escribir el documento XML, 
            // configurando una salida formateada.
            XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
            // Escribe el documento XML en el archivo especificado por 'filePath'.
            xmlOutput.output(doc, new FileOutputStream(filePath));
        }catch(IOException ex){
            Logger.getLogger(WriteJDOM.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
