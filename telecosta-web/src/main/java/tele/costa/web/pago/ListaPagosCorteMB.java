package tele.costa.web.pago;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import tele.costa.api.entity.Detallepago;
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
@ManagedBean(name = "listaPagosCorteMB")
@ViewScoped
public class ListaPagosCorteMB implements Serializable {

    private static final Logger log = Logger.getLogger(ListaPagosCorteMB.class);

    @EJB
    private PagosBeanLocal pagosBean;
    @EJB
    private ClienteBeanLocal clienteBean;
    @EJB
    private CatalogoBeanLocal catalogoBean;

    private List<Pago> listPago;
    private Integer idcliente;
    private Integer anio;
    private Date fechaInicio;
    private Date fechaFin;
    private Date fechaInicioBus;
    private Date fechaFinBus;
    private String mes;
    private List<Cliente> listClientes;
    private Integer idMunicipio;
    private List<Municipio> listMunicipio;

    public ListaPagosCorteMB() {
    }

    @PostConstruct
    void cargarDatos() {
        Date fInicio = new Date();
        Date fFin = new Date();

        if (SesionUsuarioMB.getRootUsuario()) {
            listClientes = clienteBean.ListClientesByIdEstaado(EstadoClienteEnum.CORTE.getId());
            listMunicipio = catalogoBean.listMunicipioByIdDepartamento(1);
        } else {
            List<Usuariomunicipio> listUsuarioMun = catalogoBean.listUsuarioMunicipio(SesionUsuarioMB.getUserId());
            if (listUsuarioMun != null) {
                List<Integer> list = new ArrayList<>();
                for (Usuariomunicipio uu : listUsuarioMun) {
                    list.add(uu.getIdmunicipio().getIdmunicipio());
                }
                listClientes = clienteBean.ListClientesByListMunucipioInactivoAndIdEstado(list, EstadoClienteEnum.CORTE.getId());
                listPago = pagosBean.listPagosByIdMunicipioByList(list);
                listMunicipio = catalogoBean.listMunicipioByIdMunicipioByList(list);
            } else {
                listClientes = clienteBean.ListClientesByIdMunicipioInactivoNoCorteAndIdEstado(SesionUsuarioMB.getIdMunicipio(), EstadoClienteEnum.CORTE.getId());
                listPago = pagosBean.listPagosByIdMunicipio(SesionUsuarioMB.getIdMunicipio());
                listMunicipio = catalogoBean.listMunicipioByIdMunicipio(SesionUsuarioMB.getIdMunicipio());
            }
        }
    }

