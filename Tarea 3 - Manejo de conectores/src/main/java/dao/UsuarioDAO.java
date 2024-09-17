package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import entidades.Usuario;
import interfaces.UsuarioInterface;

/**
 * CLASE DAO que proporciona una capa de abstracción para 
 * interactuar con la base de datos.
 * @author Sandra
 */
public class UsuarioDAO implements UsuarioInterface{
    
    // Variable para establecer la conexión con la base de datos
    private Connection connection;

    // Constructor
    public UsuarioDAO(Connection connection) {
        this.connection = connection;
    }
    
    // Método para obtener todos los usuarios según su ID
    @Override
    public Usuario obtenerUsuarioID(int id){
    
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1, id);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    return new Usuario(rs.getInt("id"), rs.getString("email"),rs.getString("nombre"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }
    
    // Método para obtener todos los usuarios de la tabla
    @Override
    public List<Usuario> mostrarTodosLosUsuarios(){
    
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try(PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery(sql)){
            
            while(rs.next()){
                usuarios.add(new Usuario(
                        rs.getInt("id"), 
                      rs.getString("email"), 
                     rs.getString("nombre")));
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return usuarios;
    }
    
    // Método para insertar usuarios
    @Override
    public void insertarUsuario(Usuario usuario){
    
        String sql = "INSERT INTO usuarios (email, nombre) VALUES (?, ?)";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setString(1, usuario.getEmail());
            pstmt.setString(2, usuario.getNombre());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
    // Método para borrar usuarios según su email
    @Override
    public void borrarUsuariosPorEmail(String email){
        
        String sql = "DELETE FROM usuarios WHERE email = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setString(1, email);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
    // OPCIONAL
    // Método que comprueba si existe un usuario según su email
    @Override
    public boolean existeUsuarioPorEmail(String email){
    
        String sql = "SELECT count(*) FROM usuarios WHERE email = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setString(1, email);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return false;
    }
    
    // 2ª PARTE.
    
    // Método para insertar usuarios -> Realizado arriba.
    // Método que lista todos los usuarios. -> Realizado arriba.
    
    // Método que borra usuarios a partir de su id. Si no existe, se debe indicar.
    @Override
    public boolean borrarUsuarioPorId(int id){
    
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return false;
    }
    
    // Método que muestra los datos de un usuario indicando su email. Si no existe, se debe indicar.
    @Override
    public Usuario obtenerUsuarioPorEmail(String email){
    
        String sql = "SELECT * FROM usuarios WHERE email = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setString(1, email);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    return new Usuario(
                                rs.getInt("id"),
                              rs.getString("email"),
                             rs.getString("nombre"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }
    
    // Método que comprueba si el usuario tiene artículos asociados
    @Override
    public boolean usuarioTieneArticulos(int idUsuario){
    
        String sql = "SELECT COUNT(*) FROM articulos WHERE idVendedor = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1, idUsuario);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return false;
    }
}
