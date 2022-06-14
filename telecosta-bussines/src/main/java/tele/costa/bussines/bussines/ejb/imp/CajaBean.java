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
import tele.costa.api.ejb.CajaBeanLocal;
import tele.costa.api.entity.Cajaagencia;
import tele.costa.api.entity.Detallepago;
import tele.costa.api.enums.SectorPago;

/**
 *
 * @author rcacacho
 */
@Singleton
public class CajaBean implements CajaBeanLocal {

    private static final Logger log = Logger.getLogger(CajaBean.class);

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
    public List<Cajaagencia> listCajaAgencia() {
        List<Cajaagencia> lst = em.createQuery("SELECT ca FROM Cajaagencia ca where ca.activo = true ", Cajaagencia.class)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public Cajaagencia saveCaja(Cajaagencia caja) {
        try {
            caja.setActivo(true);
            caja.setFechacreacion(new Date());
            em.persist(caja);
            return (caja);
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
    public Cajaagencia updateCaja(Cajaagencia atencion) {
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
    public Cajaagencia findCajaByIdCaja(Integer idcajaagencia) {
        if (idcajaagencia == null) {
            return null;
        }

        List<Cajaagencia> lst = em.createQuery("SELECT col FROM Cajaagencia col WHERE col.idcajaagencia =:idcajaagencia ", Cajaagencia.class)
                .setParameter("idcajaagencia", idcajaagencia)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst.get(0);
    }

    @Override
    public List<Cajaagencia> listCajaByFechaInicio(Date fechaIncio) {
        Calendar c = Calendar.getInstance();
        c.setTime(fechaIncio);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        fechaIncio = c.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.format(fechaIncio);

        List<Cajaagencia> lst = em.createQuery("SELECT qj FROM Cajaagencia qj where qj.activo = true and qj.fechacreacion >= :fechaIncio ", Cajaagencia.class)
                .setParameter("fechaIncio", fechaIncio)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Cajaagencia> listCajaByFechaFin(Date fechaFin) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(fechaFin);
        c1.set(Calendar.HOUR_OF_DAY, 23);
        c1.set(Calendar.MINUTE, 59);
        c1.set(Calendar.SECOND, 59);
        fechaFin = c1.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.format(fechaFin);
        List<Cajaagencia> lst = em.createQuery("SELECT qj FROM Cajaagencia qj where qj.activo = true and qj.fechacreacion <= :fechaFin ", Cajaagencia.class)
                .setParameter("fechaFin", fechaFin)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Cajaagencia> listCajaByFechas(Date fechaIncio, Date fechaFin) {
        Calendar c = Calendar.getInstance();
        c.setTime(fechaIncio);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        fechaIncio = c.getTime();

        Calendar c1 = Calendar.getInstance();
        c1.setTime(fechaFin);
        c1.set(Calendar.HOUR_OF_DAY, 23);
        c1.set(Calendar.MINUTE, 59);
        c1.set(Calendar.SECOND, 59);
        fechaFin = c1.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.format(fechaIncio);
        sdf.format(fechaFin);

        List<Cajaagencia> lst = em.createQuery("SELECT qj FROM Cajaagencia qj where qj.activo = true and qj.fechacreacion >= :fechaIncio and qj.fechacreacion <= :fechaFin ", Cajaagencia.class)
                .setParameter("fechaIncio", fechaIncio)
                .setParameter("fechaFin", fechaFin)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Cajaagencia> listCajaByFechaInicioAndSector(Date fechaIncio, Integer idSector) {
        Calendar c = Calendar.getInstance();
        c.setTime(fechaIncio);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        fechaIncio = c.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.format(fechaIncio);

        List<Cajaagencia> lst = em.createQuery("SELECT qj FROM Cajaagencia qj where qj.activo = true and qj.fechacreacion >= :fechaIncio and qj.idsectorpago.idsectorpago =:idSector ", Cajaagencia.class)
                .setParameter("fechaIncio", fechaIncio)
                .setParameter("idSector", idSector)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Cajaagencia> listCajaByFechaFinAndSector(Date fechaFin, Integer idSector) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(fechaFin);
        c1.set(Calendar.HOUR_OF_DAY, 23);
        c1.set(Calendar.MINUTE, 59);
        c1.set(Calendar.SECOND, 59);
        fechaFin = c1.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.format(fechaFin);

        List<Cajaagencia> lst = em.createQuery("SELECT qj FROM Cajaagencia qj where qj.activo = true and qj.fechacreacion <= :fechaFin and qj.idsectorpago.idsectorpago =:idSector ", Cajaagencia.class)
                .setParameter("fechaFin", fechaFin)
                .setParameter("idSector", idSector)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Cajaagencia> listCajaByFechasAndSector(Date fechaIncio, Date fechaFin, Integer idSector) {
        Calendar c = Calendar.getInstance();
        c.setTime(fechaIncio);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        fechaIncio = c.getTime();

        Calendar c1 = Calendar.getInstance();
        c1.setTime(fechaFin);
        c1.set(Calendar.HOUR_OF_DAY, 23);
        c1.set(Calendar.MINUTE, 59);
        c1.set(Calendar.SECOND, 59);
        fechaFin = c1.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.format(fechaIncio);
        sdf.format(fechaFin);

        List<Cajaagencia> lst = em.createQuery("SELECT qj FROM Cajaagencia qj where qj.activo = true and qj.fechacreacion >= :fechaIncio and qj.fechacreacion <= :fechaFin and qj.idsectorpago.idsectorpago =:idSector", Cajaagencia.class)
                .setParameter("fechaIncio", fechaIncio)
                .setParameter("fechaFin", fechaFin)
                .setParameter("idSector", idSector)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Cajaagencia> listCajaBySector(Integer idSector) {
        List<Cajaagencia> lst = em.createQuery("SELECT qj FROM Cajaagencia qj where qj.activo = true and qj.idsectorpago.idsectorpago =:idSector ", Cajaagencia.class)
                .setParameter("idSector", idSector)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Detallepago> listNoFactura(Date fechaInicio, Date fechaFin, Integer idsectorpago) {
        List<Detallepago> lst = null;

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

        if (idsectorpago.equals(SectorPago.CTN1.getId())) {
            lst = em.createQuery("SELECT det FROM Detallepago det where det.activo = true and det.fechapago >= :fechaInicio and det.fechapago <= :fechaFin and det.idseriefactura.idseriefactura = 1 ORDER BY det.nofactura asc", Detallepago.class)
                    .setParameter("fechaInicio", fechaInicio)
                    .setParameter("fechaFin", fechaFin)
                    .getResultList();
        } else if (idsectorpago.equals(SectorPago.CTN2.getId())) {
            lst = em.createQuery("SELECT det FROM Detallepago det where det.activo = true and det.fechapago >= :fechaInicio and det.fechapago <= :fechaFin and det.idseriefactura.idseriefactura = 2 ORDER BY det.nofactura asc", Detallepago.class)
                    .setParameter("fechaInicio", fechaInicio)
                    .setParameter("fechaFin", fechaFin)
                    .getResultList();
        } else if (idsectorpago.equals(SectorPago.MLN1.getId())) {
            lst = em.createQuery("SELECT det FROM Detallepago det where det.activo = true and det.fechapago >= :fechaInicio and det.fechapago <= :fechaFin and det.idseriefactura.idseriefactura = 3 ORDER BY det.nofactura asc", Detallepago.class)
                    .setParameter("fechaInicio", fechaInicio)
                    .setParameter("fechaFin", fechaFin)
                    .getResultList();
        } else if (idsectorpago.equals(SectorPago.MLN2.getId())) {
            lst = em.createQuery("SELECT det FROM Detallepago det where det.activo = true and det.fechapago >= :fechaInicio and det.fechapago <= :fechaFin and det.idseriefactura.idseriefactura = 4 ORDER BY det.nofactura asc", Detallepago.class)
                    .setParameter("fechaInicio", fechaInicio)
                    .setParameter("fechaFin", fechaFin)
                    .getResultList();
        } else if (idsectorpago.equals(SectorPago.ERD1.getId())) {
            lst = em.createQuery("SELECT det FROM Detallepago det where det.activo = true and det.fechapago >= :fechaInicio and det.fechapago <= :fechaFin and det.idseriefactura.idseriefactura = 8 ORDER BY det.nofactura asc", Detallepago.class)
                    .setParameter("fechaInicio", fechaInicio)
                    .setParameter("fechaFin", fechaFin)
                    .getResultList();
        } else if (idsectorpago.equals(SectorPago.MLN3.getId())) {
            lst = em.createQuery("SELECT det FROM Detallepago det where det.activo = true and det.fechapago >= :fechaInicio and det.fechapago <= :fechaFin and det.idseriefactura.idseriefactura = 5 ORDER BY det.nofactura asc", Detallepago.class)
                    .setParameter("fechaInicio", fechaInicio)
                    .setParameter("fechaFin", fechaFin)
                    .getResultList();
        } else if (idsectorpago.equals(SectorPago.MLN4.getId())) {
            lst = em.createQuery("SELECT det FROM Detallepago det where det.activo = true and det.fechapago >= :fechaInicio and det.fechapago <= :fechaFin and det.idseriefactura.idseriefactura = 6 ORDER BY det.nofactura asc", Detallepago.class)
                    .setParameter("fechaInicio", fechaInicio)
                    .setParameter("fechaFin", fechaFin)
                    .getResultList();
        } else if (idsectorpago.equals(SectorPago.MLN5.getId())) {
            lst = em.createQuery("SELECT det FROM Detallepago det where det.activo = true and det.fechapago >= :fechaInicio and det.fechapago <= :fechaFin and det.idseriefactura.idseriefactura = 7 ORDER BY det.nofactura asc", Detallepago.class)
                    .setParameter("fechaInicio", fechaInicio)
                    .setParameter("fechaFin", fechaFin)
                    .getResultList();
        } else if (idsectorpago.equals(SectorPago.SNP1.getId())) {
            lst = em.createQuery("SELECT det FROM Detallepago det where det.activo = true and det.fechapago >= :fechaInicio and det.fechapago <= :fechaFin and det.idseriefactura.idseriefactura = 10 ORDER BY det.nofactura asc", Detallepago.class)
                    .setParameter("fechaInicio", fechaInicio)
                    .setParameter("fechaFin", fechaFin)
                    .getResultList();
        } else if (idsectorpago.equals(SectorPago.SNP2.getId())) {
            lst = em.createQuery("SELECT det FROM Detallepago det where det.activo = true and det.fechapago >= :fechaInicio and det.fechapago <= :fechaFin and det.idseriefactura.idseriefactura = 11 ORDER BY det.nofactura asc", Detallepago.class)
                    .setParameter("fechaInicio", fechaInicio)
                    .setParameter("fechaFin", fechaFin)
                    .getResultList();
        } else if (idsectorpago.equals(SectorPago.SNR1.getId())) {
            lst = em.createQuery("SELECT det FROM Detallepago det where det.activo = true and det.fechapago >= :fechaInicio and det.fechapago <= :fechaFin and det.idseriefactura.idseriefactura = 9 ORDER BY det.nofactura asc", Detallepago.class)
                    .setParameter("fechaInicio", fechaInicio)
                    .setParameter("fechaFin", fechaFin)
                    .getResultList();
        }

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public Long findMontoFacturaSerie(Integer inicio, Integer fin, Integer idsectorpago, Date fechaInicio, Date fechaFin) {
        List<Long> lst = null;
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

        if (idsectorpago.equals(SectorPago.CTN1.getId())) {
            lst = em.createQuery("SELECT sum(det.montopagado) FROM Detallepago det where det.activo = true and det.nofactura >= :inicio and det.nofactura <= :fin and det.fechapago >= :fechaInicio and det.fechapago <= :fechaFin and det.idseriefactura.idseriefactura = 1 ORDER BY det.nofactura asc", Long.class)
                    .setParameter("inicio", inicio)
                    .setParameter("fin", fin)
                    .setParameter("fechaInicio", fechaInicio)
                    .setParameter("fechaFin", fechaFin)
                    .getResultList();
        } else if (idsectorpago.equals(SectorPago.CTN2.getId())) {
            lst = em.createQuery("SELECT sum(det.montopagado) FROM Detallepago det where det.activo = true and det.nofactura >= :inicio and det.nofactura <= :fin and det.fechapago >= :fechaInicio and det.fechapago <= :fechaFin and det.idseriefactura.idseriefactura = 2 ORDER BY det.nofactura asc", Long.class)
                    .setParameter("inicio", inicio)
                    .setParameter("fin", fin)
                    .setParameter("fechaInicio", fechaInicio)
                    .setParameter("fechaFin", fechaFin)
                    .getResultList();
        } else if (idsectorpago.equals(SectorPago.MLN1.getId())) {
            lst = em.createQuery("SELECT sum(det.montopagado) FROM Detallepago det where det.activo = true and det.nofactura >= :inicio and det.nofactura <= :fin and det.fechapago >= :fechaInicio and det.fechapago <= :fechaFin and det.idseriefactura.idseriefactura = 3 ORDER BY det.nofactura asc", Long.class)
                    .setParameter("inicio", inicio)
                    .setParameter("fin", fin)
                    .setParameter("fechaInicio", fechaInicio)
                    .setParameter("fechaFin", fechaFin)
                    .getResultList();
        } else if (idsectorpago.equals(SectorPago.MLN2.getId())) {
            lst = em.createQuery("SELECT sum(det.montopagado) FROM Detallepago det where det.activo = true and det.nofactura >= :inicio and det.nofactura <= :fin and det.fechapago >= :fechaInicio and det.fechapago <= :fechaFin and det.idseriefactura.idseriefactura = 4 ORDER BY det.nofactura asc", Long.class)
                    .setParameter("inicio", inicio)
                    .setParameter("fin", fin)
                    .setParameter("fechaInicio", fechaInicio)
                    .setParameter("fechaFin", fechaFin)
                    .getResultList();
        } else if (idsectorpago.equals(SectorPago.ERD1.getId())) {
            lst = em.createQuery("SELECT sum(det.montopagado) FROM Detallepago det where det.activo = true and det.nofactura >= :inicio and det.nofactura <= :fin and det.fechapago >= :fechaInicio and det.fechapago <= :fechaFin  and det.idseriefactura.idseriefactura = 8 ORDER BY det.nofactura asc", Long.class)
                    .setParameter("inicio", inicio)
                    .setParameter("fin", fin)
                    .setParameter("fechaInicio", fechaInicio)
                    .setParameter("fechaFin", fechaFin)
                    .getResultList();
        } else if (idsectorpago.equals(SectorPago.MLN3.getId())) {
            lst = em.createQuery("SELECT sum(det.montopagado) FROM Detallepago det where det.activo = true and det.nofactura >= :inicio and det.nofactura <= :fin and det.fechapago >= :fechaInicio and det.fechapago <= :fechaFin and det.idseriefactura.idseriefactura = 5 ORDER BY det.nofactura asc ", Long.class)
                    .setParameter("inicio", inicio)
                    .setParameter("fin", fin)
                    .setParameter("fechaInicio", fechaInicio)
                    .setParameter("fechaFin", fechaFin)
                    .getResultList();
        } else if (idsectorpago.equals(SectorPago.MLN4.getId())) {
            lst = em.createQuery("SELECT sum(det.montopagado) FROM Detallepago det where det.activo = true and det.nofactura >= :inicio and det.nofactura <= :fin and det.fechapago >= :fechaInicio and det.fechapago <= :fechaFin and det.idseriefactura.idseriefactura = 6 ORDER BY det.nofactura asc ", Long.class)
                    .setParameter("inicio", inicio)
                    .setParameter("fin", fin)
                    .setParameter("fechaInicio", fechaInicio)
                    .setParameter("fechaFin", fechaFin)
                    .getResultList();
        } else if (idsectorpago.equals(SectorPago.MLN5.getId())) {
            lst = em.createQuery("SELECT sum(det.montopagado) FROM Detallepago det where det.activo = true and det.nofactura >= :inicio and det.nofactura <= :fin and det.fechapago >= :fechaInicio and det.fechapago <= :fechaFin and det.idseriefactura.idseriefactura = 7 ORDER BY det.nofactura asc ", Long.class)
                    .setParameter("inicio", inicio)
                    .setParameter("fin", fin)
                    .setParameter("fechaInicio", fechaInicio)
                    .setParameter("fechaFin", fechaFin)
                    .getResultList();
        } else if (idsectorpago.equals(SectorPago.SNP1.getId())) {
            lst = em.createQuery("SELECT sum(det.montopagado) FROM Detallepago det where det.activo = true and det.nofactura >= :inicio and det.nofactura <= :fin and det.fechapago >= :fechaInicio and det.fechapago <= :fechaFin and det.idseriefactura.idseriefactura = 10 ORDER BY det.nofactura asc ", Long.class)
                    .setParameter("inicio", inicio)
                    .setParameter("fin", fin)
                    .setParameter("fechaInicio", fechaInicio)
                    .setParameter("fechaFin", fechaFin)
                    .getResultList();
        } else if (idsectorpago.equals(SectorPago.SNP2.getId())) {
            lst = em.createQuery("SELECT sum(det.montopagado) FROM Detallepago det where det.activo = true and det.nofactura >= :inicio and det.nofactura <= :fin and det.fechapago >= :fechaInicio and det.fechapago <= :fechaFin and det.idseriefactura.idseriefactura = 11 ORDER BY det.nofactura asc ", Long.class)
                    .setParameter("inicio", inicio)
                    .setParameter("fin", fin)
                    .setParameter("fechaInicio", fechaInicio)
                    .setParameter("fechaFin", fechaFin)
                    .getResultList();
        } else if (idsectorpago.equals(SectorPago.SNR1.getId())) {
            lst = em.createQuery("SELECT sum(det.montopagado) FROM Detallepago det where det.activo = true and det.nofactura >= :inicio and det.nofactura <= :fin and det.fechapago >= :fechaInicio and det.fechapago <= :fechaFin and det.idseriefactura.idseriefactura = 9 ORDER BY det.nofactura asc ", Long.class)
                    .setParameter("inicio", inicio)
                    .setParameter("fin", fin)
                    .setParameter("fechaInicio", fechaInicio)
                    .setParameter("fechaFin", fechaFin)
                    .getResultList();
        }

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst.get(0);
    }

}
