package interfaces;

import entidades.Articulo;
import java.util.List;

/**
 * INTERFAZ para la tabla de artículos.
 * @author Sandra
 */
public interface ArticuloInterface {
    
    List<Articulo> obtenerTodosLosArticulos();
    void insertarArticulo(Articulo articulo);
    Articulo obtenerArticuloPorId(int id);
    List<Articulo> obtenerArticulosPorUsuarioId(int idVendedor);
    boolean articuloTieneVentas(int idArticulo);
    //Opcional
    void borrarArticuloPorId(int id);
    boolean existeArticuloPorId(int id);
}
