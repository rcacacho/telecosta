package tele.costa.web.configuracion;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import tele.costa.api.ejb.ConfiguracionBeanLocal;
import tele.costa.api.entity.Tipocompra;
import telecosta.web.utils.JsfUtil;

/**
 *
 * @author rcacacho
 */
@ManagedBean(name = "edicionConfiguracionGastoMB")
@ViewScoped
public class EdicionConfiguracionGastoMB implements Serializable {

    private static final Logger log = Logger.getLogger(EdicionConfiguracionGastoMB.class);

    @EJB
    private ConfiguracionBeanLocal configuracionBen;

    private Integer idtipocompra;
    private Tipocompra configuracionGasto;

    public void cargarDatos() {
        configuracionGasto = configuracionBen.findConfiguracionGasto(idtipocompra);
    }

    public void actualizarConfiguracion() {
        Tipocompra responseVerificacion = configuracionBen.actualizarConfiguracionGasto(configuracionGasto);
        if (responseVerificacion != null) {
            JsfUtil.addSuccessMessage("Configuración actualizada exitosamente");
        } else {
            JsfUtil.addErrorMessage("Ocurrio un error verificar datos");
        }
    }

    /*Metodos getters y setters*/
    public void regresar() {
        JsfUtil.redirectTo("/configuracion/compra/detalle.xhtml?idtipocompra=" + idtipocompra);
    }

    /*Metodos getters y setters*/
    public Integer getIdtipocompra() {
        return idtipocompra;
    }

    public void setIdtipocompra(Integer idtipocompra) {
        this.idtipocompra = idtipocompra;
    }

    public Tipocompra getConfiguracionGasto() {
        return configuracionGasto;
    }

    public void setConfiguracionGasto(Tipocompra configuracionGasto) {
        this.configuracionGasto = configuracionGasto;
    }
}
