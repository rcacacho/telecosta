package tele.costa.web.atencion;

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
import tele.costa.api.ejb.AtencionClienteLocal;
import tele.costa.api.ejb.CatalogoBeanLocal;
import tele.costa.api.entity.Atencion;
import tele.costa.api.entity.Detalleatencion;
import tele.costa.api.entity.Ruta;
import telecosta.web.utils.JsfUtil;
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
    private Atencion selectedAtencion;
    private Detalleatencion detalle;
    private List<Detalleatencion> listDetalle;

    public ListaAtencionMB() {
        detalle = new Detalleatencion();
        listDetalle = new ArrayList<>();
    }

    @PostConstruct
    void cargarDatos() {
        if (SesionUsuarioMB.getRootUsuario()) {
            listAtencion = atencionBean.listAtenciones();
        } else if (SesionUsuarioMB.getIdMunicipio().equals(6)) {
            listAtencion = atencionBean.listAtencionByMunicipio();
        } else if (SesionUsuarioMB.getIdMunicipio().equals(3)) {
            listAtencion = atencionBean.listAtencionByMunicipioByInMunucipioSanPabloRodeoSanRafael();
        } else {
            listAtencion = atencionBean.listAtencionByIdMunicipio(SesionUsuarioMB.getIdMunicipio());
        }

        listRuta = catalogoBean.listRuta();
    }

    public void limpiarCampos() {
        idRuta = null;
        fechaInicio = null;
        fechaFin = null;
        cargarDatos();
    }

    public void buscarAtencion() {
        if (fechaInicio != null && fechaFin != null && idRuta != null) {
            List<Atencion> response = atencionBean.listAtencionByFechasAndRuta(fechaInicio, fechaFin, idRuta);
            if (response != null) {
                listAtencion = response;
            } else {
                listAtencion = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (fechaInicio != null && fechaFin != null) {
            List<Atencion> response = atencionBean.listAtencionByFechas(fechaInicio, fechaFin);
            if (response != null) {
                listAtencion = response;
            } else {
                listAtencion = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (fechaInicio != null) {
            List<Atencion> response = atencionBean.listAtencionByFechaInicio(fechaInicio);
            if (response != null) {
                listAtencion = response;
            } else {
                listAtencion = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (fechaFin != null) {
            List<Atencion> response = atencionBean.listAtencionByFechaFin(fechaFin);
            if (response != null) {
                listAtencion = response;
            } else {
                listAtencion = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (idRuta != null) {
            List<Atencion> response = atencionBean.listAtencionByRuta(idRuta);
            if (response != null) {
                listAtencion = response;
            } else {
                listAtencion = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else {
            listAtencion = new ArrayList<>();
            JsfUtil.addErrorMessage("No se encontraron datos");
        }
    }

    public String estadoAtencion(boolean estado) {
        if (estado) {
            return "Pendiente";
        } else {
            return "Finalizado";
        }
    }

    public void detalleAtencion(Integer id) {
        JsfUtil.redirectTo("/atencion/detalle.xhtml?idatencion=" + id);
    }

    public void finalizacionAtencion(Atencion at) {
        selectedAtencion = at;
        RequestContext.getCurrentInstance().execute("PF('dlgTicket').show()");
    }

    public void cargarDetalle() {
        listDetalle.add(detalle);
        detalle = null;
        detalle = new Detalleatencion();
    }

    public void cerrarDialog() {
        RequestContext.getCurrentInstance().execute("PF('dlgTicket').hide()");
    }

    public void eliminarDet(Integer id) {
        listDetalle.remove(id);
    }

    public void GuardarDetalleAtencion() throws IOException {
        for (Detalleatencion det : listDetalle) {
            det.setIdatencion(selectedAtencion);
            det.setUsuariocreacion(SesionUsuarioMB.getUserName());
            Detalleatencion detalle = atencionBean.saveDetalleAtencion(det);
        }

        selectedAtencion.setFechamodificacion(new Date());
        selectedAtencion.setUsuariomodificacion(SesionUsuarioMB.getUserName());
        selectedAtencion.setEstado(false);
        Atencion responseAtencion = atencionBean.updateAtencion(selectedAtencion);
        JsfUtil.addSuccessMessage("El ticket fue finalizado correctamente");
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

    public Atencion getSelectedAtencion() {
        return selectedAtencion;
    }

    public void setSelectedAtencion(Atencion selectedAtencion) {
        this.selectedAtencion = selectedAtencion;
    }

    public Detalleatencion getDetalle() {
        return detalle;
    }

    public void setDetalle(Detalleatencion detalle) {
        this.detalle = detalle;
    }

    public List<Detalleatencion> getListDetalle() {
        return listDetalle;
    }

    public void setListDetalle(List<Detalleatencion> listDetalle) {
        this.listDetalle = listDetalle;
    }

}
