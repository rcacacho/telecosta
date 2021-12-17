package tele.costa.web.configuracion;

import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import tele.costa.api.ejb.ConfiguracionBeanLocal;
import tele.costa.api.entity.Tipocompra;
import telecosta.web.utils.JsfUtil;
import telecosta.web.utils.SesionUsuarioMB;

/**
 *
 * @author rcacacho
 */
@ManagedBean(name = "registroConfiguracionGastoMB")
@ViewScoped
public class RegistroConfiguracionGastoMB implements Serializable {

    private static final Logger log = Logger.getLogger(RegistroConfiguracionGastoMB.class);

    @EJB
    private ConfiguracionBeanLocal configuracionBean;

    private Tipocompra configuracionGasto;

    public RegistroConfiguracionGastoMB() {
        configuracionGasto = new Tipocompra();
    }

    public void regresar() {
        JsfUtil.redirectTo("/configuracion/compra/lista.xhtml");
    }

    public void saveConfiguracion() throws IOException {
        configuracionGasto.setUsuariocreacion(SesionUsuarioMB.getUserName());
        Tipocompra response = configuracionBean.saveConfiguracionGasto(configuracionGasto);
        if (response != null) {
            JsfUtil.addSuccessMessage("Configuración registrada exitosamente");
            configuracionGasto = null;
            return;
        }
    }

    /*Metodos getters y setters*/
    public Tipocompra getConfiguracionGasto() {
        return configuracionGasto;
    }

    public void setConfiguracionGasto(Tipocompra configuracionGasto) {
        this.configuracionGasto = configuracionGasto;
    }

}
