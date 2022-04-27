package tele.costa.web.inventario;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import telecosta.web.utils.JsfUtil;
import tele.costa.api.ejb.InsumoBeanLocal;
import tele.costa.api.entity.Inventario;

/**
 *
 * @author elfo_
 */
@ManagedBean(name = "detalleInsumoMB")
@ViewScoped
public class DetalleInsumoMB implements Serializable {

    private static final Logger log = Logger.getLogger(DetalleInsumoMB.class);

    @EJB
    private InsumoBeanLocal bodegaBeanLocal;

    private Integer idinventario;
    private Integer idregresar;
    private Inventario inventario;

    public void cargarDatos() {
        inventario = bodegaBeanLocal.findInventarioById(idinventario);
    }

    public void regresar() {
        if (idregresar.equals(1)) {
            JsfUtil.redirectTo("/inventario/ingreso.xhtml");
        } else if (idregresar.equals(2)) {
            JsfUtil.redirectTo("/inventario/salidas.xhtml");
        } else if (idregresar.equals(3)) {
            JsfUtil.redirectTo("/inventario/traslados.xhtml");
        } else if (idregresar.equals(4)) {
            JsfUtil.redirectTo("/inventario/tecnicos.xhtml");
        }

    }

    public void regresarDetalle() {
        JsfUtil.redirectTo("/insumos/lista.xhtml");
    }

    /*Metodos getters y setters*/
    public Integer getIdinventario() {
        return idinventario;
    }

    public void setIdinventario(Integer idinventario) {
        this.idinventario = idinventario;
    }

    public Integer getIdregresar() {
        return idregresar;
    }

    public void setIdregresar(Integer idregresar) {
        this.idregresar = idregresar;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

}
