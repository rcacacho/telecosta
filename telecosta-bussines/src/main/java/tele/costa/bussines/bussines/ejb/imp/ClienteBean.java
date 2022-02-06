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

        List<Cliente> lst = em.createQuery("SELECT col FROM Cliente col WHERE col.codigo =:codigo ", Cliente.class)
                .setParameter("codigo", codigo)
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
        List<Cliente> lst = em.createQuery("SELECT qj FROM Cliente qj where qj.activo = true and qj.idmunicipio.idmunicipio in (6,7) ", Cliente.class)
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

}
