package entidades;

import java.util.Objects;

/**
 * CLASE ENTIDAD que representa la tabla usuario
 * en la base de datos tienda.sql.
 * @author Sandra
 */
public class Usuario {
    
    // Declaración de atributos
    private int id;
    private String email;
    private String nombre;

    // Constructor por defecto
    public Usuario() {
    }

    // Constructor con todos los campos
    public Usuario(int id, String email, String nombre) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Método toString() para imprimir datos
    @Override
    public String toString() {
        return "UsuarioEntidad{" + "id=" + id + ", email=" + email + ", nombre=" + nombre + '}';
    }
}
