package tele.costa.web.pago;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import tele.costa.api.ejb.PagosBeanLocal;
import tele.costa.api.entity.Detallepago;
import tele.costa.api.entity.Pago;
import telecosta.web.utils.JsfUtil;
import telecosta.web.utils.SesionUsuarioMB;

/**
 *
 * @author rcacacho
 */
@ManagedBean(name = "detallePagoMB")
@ViewScoped
public class DetallePagoMB implements Serializable {

    private static final Logger log = Logger.getLogger(DetallePagoMB.class);

    @EJB
    private PagosBeanLocal pagosBean;

    private Integer idPago;
    private Pago pago;
    private List<Detallepago> listDetalle;

    public void cargarDatos() {
        pago = pagosBean.findPagoByIdPago(idPago);
        listDetalle = pagosBean.listDetallePago(idPago);
    }

    public void regresar() {
        JsfUtil.redirectTo("/pagos/lista.xhtml");
    }

    public void editar() {
        JsfUtil.redirectTo("/pagos/editar.xhtml?idpago=" + idPago);
    }

    public boolean obtenerRoot() {
        if (SesionUsuarioMB.getRootUsuario()) {
            return true;
        } else {
            return false;
        }

    }

    /*Metodos getters y setters*/
    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public Integer getIdPago() {
        return idPago;
    }

    public void setIdPago(Integer idPago) {
        this.idPago = idPago;
    }

    public List<Detallepago> getListDetalle() {
        return listDetalle;
    }

    public void setListDetalle(List<Detallepago> listDetalle) {
        this.listDetalle = listDetalle;
    }

}
