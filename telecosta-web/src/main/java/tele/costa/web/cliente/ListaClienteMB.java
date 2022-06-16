package tele.costa.web.cliente;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import tele.costa.api.ejb.CatalogoBeanLocal;
import tele.costa.api.ejb.ClienteBeanLocal;
import tele.costa.api.ejb.PagosBeanLocal;
import tele.costa.api.entity.Cliente;
import tele.costa.api.entity.Configuracionpago;
import tele.costa.api.entity.Estadocliente;
import tele.costa.api.entity.Municipio;
import tele.costa.api.entity.Pago;
import tele.costa.api.entity.Usuariomunicipio;
import tele.costa.api.enums.EstadoClienteEnum;
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
    private String motivoCorte;
    private Cliente clienteSelected;
    private List<Configuracionpago> listConfiguracionPago;
    private String color;

    public ListaClienteMB() {
    }

    @PostConstruct
    void cargarDatos() {
        if (SesionUsuarioMB.getRootUsuario()) {
            listCliente = clienteBean.ListClientesInactivos();
        } else {
            List<Usuariomunicipio> listUsuarioMun = catalogoBean.listUsuarioMunicipio(SesionUsuarioMB.getUserId());
            if (listUsuarioMun != null) {
                List<Integer> list = new ArrayList<>();
                for (Usuariomunicipio uu : listUsuarioMun) {
                    list.add(uu.getIdmunicipio().getIdmunicipio());
                }
                listCliente = clienteBean.ListClientesByListMunucipioInactivo(list);
            } else {
                listCliente = clienteBean.ListClientesByIdMunicipioInactivo(SesionUsuarioMB.getIdMunicipio());
            }
        }

        listMunicipios = catalogoBean.listMunicipioByIdDepartamento(1);
        listConfiguracionPago = catalogoBean.ListConfiguracionPago();
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
            if (response != null) {
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

            if (response.getFechapago() != null) {
                Date fechaInicio = new Date();
                LocalDate startDate = response.getFechapago().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate endDate = fechaInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                ZoneId defaultZoneId = ZoneId.systemDefault();
                Integer count = 0;
                for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
                    count++;
                }

                if (count >= 90) {
                    color = "background-color:#FFD1D1 !important;";
                } else if (count >= 60 && count <= 89) {
                    color = "background-color:#F6F7BB !important;";
                } else if (count >= 30 && count <= 59) {
                    color = "background-color:#F7BF8D !important;";
                }
            }

            String a = new StringBuilder(response.getMes()).append("-").append(response.getAnio().toString()).toString();
            return a;
        } else {
            return null;
        }
    }

    public void suspenderCliente(Cliente cl) throws IOException {
        Estadocliente estado = catalogoBean.findEstadoCliente(EstadoClienteEnum.SUSPENDIDO.getId());
        cl.setIdestadocliente(estado);
        cl.setFechamodificacion(new Date());
        cl.setUsuariomodificacion(SesionUsuarioMB.getUserName());
        Cliente response = clienteBean.updateCliente(cl);
        if (response != null) {
            JsfUtil.addSuccessMessage("Cliente suspendido exitosamente");
        } else {
            JsfUtil.addErrorMessage("Ocurrio un al inactivar al cliente");
        }
    }

    public void dialogCorte(Cliente cl) {
        RequestContext.getCurrentInstance().execute("PF('dlgCorte').show()");
        motivoCorte = null;
        clienteSelected = cl;
    }

    public void cerrarDialog() {
        RequestContext.getCurrentInstance().execute("PF('dlgCorte').hide()");
    }

    public void corteCliente() throws IOException {
        Estadocliente estado = catalogoBean.findEstadoCliente(EstadoClienteEnum.CORTE.getId());
        clienteSelected.setIdestadocliente(estado);
        clienteSelected.setFechaeliminacion(new Date());
        clienteSelected.setUsuarioeliminacion(SesionUsuarioMB.getUserName());
        clienteSelected.setMotivoeliminacion(motivoCorte);
        Cliente response = clienteBean.updateCliente(clienteSelected);
        if (response != null) {
            JsfUtil.addSuccessMessage("Cliente inactivado exitosamente");
            cargarDatos();
        } else {
            JsfUtil.addErrorMessage("Ocurrio un al inactivar al cliente");
        }
    }

    public void pagoCliente(Integer id) {
        JsfUtil.redirectTo("/pagos/registroCliente.xhtml?idCliente=" + id);
    }

    public void onRowEdit(RowEditEvent event) {
        Object value = event.getObject();
        Cliente tipo = (Cliente) value;

        if (tipo != null) {
            Cliente tt = clienteBean.updateCliente(tipo);
            JsfUtil.addSuccessMessage("Se actualizo el cliente exitosamente");
            cargarDatos();
        } else {
            JsfUtil.addErrorMessage("Sucedio un error al actualizar el registro");
        }
    }

    public void ticketCliente(Integer id) {
        JsfUtil.redirectTo("/atencion/registroCliente.xhtml?idcliente=" + id);
    }

    public void activarCliente(Cliente cl) throws IOException {
        Estadocliente estado = catalogoBean.findEstadoCliente(EstadoClienteEnum.ACTIVO.getId());
        cl.setIdestadocliente(estado);
        cl.setFechamodificacion(new Date());
        cl.setUsuariomodificacion(SesionUsuarioMB.getUserName());
        Cliente response = clienteBean.updateCliente(cl);
        if (response != null) {
            JsfUtil.addSuccessMessage("Cliente activado exitosamente");
        } else {
            JsfUtil.addErrorMessage("Ocurrio un al activar al cliente");
        }
    }

    public void pagoMaterialCliente(Integer id) {
        JsfUtil.redirectTo("/pagos/registroMaterial.xhtml?idCliente=" + id);
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

    public String getMotivoCorte() {
        return motivoCorte;
    }

    public void setMotivoCorte(String motivoCorte) {
        this.motivoCorte = motivoCorte;
    }

    public List<Configuracionpago> getListConfiguracionPago() {
        return listConfiguracionPago;
    }

    public void setListConfiguracionPago(List<Configuracionpago> listConfiguracionPago) {
        this.listConfiguracionPago = listConfiguracionPago;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
