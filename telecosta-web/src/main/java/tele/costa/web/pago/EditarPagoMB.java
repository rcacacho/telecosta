package tele.costa.web.pago;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
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
@ManagedBean(name = "editarPagoMB")
@ViewScoped
public class EditarPagoMB implements Serializable {

    private static final Logger log = Logger.getLogger(EditarPagoMB.class);

    @EJB
    private PagosBeanLocal pagosBean;

    private Pago pago;
    private Integer idpago;
    private List<Detallepago> listDetalle;

    public void cargarDatos() {
        pago = pagosBean.findPagoByIdPago(idpago);
        listDetalle = pagosBean.listDetallePago(idpago);
    }

    public void regresar() {
        JsfUtil.redirectTo("/pagos/detalle.xhtml?idpago=" + idpago);
    }

    public void editarPago() throws IOException {
        pago.setUsuariomodificacion(SesionUsuarioMB.getUserName());
        pago.setFechamodificacion(new Date());
        Pago responseVerificacion = pagosBean.updatePago(pago);
        if (responseVerificacion != null) {
            JsfUtil.addSuccessMessage("Pago actualizado exitosamente");
            JsfUtil.redirectTo("/pagos/detalle.xhtml?idpago=" + idpago);
        } else {
            JsfUtil.addErrorMessage("Ocurrio un error verificar datos");
        }
    }

    /*Metodos getters y setters*/
    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public Integer getIdpago() {
        return idpago;
    }

    public void setIdpago(Integer idpago) {
        this.idpago = idpago;
    }

    public List<Detallepago> getListDetalle() {
        return listDetalle;
    }

    public void setListDetalle(List<Detallepago> listDetalle) {
        this.listDetalle = listDetalle;
    }

}
