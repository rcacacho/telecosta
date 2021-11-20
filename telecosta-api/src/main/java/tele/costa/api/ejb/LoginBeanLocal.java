package tele.costa.api.ejb;

import tele.costa.api.entity.Usuario;

/**
 *
 * @author elfo_
 */
public interface LoginBeanLocal {

    Usuario verificarUsuario(String usuario, String password);

    String findUsuario(String usuario);

}
