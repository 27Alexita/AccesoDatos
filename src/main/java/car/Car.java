package car;

import java.io.Serializable; // Interfaz Serializable: permite que los objetos de tipo Coche se puedan convertir a un flujo de bytes para almacenarlos o transmitirlos.
import javax.xml.bind.annotation.XmlElement; // JAXB.Anotaciones de XML para la serialización de objetos en formato XML.
import javax.xml.bind.annotation.XmlRootElement; // JAXB.

/**
 *
 * @author Sandra
 */
@XmlRootElement(name = "coche") // Elemento raíz para serializar el archivo XML con JAXB
public class Car implements Serializable { //Esta clase debe ser serializable para poder leer los objetos del archivo '.dat'

    // Declaración de atributos
    private String matricula, modelo;
    private int anio, numKilometros;

    // Constructores
    public Car() {
    }

    public Car(String matricula, String modelo, int anio, int numKilometros) {
        this.matricula = matricula;
        this.modelo = modelo;
        this.anio = anio;
        this.numKilometros = numKilometros;
    }

    // Getters y Setters
    @XmlElement //JAXB
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    @XmlElement //JAXB
    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    @XmlElement //JAXB
    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    @XmlElement //JAXB
    public int getNumKilometros() {
        return numKilometros;
    }

    public void setNumKilometros(int numKilometros) {
        this.numKilometros = numKilometros;
    }

    // toString
    @Override
    public String toString() {
        return "Coche{" + "matricula=" + matricula + ", modelo=" + modelo + ", anio=" + anio + ", numKilometros=" + numKilometros + '}';
    }
}
