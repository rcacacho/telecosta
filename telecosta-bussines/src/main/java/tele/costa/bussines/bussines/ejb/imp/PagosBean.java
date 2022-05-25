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
import tele.costa.api.entity.Cobro;
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

        List<Pago> lst = em.createQuery("SELECT pa FROM Pago pa WHERE pa.fechapago >= :fechainicio and pa.fechapago <= :fechafin ", Pago.class)
                .setParameter("fechainicio", fechainicio)
                .setParameter("fechafin", fechafin)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Cobro> listCobros(Date fechainicio, Date fechafin) {
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

        List<Cobro> lst = em.createQuery("SELECT pa FROM Cobro pa WHERE pa.fechacreacion >= :fechainicio and pa.fechacreacion <= :fechafin and pa.activo = true", Cobro.class)
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
    public Cobro saveCobro(Cobro cobro) {
        try {
            cobro.setActivo(true);
            cobro.setFechacreacion(new Date());
            em.persist(cobro);
            em.flush();
            return (cobro);
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

        List<Pago> lst = em.createQuery("SELECT pa FROM Pago pa WHERE pa.idcliente.idcliente =:idcliente and pa.anio =:anio and pa.mes like :mes and pa.activo = true", Pago.class)
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

        List<Pago> lst = em.createQuery("SELECT pa FROM Pago pa WHERE pa.idcliente.idcliente =:idcliente and pa.activo = true ", Pago.class)
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

        List<Pago> lst = em.createQuery("SELECT pa FROM Pago pa WHERE pa.anio =:anio and pa.activo = true ", Pago.class)
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

        List<Pago> lst = em.createQuery("SELECT pa FROM Pago pa WHERE pa.mes like :mes and pa.activo = true ", Pago.class)
                .setParameter("mes", '%' + mes + '%')
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Cobro> listCobroByIdCliente(Integer idcliente) {
        if (idcliente == null) {
            return null;
        }

        List<Cobro> lst = em.createQuery("SELECT pa FROM Cobro pa WHERE pa.idcliente.idcliente =:idcliente and pa.activo = true ", Cobro.class)
                .setParameter("idcliente", idcliente)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Cobro> listCobroByAnio(Integer anio) {
        if (anio == null) {
            return null;
        }

        List<Cobro> lst = em.createQuery("SELECT pa FROM Cobro pa WHERE pa.anio =:anio and pa.activo = true ", Cobro.class)
                .setParameter("anio", anio)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Cobro> listCobroByMes(String mes) {
        if (mes == null) {
            return null;
        }

        List<Cobro> lst = em.createQuery("SELECT pa FROM Cobro pa WHERE pa.mes like :mes and  pa.activo = true", Cobro.class)
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

        List<Pago> lst = em.createQuery("SELECT pa FROM Pago pa WHERE pa.idpago =:idPago and pa.activo = true", Pago.class)
                .setParameter("idPago", idPago)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst.get(0);
    }

    @Override
    public List<Pago> listPagos() {
        List<Pago> lst = em.createQuery("SELECT pa FROM Pago pa WHERE pa.activo = true  order by pa.fechapago desc", Pago.class)
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

        List<Detallepago> lst = em.createQuery("SELECT pa FROM Detallepago pa WHERE pa.idpago.idpago =:idpago and pa.activo = true ", Detallepago.class)
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

        List<Pago> lst = em.createQuery("SELECT pa FROM Pago pa WHERE pa.idcliente.idmunicipio.idmunicipio =:idmunicicpio and pa.activo = true order by pa.fechapago desc", Pago.class)
                .setParameter("idmunicicpio", idmunicicpio)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Pago> listPagosByInIdMunicipios() {
        List<Pago> lst = em.createQuery("SELECT pa FROM Pago pa WHERE pa.idcliente.idmunicipio.idmunicipio in (6,7) and pa.activo = true order by pa.fechapago desc", Pago.class)
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

        List<Pago> lst = em.createQuery("SELECT pa FROM Pago pa WHERE pa.idcliente.idcliente =:idcliente and pa.activo = true order by pa.fechapago desc ", Pago.class)
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

        List<Pago> lst = em.createQuery("SELECT pa FROM Pago pa WHERE pa.idcliente.idcliente =:idcliente and pa.anio =:anio and pa.activo = true ", Pago.class)
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

        List<Pago> lst = em.createQuery("SELECT pa FROM Pago pa WHERE pa.fechapago >= :fechainicio and pa.fechapago <= :fechafin and pa.usuariocreacion =:usuariocreacion and pa.activo = true ", Pago.class)
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
        List<Pago> lst = em.createQuery("SELECT pa FROM Pago pa WHERE pa.idcliente.idmunicipio.idmunicipio in (3,6,7) and pa.activo = true order by pa.fechapago desc", Pago.class)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Pago> listPagosByFechaInicioAndFinAndIdMunicipio(Date fechainicio, Date fechafin, Integer idMunicipio) {
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

        List<Pago> lst = em.createQuery("SELECT pa FROM Pago pa WHERE pa.fechapago >= :fechainicio and pa.fechapago <= :fechafin and pa.idcliente.idmunicipio.idmunicipio =:idMunicipio and pa.activo = true ", Pago.class)
                .setParameter("fechainicio", fechainicio)
                .setParameter("fechafin", fechafin)
                .setParameter("idMunicipio", idMunicipio)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Pago> listPagosByMunicipio(Integer idMunicipio) {
        if (idMunicipio == null) {
            return null;
        }

        List<Pago> lst = em.createQuery("SELECT pa FROM Pago pa WHERE pa.idcliente.idmunicipio.idmunicipio =:idMunicipio ", Pago.class)
                .setParameter("idMunicipio", idMunicipio)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Pago> listPagosByAnioAndMesAndMunicipio(Integer anio, String mes, Integer idMunicipio) {
        if (anio == null) {
            return null;
        }

        List<Pago> lst = em.createQuery("SELECT pa FROM Pago pa WHERE pa.anio =:anio and pa.mes like :mes and pa.idcliente.idmunicipio.idmunicipio =:idMunicipio", Pago.class)
                .setParameter("anio", anio)
                .setParameter("mes", mes)
                .setParameter("idMunicipio", idMunicipio)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Pago> listPagoByAnioAndMes(Integer anio, String mes) {
        if (anio == null) {
            return null;
        }

        List<Pago> lst = em.createQuery("SELECT pa FROM Pago pa WHERE pa.anio =:anio and pa.mes like :mes ", Pago.class)
                .setParameter("anio", anio)
                .setParameter("mes", mes)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Pago> listPagosByFechaInicio(Date fechainicio) {
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

        List<Pago> lst = em.createQuery("SELECT pa FROM Pago pa WHERE pa.fechapago >= :fechainicio  and pa.activo = true ", Pago.class)
                .setParameter("fechainicio", fechainicio)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Pago> listPagosByFechaFin(Date fechaFin) {
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

        List<Pago> lst = em.createQuery("SELECT pa FROM Pago pa WHERE pa.fechapago <= :fechaFin and pa.activo = true", Pago.class)
                .setParameter("fechaFin", fechaFin)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public Detallepago updateDetallePago(Detallepago detalle) {
        try {
            em.merge(detalle);
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
    public Detallepago eliminarDetallePago(Integer idpago, String usuario) {
        if (idpago == null) {
            context.setRollbackOnly();
            return null;
        }

        try {
            Detallepago toUpdate = em.find(Detallepago.class, idpago);

            toUpdate.setActivo(false);
            toUpdate.setUsuariomodificacion(usuario);
            toUpdate.setFechamodificacion(new Date());
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
    public Cobro findCobroByIdClienteAndAnioAndMes(Integer idcliente, Integer anio, String mes) {
        if (idcliente == null) {
            return null;
        }

        List<Cobro> lst = em.createQuery("SELECT pa FROM Cobro pa WHERE pa.idcliente.idcliente =:idcliente and pa.anio =:anio and pa.mes like :mes and pa.activo = true", Cobro.class)
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
    public Cobro updateCobro(Cobro cobro) {
        try {
            em.merge(cobro);
            em.flush();
            return (cobro);
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
    public Cobro findCobroByIdCobro(Integer idCobro) {
        if (idCobro == null) {
            return null;
        }

        List<Cobro> lst = em.createQuery("SELECT pa FROM Cobro pa WHERE pa.idcobro =:idCobro and pa.activo = true ", Cobro.class)
                .setParameter("idCobro", idCobro)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst.get(0);
    }

    @Override
    public List<Pago> listPagosByIdMunicipioByList(List<Integer> listIdmunicipio) {
        if (listIdmunicipio == null) {
            return null;
        }

        List<Pago> lst = em.createQuery("SELECT pa FROM Pago pa WHERE pa.idcliente.idmunicipio.idmunicipio in :listIdmunicipio and pa.activo = true order by pa.fechapago desc", Pago.class)
                .setParameter("listIdmunicipio", listIdmunicipio)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

}
