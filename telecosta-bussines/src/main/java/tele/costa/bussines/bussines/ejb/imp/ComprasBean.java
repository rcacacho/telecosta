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
import tele.costa.api.ejb.ComprasBeanLocal;
import tele.costa.api.entity.Compra;
import tele.costa.api.entity.Proveedor;

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
    public List<Compra> listCompra() {
        List<Compra> lst = em.createQuery("SELECT qj FROM Compra qj where qj.activo = true and qj.idtipocompra.idtipocompra not in (60)", Compra.class)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public Compra saveCompra(Compra compra) {
        try {
            compra.setActivo(true);
            compra.setFechacreacion(new Date());
            em.persist(compra);
            em.flush();
            return (compra);
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
    public Compra findCompraById(Integer idcompra) {
        if (idcompra == null) {
            return null;
        }

        List<Compra> lst = em.createQuery("SELECT pa FROM Compra pa WHERE pa.idcompra =:idcompra ", Compra.class)
                .setParameter("idcompra", idcompra)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst.get(0);
    }

    @Override
    public Proveedor saveProveedor(Proveedor proveedor) {
        try {
            proveedor.setActivo(true);
            proveedor.setFechacreacion(new Date());
            em.persist(proveedor);
            em.flush();
            return (proveedor);
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
    public List<Compra> listCompraByFechaInicio(Date fechaInicio) {
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

        List<Compra> lst = em.createQuery("SELECT pa FROM Compra pa WHERE pa.fechacompra >= :fechainicio and pa.activo = true and pa.idtipocompra.idtipocompra not in (60)", Compra.class)
                .setParameter("fechainicio", fechaInicio)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Compra> listCompraByFechaFin(Date fechaFin) {
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

        List<Compra> lst = em.createQuery("SELECT pa FROM Compra pa WHERE pa.fechacompra <= :fechafin and pa.activo = true and pa.idtipocompra.idtipocompra not in (60)", Compra.class)
                .setParameter("fechacompra", fechaFin)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Compra> listCompraByFechaInicioFechaFin(Date fechainicio, Date fechafin) {
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

        List<Compra> lst = em.createQuery("SELECT pa FROM Compra pa WHERE pa.fechacompra >= :fechainicio and pa.fechacompra <= :fechafin and pa.activo = true and pa.idtipocompra.idtipocompra not in (60)", Compra.class)
                .setParameter("fechainicio", fechainicio)
                .setParameter("fechafin", fechafin)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public Compra eliminarCompra(Integer idcompra, String usuario) {
        if (idcompra == null) {
            context.setRollbackOnly();
            return null;
        }

        try {
            Compra toUpdate = em.find(Compra.class, idcompra);

            toUpdate.setActivo(false);
            toUpdate.setFechaeliminacion(new Date());
            toUpdate.setUsuarioeliminacion(usuario);
            em.merge(toUpdate);

            return toUpdate;
        } catch (ConstraintViolationException ex) {
            String validationError = getConstraintViolationExceptionAsString(ex);
            log.error(validationError);
            context.setRollbackOnly();
            return null;
        } catch (Exception ex) {
            processException(ex);
            return null;
        }
    }

    @Override
    public Compra updateCompra(Compra compra) {
        try {
            em.merge(compra);
            em.flush();
            return (compra);
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
    public List<Compra> listCompraByFechaInicioAndTipoCompra(Date fechaInicio, Integer idTipoCompra) {
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

        List<Compra> lst = em.createQuery("SELECT pa FROM Compra pa WHERE pa.fechacompra >= :fechainicio and pa.idtipocompra.idtipocompra =:idTipoCompra and pa.activo = true and pa.idtipocompra.idtipocompra not in (60)", Compra.class)
                .setParameter("fechainicio", fechaInicio)
                .setParameter("idTipoCompra", idTipoCompra)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Compra> listCompraByFechaFinAndTipoCompra(Date fechaFin, Integer idTipoCompra) {
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

        List<Compra> lst = em.createQuery("SELECT pa FROM Compra pa WHERE pa.fechacompra <= :fechafin and pa.idtipocompra.idtipocompra =:idTipoCompra and pa.activo = true and pa.idtipocompra.idtipocompra not in (60)", Compra.class)
                .setParameter("fechacompra", fechaFin)
                .setParameter("idTipoCompra", idTipoCompra)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Compra> listCompraByFechaInicioFechaFinAndTipoCompra(Date fechaInicio, Date fechaFin, Integer idTipoCompra) {
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

        List<Compra> lst = em.createQuery("SELECT pa FROM Compra pa WHERE pa.fechacompra >= :fechainicio and pa.fechacompra <= :fechafin and pa.idtipocompra.idtipocompra =:idTipoCompra and pa.activo = true and pa.idtipocompra.idtipocompra not in (60) ", Compra.class)
                .setParameter("fechainicio", fechaInicio)
                .setParameter("fechafin", fechaFin)
                .setParameter("idTipoCompra", idTipoCompra)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Compra> listCompraByidTipoCompra(Integer idTipoCompra) {
        if (idTipoCompra == null) {
            return null;
        }

        List<Compra> lst = em.createQuery("SELECT pa FROM Compra pa WHERE pa.idtipocompra.idtipocompra =:idTipoCompra and pa.activo = true and qj.idtipocompra.idtipocompra not in (60)", Compra.class)
                .setParameter("idTipoCompra", idTipoCompra)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Compra> listCompraBodegaByFechaInicioFechaFin(Date fechaInicio, Date fechaFin) {
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

        List<Compra> lst = em.createQuery("SELECT pa FROM Compra pa WHERE pa.fechacompra >= :fechainicio and pa.fechacompra <= :fechafin and pa.activo = true and pa.idtipocompra.idtipocompra in (60)", Compra.class)
                .setParameter("fechainicio", fechaInicio)
                .setParameter("fechafin", fechaFin)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Compra> listCompraBodegaByFechaInicio(Date fechaInicio) {
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

        List<Compra> lst = em.createQuery("SELECT pa FROM Compra pa WHERE pa.fechacompra >= :fechainicio and pa.activo = true and pa.idtipocompra.idtipocompra in (60)", Compra.class)
                .setParameter("fechainicio", fechaInicio)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Compra> listCompraBodegaByFechaFin(Date fechaFin) {
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

        List<Compra> lst = em.createQuery("SELECT pa FROM Compra pa WHERE pa.fechacompra <= :fechafin and pa.activo = true and pa.idtipocompra.idtipocompra in (60)", Compra.class)
                .setParameter("fechacompra", fechaFin)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Compra> listCompraBodega() {
             List<Compra> lst = em.createQuery("SELECT qj FROM Compra qj where qj.activo = true and qj.idtipocompra.idtipocompra in (60)", Compra.class)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

}
