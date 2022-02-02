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
@ManagedBean(name = "registroAtencionMB")
@ViewScoped
public class ListaAtencionMB implements Serializable {

    private static final Logger log = Logger.getLogger(RegistroAtencionMB.class);

    @EJB
    private AtencionClienteLocal atencionBean;
    @EJB
    private CatalogoBeanLocal catalogoBean;

    private List<Ruta> listRuta;
    private List<Atencion> listAtencion;
    private Date fechaInicio;
    private Date fechaFin;

    @PostConstruct
    void cargarDatos() {

        if (SesionUsuarioMB.getRootUsuario()) {
            listClientes = atencionBean.listAtenciones();
            listPago = pagosBean.listPagos();
        } else if (SesionUsuarioMB.getIdMunicipio().equals(6)) {
            listClientes = clienteBean.listClientesByInMunucipio();
        } else {
            listClientes = clienteBean.ListClientesByIdMunucipio(SesionUsuarioMB.getIdMunicipio());
            listPago = pagosBean.listPagosByInIdMunicipios();
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

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

}
