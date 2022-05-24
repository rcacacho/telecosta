package tele.costa.api.ejb;

import java.util.List;
import tele.costa.api.entity.Usuario;
import tele.costa.api.entity.Usuariomunicipio;

/**
 *
 * @author rcacacho
 */
public interface UsuarioBeanLocal {

    List<Usuario> listaUsuarios();

    Usuario saveUsuario(Usuario usuario);

    Usuario findUsuario(Integer idusuario);

    Usuario findUsuario(String usuario);

    Usuario reinicioPassword(Usuario usuario);

    Usuario updateUsuario(Usuario usuario);

    List<Usuariomunicipio> listUsuarioMunicipio(Integer idusuario);

    Usuariomunicipio updateUsuarioMunicipio(Usuariomunicipio usuario);
    
    Usuariomunicipio saveUsuarioMunicipio(Usuariomunicipio usuarioMunicipio);

}
