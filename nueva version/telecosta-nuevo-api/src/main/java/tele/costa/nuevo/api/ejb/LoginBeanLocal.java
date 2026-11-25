package tele.costa.nuevo.api.ejb;

import tele.costa.nuevo.api.entity.Usuario;
import jakarta.ejb.Local;

/**
 *
 * @author rcacacho
 */
@Local
public interface LoginBeanLocal {

    Usuario buscarPorUsuario(String usuario);

    void actualizarPassword(Integer idusuario, String nuevoHash);

    String findUsuario(String usuario);

}
