package telecosta.web.utils;

import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;

/**
 *
 * @author rcacacho
 */
@FacesConverter(value = "genericEntityConverter")
public class GenericEntityConverter extends AbstractEntityConverter {

    @PersistenceContext(unitName = "DiacoPU")
    private EntityManager em;
    private static final Logger log = Logger.getLogger(GenericEntityConverter.class);

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
