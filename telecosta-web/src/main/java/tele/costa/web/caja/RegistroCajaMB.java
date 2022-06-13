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
import tele.costa.api.ejb.CajaBeanLocal;
import tele.costa.api.ejb.CatalogoBeanLocal;
import tele.costa.api.entity.Cajaagencia;
import tele.costa.api.entity.Detallepago;
import tele.costa.api.entity.Municipio;
import tele.costa.api.entity.Sectorpago;
import telecosta.web.utils.JsfUtil;
import telecosta.web.utils.SesionUsuarioMB;

/**
 *
 * @author rcacacho
 */
@ManagedBean(name = "registroCajaMB")
@ViewScoped
public class RegistroCajaMB implements Serializable {

    private static final Logger log = Logger.getLogger(RegistroCajaMB.class);

    @EJB
    private CatalogoBeanLocal catalogoBean;
    @EJB
    private CajaBeanLocal cajaBean;

    private Cajaagencia caja;
    private List<Municipio> listMunicipios;
    private List<Sectorpago> listSectorPago;
    private Municipio municipioSelected;
    private Date fechaInicio;
    private Date fechaFin;
    private List<Detallepago> listDetalle;
    private List<Detallepago> selectedDetallePago;

    public RegistroCajaMB() {
        caja = new Cajaagencia();
    }

    @PostConstruct
    void cargarDatos() {
        listMunicipios = catalogoBean.listMunicipioByIdDepartamento(1);
    }

    public void cargarSector() {
        listSectorPago = catalogoBean.listSectorPagoByIdMunicipio(municipioSelected.getIdmunicipio());
    }

    public void cargarFacturas() {
        if (caja.getIdsectorpago() == null) {
            JsfUtil.addErrorMessage("Debe seleccionar un sector para realizar la busqueda");
            return;
        }

        if (fechaInicio == null) {
            JsfUtil.addErrorMessage("Debe ingresar una fecha inicio");
            return;
        }

        listDetalle = cajaBean.listNoFactura(fechaInicio, fechaFin, caja.getIdsectorpago().getIdsectorpago());
    }

    public void calcularMonto() {
        if (caja.getCorrelativodel() == null) {
            JsfUtil.addErrorMessage("Debe seleccionar un sector para realizar la busqueda");
            return;
        }

        Long det = 0L;
        det = cajaBean.findMontoFacturaSerie(caja.getCorrelativodel(), caja.getCorrelativoal(), caja.getIdsectorpago().getIdsectorpago(), fechaInicio, fechaFin);
        if (det > 0) {
            caja.setIngreso(det.intValue());
        }
    }

    public void regresar() {
        JsfUtil.redirectTo("/caja/lista.xhtml");
    }

    public void saveCaja() throws IOException {
        if (caja.getIngreso() == null) {
            JsfUtil.addErrorMessage("Debe de calcular un total facturado");
            return;
        }

        if (caja.getIdsectorpago() == null) {
            JsfUtil.addErrorMessage("Debe seleccionar un sector");
            return;
        }

        caja.setUsuariocreacion(SesionUsuarioMB.getUserName());
        Cajaagencia responseVerificacion = cajaBean.saveCaja(caja);
        if (responseVerificacion != null) {
            JsfUtil.addSuccessMessage("Caja registrada exitosamente");
        } else {
            JsfUtil.addErrorMessage("Ocurrio un error verificar datos");
        }
        caja = new Cajaagencia();
        listDetalle = new ArrayList<>();
        listSectorPago = new ArrayList<>();
        listMunicipios = new ArrayList<>();
        fechaFin = null;
        fechaInicio = null;
    }

    /*Metodos getters y setters*/
    public Cajaagencia getCaja() {
        return caja;
    }

    public void setCaja(Cajaagencia caja) {
        this.caja = caja;
    }

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

    public List<Detallepago> getListDetalle() {
        return listDetalle;
    }

    public void setListDetalle(List<Detallepago> listDetalle) {
        this.listDetalle = listDetalle;
    }

    public List<Detallepago> getSelectedDetallePago() {
        return selectedDetallePago;
    }

    public void setSelectedDetallePago(List<Detallepago> selectedDetallePago) {
        this.selectedDetallePago = selectedDetallePago;
    }

}
