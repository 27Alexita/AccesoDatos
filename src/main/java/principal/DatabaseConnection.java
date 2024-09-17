package principal;

import java.sql.*;
import java.util.Scanner;

/**
 * CLASE que implementa la conexión de la base de datos y el menú que se muestra
 * en pantalla.
 *
 * @author Sandra
 */
public class DatabaseConnection {

    // Variables para establecer la conexión con la base de datos de MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/segunda_mano";
    private static final String USER = "root";
    private static final String PASSWORD = "AccesoDatos2023";

    // Método para establecer la conexión con la base de datos
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
