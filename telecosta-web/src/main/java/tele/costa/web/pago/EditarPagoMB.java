package tele.costa.web.pago;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import tele.costa.api.ejb.PagosBeanLocal;
import tele.costa.api.entity.Pago;
import telecosta.web.utils.JsfUtil;

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

     public void cargarDatos() {
        pago = pagosBean.findPagoByIdPago(idpago);
    }
    
    public void regresar() {
        JsfUtil.redirectTo("/pagos/detalle.xhtml?idpago=" + idpago);
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

}
