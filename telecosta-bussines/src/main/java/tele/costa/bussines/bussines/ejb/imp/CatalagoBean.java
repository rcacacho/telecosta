package tele.costa.bussines.bussines.ejb.imp;

import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import tele.costa.api.ejb.CatalogoBeanLocal;
import tele.costa.api.entity.Departamento;
import tele.costa.api.entity.Municipio;

/**
 *
 * @author elfo_
 */
@Singleton
public class CatalagoBean implements CatalogoBeanLocal {

    private static final Logger log = Logger.getLogger(CatalagoBean.class);

    @PersistenceContext(unitName = "TeleCostaPU")
    EntityManager em;

    @Resource
    private EJBContext context;

    @Override
    public List<Municipio> listMunicipioByIdDepartamento(Integer iddepartamento) {
        if (iddepartamento == null) {
            return null;
        }

        List<Municipio> lst = em.createQuery("SELECT muni FROM Municipio muni WHERE muni.iddepartamento.iddepartamento =:iddepartamento", Municipio.class)
                .setParameter("iddepartamento", iddepartamento)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Departamento> listDepartamentos() {
        List<Departamento> lst = em.createQuery("SELECT dep FROM Departamento dep WHERE dep.activo  = true", Departamento.class)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

}
