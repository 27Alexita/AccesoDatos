package principal;

import interfaces.ArticuloInterface;
import interfaces.UsuarioInterface;
import interfaces.VentaInterface;
import java.util.Scanner;

/**
 *
 * @author Sandra
 */
public class MenuHandler {

    // Declaración de variables
    private Scanner scanner;
    private ArticuloInterface articuloDAO;
    private UsuarioInterface usuarioDAO;
    private VentaInterface ventaDAO;

    // Constructor
    public MenuHandler(UsuarioInterface usuarioDAO, ArticuloInterface articuloDAO, VentaInterface ventaDAO, Scanner scanner) {
        this.usuarioDAO = usuarioDAO;
        this.articuloDAO = articuloDAO;
        this.ventaDAO = ventaDAO;
        this.scanner = scanner;
    }

    // Método para cerrar el scanner
    public void cerrar() {
        scanner.close();
    }

    // Método para mostrar el menú  principal para interactuar con el usuario
    public void menuPrincipal() {

        boolean continuar = true;
        while (continuar) {
            System.out.println("> SELECCIONE UNA OPCIÓN: ");
            System.out.println("1. Operaciones de usuario.");
            System.out.println("2. Operaciones de artículos.");
            System.out.println("3. Operaciones de venta.");
            System.out.println("4. Salir.");
            System.out.println("");

            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    menuUsuario();
                    break;
                case 2:
                    menuArticulos();
                    break;
                case 3:
                    menuVentas();
                    break;
                case 4:
                    continuar = false;
                    System.out.println("Programa cerrado.");
                    break;
                default:
                    System.out.println("Opción no reconocida, inténtelo de nuevo.");
            }
        }
    }

    // Método que muestra las opciones de menú con la tabla usuarios
    public void menuUsuario() {

        boolean volver = true;
        while (volver) {
            System.out.println("> OPERACIONES DE USUARIO: ");
            System.out.println("1. Insertar un usuario.");
            System.out.println("2. Listar todos los usuarios.");
            System.out.println("3. Datos de un usuario según su email.");
            System.out.println("4. Borrar un usuario según su ID.");
            System.out.println("5. Borrar un usuario según su email.");
            System.out.println("6. Volver al menú principal.");
            System.out.println("");

            int subopcion = Integer.parseInt(scanner.nextLine());

            switch (subopcion) {
                case 1:
                    UserActions.insertarUsuario(usuarioDAO);
                    break;
                case 2:
                    UserActions.mostrarTodosLosUsuarios(usuarioDAO);
                    break;
                case 3:
                    UserActions.verDatosUsuarioPorEmail(usuarioDAO);
                    break;
                case 4:
                    System.out.println("Ingrese el ID del usuario a borrar: ");
                    int idUsuario = Integer.parseInt(scanner.nextLine());
                    UserActions.borrarUsuarioPorId(usuarioDAO, articuloDAO, idUsuario);
                    break;
                case 5:
                    UserActions.borrarUsuarioPorEmail(usuarioDAO);
                    break;
                case 6:
                    volver = false;
                    break;
                default:
                    System.out.println("Opción no reconocida. Inténtelo de nuevo.");
                    break;
            }
        }
    }

    // método que muestra las opciones de menú con la tabla artículos
    public void menuArticulos() {

        boolean volver = true;
        while (volver) {
            System.out.println("> OPERACIONES DE ARTÍCULOS: ");
            System.out.println("1. Insertar un artículo.");
            System.out.println("2. Listar todos los artículos.");
            System.out.println("3. Borrar un artículo según su ID. ");
            System.out.println("4. Volver al menú principal.");
            System.out.println("");

            int subopcion = scanner.nextInt();
            scanner.nextLine();

            switch (subopcion) {
                case 1:
                    UserActions.insertarArticulo(articuloDAO, usuarioDAO);
                    break;
                case 2:
                    UserActions.mostrarTodosLosArticulos(articuloDAO);
                    break;
                case 3:
                    System.out.println("Ingrese el ID del articulo a borrar: ");
                    int idArticulo = Integer.parseInt(scanner.nextLine());
                    UserActions.borrarArticuloPorId(articuloDAO, idArticulo);
                    break;
                case 4:
                    volver = false;
                    break;
                default:
                    System.out.println("Opción no reconocida. Inténtelo de nuevo. ");
                    break;
            }
        }
    }

    // Método que muestra las opciones de menú con la tabla ventas
    public void menuVentas() {

        boolean volver = true;
        while (volver) {
            System.out.println("> OPERACIONES DE VENTAS: ");
            System.out.println("1. Insertar venta.");
            System.out.println("2. Listar todas las ventas.");
            System.out.println("3. Borrar una venta según su ID.");
            System.out.println("4. Volver al menú principal.");
            System.out.println("");

            int subopcion = scanner.nextInt();
            scanner.nextLine();

            switch (subopcion) {
                case 1:
                    UserActions.insertarVenta(ventaDAO, articuloDAO, usuarioDAO);
                    break;
                case 2:
                    UserActions.mostrarTodasLasVentas(ventaDAO);
                    break;
                case 3:
                    UserActions.borrarVentaPorId(ventaDAO);
                case 4:
                    volver = false;
                    break;
                default:
                    System.out.println("Opción no reconocida. Inténtelo de nuevo. ");
                    break;
            }
        }
    }

    // Método para manejar excepciones
    public static void controlarExcepciones(Exception ex, String mensaje) {
        System.out.println(mensaje + ": " + ex.getMessage());
        //Logger.getLogger(Utilidades.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
    }
}
