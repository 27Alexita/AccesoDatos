package principal;

import interfaces.ArticuloInterface;
import interfaces.UsuarioInterface;
import interfaces.VentaInterface;
import entidades.Articulo;
import entidades.Usuario;
import entidades.Venta;
import java.util.List;
import java.util.Scanner;

/**
 * CLASE que centraliza la lógica de interacción con la base de datos.
 * Implementa los métodos que interactúan con el usuario para obtener la
 * información necesaria y después llamar a los métodos DAO.
 *
 * @author Sandra
 */
public class UserActions {

    // Declaración de variables
    private UsuarioInterface usuarioDAO;
    private ArticuloInterface articuloDAO;
    private VentaInterface ventaDAO;
    private static final Scanner scanner = new Scanner(System.in);

    // Constructor
    public UserActions(UsuarioInterface usuarioDAO, ArticuloInterface articuloDAO, VentaInterface ventaDAO) {
        this.usuarioDAO = usuarioDAO;
        this.articuloDAO = articuloDAO;
        this.ventaDAO = ventaDAO;
    }

    // Método para mostrar todos los artículos:
    public static void mostrarTodosLosArticulos(ArticuloInterface articuloDAO) {

        List<Articulo> articulos = articuloDAO.obtenerTodosLosArticulos();
        if (articulos.isEmpty()) {
            System.out.println("No hay artículos para mostrar.");
        } else {
            for (Articulo articulo : articulos) {
                String vendido = articulo.isVendido() ? "Vendido" : "Disponible";
                System.out.println("ID: " + articulo.getId() + "\n"
                        + "Descripcion: " + articulo.getDescripcion() + "\n"
                        + "Fecha: " + articulo.getFecha() + "\n"
                        + "Precio: " + articulo.getPrecio() + "\n"
                        + "Vendedor: " + articulo.getIdVendedor() + "\n"
                        + "Estado: " + vendido + "\n");
            }
        }
    }

    // Método que interactua con el usuario para insertar usuarios
    public static void insertarUsuario(UsuarioInterface usuarioDAO) {

        boolean usuarioInsertado = false;
        do {
            System.out.println("Ingrese el email del usuario: ");
            String email = scanner.nextLine();

            // Compruebo si ya existe un usuario con este email
            if (usuarioDAO.existeUsuarioPorEmail(email)) {
                System.out.println("Ya existe un usuario con este email.");
                continue; // Continúa al próximo ciclo del bucle
            }

            System.out.println("Ingrese el nombre del usuario: ");
            String nombre = scanner.nextLine();

            Usuario usuario = new Usuario();
            usuario.setEmail(email);
            usuario.setNombre(nombre);

            try {
                usuarioDAO.insertarUsuario(usuario);
                System.out.println("Usuario insertado correctamente.");
                usuarioInsertado = true; // Establezco la variable en true para terminar el bucle
            } catch (Exception e) {
                System.out.println("No se pudo insertar el usuario." + e.getMessage());
                break; // Salgo del bucle si ocurre un error irrecuperable
            }
        } while (!usuarioInsertado);
    }

