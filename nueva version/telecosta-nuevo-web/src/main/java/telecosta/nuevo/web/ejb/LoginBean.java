package telecosta.nuevo.web.ejb;

import jakarta.annotation.Resource;
import jakarta.ejb.EJBContext;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import tele.costa.nuevo.api.ejb.LoginBeanLocal;
import tele.costa.nuevo.api.entity.Usuario;

/**
 *
 * @author rcacacho
 */
@Stateless
public class LoginBean implements LoginBeanLocal {


    @PersistenceContext(unitName = "TeleCostaPU")
    EntityManager em;

    @Resource
    private EJBContext context;

    @Override
    public Usuario buscarPorUsuario(String usuario) {
        List<Usuario> lst = em.createQuery("SELECT us FROM Usuario us WHERE us.usuario = :usuario AND us.activo = true", Usuario.class)
                .setParameter("usuario", usuario)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst.get(0);
    }

    @Override
    public void actualizarPassword(Integer idusuario, String nuevoHash) {
        em.createQuery("UPDATE Usuario us SET us.password = :password WHERE us.idusuario = :id")
                .setParameter("password", nuevoHash)
                .setParameter("id", idusuario)
                .executeUpdate();
    }

    @Override
    public String findUsuario(String usuario) {
        List<Usuario> lst = em.createQuery("SELECT us FROM Usuario us WHERE us.usuario =:usuario ", Usuario.class)
                .setParameter("usuario", usuario)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst.get(0).getUsuario();
    }

}
