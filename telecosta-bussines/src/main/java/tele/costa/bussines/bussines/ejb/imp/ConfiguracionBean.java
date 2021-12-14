package tele.costa.bussines.bussines.ejb.imp;

import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import tele.costa.api.ejb.ConfiguracionBeanLocal;
import tele.costa.api.entity.Configuracionpago;

/**
 *
 * @author elfo_
 */
public class ConfiguracionBean implements ConfiguracionBeanLocal {

    private static final Logger log = Logger.getLogger(ConfiguracionBean.class);

    @PersistenceContext(unitName = "TeleCostaPU")
    EntityManager em;

    @Resource
    private EJBContext context;

    private void processException(Exception ex) {
        log.error(ex.getMessage(), ex);
    }

    @Override
    public List<Configuracionpago> listConfiguracionPago() {
        List<Configuracionpago> lst = em.createQuery("SELECT qj FROM Configuracionpago qj where qj.activo = true ", Configuracionpago.class)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

}
