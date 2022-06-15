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
import tele.costa.api.ejb.ClienteBeanLocal;
import tele.costa.api.entity.Cliente;

/**
 *
 * @author elfo_
 */
@Singleton
public class ClienteBean implements ClienteBeanLocal {

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
    public List<Cliente> ListClientes() {
        List<Cliente> lst = em.createQuery("SELECT qj FROM Cliente qj where qj.activo = true ", Cliente.class)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public Cliente saveCliente(Cliente cliente) {
        try {
            cliente.setActivo(true);
            cliente.setFechacreacion(new Date());
            em.persist(cliente);
            em.flush();
            return (cliente);
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
    public Cliente updateCliente(Cliente cliente) {
        try {
            em.merge(cliente);
            em.flush();
            return (cliente);
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
    public List<Cliente> ListClientesByNombre(String nombre) {
        if (nombre == null) {
            return null;
        }

        List<Cliente> lst = em.createQuery("SELECT col FROM Cliente col WHERE col.nombres like :nombre ", Cliente.class)
                .setParameter("nombre", '%' + nombre + '%')
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Cliente> ListClientesByCodigo(String codigo) {
        if (codigo == null) {
            return null;
        }

        List<Cliente> lst = em.createQuery("SELECT col FROM Cliente col WHERE col.codigo like :codigo ", Cliente.class)
                .setParameter("codigo", '%' + codigo + '%')
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public Cliente findClienteById(Integer idcliente) {
        if (idcliente == null) {
            return null;
        }

        List<Cliente> lst = em.createQuery("SELECT col FROM Cliente col WHERE col.idcliente =:idcliente ", Cliente.class)
                .setParameter("idcliente", idcliente)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst.get(0);
    }

    @Override
    public List<Cliente> ListClientesByIdMunucipio(Integer idmunicipio) {
        List<Cliente> lst = em.createQuery("SELECT qj FROM Cliente qj where qj.activo = true and qj.idmunicipio.idmunicipio =:idmunicipio ", Cliente.class)
                .setParameter("idmunicipio", idmunicipio)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Cliente> ListClientesByNombreAndSectorAndMunicipio(String nombre, String sector, Integer idmunicipio) {
        if (sector == null) {
            return null;
        }

        List<Cliente> lst = em.createQuery("SELECT col FROM Cliente col WHERE col.sector like :sector  and col.idmunicipio.idmunicipio =:idmunicipio ", Cliente.class)
                .setParameter("sector", '%' + sector + '%')
                .setParameter("idmunicipio", idmunicipio)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Cliente> ListClientesByNombreAndSector(String nombre, String sector) {
        if (nombre == null) {
            return null;
        }

        List<Cliente> lst = em.createQuery("SELECT col FROM Cliente col WHERE col.nombres like :sector and col.sector like :sector  ", Cliente.class)
                .setParameter("nombre", '%' + nombre + '%')
                .setParameter("sector", '%' + sector + '%')
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Cliente> ListClientesByNombreAndMunicipio(String nombre, Integer idmunicipio) {
        if (nombre == null) {
            return null;
        }

        List<Cliente> lst = em.createQuery("SELECT col FROM Cliente col WHERE col.nombres like :sector and col.idmunicipio.idmunicipio =:idmunicipio ", Cliente.class)
                .setParameter("nombre", '%' + nombre + '%')
                .setParameter("idmunicipio", idmunicipio)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Cliente> ListClientesBySectorAndMunicipio(String sector, Integer idmunicipio) {
        if (sector == null) {
            return null;
        }

        List<Cliente> lst = em.createQuery("SELECT col FROM Cliente col WHERE col.sector like :sector and col.idmunicipio.idmunicipio =:idmunicipio ", Cliente.class)
                .setParameter("sector", '%' + sector + '%')
                .setParameter("idmunicipio", idmunicipio)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Cliente> ListClientesBySector(String sector) {
        if (sector == null) {
            return null;
        }

        List<Cliente> lst = em.createQuery("SELECT col FROM Cliente col WHERE col.sector like :sector ", Cliente.class)
                .setParameter("sector", '%' + sector + '%')
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Cliente> listClientesByInMunucipio() {
        List<Cliente> lst = em.createQuery("SELECT qj FROM Cliente qj where qj.activo = true and qj.idmunicipio.idmunicipio in (1,6,7) ", Cliente.class)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Cliente> listClientesByInMunucipioSanPabloRodeoSanRafael() {
        List<Cliente> lst = em.createQuery("SELECT qj FROM Cliente qj where qj.activo = true and qj.idmunicipio.idmunicipio in (3,6,7) ", Cliente.class)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Cliente> ListClientesByIdSector(Integer idSector) {
        List<Cliente> lst = em.createQuery("SELECT qj FROM Cliente qj where qj.activo = true and qj.idSector.idsector =:idSector and qj.idestadocliente.idestadocliente = 1 ", Cliente.class)
                .setParameter("idSector", idSector)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Cliente> ListClientesCobro() {
        List<Cliente> lst = em.createQuery("SELECT qj FROM Cliente qj where qj.activo = true and (qj.suspendido is null or qj.suspendido = false) ", Cliente.class)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Cliente> ListClientesByTipoCliente(String tipocliente) {
        List<Cliente> lst = em.createQuery("SELECT qj FROM Cliente qj where qj.activo = true and qj.tipocliente =:tipocliente ", Cliente.class)
                .setParameter("tipocliente", tipocliente)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Cliente> ListClientesByIdMunucipioAndTipo(Integer idmunicipio, String tipocliente) {
        List<Cliente> lst = em.createQuery("SELECT qj FROM Cliente qj where qj.activo = true and qj.idmunicipio.idmunicipio =:idmunicipio and qj.tipocliente =:tipocliente ", Cliente.class)
                .setParameter("idmunicipio", idmunicipio)
                .setParameter("tipocliente", tipocliente)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Cliente> ListClientesInactivos() {
        List<Cliente> lst = em.createQuery("SELECT qj FROM Cliente qj ", Cliente.class)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Cliente> listClientesByInMunucipioInactivos() {
        List<Cliente> lst = em.createQuery("SELECT qj FROM Cliente qj where qj.idmunicipio.idmunicipio in (1,6,7) ", Cliente.class)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Cliente> listClientesByInMunucipioSanPabloRodeoSanRafaelInactivo() {
        List<Cliente> lst = em.createQuery("SELECT qj FROM Cliente qj where  qj.idmunicipio.idmunicipio in (3,6,7) ", Cliente.class)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Cliente> ListClientesByIdMunicipioInactivo(Integer idmunicipio) {
        List<Cliente> lst = em.createQuery("SELECT qj FROM Cliente qj where qj.idmunicipio.idmunicipio =:idmunicipio ", Cliente.class)
                .setParameter("idmunicipio", idmunicipio)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Cliente> ListClientesByListMunucipioInactivo(List<Integer> listIdmunicipio) {
        List<Cliente> lst = em.createQuery("SELECT qj FROM Cliente qj where qj.idmunicipio.idmunicipio in :listIdmunicipio ", Cliente.class)
                .setParameter("listIdmunicipio", listIdmunicipio)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Cliente> ListClientesByIdMunucipioCobro(Integer idmunicipio) {
        List<Cliente> lst = em.createQuery("SELECT qj FROM Cliente qj where qj.activo = true and qj.idmunicipio.idmunicipio =:idmunicipio and qj.idestadocliente.idestadocliente = 1 ", Cliente.class)
                .setParameter("idmunicipio", idmunicipio)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Cliente> ListClientesEstadoActivo() {
        List<Cliente> lst = em.createQuery("SELECT qj FROM Cliente qj where qj.idestadocliente.idestadocliente = 1 and qj.activo = true ", Cliente.class)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Cliente> ListClientesByListMunucipioEstadoActivo(List<Integer> listIdmunicipio) {
        List<Cliente> lst = em.createQuery("SELECT qj FROM Cliente qj where qj.idmunicipio.idmunicipio in :listIdmunicipio and qj.idestadocliente.idestadocliente = 1 and qj.activo = true ", Cliente.class)
                .setParameter("listIdmunicipio", listIdmunicipio)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Cliente> ListClientesByIdMunicipioEstadoActivo(Integer idmunicipio) {
        List<Cliente> lst = em.createQuery("SELECT qj FROM Cliente qj where qj.idmunicipio.idmunicipio =:idmunicipio and qj.idestadocliente.idestadocliente = 1 and qj.activo = true ", Cliente.class)
                .setParameter("idmunicipio", idmunicipio)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Cliente> ListClientesNoCorte() {
        List<Cliente> lst = em.createQuery("SELECT qj FROM Cliente qj where qj.activo = true and qj.idestadocliente.idestadocliente in (1,2) ", Cliente.class)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Cliente> ListClientesByListMunucipioInactivoNoCorte(List<Integer> listIdmunicipio) {
        List<Cliente> lst = em.createQuery("SELECT qj FROM Cliente qj where qj.idmunicipio.idmunicipio in :listIdmunicipio and qj.idestadocliente.idestadocliente in (1,2) ", Cliente.class)
                .setParameter("listIdmunicipio", listIdmunicipio)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Cliente> ListClientesByIdMunicipioInactivoNoCorte(Integer idmunicipio) {
        List<Cliente> lst = em.createQuery("SELECT qj FROM Cliente qj where qj.idmunicipio.idmunicipio =:idmunicipio and qj.idestadocliente.idestadocliente in (1,2)  ", Cliente.class)
                .setParameter("idmunicipio", idmunicipio)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Cliente> ListClientesByIdMunicipioByIdEstado(Integer idmunicipio, Integer idestadocliente) {
        List<Cliente> lst = em.createQuery("SELECT qj FROM Cliente qj where qj.idmunicipio.idmunicipio =:idmunicipio and qj.idestadocliente.idestadocliente =:idestadocliente and qj.activo = true ", Cliente.class)
                .setParameter("idmunicipio", idmunicipio)
                .setParameter("idestadocliente", idestadocliente)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Cliente> ListClientesByIdMunicipioByIdEstadoAndFechas(Integer idmunicipio, Integer idestadocliente, Date fechainicio, Date fechafin) {
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

        List<Cliente> lst = em.createQuery("SELECT qj FROM Cliente qj where qj.idmunicipio.idmunicipio =:idmunicipio and qj.idestadocliente.idestadocliente =:idestadocliente and qj.activo = true and qj.fechacreacion >=:fechainicio and qj.fechacreacion <=:fechafin ", Cliente.class)
                .setParameter("idmunicipio", idmunicipio)
                .setParameter("idestadocliente", idestadocliente)
                .setParameter("fechainicio", fechainicio)
                .setParameter("fechafin", fechafin)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Cliente> ListClientesByIdEstadoAndFechas(Integer idestadocliente, Date fechainicio, Date fechafin) {
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

        List<Cliente> lst = em.createQuery("SELECT qj FROM Cliente qj where qj.idestadocliente.idestadocliente =:idestadocliente and qj.activo = true and qj.fechacreacion >=:fechainicio and qj.fechacreacion <=:fechafin ", Cliente.class)
                .setParameter("idestadocliente", idestadocliente)
                .setParameter("fechainicio", fechainicio)
                .setParameter("fechafin", fechafin)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Cliente> ListClientesByNombreNoCorte(String nombre) {
        if (nombre == null) {
            return null;
        }

        List<Cliente> lst = em.createQuery("SELECT col FROM Cliente col WHERE col.nombres like :nombre and col.idestadocliente.idestadocliente in (1,2) ", Cliente.class)
                .setParameter("nombre", '%' + nombre + '%')
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Cliente> ListClientesByIdMunucipioNoCorte(Integer idmunicipio) {
        List<Cliente> lst = em.createQuery("SELECT qj FROM Cliente qj where qj.activo = true and qj.idmunicipio.idmunicipio =:idmunicipio and qj.idestadocliente.idestadocliente in (1,2) ", Cliente.class)
                .setParameter("idmunicipio", idmunicipio)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Cliente> ListClientesByIdEstaado(Integer idestadocliente) {
        List<Cliente> lst = em.createQuery("SELECT qj FROM Cliente qj where qj.activo = true  and qj.idestadocliente.idestadocliente =:idestadocliente", Cliente.class)
                .setParameter("idestadocliente", idestadocliente)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Cliente> ListClientesByListMunucipioInactivoAndIdEstado(List<Integer> listIdmunicipio, Integer idestadocliente) {
        List<Cliente> lst = em.createQuery("SELECT qj FROM Cliente qj where qj.idmunicipio.idmunicipio in :listIdmunicipio and qj.idestadocliente.idestadocliente =:idestadocliente ", Cliente.class)
                .setParameter("listIdmunicipio", listIdmunicipio)
                .setParameter("idestadocliente", idestadocliente)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Cliente> ListClientesByIdMunicipioInactivoNoCorteAndIdEstado(Integer idmunicipio, Integer idestadocliente) {
        List<Cliente> lst = em.createQuery("SELECT qj FROM Cliente qj where qj.idmunicipio.idmunicipio =:idmunicipio and qj.idestadocliente.idestadocliente =:idestadocliente  ", Cliente.class)
                .setParameter("idmunicipio", idmunicipio)
                .setParameter("idestadocliente", idestadocliente)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

}
