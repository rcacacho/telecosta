package tele.costa.bussines.bussines.ejb.imp;

import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import tele.costa.api.ejb.ComprasBeanLocal;
import tele.costa.api.entity.Compra;
import tele.costa.api.entity.Configuracionpago;

/**
 *
 * @author elfo_
 */
@Singleton
public class ComprasBean implements ComprasBeanLocal {

    private static final Logger log = Logger.getLogger(ComprasBean.class);

    @PersistenceContext(unitName = "TeleCostaPU")
    EntityManager em;

    @Resource
    private EJBContext context;

    private void processException(Exception ex) {
        log.error(ex.getMessage(), ex);
    }

    @Override
    public List<Compra> listCompra() {
        List<Compra> lst = em.createQuery("SELECT qj FROM Compra qj where qj.activo = true ", Compra.class)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

}
