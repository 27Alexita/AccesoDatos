package entidades;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * CLASE ENTIDAD que representa la tabla venta
 * en la base de datos tienda.sql
 * @author Sandra
 */
public class Venta {
    
    // Declaración de atributos
    private int id;
    private Timestamp fecha;
    private int idArticulo;
    private int idComprador;

    // Constructor por defecto
    public Venta() {
    }

    // Constructor con todos los campos
    public Venta(int id, Timestamp fecha, int idArticulo, int idComprador) {
        this.id = id;
        this.fecha = fecha;
        this.idArticulo = idArticulo;
        this.idComprador = idComprador;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public int getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }

    public int getIdComprador() {
        return idComprador;
    }

    public void setIdComprador(int idComprador) {
        this.idComprador = idComprador;
    }

    // Método toString() para imprimir datos
    @Override
    public String toString() {
        return "VentaEntidad{" + "id=" + id + ", fecha=" + fecha + ", idArticulo=" + idArticulo + ", idComprador=" + idComprador + '}';
    }
}
