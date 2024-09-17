package dao;

import entidades.Articulo;
import interfaces.ArticuloInterface;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * CLASE DAO que proporciona una capa de abstracción para interactuar con la
 * base de datos.
 *
 * @author Sandra
 */
public class ArticuloDAO implements ArticuloInterface {

    // Variable para establecer la conexión con la base de datos
    private Connection connection;

    // Constructor
    public ArticuloDAO(Connection connection) {
        this.connection = connection;
    }

    // Método que obtiene todos los artículos de la tabla
    @Override
    public List<Articulo> obtenerTodosLosArticulos() {

        List<Articulo> articulos = new ArrayList<>();
        String sql = "SELECT articulos.*, usuarios.nombre AS nombreVendedor,"
                + "CASE WHEN ventas.id IS NULL THEN FALSE ELSE TRUE END AS vendido "
                + "FROM articulos "
                + "LEFT JOIN usuarios ON articulos.idVendedor = usuarios.id "
                + "LEFT JOIN ventas ON articulos.id = ventas.idArticulo "
                + "ORDER BY articulos.fecha";
        try (PreparedStatement pstmt = connection.prepareStatement(sql); 
                ResultSet rs = pstmt.executeQuery(sql)) {

            while (rs.next()) {
                boolean vendido = rs.getBoolean("vendido"); // Asumiendo que la consulta SQL incluye una columna 'vendido' que es un booleano
                Articulo articulo = new Articulo(
                        rs.getInt("id"),
                        rs.getString("descripcion"),
                        rs.getTimestamp("fecha"),
                        rs.getFloat("precio"),
                        rs.getInt("idVendedor"),
                        vendido);
                articulos.add(articulo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return articulos;
    }

    // Método para insertar artículos
    @Override
    public void insertarArticulo(Articulo articulo) {

        String sql = "INSERT INTO articulos (descripcion, fecha, precio, idVendedor) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, articulo.getDescripcion());
            pstmt.setTimestamp(2, new Timestamp(articulo.getFecha().getTime()));
            pstmt.setFloat(3, articulo.getPrecio());
            pstmt.setInt(4, articulo.getIdVendedor());
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    // Método para obtener un artículo según su ID
    @Override
    public Articulo obtenerArticuloPorId(int id) {

        String sql = "SELECT * FROM articulos WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Articulo articulo = new Articulo();
                    articulo.setId(rs.getInt("id"));
                    articulo.setDescripcion(rs.getString("descripcion"));
                    articulo.setFecha(rs.getTimestamp("fecha"));
                    articulo.setPrecio(rs.getFloat("precio"));
                    articulo.setIdVendedor(rs.getInt("idVendedor"));
                    return articulo;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }
    
    // Método para obtener un artículo según el ID del vendedor
    @Override
    public List<Articulo> obtenerArticulosPorUsuarioId(int idVendedor){
    
        List<Articulo> articulos = new ArrayList<>();
        String sql = "SELECT articulos.*, (ventas.id IS NOT NULL) as vendido "
                + "FROM articulos "
                + "LEFT JOIN ventas ON articulos.id = ventas.idArticulo "
                + "WHERE articulos.idVendedor = ? ";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1, idVendedor);
            try(ResultSet rs = pstmt.executeQuery()){
                while(rs.next()){
                    Articulo articulo = new Articulo(
                                    rs.getInt("id"),
                                    rs.getString("descripcion"),
                                    rs.getTimestamp("fecha"),
                                    rs.getFloat("precio"),
                                    idVendedor,
                                    rs.getBoolean("vendido"));
                    articulos.add(articulo);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return articulos;
    }
    
    // Método que comprueba si un artículo tiene ventas
    @Override
    public boolean articuloTieneVentas(int idArticulo){
    
        String sql = "SELECT COUNT(*) FROM ventas WHERE idArticulo = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1, idArticulo);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return false;
    }

    // Método para borrar un artículo según su ID. 
    //OPCIONAL
    @Override
    public void borrarArticuloPorId(int id) {

        String sql = "DELETE FROM articulos WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            if(affectedRows == 0){
                System.out.println("No se pudo encontrar el artículo con ID: " + id);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
    // Método que comprueba si existe un artículo según su ID
    // OPCIONAL
    @Override
    public boolean existeArticuloPorId(int id){
    
        String sql = "SELECT COUNT(*) FROM articulos WHERE id = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return false;
    }
}
