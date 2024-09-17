package entidades;

import java.sql.Timestamp;

/**
 * CLASE ENTIDAD que representa la tabla de artículos en la base de datos
 * tienda.sql.
 *
 * @author Sandra
 */
public class Articulo {

    // Declaración de atributos
    private int id;
    private String descripcion;
    private Timestamp fecha;
    private float precio;
    private int idVendedor;
    private boolean vendido;

    // Constructor por defecto
    public Articulo() {
    }

    // Constructor con todos los campos
    public Articulo(int id, String descripcion, Timestamp fecha, float precio, int idVendedor, boolean vendido) {
        this.id = id;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.precio = precio;
        this.idVendedor = idVendedor;
        this.vendido = vendido;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public boolean isVendido() {
        return vendido;
    }

    public void setVendido(boolean vendido) {
        this.vendido = vendido;
    }
    
    // Método toString() para imprimir datos
    @Override
    public String toString() {
        return "Articulo{" + "id=" + id + ", descripcion=" + descripcion + ", fecha=" + fecha + ", precio=" + precio + ", idVendedor=" + idVendedor + ", vendido=" + vendido + '}';
    }
}
