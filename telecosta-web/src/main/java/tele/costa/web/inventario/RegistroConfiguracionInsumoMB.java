package tele.costa.web.inventario;

import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import tele.costa.api.ejb.InsumoBeanLocal;
import tele.costa.api.entity.Insumos;
import telecosta.web.utils.JsfUtil;
import telecosta.web.utils.SesionUsuarioMB;

/**
 *
 * @author rcacacho
 */
@ManagedBean(name = "registroConfiguracionInsumoMB")
@ViewScoped
public class RegistroConfiguracionInsumoMB implements Serializable {

    @EJB
    private InsumoBeanLocal insumoBeanLocal;

    private Insumos insumo;

    public RegistroConfiguracionInsumoMB() {
        insumo = new Insumos();
    }

    public void saveInsumo() throws IOException {
        if (insumo.getCodigo() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar un código");
            return;
        }

        if (insumo.getDescripcion() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar una descripción");
            return;
        }

        insumo.setUsuariocreacion(SesionUsuarioMB.getUserName());

        Insumos response = insumoBeanLocal.saveInsumo(insumo);
        if (response != null) {
            JsfUtil.addSuccessMessage("Insumo registrado exitosamente");
        } else {
            JsfUtil.addErrorMessage("Ocurrio un error verificar datos");
        }

        insumo = null;
    }

    public void regresar() {
        JsfUtil.redirectTo("/configuracion/insumos/lista.xhtml");
    }

    /*Metodos getters y setters*/
    public Insumos getInsumo() {
        return insumo;
    }

    public void setInsumo(Insumos insumo) {
        this.insumo = insumo;
    }

}
