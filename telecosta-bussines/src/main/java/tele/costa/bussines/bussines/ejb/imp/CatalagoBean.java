package tele.costa.bussines.bussines.ejb.imp;

import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import tele.costa.api.ejb.CatalogoBeanLocal;
import tele.costa.api.entity.Agencia;
import tele.costa.api.entity.Configuracionpago;
import tele.costa.api.entity.Departamento;
import tele.costa.api.entity.Estadocliente;
import tele.costa.api.entity.Formapago;
import tele.costa.api.entity.Insumos;
import tele.costa.api.entity.Municipio;
import tele.costa.api.entity.Proveedor;
import tele.costa.api.entity.Ruta;
import tele.costa.api.entity.Sector;
import tele.costa.api.entity.Sectorpago;
import tele.costa.api.entity.Tipoatencion;
import tele.costa.api.entity.Tipocarga;
import tele.costa.api.entity.Tipocompra;
import tele.costa.api.entity.Tipodocumentocompra;
import tele.costa.api.entity.Tipopago;
import tele.costa.api.entity.Usuario;
import tele.costa.api.entity.Usuariomunicipio;

/**
 *
 * @author elfo_
 */
@Singleton
public class CatalagoBean implements CatalogoBeanLocal {

    private static final Logger log = Logger.getLogger(CatalagoBean.class);

    @PersistenceContext(unitName = "TeleCostaPU")
    EntityManager em;

    @Resource
    private EJBContext context;

