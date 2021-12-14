package tele.costa.web.configuracion;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import tele.costa.api.ejb.ConfiguracionBeanLocal;
import tele.costa.api.entity.Configuracionpago;
import telecosta.web.utils.JsfUtil;

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

    public void cargarDatos() {
        listConfiguracion = configuracionBean.listConfiguracionPago();
    }

    public void linkRegistro() {
        JsfUtil.redirectTo("/usuario/registro.xhtml");
    }

    public String verDetalle(Integer id) {
        return "detalle.xhtml?faces-redirect=true&idColaborador=" + id;
    }

    /*Metodos getters y setters*/
    public List<Configuracionpago> getListConfiguracion() {
        return listConfiguracion;
    }

    public void setListConfiguracion(List<Configuracionpago> listConfiguracion) {
        this.listConfiguracion = listConfiguracion;
    }

}
