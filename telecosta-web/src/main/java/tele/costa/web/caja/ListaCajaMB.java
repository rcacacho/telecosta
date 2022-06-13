package tele.costa.web.caja;

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
import org.primefaces.context.RequestContext;
import tele.costa.api.ejb.CajaBeanLocal;
import tele.costa.api.ejb.CatalogoBeanLocal;
import tele.costa.api.entity.Cajaagencia;
import tele.costa.api.entity.Municipio;
import tele.costa.api.entity.Sectorpago;
import telecosta.web.utils.JsfUtil;
import telecosta.web.utils.SesionUsuarioMB;

/**
 *
 * @author rcacacho
 */
@ManagedBean(name = "listaCajaMB")
@ViewScoped
public class ListaCajaMB implements Serializable {

    private static final Logger log = Logger.getLogger(ListaCajaMB.class);

    @EJB
    private CatalogoBeanLocal catalogoBean;
    @EJB
    private CajaBeanLocal cajaBean;

    private List<Municipio> listMunicipios;
    private List<Sectorpago> listSectorPago;
    private Municipio municipioSelected;
    private Date fechaInicio;
    private Date fechaFin;
    private List<Cajaagencia> listCaja;
    private Integer idSectorPago;
    private Cajaagencia selectedCajaAgencia;

    @PostConstruct
    void cargarDatos() {
        listMunicipios = catalogoBean.listMunicipioByIdDepartamento(1);
    }

    public void limpiarCampos() {
        fechaInicio = null;
        fechaFin = null;
        municipioSelected = null;
        idSectorPago = null;
        listCaja = new ArrayList<>();

        cargarDatos();
    }

    public void cargarSector() {
        listSectorPago = catalogoBean.listSectorPagoByIdMunicipio(municipioSelected.getIdmunicipio());
    }

    public void detalleCaja(Integer id) {
        JsfUtil.redirectTo("/caja/detalle.xhtml?idcajaagencia=" + id);
    }

    public void buscarCaja() {
        if (fechaInicio != null && fechaFin != null && idSectorPago !=  null) {
            List<Cajaagencia> response = cajaBean.listCajaByFechasAndSector(fechaInicio, fechaFin, idSectorPago);
            if (response != null) {
                listCaja = response;
            } else {
                listCaja = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (fechaInicio != null && fechaFin != null) {
            List<Cajaagencia> response = cajaBean.listCajaByFechas(fechaInicio, fechaFin);
            if (response != null) {
                listCaja = response;
            } else {
                listCaja = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (idSectorPago != null && fechaFin != null) {
            List<Cajaagencia> response = cajaBean.listCajaByFechaFinAndSector(fechaFin, idSectorPago);
            if (response != null) {
                listCaja = response;
            } else {
                listCaja = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (idSectorPago != null && fechaInicio != null) {
            List<Cajaagencia> response = cajaBean.listCajaByFechaInicioAndSector(fechaInicio, idSectorPago);
            if (response != null) {
                listCaja = response;
            } else {
                listCaja = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (idSectorPago != null) {
            List<Cajaagencia> response = cajaBean.listCajaBySector(idSectorPago);
            if (response != null) {
                listCaja = response;
            } else {
                listCaja = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (fechaInicio != null) {
            List<Cajaagencia> response = cajaBean.listCajaByFechaInicio(fechaInicio);
            if (response != null) {
                listCaja = response;
            } else {
                listCaja = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (fechaFin != null) {
            List<Cajaagencia> response = cajaBean.listCajaByFechaFin(fechaFin);
            if (response != null) {
                listCaja = response;
            } else {
                listCaja = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        }
    }

    public void dialogEgreso(Cajaagencia cl) {
        RequestContext.getCurrentInstance().execute("PF('dlgEgreso').show()");
        selectedCajaAgencia = cl;
    }

    public void cerrarDialog() {
        RequestContext.getCurrentInstance().execute("PF('dlgEgreso').hide()");
    }

    public void egresoCaja() throws IOException {
        if (selectedCajaAgencia.getEgreso() == null) {
            JsfUtil.addErrorMessage("Debe ingresar un egreso");
            return;
        }

        if (selectedCajaAgencia.getNodocumento() == null || selectedCajaAgencia.getNodocumento().isEmpty()) {
            JsfUtil.addErrorMessage("Debe de ingresar un documento");
            return;
        }

        if (selectedCajaAgencia.getForma() == null || selectedCajaAgencia.getForma().isEmpty()) {
            JsfUtil.addErrorMessage("Debe ingresar una forma");
            return;
        }

        selectedCajaAgencia.setFechamodificacion(new Date());
        selectedCajaAgencia.setUsuariomodificacion(SesionUsuarioMB.getUserName());
        selectedCajaAgencia.setSaldo(selectedCajaAgencia.getIngreso() - selectedCajaAgencia.getEgreso());
        Cajaagencia response = cajaBean.updateCaja(selectedCajaAgencia);
        if (response != null) {
            JsfUtil.addSuccessMessage("Egreso registrado exitosamente");
            cargarDatos();
        } else {
            JsfUtil.addErrorMessage("Ocurrio un al registrar el egreso");
        }
    }

    /*Metodos getters y setters*/
    public List<Municipio> getListMunicipios() {
        return listMunicipios;
    }

    public void setListMunicipios(List<Municipio> listMunicipios) {
        this.listMunicipios = listMunicipios;
    }

    public List<Sectorpago> getListSectorPago() {
        return listSectorPago;
    }

    public void setListSectorPago(List<Sectorpago> listSectorPago) {
        this.listSectorPago = listSectorPago;
    }

    public Municipio getMunicipioSelected() {
        return municipioSelected;
    }

    public void setMunicipioSelected(Municipio municipioSelected) {
        this.municipioSelected = municipioSelected;
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

    public List<Cajaagencia> getListCaja() {
        return listCaja;
    }

    public void setListCaja(List<Cajaagencia> listCaja) {
        this.listCaja = listCaja;
    }

    public Integer getIdSectorPago() {
        return idSectorPago;
    }

    public void setIdSectorPago(Integer idSectorPago) {
        this.idSectorPago = idSectorPago;
    }

    public Cajaagencia getSelectedCajaAgencia() {
        return selectedCajaAgencia;
    }

    public void setSelectedCajaAgencia(Cajaagencia selectedCajaAgencia) {
        this.selectedCajaAgencia = selectedCajaAgencia;
    }

}
