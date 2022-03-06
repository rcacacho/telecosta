package tele.costa.bussines.bussines.ejb.imp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import tele.costa.api.ejb.BodegaBeanLocal;
import tele.costa.api.entity.Insumos;

/**
 *
 * @author rcacacho
 */
@Singleton
public class BodegaBean implements BodegaBeanLocal {

    private static final Logger log = Logger.getLogger(BodegaBean.class);

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
    public List<Insumos> listInsumoByFechaInicio(Date fechaInicio) {
        if (fechaInicio == null) {
            return null;
        }

        Calendar c = Calendar.getInstance();
        c.setTime(fechaInicio);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        fechaInicio = c.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.format(fechaInicio);

        List<Insumos> lst = em.createQuery("SELECT pa FROM Insumos pa WHERE pa.fecha >= :fechaInicio and pa.activo = true ", Insumos.class)
                .setParameter("fechaInicio", fechaInicio)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Insumos> listInsumoByFechaFin(Date fechaFin) {
        if (fechaFin == null) {
            return null;
        }

        Calendar c1 = Calendar.getInstance();
        c1.setTime(fechaFin);
        c1.set(Calendar.HOUR_OF_DAY, 23);
        c1.set(Calendar.MINUTE, 59);
        c1.set(Calendar.SECOND, 59);
        fechaFin = c1.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.format(fechaFin);

        List<Insumos> lst = em.createQuery("SELECT pa FROM Insumos pa WHERE pa.fecha <= :fechaFin and pa.activo = true ", Insumos.class)
                .setParameter("fechaFin", fechaFin)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Insumos> listInsumoByFechaInicioAndFechaFin(Date fechaInicio, Date fechaFin) {
        if (fechaInicio == null) {
            return null;
        }

        Calendar c = Calendar.getInstance();
        c.setTime(fechaInicio);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        fechaInicio = c.getTime();

        Calendar c1 = Calendar.getInstance();
        c1.setTime(fechaFin);
        c1.set(Calendar.HOUR_OF_DAY, 23);
        c1.set(Calendar.MINUTE, 59);
        c1.set(Calendar.SECOND, 59);
        fechaFin = c1.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.format(fechaInicio);
        sdf.format(fechaFin);

        List<Insumos> lst = em.createQuery("SELECT pa FROM Insumos pa WHERE pa.fecha >= :fechaInicio and pa.fecha <= :fechaFin and pa.activo = true ", Insumos.class)
                .setParameter("fechaInicio", fechaInicio)
                .setParameter("fechaFin", fechaFin)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Insumos> listInsumoByIdAgencia(Integer idAgencia) {
        if (idAgencia == null) {
            return null;
        }

        List<Insumos> lst = em.createQuery("SELECT pa FROM Insumos pa WHERE pa.idagencia.idagencia =:idAgencia and pa.activo = true ", Insumos.class)
                .setParameter("idAgencia", idAgencia)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Insumos> listInsumoByFechaInicioAndFechaFinAndIdAgencia(Date fechaInicio, Date fechaFin, Integer idAgencia) {
        if (fechaInicio == null) {
            return null;
        }

        Calendar c = Calendar.getInstance();
        c.setTime(fechaInicio);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        fechaInicio = c.getTime();

        Calendar c1 = Calendar.getInstance();
        c1.setTime(fechaFin);
        c1.set(Calendar.HOUR_OF_DAY, 23);
        c1.set(Calendar.MINUTE, 59);
        c1.set(Calendar.SECOND, 59);
        fechaFin = c1.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.format(fechaInicio);
        sdf.format(fechaFin);

        List<Insumos> lst = em.createQuery("SELECT pa FROM Insumos pa WHERE pa.fecha >= :fechaInicio and pa.fecha <= :fechaFin and pa.idagencia.idagencia =:idAgencia and pa.activo = true ", Insumos.class)
                .setParameter("fechaInicio", fechaInicio)
                .setParameter("fechaFin", fechaFin)
                .setParameter("idAgencia", idAgencia)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public Insumos saveInsumo(Insumos insumo) {
        try {
            insumo.setActivo(true);
            insumo.setFechacreacion(new Date());
            em.persist(insumo);
            em.flush();
            return (insumo);
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
    public Insumos updateInsumo(Insumos insumo) {
        try {
            em.merge(insumo);
            em.flush();
            return (insumo);
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
    public Insumos findInsumoById(Integer idinsumo) {
        if (idinsumo == null) {
            return null;
        }

        List<Insumos> lst = em.createQuery("SELECT pa FROM Insumos pa WHERE pa.idinsumo =:idinsumo and pa.activo = true ", Insumos.class)
                .setParameter("idinsumo", idinsumo)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst.get(0);
    }

}
