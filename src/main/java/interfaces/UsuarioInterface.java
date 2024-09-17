package interfaces;

import entidades.Usuario;
import java.util.List;

/**
 * INTERFAZ para la tabla de usuarios.
 * @author Sandra
 */
public interface UsuarioInterface {
    
    Usuario obtenerUsuarioID(int id);
    List<Usuario> mostrarTodosLosUsuarios();
    void insertarUsuario(Usuario usuario);
    void borrarUsuariosPorEmail(String email);
    //Opcional
    boolean existeUsuarioPorEmail(String email);
    
    // 2ª PARTE.
    boolean borrarUsuarioPorId(int id);
    Usuario obtenerUsuarioPorEmail(String email);
    boolean usuarioTieneArticulos(int idUsuario);
}
