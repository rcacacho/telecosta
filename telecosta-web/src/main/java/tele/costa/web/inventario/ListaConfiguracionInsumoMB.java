package tele.costa.web.inventario;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import org.primefaces.event.RowEditEvent;
import tele.costa.api.ejb.InsumoBeanLocal;
import tele.costa.api.entity.Insumos;
import telecosta.web.utils.JsfUtil;
import telecosta.web.utils.SesionUsuarioMB;

/**
 *
 * @author rcacacho
 */
@ManagedBean(name = "listaConfiguracionInsumoMB")
@ViewScoped
public class ListaConfiguracionInsumoMB implements Serializable {

    private static final Logger log = Logger.getLogger(ListaConfiguracionInsumoMB.class);

    @EJB
    private InsumoBeanLocal insumoBean;

    private List<Insumos> listInsumo;

    @PostConstruct
    public void cargarDatos() {
        listInsumo = insumoBean.listInsumo();
    }

    public void linkRegistro() {
        JsfUtil.redirectTo("/configuracion/insumos/registro.xhtml");
    }

    public void detalle(Integer id) {
        JsfUtil.redirectTo("/configuracion/insumos/detalle.xhtml?idinsumo=" + id);
    }

    public void eliminar(Integer id) throws IOException {
        String usuario = SesionUsuarioMB.getUserName();
        Insumos response = insumoBean.deleteInsumo(id, usuario);
        if (response != null) {
            JsfUtil.addSuccessMessage("Se elimino la configuración exitosamente");
            cargarDatos();
            return;
        }

        JsfUtil.addErrorMessage("Sucedio un error al elimnar");
    }

    public void onRowEdit(RowEditEvent event) {
        Object value = event.getObject();
        Insumos tipo = (Insumos) value;

        if (tipo != null) {
            Insumos tt = insumoBean.updateInsumo(tipo);
            JsfUtil.addSuccessMessage("Se actualizo el insumo exitosamente");
            cargarDatos();
        } else {
            JsfUtil.addErrorMessage("Sucedio un error al actualizar el registro");
        }
    }

    /*Metodos getters y setters*/
    public List<Insumos> getListInsumo() {
        return listInsumo;
    }

    public void setListInsumo(List<Insumos> listInsumo) {
        this.listInsumo = listInsumo;
    }

}
