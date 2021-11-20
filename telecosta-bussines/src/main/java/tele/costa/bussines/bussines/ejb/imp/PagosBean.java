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
import tele.costa.api.ejb.PagosBeanLocal;
import tele.costa.api.entity.Pago;

/**
 *
 * @author rcacacho
 */
@Singleton
public class PagosBean implements PagosBeanLocal {

    private static final Logger log = Logger.getLogger(ClienteBean.class);

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
    public List<Pago> listPagos(Date fechainicio, Date fechafin) {
        if (fechainicio == null) {
            return null;
        }

        List<Pago> lst = em.createQuery("SELECT pa FROM Pago pa WHERE pa.fechapago >= :fechainicio and pa.fechapago <= :fechafin and pa.idtipopago.idtipopago = 2 ", Pago.class)
                .setParameter("fechainicio", fechainicio)
                .setParameter("fechafin", fechafin)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Pago> listCobros(Date fechainicio, Date fechafin) {
        if (fechainicio == null) {
            return null;
        }

        List<Pago> lst = em.createQuery("SELECT pa FROM Pago pa WHERE pa.fechacreacion >= :fechainicio and pa.fechacreacion <= :fechafin and pa.idtipopago.idtipopago = 1 ", Pago.class)
                .setParameter("fechainicio", fechainicio)
                .setParameter("fechafin", fechafin)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public Pago savePago(Pago pago) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Pago saveCobro(Pago pago) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Pago findPagoByIdClienteAndAnioAndMes(Integer idCliente, Integer anio, String mes) {
       if (idCliente == null) {
            return null;
        }

        List<Pago> lst = em.createQuery("SELECT pa FROM Pago pa WHERE pa.fechacreacion >= :fechainicio and pa.fechacreacion <= :fechafin and pa.idtipopago.idtipopago = 1 ", Pago.class)
                .setParameter("fechainicio", fechainicio)
                .setParameter("fechafin", fechafin)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

}
