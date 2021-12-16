package tele.costa.web.configuracion;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import tele.costa.api.ejb.ConfiguracionBeanLocal;
import tele.costa.api.entity.Configuracionpago;
import telecosta.web.utils.JsfUtil;
import telecosta.web.utils.SesionUsuarioMB;

/**
 *
 * @author elfo_
 */
@ManagedBean(name = "listaConfiguracionMB")
@ViewScoped
public class ListaConfiguracionMB implements Serializable {

    private static final Logger log = Logger.getLogger(ListaConfiguracionMB.class);

    @EJB
    private ConfiguracionBeanLocal configuracionBean;

    private List<Configuracionpago> listConfiguracion;

    public ListaConfiguracionMB() {
    }

    @PostConstruct
    public void cargarDatos() {
        listConfiguracion = configuracionBean.listConfiguracionPago();
    }

    public void linkRegistro() {
        JsfUtil.redirectTo("/configuracion/registro.xhtml");
    }

    public void detalle(Integer id) {
        JsfUtil.redirectTo("/configuracion/detalle.xhtml?idconfiguracionpago=" + id);
    }

    public void eliminar(Integer id) throws IOException {
        String usuario = SesionUsuarioMB.getUserName();
        Configuracionpago response = configuracionBean.deleteConfiguracionPago(id, usuario);
        if (response != null) {
            JsfUtil.addSuccessMessage("Se elimino la configuración exitosamente");
            cargarDatos();
            return;
        }

        JsfUtil.addErrorMessage("Sucedio un error al elimnar");
    }

    public void editarPerfil(Configuracionpago per) {
        Configuracionpago response = configuracionBean.actualizarConfiguracion(per);
        if (response != null) {
            JsfUtil.addSuccessMessage("Se actualizo la configuración exitosamente");
            return;
        }

        JsfUtil.addErrorMessage("Sucedio un error al actualizar");
    }

    /*Metodos getters y setters*/
    public List<Configuracionpago> getListConfiguracion() {
        return listConfiguracion;
    }

    public void setListConfiguracion(List<Configuracionpago> listConfiguracion) {
        this.listConfiguracion = listConfiguracion;
    }

}
