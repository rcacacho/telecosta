package tele.costa.web.caja;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import tele.costa.api.ejb.CajaBeanLocal;
import tele.costa.api.ejb.CatalogoBeanLocal;
import tele.costa.api.entity.Cajaagencia;
import tele.costa.api.entity.Municipio;
import tele.costa.api.entity.Sectorpago;
import telecosta.web.utils.JsfUtil;

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

    public void limpiarCampos() {
        fechaInicio = null;
        fechaFin = null;
        municipioSelected = null;
        listCaja = new ArrayList<>();

        cargarDatos();
    }

    public void cargarSector() {
        listSectorPago = catalogoBean.listSectorPagoByIdMunicipio(municipioSelected.getIdmunicipio());
    }

    public void detalleCliente(Integer id) {
        JsfUtil.redirectTo("/caja/detalle.xhtml?idcajaagencia=" + id);
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

}
