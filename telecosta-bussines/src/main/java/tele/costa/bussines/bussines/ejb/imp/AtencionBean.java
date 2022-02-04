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
import tele.costa.api.ejb.AtencionClienteLocal;
import tele.costa.api.entity.Atencion;
import tele.costa.api.entity.Detalleatencion;

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
    public Atencion updateAtencion(Atencion atencion) {
        try {
            em.merge(atencion);
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
    public List<Atencion> listAtencionByIdMunicipio(Integer idmunicipio) {
        List<Atencion> lst = em.createQuery("SELECT qj FROM Atencion qj where qj.activo = true and qj.idcliente.idmunicipio.idmunicipio =:idmunicipio ", Atencion.class)
                .setParameter("idmunicipio", idmunicipio)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Atencion> listAtencionByMunicipio() {
        List<Atencion> lst = em.createQuery("SELECT qj FROM Atencion qj where qj.activo = true and qj.idcliente.idmunicipio.idmunicipio in (6,7) ", Atencion.class)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public Atencion findAtencionById(Integer idatencion) {
        List<Atencion> lst = em.createQuery("SELECT qj FROM Atencion qj where qj.activo = true and qj.idatencion =:idatencion ", Atencion.class)
                .setParameter("idatencion", idatencion)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst.get(0);
    }

    @Override
    public List<Atencion> listAtencionByFechas(Date fechainicio, Date fechafin) {
        if (fechainicio == null) {
            return null;
        }

        Calendar c = Calendar.getInstance();
        c.setTime(fechainicio);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        fechainicio = c.getTime();

        Calendar c1 = Calendar.getInstance();
        c1.setTime(fechafin);
        c1.set(Calendar.HOUR_OF_DAY, 23);
        c1.set(Calendar.MINUTE, 59);
        c1.set(Calendar.SECOND, 59);
        fechafin = c1.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.format(fechainicio);
        sdf.format(fechafin);

        List<Atencion> lst = em.createQuery("SELECT qj FROM Atencion qj where qj.fechacreacion >= :fechainicio and qj.fechacreacion <= :fechafin and qj.activo = true ", Atencion.class)
                .setParameter("fechainicio", fechainicio)
                .setParameter("fechafin", fechafin)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Atencion> listAtencionByFechaInicio(Date fechainicio) {
        if (fechainicio == null) {
            return null;
        }

        Calendar c = Calendar.getInstance();
        c.setTime(fechainicio);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        fechainicio = c.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.format(fechainicio);

        List<Atencion> lst = em.createQuery("SELECT qj FROM Atencion qj where qj.fechacreacion >= :fechainicio and qj.activo = true ", Atencion.class)
                .setParameter("fechainicio", fechainicio)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Atencion> listAtencionByFechaFin(Date fechafin) {
        if (fechafin == null) {
            return null;
        }

        Calendar c1 = Calendar.getInstance();
        c1.setTime(fechafin);
        c1.set(Calendar.HOUR_OF_DAY, 23);
        c1.set(Calendar.MINUTE, 59);
        c1.set(Calendar.SECOND, 59);
        fechafin = c1.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.format(fechafin);

        List<Atencion> lst = em.createQuery("SELECT qj FROM Atencion qj where qj.fechacreacion <= :fechafin and qj.activo = true ", Atencion.class)
                .setParameter("fechafin", fechafin)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Atencion> listAtencionByFechasAndRuta(Date fechainicio, Date fechafin, Integer idruta) {
        if (fechainicio == null) {
            return null;
        }

        Calendar c = Calendar.getInstance();
        c.setTime(fechainicio);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        fechainicio = c.getTime();

        Calendar c1 = Calendar.getInstance();
        c1.setTime(fechafin);
        c1.set(Calendar.HOUR_OF_DAY, 23);
        c1.set(Calendar.MINUTE, 59);
        c1.set(Calendar.SECOND, 59);
        fechafin = c1.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.format(fechainicio);
        sdf.format(fechafin);

        List<Atencion> lst = em.createQuery("SELECT qj FROM Atencion qj where qj.fechacreacion >= :fechainicio and qj.fechacreacion <= :fechafin and qj.idruta.idruta =:idruta and  qj.activo = true ", Atencion.class)
                .setParameter("fechainicio", fechainicio)
                .setParameter("fechafin", fechafin)
                 .setParameter("idruta", idruta)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Atencion> listAtencionByRuta(Integer idruta) {
        List<Atencion> lst = em.createQuery("SELECT qj FROM Atencion qj where qj.activo = true and qj.idruta.idruta =:idruta ", Atencion.class)
                .setParameter("idruta", idruta)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public Detalleatencion saveDetalleAtencion(Detalleatencion detalle) {
       try {
            detalle.setActivo(true);
            detalle.setFechacreacion(new Date());
            em.persist(detalle);
            em.flush();
            return (detalle);
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
    public List<Detalleatencion> listDetalleAtencioByIdAtencion(Integer idatencion) {
          List<Detalleatencion> lst = em.createQuery("SELECT qj FROM Detalleatencion qj where qj.activo = true and qj.idatencion.idatencion =:idatencion ", Detalleatencion.class)
                .setParameter("idatencion", idatencion)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

}