    public void buscarPago() {
        if (fechaInicioBus != null && fechaFinBus != null && idMunicipio > 0) {
            List<Pago> response = pagosBean.listPagosByFechaInicioAndFinAndIdMunicipio(fechaInicioBus, fechaFinBus, idMunicipio);
            if (response != null) {
                listPago = response;
            } else {
                listPago = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (anio != null && mes != "" && idMunicipio > 0) {
            List<Pago> response = pagosBean.listPagosByAnioAndMesAndMunicipio(anio, mes, idMunicipio);
            if (response != null) {
                listPago = response;
            } else {
                listPago = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (fechaInicioBus != null && fechaFinBus != null) {
            List<Pago> response = pagosBean.listPagosByFechaInicioAndFin(fechaInicioBus, fechaFinBus);
            if (response != null) {
                listPago = response;
            } else {
                listPago = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (anio != null && mes != "") {
            List<Pago> response = pagosBean.listPagoByAnioAndMes(anio, mes);
            if (response != null) {
                listPago = response;
            } else {
                listPago = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (idcliente != 0 && anio != null) {
            List<Pago> response = pagosBean.listPagoByIdClienteAndAnio(idcliente, anio);
            if (response != null) {
                listPago = response;
            } else {
                listPago = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (idcliente != 0) {
            List<Pago> response = pagosBean.listPagoByIdCliente(idcliente);
            if (response != null) {
                listPago = response;
            } else {
                listPago = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (anio != null) {
            List<Pago> response = pagosBean.listPagoByAnio(anio);
            if (response != null) {
                listPago = response;
            } else {
                listPago = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (mes != "") {
            List<Pago> response = pagosBean.listPagoByMes(mes);
            if (response != null) {
                listPago = response;
            } else {
                listPago = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (idMunicipio > 0) {
            List<Pago> response = pagosBean.listPagosByMunicipio(idMunicipio);
            if (response != null) {
                listPago = response;
            } else {
                listPago = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (fechaInicioBus != null) {
            List<Pago> response = pagosBean.listPagosByFechaInicio(fechaInicioBus);
            if (response != null) {
                listPago = response;
            } else {
                listPago = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (fechaFinBus != null) {
            List<Pago> response = pagosBean.listPagosByFechaFin(fechaFinBus);
            if (response != null) {
                listPago = response;
            } else {
                listPago = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else {
            listPago = new ArrayList<>();
            JsfUtil.addErrorMessage("No se encontraron datos");
        }
    }

    public void limpiarCampos() {
        anio = null;
        mes = "";
        idcliente = 0;
        fechaInicioBus = null;
        fechaFinBus = null;
        listPago = null;
        idMunicipio = null;
    }

    public void detalle(Integer id) {
        JsfUtil.redirectTo("/pagos/detalle.xhtml?idpago=" + id);
    }

    public void eliminarPago(Integer id) throws IOException {
        Pago response = pagosBean.eliminarPago(id, SesionUsuarioMB.getUserName());
        if (response != null) {
            Detallepago responseDetalle = pagosBean.eliminarDetallePago(id, SesionUsuarioMB.getUserName());
            buzon();
            JsfUtil.addSuccessMessage("Se elimino el pago exitosamente");
            return;
        }

        JsfUtil.addErrorMessage("Sucedio un error al elimnar");
    }

    public boolean obtenerRoot() {
        if (SesionUsuarioMB.getRootUsuario()) {
            return true;
        } else {
            return false;
        }
    }

    public void buzon() {
        if (SesionUsuarioMB.getRootUsuario()) {
            listClientes = clienteBean.ListClientes();
            listPago = pagosBean.listPagos();
        } else if (SesionUsuarioMB.getIdMunicipio().equals(6)) {
            listClientes = clienteBean.listClientesByInMunucipio();
        } else {
            listClientes = clienteBean.ListClientesByIdMunucipio(SesionUsuarioMB.getIdMunicipio());
            listPago = pagosBean.listPagosByInIdMunicipios();
        }
    }

    public Date obtenerFechPago(Integer idpago) {
        Detallepago response = pagosBean.findDetallePago(idpago);
        if (response != null) {
            if (response.getFechapago() != null) {
                return response.getFechapago();
            }
        } else {
            return null;
        }
        return null;
    }

    /*Metodos getters y setters*/
    public List<Pago> getListPago() {
        return listPago;
    }

    public void setListPago(List<Pago> listPago) {
        this.listPago = listPago;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public List<Cliente> getListClientes() {
        return listClientes;
    }

    public void setListClientes(List<Cliente> listClientes) {
        this.listClientes = listClientes;
    }

    public Integer getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Integer idcliente) {
        this.idcliente = idcliente;
    }

    public Date getFechaInicioBus() {
        return fechaInicioBus;
    }

    public void setFechaInicioBus(Date fechaInicioBus) {
        this.fechaInicioBus = fechaInicioBus;
    }

    public Date getFechaFinBus() {
        return fechaFinBus;
    }

    public void setFechaFinBus(Date fechaFinBus) {
        this.fechaFinBus = fechaFinBus;
    }

    public Integer getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Integer idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public List<Municipio> getListMunicipio() {
        return listMunicipio;
    }

    public void setListMunicipio(List<Municipio> listMunicipio) {
        this.listMunicipio = listMunicipio;
    }

}