    // Método que interactua con el usuario para insertar artículos
    public static void insertarArticulo(ArticuloInterface articuloDAO, UsuarioInterface usuarioDAO) {

        System.out.println("Introduzca la descripción del artículo: ");
        String descripcion = scanner.nextLine();

        System.out.println("Introduzca el precio del artículo: ");
        float precio = Float.parseFloat(scanner.nextLine());

        int idVendedor;
        Usuario vendedor = null;
        do {
            List<Usuario> usuarios = usuarioDAO.mostrarTodosLosUsuarios();
            if (usuarios.isEmpty()) {
                System.out.println("No hay vendedores registrados.");
                return; // No continuar si no hay vendedores
            }
            usuarios.forEach(usuario -> System.out.println(usuario.getId() + ": " + usuario.getNombre()));

            System.out.println("Seleccione el ID del vendedor del artículo: ");
            idVendedor = Integer.parseInt(scanner.nextLine());

            vendedor = usuarioDAO.obtenerUsuarioID(idVendedor);
            if (vendedor == null) {
                System.out.println("No existe un vendedor con el ID " + idVendedor + ". Por favor, inténtelo de nuevo.");
            }
        } while (vendedor == null);
        Articulo articulo = new Articulo();
        articulo.setDescripcion(descripcion);
        articulo.setPrecio(precio);
        articulo.setIdVendedor(idVendedor);
        articulo.setFecha(new java.sql.Timestamp(System.currentTimeMillis())); // Asumiendo que la fecha se establece al momento de la inserción

        try {
            articuloDAO.insertarArticulo(articulo);
            System.out.println("Artículo insertado con éxito.");
        } catch (Exception e) {
            System.out.println("No se pudo insertar el artículo. " + e.getMessage());
        }
    }

    // Método que interactua con el usuario para insertar ventas
    public static void insertarVenta(VentaInterface ventaDAO, ArticuloInterface articuloDAO, UsuarioInterface usuarioDAO) {

        int idArticulo = 0;
        Articulo articulo = null;
        while (articulo == null) {
            // Muestro y selecciono el artículo
            List<Articulo> articulos = articuloDAO.obtenerTodosLosArticulos();
            // Compruebo si la lista de artículos está vacía
            if (articulos.isEmpty()) {
                System.out.println("No hay artículos disponibles para la venta.");
                return; // Sale del método si no hay artículos.
            }
            articulos.forEach(a -> System.out.println(a.getId() + ": " + a.getDescripcion()));

            System.out.println("Seleccione el ID del artículo vendido: ");
            idArticulo = Integer.parseInt(scanner.nextLine());
            articulo = articuloDAO.obtenerArticuloPorId(idArticulo);

            if (articulo == null) {
                System.out.println("No existe un artículo con el ID " + idArticulo + ". Por favor, inténtelo de nuevo.");
            }
        }

        int idComprador = 0;
        Usuario comprador = null;
        while (comprador == null) {
            List<Usuario> usuarios = usuarioDAO.mostrarTodosLosUsuarios();
            if (usuarios.isEmpty()) {
                System.out.println("No hay usuarios registrados como compradores.");
                return; // Sale del método si no hay usuarios
            }
            usuarios.forEach(u -> System.out.println(u.getId() + ": " + u.getNombre()));

            System.out.println("Seleccione el ID del comprador: ");
            idComprador = Integer.parseInt(scanner.nextLine());
            comprador = usuarioDAO.obtenerUsuarioID(idComprador);

            if (comprador == null) {
                System.out.println("No existe usuario con el ID " + idComprador + ". Por favor, inténtelo de nuevo.");
            }
        }

        Venta venta = new Venta();
        venta.setIdArticulo(idArticulo);
        venta.setIdComprador(idComprador);
        venta.setFecha(new java.sql.Timestamp(System.currentTimeMillis()));

        ventaDAO.insertarVenta(venta);
        System.out.println("Venta insertada con éxito.");
    }

    // Método que interactua con el usuario para borrar un usuario según su email
    public static void borrarUsuarioPorEmail(UsuarioInterface usuarioDAO) {

        String email;
        Usuario usuario = null;

        do {
            System.out.println("Introduzca el email del usuario que desea borrar: ");
            email = scanner.nextLine();

            // Compruebo si el usuario existe
            usuario = usuarioDAO.obtenerUsuarioPorEmail(email);
            if (usuario == null) {
                System.out.println("No existe un usuario con el email indicado.");
            }
        } while (usuario == null); // Repite mientras no se encuentre el usuario

        // Intento borrar el usuario
        usuarioDAO.borrarUsuariosPorEmail(email);
        System.out.println("Usuario borrado con éxito.");
    }

