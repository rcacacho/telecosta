package tele.costa.web.caja;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import tele.costa.api.ejb.CatalogoBeanLocal;
import tele.costa.api.entity.Cajaagencia;
import tele.costa.api.entity.Municipio;
import tele.costa.api.entity.Sectorpago;
import tele.costa.web.cliente.RegistroClienteMB;

/**
 *
 * @author rcacacho
 */
@ManagedBean(name = "registroCajaMB")
@ViewScoped
public class RegistroCajaMB implements Serializable {

    private static final Logger log = Logger.getLogger(RegistroClienteMB.class);

    @EJB
    private CatalogoBeanLocal catalogoBean;

    private Cajaagencia caja;
    private List<Municipio> listMunicipios;
    private List<Sectorpago> listSectorPago;
    private Municipio municipioSelected;
    private Date fechaInicio;
    private Date fechaFin;

    @PostConstruct
    void cargarDatos() {
        listMunicipios = catalogoBean.listMunicipioByIdDepartamento(1);
    }

    public void cargarSector() {
        listSectorPago = catalogoBean.listSectorPagoByIdMunicipio(municipioSelected.getIdmunicipio());
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
    
    

}
