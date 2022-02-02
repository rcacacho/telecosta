package tele.costa.bussines.bussines.ejb.imp;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.apache.log4j.Logger;
import tele.costa.api.ejb.AtencionClienteLocal;
import tele.costa.api.entity.Atencion;

/**
 *
 * @author rcacacho
 */
@Singleton
public class AtencionBean implements AtencionClienteLocal {

    private static final Logger log = Logger.getLogger(AtencionBean.class);

    @PersistenceContext(unitName = "TeleCostaPU")
    EntityManager em;

    @Resource
    private EJBContext context;

    private void processException(Exception ex) {
        log.error(ex.getMessage(), ex);
    }

    private String getConstraintViolationExceptionAsString(ConstraintViolationException ex) {
        StringBuilder sb = new StringBuilder();
        sb.append("Error de validación:\n");
        for (ConstraintViolation c : ex.getConstraintViolations()) {
            sb.append(String.format("[bean: %s; field: %s; message: %s; value: %s]",
                    c.getRootBeanClass().getName(),
                    c.getPropertyPath().toString(),
                    c.getMessage(), c.getInvalidValue()));
        }
        return sb.toString();
    }

    @Override
    public List<Atencion> listAtenciones() {
       List<Atencion> lst = em.createQuery("SELECT qj FROM Atencion qj where qj.activo = true ", Atencion.class)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public Atencion saveAtencion(Atencion atencion) {
         try {
            atencion.setActivo(true);
            atencion.setFechacreacion(new Date());
            em.persist(atencion);
            em.flush();
            return (atencion);
        } catch (ConstraintViolationException ex) {
            String validationError = getConstraintViolationExceptionAsString(ex);
            log.error(validationError);
            context.setRollbackOnly();
            return null;
        } catch (Exception ex) {
            processException(ex);
            context.setRollbackOnly();
            return null;
        }
    }

    @Override
    public Atencion updateAtencion(Atencion cliente) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Atencion> listAtencionByIdMunicipio(Integer idmunicipio) {
        List<Atencion> lst = em.createQuery("SELECT qj FROM Atencion qj where qj.activo = true and qj.idcliente.idmunicipio.idmunicipio =:idmunicipio ", Atencion.class)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

}