    // Método que interactua con el usuario para borrar un usuario según su ID
    /*
        La integridad referencial de la base de datos evita que se realice esta acción
    para mantener la consistencia de los datos.
    Para borrar un registro de la tabla 'usuarios' que se referencia por una clave
    foránea en la tabla 'artículos', debo eliminar los registros dependientes antes
    de eliminar al usuario. Es decir, primero debo eliminar o actualizar los artículos
    que hacen referencia a su ID para que no se violen las restricciones de clave foránea.
     */
    public static void borrarUsuarioPorId(UsuarioInterface usuarioDAO, ArticuloInterface articuloDAO, int idUsuario) {

        // Compruebo si el usuario tiene artículos asociados
        if (usuarioDAO.usuarioTieneArticulos(idUsuario)){
            System.out.println("No se puede borrar el usuario ya que tiene artículos asociados.");
        } else {
            // Si no hay artículos asociados, intentar borrar el usuario directamente
            usuarioDAO.borrarUsuarioPorId(idUsuario);
            System.out.println("Usuario borrado con éxito.");
        }
    }

    // Método que muestra todos los usuarios
    // OPCIONAL
    public static void mostrarTodosLosUsuarios(UsuarioInterface usuarioDAO) {

        List<Usuario> usuarios = usuarioDAO.mostrarTodosLosUsuarios();
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados");
        } else {
            usuarios.forEach(usuario -> System.out.println(
                    "ID: " + usuario.getId() + "\n"
                    + "Email: " + usuario.getEmail() + "\n"
                    + "Nombre: " + usuario.getNombre() +"\n"));
        }
    }

    // Método que muestra datos de un usuario según su email
    // OPCIONAL
    public static void verDatosUsuarioPorEmail(UsuarioInterface usuarioDAO) {

        System.out.println("Ingrese el email del usuario: ");
        String email = scanner.nextLine();

        Usuario usuario = usuarioDAO.obtenerUsuarioPorEmail(email);
        if (usuario != null) {
            System.out.println(
                    "ID: " + usuario.getId() + "\n"
                    + "Email: " + usuario.getEmail() + "\n"
                    + "Nombre: " + usuario.getNombre() +"\n");
        } else {
            System.out.println("No se encontró ningún usuario con el email proporcionado.");
        }
    }

    // Método que interactua con el usuario para borrar un articulo según su ID
    // OPCIONAL
    public static void borrarArticuloPorId(ArticuloInterface articuloDAO, int idArticulo) {

        // Primero compruebo si el artículo está referenciado en la tabla 'ventas'
        if (articuloDAO.articuloTieneVentas(idArticulo)) {
            System.out.println("No se puede borrar el artículo ya que tiene ventas asociadas.");
        } else {
            // Si no hay ventas, intento borrar el artículo
            articuloDAO.borrarArticuloPorId(idArticulo);
            System.out.println("El artículo ha sido borrado con éxito.");
        }
    }

    // Método que muestra todas las ventas
    // OPCIONAL
    public static void mostrarTodasLasVentas(VentaInterface ventaDAO) {

        List<Venta> ventas = ventaDAO.mostrarTodasLasVentas();
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
        } else {
            ventas.forEach(venta -> System.out.println(
                    "ID: " + venta.getId() + "\n"
                    + "Fecha: " + venta.getFecha() + "\n"
                    + "ID Artículo: " + venta.getId() + "\n"
                    + "ID Comprador: " + venta.getIdComprador() +"\n"));
        }
    }

    // Método que interactua con el usuario para borrar una venta según su ID
    // OPCIONAL
    public static void borrarVentaPorId(VentaInterface ventaDAO) {

        System.out.println("Introduzca el ID de la venta que desea borrar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Venta venta = ventaDAO.obtenerVentaPorId(id);
        if (venta != null) {
            ventaDAO.borrarVentaPorId(id);
            System.out.println("La venta ha sido borrada con éxito.");
        } else {
            System.out.println("No se encontró ninguna venta con el ID: " + id);
        }
    }
}
