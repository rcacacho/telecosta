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
import tele.costa.api.dto.ReporteCobrosDto;
import tele.costa.api.ejb.PagosBeanLocal;
import tele.costa.api.entity.Detallepago;
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
    public List<Pago> listPagosByFechaInicioAndFin(Date fechainicio, Date fechafin) {
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
        try {
            pago.setActivo(true);
            pago.setFechacreacion(new Date());
            em.persist(pago);
            em.flush();
            return (pago);
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
    public Pago saveCobro(Pago pago) {
        try {
            pago.setActivo(true);
            pago.setFechacreacion(new Date());
            em.persist(pago);
            em.flush();
            return (pago);
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
    public Pago findPagoByIdClienteAndAnioAndMes(Integer idcliente, Integer anio, String mes) {
        if (idcliente == null) {
            return null;
        }

        List<Pago> lst = em.createQuery("SELECT pa FROM Pago pa WHERE pa.idcliente.idcliente =:idcliente and pa.anio =:anio and pa.mes =:mes", Pago.class)
                .setParameter("idcliente", idcliente)
                .setParameter("anio", anio)
                .setParameter("mes", mes)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst.get(0);
    }

    @Override
    public List<Pago> listPagoByIdCliente(Integer idcliente) {
        if (idcliente == null) {
            return null;
        }

        List<Pago> lst = em.createQuery("SELECT pa FROM Pago pa WHERE pa.idcliente.idcliente =:idcliente and pa.idtipopago.idtipopago = 2 ", Pago.class)
                .setParameter("idcliente", idcliente)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Pago> listPagoByAnio(Integer anio) {
        if (anio == null) {
            return null;
        }

        List<Pago> lst = em.createQuery("SELECT pa FROM Pago pa WHERE pa.anio =:anio and pa.idtipopago.idtipopago = 2 ", Pago.class)
                .setParameter("anio", anio)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Pago> listPagoByMes(String mes) {
        if (mes == null) {
            return null;
        }

        List<Pago> lst = em.createQuery("SELECT pa FROM Pago pa WHERE pa.mes =:mes and pa.idtipopago.idtipopago = 2 ", Pago.class)
                .setParameter("mes", mes)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Pago> listCobroByIdCliente(Integer idcliente) {
        if (idcliente == null) {
            return null;
        }

        List<Pago> lst = em.createQuery("SELECT pa FROM Pago pa WHERE pa.idcliente.idcliente =:idcliente and pa.idtipopago.idtipopago = 1 ", Pago.class)
                .setParameter("idcliente", idcliente)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Pago> listCobroByAnio(Integer anio) {
        if (anio == null) {
            return null;
        }

        List<Pago> lst = em.createQuery("SELECT pa FROM Pago pa WHERE pa.anio =:anio and pa.idtipopago.idtipopago = 1 ", Pago.class)
                .setParameter("anio", anio)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Pago> listCobroByMes(String mes) {
        if (mes == null) {
            return null;
        }

        List<Pago> lst = em.createQuery("SELECT pa FROM Pago pa WHERE pa.mes =:mes and pa.idtipopago.idtipopago = 1 ", Pago.class)
                .setParameter("mes", mes)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public Pago findPagoByIdPago(Integer idPago) {
        if (idPago == null) {
            return null;
        }

        List<Pago> lst = em.createQuery("SELECT pa FROM Pago pa WHERE pa.idpago =:idPago ", Pago.class)
                .setParameter("idPago", idPago)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst.get(0);
    }

    @Override
    public List<Pago> listPagos() {
        List<Pago> lst = em.createQuery("SELECT pa FROM Pago pa WHERE pa.idtipopago.idtipopago = 2 and pa.activo = true  order by pa.fechapago desc", Pago.class)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public Detallepago saveDetallepago(Detallepago detalle) {
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
    public List<Detallepago> listDetallePago(Integer idpago) {
        if (idpago == null) {
            return null;
        }

        List<Detallepago> lst = em.createQuery("SELECT pa FROM Detallepago pa WHERE pa.idpago.idpago =:idpago ", Detallepago.class)
                .setParameter("idpago", idpago)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public Pago updatePago(Pago pago) {
        try {
            em.merge(pago);
            em.flush();
            return (pago);
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
    public List<Pago> listPagosByIdMunicipio(Integer idmunicicpio) {
        if (idmunicicpio == null) {
            return null;
        }

        List<Pago> lst = em.createQuery("SELECT pa FROM Pago pa WHERE pa.idcliente.idmunicipio.idmunicipio =:idmunicicpio  order by pa.fechapago desc", Pago.class)
                .setParameter("idmunicicpio", idmunicicpio)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Pago> listPagosByInIdMunicipios() {
        List<Pago> lst = em.createQuery("SELECT pa FROM Pago pa WHERE pa.idcliente.idmunicipio.idmunicipio in (6,7) order by pa.fechapago desc", Pago.class)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public Pago findUltimoPago(Integer idcliente) {
        if (idcliente == null) {
            return null;
        }

        List<Pago> lst = em.createQuery("SELECT pa FROM Pago pa WHERE pa.idcliente.idcliente =:idcliente order by pa.fechapago desc ", Pago.class)
                .setParameter("idcliente", idcliente)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst.get(0);
    }

    @Override
    public Pago eliminarPago(Integer idpago, String usuario) {
        if (idpago == null) {
            context.setRollbackOnly();
            return null;
        }

        try {
            Pago toUpdate = em.find(Pago.class, idpago);

            toUpdate.setActivo(false);
            toUpdate.setUsuarioeliminacion(usuario);
            toUpdate.setFechaeliminacion(new Date());
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
    public List<Pago> listPagoByIdClienteAndAnio(Integer idcliente, Integer anio) {
        if (anio == null) {
            return null;
        }

        List<Pago> lst = em.createQuery("SELECT pa FROM Pago pa WHERE pa.idcliente.idcliente =:idcliente and pa.anio =:anio and pa.idtipopago.idtipopago = 2 ", Pago.class)
                .setParameter("idcliente", idcliente)
                .setParameter("anio", anio)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Pago> listPagosByFechaInicioAndFinandUsuario(Date fechainicio, Date fechafin, String usuariocreacion) {
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

        List<Pago> lst = em.createQuery("SELECT pa FROM Pago pa WHERE pa.fechapago >= :fechainicio and pa.fechapago <= :fechafin and pa.usuariocreacion =:usuariocreacion ", Pago.class)
                .setParameter("fechainicio", fechainicio)
                .setParameter("fechafin", fechafin)
                .setParameter("usuariocreacion", usuariocreacion)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<ReporteCobrosDto> listCobrosByIdSector(Integer idsector) {
        return em.createNamedQuery("ReporteCobrosDto.cobrosector")
                .setParameter(1, idsector)
                .getResultList();
    }

    @Override
    public List<ReporteCobrosDto> listCobrosByIdMunicipio(Integer idmunicipio) {
        return em.createNamedQuery("ReporteCobrosDto.cobroMunicipio")
                .setParameter(1, idmunicipio)
                .getResultList();
    }

    @Override
    public List<Pago> listPagosByInIdMunicipiosSanRafaelSanPabloRodeo() {
      List<Pago> lst = em.createQuery("SELECT pa FROM Pago pa WHERE pa.idcliente.idmunicipio.idmunicipio in (3,6,7) order by pa.fechapago desc", Pago.class)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

}
