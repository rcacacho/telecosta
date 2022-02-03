package tele.costa.web.atencion;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import tele.costa.api.ejb.AtencionClienteLocal;
import tele.costa.api.ejb.CatalogoBeanLocal;
import tele.costa.api.entity.Atencion;
import tele.costa.api.entity.Ruta;
import telecosta.web.utils.SesionUsuarioMB;

/**
 *
 * @author rcacacho
 */
@ManagedBean(name = "listaAtencionMB")
@ViewScoped
public class ListaAtencionMB implements Serializable {

    private static final Logger log = Logger.getLogger(ListaAtencionMB.class);

    @EJB
    private AtencionClienteLocal atencionBean;
    @EJB
    private CatalogoBeanLocal catalogoBean;

    private Integer idRuta;
    private List<Ruta> listRuta;
    private List<Atencion> listAtencion;
    private Date fechaInicio;
    private Date fechaFin;

    @PostConstruct
    void cargarDatos() {

        if (SesionUsuarioMB.getRootUsuario()) {
            listAtencion = atencionBean.listAtenciones();
        } else if (SesionUsuarioMB.getIdMunicipio().equals(6)) {
            listAtencion = atencionBean.listAtencionByMunicipio();
        } else {
            listAtencion = atencionBean.listAtencionByIdMunicipio(SesionUsuarioMB.getIdMunicipio());
        }
    }

    public void limpiarCampos() {
        idRuta = null;
        fechaInicio = null;
        fechaFin = null;
        cargarDatos();
    }

    public void buscarAtencion() {

    }

    public String estadoAtencion(boolean estado) {
        if (estado) {
            return "Pendiente";
        } else {
            return "Finalizado";
        }
    }

    /*Metodos getters y setters */
    public List<Ruta> getListRuta() {
        return listRuta;
    }

    public void setListRuta(List<Ruta> listRuta) {
        this.listRuta = listRuta;
    }

    public List<Atencion> getListAtencion() {
        return listAtencion;
    }

    public void setListAtencion(List<Atencion> listAtencion) {
        this.listAtencion = listAtencion;
    }

    public Integer getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(Integer idRuta) {
        this.idRuta = idRuta;
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
