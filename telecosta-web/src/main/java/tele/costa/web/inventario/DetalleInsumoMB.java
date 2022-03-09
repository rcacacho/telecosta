package tele.costa.web.inventario;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import tele.costa.api.entity.Insumos;
import telecosta.web.utils.JsfUtil;
import tele.costa.api.ejb.InsumoBeanLocal;

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

    private Integer idinsumo;
    private Insumos insumos;

    public void cargarDatos() {
        insumos = bodegaBeanLocal.findInsumoById(idinsumo);
    }

    public void regresar() {
        JsfUtil.redirectTo("/bodega/ingreso.xhtml");
    }

    /*Metodos getters y setters*/
    public Integer getIdinsumo() {
        return idinsumo;
    }

    public void setIdinsumo(Integer idinsumo) {
        this.idinsumo = idinsumo;
    }

    public Insumos getInsumos() {
        return insumos;
    }

    public void setInsumos(Insumos insumos) {
        this.insumos = insumos;
    }

}
