package tele.costa.web.cliente;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import tele.costa.api.ejb.CatalogoBeanLocal;
import tele.costa.api.ejb.ClienteBeanLocal;
import tele.costa.api.ejb.PagosBeanLocal;
import tele.costa.api.entity.Cliente;
import tele.costa.api.entity.Municipio;
import tele.costa.api.entity.Pago;
import telecosta.web.utils.JsfUtil;
import telecosta.web.utils.SesionUsuarioMB;

/**
 *
 * @author elfo_
 */
@ManagedBean(name = "listaClienteMB")
@ViewScoped
public class ListaClienteMB implements Serializable {

    private static final Logger log = Logger.getLogger(ListaClienteMB.class);

    @EJB
    private ClienteBeanLocal clienteBean;
    @EJB
    private CatalogoBeanLocal catalogoBean;
    @EJB
    private PagosBeanLocal pagosBean;

    private List<Cliente> listCliente;
    private String nombre;
    private String codigo;
    private Integer idMunicipio;
    private String sector;
    private List<Municipio> listMunicipios;

    public ListaClienteMB() {
    }

    @PostConstruct
    void cargarDatos() {
        if (SesionUsuarioMB.getRootUsuario()) {
            listCliente = clienteBean.ListClientes();
        } else if (SesionUsuarioMB.getIdMunicipio().equals(6)) {
            listCliente = clienteBean.listClientesByInMunucipio();
        } else if (SesionUsuarioMB.getIdMunicipio().equals(9)) {
            listCliente = clienteBean.listClientesByInMunucipioSanPabloRodeoSanRafael();
        } else {
            listCliente = clienteBean.ListClientesByIdMunucipio(SesionUsuarioMB.getIdMunicipio());
        }

        listMunicipios = catalogoBean.listMunicipioByIdDepartamento(1);
    }

    public void buscarCliente() {
        if (nombre != null && idMunicipio != null && sector != null) {
            List<Cliente> response = clienteBean.ListClientesByNombreAndSectorAndMunicipio(nombre, sector, idMunicipio);
            if (response != null) {
                listCliente = response;
            } else {
                listCliente = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (nombre != null && sector != null) {
            List<Cliente> response = clienteBean.ListClientesByNombreAndSector(nombre, sector);
            if (response != null) {
                listCliente = response;
            } else {
                listCliente = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (nombre != null && idMunicipio != 0) {
            List<Cliente> response = clienteBean.ListClientesByNombreAndMunicipio(nombre, idMunicipio);
            if (response != null) {
                listCliente = response;
            } else {
                listCliente = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (idMunicipio != 0 && sector != null) {
            List<Cliente> response = clienteBean.ListClientesBySectorAndMunicipio(sector, idMunicipio);
            if (response != null) {
                listCliente = response;
            } else {
                listCliente = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (idMunicipio != 0) {
            List<Cliente> response = clienteBean.ListClientesByIdMunucipio(idMunicipio);
            if (response != null) {
                listCliente = response;
            } else {
                listCliente = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (sector != null) {
            List<Cliente> response = clienteBean.ListClientesBySector(sector);
            if (response != null) {
                listCliente = response;
            } else {
                listCliente = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (nombre != null) {
            List<Cliente> response = clienteBean.ListClientesByNombre(nombre);
            if (response != null) {
                listCliente = response;
            } else {
                listCliente = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (codigo != null) {
            List<Cliente> response = clienteBean.ListClientesByCodigo(codigo);
            if (response.size() > 0) {
                listCliente = response;
            } else {
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else {
            JsfUtil.addErrorMessage("No se encontraron datos");
        }
    }

    public void limpiarCampos() {
        nombre = null;
        codigo = null;
        sector = null;
        idMunicipio = null;
        cargarDatos();
    }

    public void detalleCliente(Integer id) {
        JsfUtil.redirectTo("/clientes/detalle.xhtml?idCliente=" + id);
    }

    public String obtenerUltimoPago(Integer idcliente) {
        Pago response = pagosBean.findUltimoPago(idcliente);
        if (response != null) {
            String a = new StringBuilder(response.getMes()).append("-").append(response.getAnio().toString()).toString();
            return a;
        } else {
            return null;
        }
    }

    /*Metodos getters y setters*/
    public List<Cliente> getListCliente() {
        return listCliente;
    }

    public void setListCliente(List<Cliente> listCliente) {
        this.listCliente = listCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List<Municipio> getListMunicipios() {
        return listMunicipios;
    }

    public void setListMunicipios(List<Municipio> listMunicipios) {
        this.listMunicipios = listMunicipios;
    }

    public Integer getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Integer idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

}
