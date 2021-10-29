package tele.costa.bussines.bussines.ejb.imp;

import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import tele.costa.api.ejb.LoginBeanLocal;
import tele.costa.api.entity.Usuario;

/**
 *
 * @author rcacacho
 */
@Singleton
public class LoginBean implements LoginBeanLocal {
    
    private static final Logger log = Logger.getLogger(LoginBean.class);

    @PersistenceContext(unitName = "TeleCostaPU")
    EntityManager em;

    @Resource
    private EJBContext context;

    @Override
    public Usuario verificarUsuario(String usuario, String password) {
        List<Usuario> lst = em.createQuery("SELECT usuario FROM Usuario usuario WHERE usuario.usuario =:usuario and usuario.password =:password ", Usuario.class)
                .setParameter("usuario", usuario)
                .setParameter("password", password)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst.get(0);
    }

    @Override
    public Usuario saveUsuarioNoVerificado(Usuario colaborador) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String findUsuario(String usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
