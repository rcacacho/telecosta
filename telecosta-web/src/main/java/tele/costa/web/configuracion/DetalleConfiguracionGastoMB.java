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
@ManagedBean(name = "detalleConfiguracionGastoMB")
@ViewScoped
public class DetalleConfiguracionGastoMB implements Serializable {

    private static final Logger log = Logger.getLogger(DetalleConfiguracionGastoMB.class);

    @EJB
    private ConfiguracionBeanLocal configuracionBean;

    private Integer idtipocompra;
    private Tipocompra configuracionGasto;

    public void cargarDatos() {
        configuracionGasto = configuracionBean.findConfiguracionGasto(idtipocompra);
    }

    public void regresar() {
        JsfUtil.redirectTo("/configuracion/compra/lista.xhtml");
    }

    public void editar() {
        JsfUtil.redirectTo("/configuracion/compra/editar.xhtml?idtipocompra=" + idtipocompra);
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
