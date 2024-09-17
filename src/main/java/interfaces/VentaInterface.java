package interfaces;

import entidades.Venta;
import java.util.List;

/**
 * INTERFAZ para la tabla de ventas.
 * @author Sandra
 */
public interface VentaInterface {
    
    void insertarVenta(Venta venta);
    //OPCIONAL
    List<Venta> mostrarTodasLasVentas();
    void borrarVentaPorId(int id);
    Venta obtenerVentaPorId(int id);
}
