package dao;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import entidades.Venta;
import interfaces.VentaInterface;
import java.util.ArrayList;
import java.util.List;

/**
 * CLASE DAO que proporciona una capa de abstracción para 
 * interactuar con la base de datos.
 * @author Sandra
 */
public class VentaDAO implements VentaInterface{
    
    // Variable para establecer la conexión con la base de datos
    private Connection connection;

    // Constructor
    public VentaDAO(Connection connection) {
        this.connection = connection;
    }
    
    // Método para insertar ventas
    @Override
    public void insertarVenta(Venta venta){
    
        String sql = "INSERT INTO ventas (fecha, idArticulo, idComprador) VALUES (?, ?, ?)"; 
        try(PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setTimestamp(1, new Timestamp(venta.getFecha().getTime()));
            pstmt.setInt(2, venta.getIdArticulo());
            pstmt.setInt(3, venta.getIdComprador());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(VentaDAO.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
    // Método para mostrar todas las ventas de la tabla
    // OPCIONAL
    @Override
    public List<Venta> mostrarTodasLasVentas(){
    
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT * FROM ventas";
        try(PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql)){
        
            while(rs.next()){
                Venta venta = new Venta(
                                rs.getInt("id"),
                                rs.getTimestamp("fecha"),
                                rs.getInt("idArticulo"),
                                rs.getInt("idComprador"));
                ventas.add(venta);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VentaDAO.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return ventas;
    }
    
    // Método para borrar ventas según su Id
    // OPCIONAL
    @Override
    public void borrarVentaPorId(int id){
    
        String sql = "DELETE FROM ventas WHERE id = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            if(affectedRows == 0){
                System.out.println("No se pudo borrar la venta con ID: " + id);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VentaDAO.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
    // Método que obtiene una venta según su ID
    // OPCIONAL
    @Override
    public Venta obtenerVentaPorId(int id){
    
        String sql = "SELECT * FROM ventas WHERE id = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1, id);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    return new Venta(
                            rs.getInt("id"),
                            rs.getTimestamp("fecha"),
                            rs.getInt("idArticulo"),
                            rs.getInt("idComprador"));
                } 
            }
        } catch (SQLException ex) {
            Logger.getLogger(VentaDAO.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }
}
