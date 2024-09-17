package accesodatos_tarea02;

//import writeSAX.WriteSAX;
//import writeJAXB.WriteJAXB;
//import writeJDOM.WriteJDOM;
//import readFileXmlJDOM.ReadFileXmlJDOM;
//import readFileXmlSAX.ReadFileXmlSAX;
//import readFileXmlJAXB.ReadFileXmlJAXB;

import car.Car;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import readFileRandom.ReadFileRandom;
import readFileXmlDOM.ReadFileXmlDOM;
import writeDOM.WriteDOM;


/**
 *
 * @author Sandra
 */
public class AccesoDatos_Tarea02 {

    public static void main(String[] args) {

        // Entrada de datos por teclado:
        Scanner input = new Scanner(System.in);
        // Declaración de variables:
        ArrayList<Car> coches = new ArrayList<>(); // Declaración del ArrayList
        int opcion;
        boolean salida = false;

        // Añado objetos de la clase coche:
        coches.add(new Car("5965HPH", "Seat Leon", 2013, 175000));
        coches.add(new Car("0173CPV", "Honda Civic", 2004, 220000));
        coches.add(new Car("9444CDP", "Peugeot 308", 2018, 150000));
        coches.add(new Car("8756MGN", "Volkswagen Scirocco", 2009, 240000));
        coches.add(new Car("6574FGH", "Ford Focus", 2006, 300000));

        do {
            menu();
            opcion = input.nextInt();
            input.nextLine();
            switch (opcion) {

                case 1 -> {
                    // Creación del archivo de datos de forma aleatoria 'Coches.dat'
                    try {
                        // Creo un archivo de acceso aleatorio para escribir los objetos de tipo coche.
                        try (RandomAccessFile file = new RandomAccessFile("Coches.dat", "rw")) {
                            for (Car coche : coches) {
                                file.writeUTF(String.format("%-10s", coche.getMatricula()));
                                file.writeUTF(String.format("%-20s", coche.getModelo()));
                                file.writeInt(coche.getAnio());
                                file.writeInt(coche.getNumKilometros());
                            }
                        }
                        System.out.println("Se ha creado el archivo correctamente.\n");
                    } catch (IOException e) {
                        System.out.println("Error al crear el archivo '.dat'");
                    }

                    break;
                }

                case 2 -> {
                    // Leo el archivo 'Coches.dat' de forma aleatoria.
                    ReadFileRandom lectura = new ReadFileRandom();
                    coches = lectura.readFile("Coches.dat");

                    // Creo el archivo XML con DOM:
                    if (coches != null) {
                        // Instancia del ejemplo con DOM:
                        WriteDOM xmlDOM = new WriteDOM();
                        try {
                            xmlDOM.createXML(coches, "CochesDOM.xml");
                            System.out.println("Se han escrito los datos correctamente.");
                        } catch (Exception ex) {
                            Logger.getLogger(WriteDOM.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                        }
                    } else {
                        System.out.println("No se han podido leer los datos desde Coches.dat");
                    }
                    break;
                }

                case 3 -> {
                    // Leo el archivo con DOM:
                    ReadFileXmlDOM xmlReader = new ReadFileXmlDOM();
                    xmlReader.readAndPrintXML("Libros.xml");
                    break;
                }
                
                case 4 ->{
                    // Salida del programa.
                    salida = true;
                    System.out.println("Progama finalizado.");
                }

                default -> {
                    System.out.println("Opción no reconocida. Seleccione del 1 al 4 en orden.");
                }
            }
        } while (!salida);
    }

    // Menú que muestra las opciones a escoger al ejecutar el programa:
    public static void menu() {
        System.out.println("\nSeleccione las opciones del 1 al 4 en orden, por favor.\n");
        System.out.println("1. Creación del archivo 'Coches.dat'");
        System.out.println("2. Lectura del archivo 'Coches.dat' y escritura en el archivo 'Coches.xml'");
        System.out.println("3. Lectura del archivo 'Libros.xml'");
        System.out.println("4. Salir.");
        System.out.println("\n");
    }
}

// Creación del archivo de forma secuencial:
/*
        Se importan:
        import java.io.FileOutputStream;
        import java.io.ObjectOutputStream;
        
        try(ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream ("Coches.dat"))){
            oos.writeObject(coches);
        }catch(Exception e){
            e.printStackTrace();
        }
 */
// Instancia del ejemplo con SAX:
// WriteDOM xmlSAX = new WriteSAX();
// xmlSAX.crearXML(coches, "cochesSAX.xml");
// Instancia del ejemplo con JDOM:
// WriteJDOM xmlJDOM = new WriteJDOM();
// xmlJDOM.crearXML(coches, "cochesJDOM.xml");
// Instancia del ejemplo con JAXB:
// WriteJAXB xmlJAXB = new WriteJAXB();
// xmlJAXB.createXML(coches, "cochesJAXB.xml");
// Leo el archivo con DOM:
// ReadFileXmlDOM xmlReader = new ReadFileXmlDOM();
// xmlReader.readAndPrintXML("Libros.xml");
// Leo el archivo con JDOM:
//ReadFileXmlJDOM xmlReader = new ReadFileXmlJDOM();
//xmlReader.readAndPrintXML("Libros.xml");
