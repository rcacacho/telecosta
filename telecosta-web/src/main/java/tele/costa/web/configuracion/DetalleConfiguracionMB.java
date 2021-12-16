package tele.costa.web.configuracion;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import tele.costa.api.ejb.ConfiguracionBeanLocal;
import tele.costa.api.entity.Configuracionpago;
import telecosta.web.utils.JsfUtil;

/**
 *
 * @author rcacacho
 */
@ManagedBean(name = "detalleConfiguracionMB")
@ViewScoped
public class DetalleConfiguracionMB  implements Serializable{
    
     private static final Logger log = Logger.getLogger(ListaConfiguracionMB.class);

    @EJB
    private ConfiguracionBeanLocal configuracionBean;
    
    private Integer idconfiguracionpago;
    private Configuracionpago configuracion;
    
    public void cargarDatos() {
        configuracion = configuracionBean.findConfiguracionPago(idconfiguracionpago);
    }
    
    
    public void regresar() {
        JsfUtil.redirectTo("/configuracion/lista.xhtml");
    }
    
    public void editar() {
        JsfUtil.redirectTo("/configuracion/editar.xhtml?idconfiguracionpago=" + idconfiguracionpago);
    }


    /*Metodos getters y setters*/
    public Integer getIdconfiguracionpago() {
        return idconfiguracionpago;
    }

    public void setIdconfiguracionpago(Integer idconfiguracionpago) {
        this.idconfiguracionpago = idconfiguracionpago;
    }

    public Configuracionpago getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(Configuracionpago configuracion) {
        this.configuracion = configuracion;
    }
    
    
    
}
