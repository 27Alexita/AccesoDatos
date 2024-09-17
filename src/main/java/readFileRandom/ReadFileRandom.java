package readFileRandom;

import car.Car;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sandra
 */
public class ReadFileRandom {
    
    // Método que toma como argumento 'String filename' y retorna un 'ArrayList<Car>'.
    // Lee datos del archivo y los convierte en una lista de objetos 'Car'.
    public ArrayList<Car> readFile (String filename){
        // Creo la lista vacía 'coches' para almacenar objetos de tipo 'Car'.
        ArrayList<Car> coches = new ArrayList<>();
        // En este bloque 'try-with-resources' abro el archivo 'filename' en modo lectura
        // con la clase RandomAccessFile que permite leer datos de cualquier parte del archivo
        // sin seguir un orden secuencial.
        try(RandomAccessFile raf = new RandomAccessFile (filename, "r")){
            // Leo el archivo. El bucle se ejecutará mientras el puntero del archivo 'raf.getFilePointer()'
            // sea menor que la longitud total del archivo 'raf.length()'.
            while (raf.getFilePointer() < raf.length() ){
                // Suponiendo que el formato de escritura es UTF para String e int para enteros.
                // Lectura de datos del archivo.
                String matricula = raf.readUTF().trim();
                String modelo = raf.readUTF().trim();
                int anio = raf.readInt();
                int numKilometros = raf.readInt();
                // Crea objetos 'Car' con los datos leídos anteriormente y los añade a la lista 'coches'.
                coches.add(new Car(matricula,modelo,anio,numKilometros));
            }
        }catch(IOException ex){
          Logger.getLogger(ReadFileRandom.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return coches;
    }
}

/*
Si la escritura fuera secuencial:
import java.io.FileInputStream;
import java.io.ObjectInputStream;

    @SuppressWarnings("unchecked")
    public ArrayList<Coche> leeArchivo (String filename){
        ArrayList<Coche> coches = null;
        try(ObjectInputStream ois = new ObjectInputStream ( new FileInputStream (filename))){
            coches = (ArrayList<Coche>) ois.readObject();
        }catch(Exception e){
            e.printStackTrace();
        }
        return coches;
    }
*/
