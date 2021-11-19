package tele.costa.api.ejb;

import java.util.List;
import tele.costa.api.entity.Usuario;

/**
 *
 * @author rcacacho
 */
public interface UsuarioBeanLocal {

    List<Usuario> listaUsuarios();

    Usuario saveUsuario(Usuario usuario);

    Usuario findUsuario(Integer idusuario);

}
