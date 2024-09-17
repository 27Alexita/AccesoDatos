package principal;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import interfaces.ArticuloInterface;
import interfaces.UsuarioInterface;
import interfaces.VentaInterface;
import dao.ArticuloDAO;
import dao.UsuarioDAO;
import dao.VentaDAO;
import java.util.Scanner;

/**
 * CLASE PRINCIPAL que implementa la funcionalidad del programa.
 * @author Sandra
 */
public class MainApp {

    public static void main(String[] args) {
        
        // Establezco la conexión con la base de datos
        try(Connection connection = DatabaseConnection.getConnection()){
        
            //Instancias de las clases DAO
            UsuarioInterface usuarioDAO = new UsuarioDAO(connection);
            ArticuloInterface articuloDAO = new ArticuloDAO(connection);
            VentaInterface ventaDAO = new VentaDAO(connection);
            
            // Entrada de datos
            Scanner scanner = new Scanner (System.in);
            
            // Instancia de la clase que maneja los menús y pasa las instancias DAO
            MenuHandler menuHandler = new MenuHandler(usuarioDAO, articuloDAO, ventaDAO, scanner);
            
            // Llamo al menú principal
            menuHandler.menuPrincipal();
            
            // Cierro el scanner
            menuHandler.cerrar();
        } catch (SQLException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