    @Override
    public List<Municipio> listMunicipioByIdDepartamento(Integer iddepartamento) {
        if (iddepartamento == null) {
            return null;
        }

        List<Municipio> lst = em.createQuery("SELECT muni FROM Municipio muni WHERE muni.iddepartamento.iddepartamento =:iddepartamento", Municipio.class)
                .setParameter("iddepartamento", iddepartamento)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Departamento> listDepartamentos() {
        List<Departamento> lst = em.createQuery("SELECT dep FROM Departamento dep WHERE dep.activo  = true", Departamento.class)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public Tipopago findTipoPago(Integer idtipopago) {
        List<Tipopago> lst = em.createQuery("SELECT dep FROM Tipopago dep WHERE dep.activo  = true and dep.idtipopago =:idtipopago", Tipopago.class)
                .setParameter("idtipopago", idtipopago)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst.get(0);
    }

    @Override
    public Configuracionpago findConfiguracionPago(Integer idconfiguracionpago) {
        List<Configuracionpago> lst = em.createQuery("SELECT dep FROM Configuracionpago dep WHERE dep.activo  = true and dep.idconfiguracionpago =:idconfiguracionpago ", Configuracionpago.class)
                .setParameter("idconfiguracionpago", idconfiguracionpago)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst.get(0);
    }

    @Override
    public List<Configuracionpago> ListConfiguracionPago() {
        List<Configuracionpago> lst = em.createQuery("SELECT dep FROM Configuracionpago dep WHERE dep.activo  = true ", Configuracionpago.class)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Proveedor> listProveedor() {
        List<Proveedor> lst = em.createQuery("SELECT qj FROM Proveedor qj where qj.activo = true ", Proveedor.class)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Tipodocumentocompra> listTipoDocumento() {
        List<Tipodocumentocompra> lst = em.createQuery("SELECT qj FROM Tipodocumentocompra qj where qj.activo = true ", Tipodocumentocompra.class)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Tipocompra> listTipoCompra() {
        List<Tipocompra> lst = em.createQuery("SELECT qj FROM Tipocompra qj where qj.activo = true and qj.idtipocompra not in (60)", Tipocompra.class)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Formapago> listFormaPago() {
        List<Formapago> lst = em.createQuery("SELECT qj FROM Formapago qj where qj.activo = true ", Formapago.class)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Usuario> listaUsuarios() {
        List<Usuario> lst = em.createQuery("SELECT qj FROM Usuario qj where qj.activo = true", Usuario.class)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Sector> listSector() {
        List<Sector> lst = em.createQuery("SELECT qj FROM Sector qj where qj.activo = true", Sector.class)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Ruta> listRuta() {
        List<Ruta> lst = em.createQuery("SELECT qj FROM Ruta qj where qj.activo = true", Ruta.class)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Tipoatencion> listTipoAtencion() {
        List<Tipoatencion> lst = em.createQuery("SELECT qj FROM Tipoatencion qj where qj.activo = true", Tipoatencion.class)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Sectorpago> listSectorPagoByIdMunicipio(Integer idmunicipio) {
        List<Sectorpago> lst = em.createQuery("SELECT dep FROM Sectorpago dep WHERE dep.activo  = true and dep.idmunicipio.idmunicipio =:idmunicipio", Sectorpago.class)
                .setParameter("idmunicipio", idmunicipio)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Municipio> listMunicipioBySanRafaelSanPableRodeo() {
        List<Municipio> lst = em.createQuery("SELECT muni FROM Municipio muni WHERE muni.idmunicipio in (3,6,7) ", Municipio.class)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Municipio> listMunicipioByIdMunicipio(Integer idMunicipio) {
        if (idMunicipio == null) {
            return null;
        }

        List<Municipio> lst = em.createQuery("SELECT muni FROM Municipio muni WHERE muni.idmunicipio =:idMunicipio", Municipio.class)
                .setParameter("idMunicipio", idMunicipio)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Municipio> listMunicipioBySanpabloAndSanRafael() {
        List<Municipio> lst = em.createQuery("SELECT muni FROM Municipio muni WHERE muni.idmunicipio in (6,7) ", Municipio.class)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public List<Agencia> listAgencias() {
        List<Agencia> lst = em.createQuery("SELECT ag FROM Agencia ag WHERE ag.activo= true ", Agencia.class)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public Tipocarga findTipoCarga(Integer idtipocarga) {
        List<Tipocarga> lst = em.createQuery("SELECT dep FROM Tipocarga dep WHERE dep.activo  = true and dep.idtipocarga =:idtipocarga ", Tipocarga.class)
                .setParameter("idtipocarga", idtipocarga)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst.get(0);
    }

    @Override
    public Estadocliente findEstadoCliente(Integer idestadocliente) {
        List<Estadocliente> lst = em.createQuery("SELECT dep FROM Estadocliente dep WHERE dep.activo  = true and dep.idestadocliente =:idestadocliente", Estadocliente.class)
                .setParameter("idestadocliente", idestadocliente)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst.get(0);
    }

    @Override
    public List<Insumos> listInsumos() {
        List<Insumos> lst = em.createQuery("SELECT qj FROM Insumos qj where qj.activo = true", Insumos.class)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public Tipocompra findTipoCompra(Integer idtipocompra) {
        List<Tipocompra> lst = em.createQuery("SELECT dep FROM Tipocompra dep WHERE dep.activo  = true and dep.idtipocompra =:idtipocompra ", Tipocompra.class)
                .setParameter("idtipocompra", idtipocompra)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst.get(0);
    }

    @Override
    public Tipodocumentocompra findTipoDocumentoCompra(Integer idtipodocumentocompra) {
        List<Tipodocumentocompra> lst = em.createQuery("SELECT dep FROM Tipodocumentocompra dep WHERE dep.activo  = true and dep.idtipodocumentocompra =:idtipodocumentocompra ", Tipodocumentocompra.class)
                .setParameter("idtipodocumentocompra", idtipodocumentocompra)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst.get(0);
    }

    @Override
    public Formapago findFormaPago(Integer idformapago) {
        List<Formapago> lst = em.createQuery("SELECT dep FROM Formapago dep WHERE dep.activo  = true and dep.idformapago =:idformapago ", Formapago.class)
                .setParameter("idformapago", idformapago)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst.get(0);
    }

    @Override
    public List<Usuariomunicipio> listUsuarioMunicipio(Integer idusuario) {
        List<Usuariomunicipio> lst = em.createQuery("SELECT qj FROM Usuariomunicipio qj where qj.activo = true and qj.idusuario.idusuario =:idusuario", Usuariomunicipio.class)
                .setParameter("idusuario", idusuario)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

}
