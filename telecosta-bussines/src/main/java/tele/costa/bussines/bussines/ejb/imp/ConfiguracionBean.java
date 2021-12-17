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
import tele.costa.api.ejb.ConfiguracionBeanLocal;
import tele.costa.api.entity.Configuracionpago;
import tele.costa.api.entity.Tipocompra;

/**
 *
 * @author elfo_
 */
@Singleton
public class ConfiguracionBean implements ConfiguracionBeanLocal {

    private static final Logger log = Logger.getLogger(ConfiguracionBean.class);

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
    public List<Configuracionpago> listConfiguracionPago() {
        List<Configuracionpago> lst = em.createQuery("SELECT qj FROM Configuracionpago qj where qj.activo = true ", Configuracionpago.class)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public Configuracionpago saveConfiguracionPago(Configuracionpago configuracion) {
        try {
            configuracion.setActivo(true);
            configuracion.setFechacreacion(new Date());
            em.persist(configuracion);
            em.flush();
            return (configuracion);
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
    public Configuracionpago deleteConfiguracionPago(Integer idconfiguracionpago, String usuario) {
        if (idconfiguracionpago == null) {
            context.setRollbackOnly();
            return null;
        }

        try {
            Configuracionpago toUpdate = em.find(Configuracionpago.class, idconfiguracionpago);

            toUpdate.setUsuarioeliminacion(usuario);
            toUpdate.setFechaeliminacion(new Date());
            toUpdate.setActivo(false);
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
    public Configuracionpago findConfiguracionPago(Integer idconfiguracionpago) {
        if (idconfiguracionpago == null) {
            return null;
        }

        List<Configuracionpago> lst = em.createQuery("SELECT col FROM Configuracionpago col WHERE col.idconfiguracionpago =:idconfiguracionpago ", Configuracionpago.class)
                .setParameter("idconfiguracionpago", idconfiguracionpago)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst.get(0);
    }

    @Override
    public Configuracionpago actualizarConfiguracion(Configuracionpago configuracion) {
        if (configuracion == null) {
            context.setRollbackOnly();
            return null;
        }

        try {
            Configuracionpago toUpdate = em.find(Configuracionpago.class, configuracion.getIdconfiguracionpago());

            toUpdate.setDescripcion(configuracion.getDescripcion());
            toUpdate.setValor(configuracion.getValor());
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
    public List<Tipocompra> listConfiguracionGasto() {
        List<Tipocompra> lst = em.createQuery("SELECT qj FROM Tipocompra qj where qj.activo = true ", Tipocompra.class)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public Tipocompra saveConfiguracionGasto(Tipocompra configuracionGasto) {
        try {
            configuracionGasto.setActivo(true);
            configuracionGasto.setFechacreacion(new Date());
            em.persist(configuracionGasto);
            em.flush();
            return (configuracionGasto);
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
    public Tipocompra deleteConfiguracionGasto(Integer idtipocompra, String usuario) {
        if (idtipocompra == null) {
            context.setRollbackOnly();
            return null;
        }

        try {
            Tipocompra toUpdate = em.find(Tipocompra.class, idtipocompra);

            toUpdate.setUsuariomodificacion(usuario);
            toUpdate.setFechamodificacion(new Date());
            toUpdate.setActivo(false);
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
    public Tipocompra findConfiguracionGasto(Integer idtipocompra) {
        if (idtipocompra == null) {
            return null;
        }

        List<Tipocompra> lst = em.createQuery("SELECT col FROM Tipocompra col WHERE col.idtipocompra =:idtipocompra ", Tipocompra.class)
                .setParameter("idtipocompra", idtipocompra)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst.get(0);
    }

    @Override
    public Tipocompra actualizarConfiguracionGasto(Tipocompra configuracionGasto) {
        if (configuracionGasto == null) {
            context.setRollbackOnly();
            return null;
        }

        try {
            Tipocompra toUpdate = em.find(Tipocompra.class, configuracionGasto.getIdtipocompra());

            toUpdate.setDescripcion(configuracionGasto.getDescripcion());
            toUpdate.setTipo(configuracionGasto.getTipo());
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

}
