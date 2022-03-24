package tele.costa.web.cobro;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import tele.costa.api.ejb.PagosBeanLocal;
import tele.costa.api.entity.Cobro;
import tele.costa.web.pago.DetallePagoMB;
import telecosta.web.utils.JsfUtil;

/**
 *
 * @author elfo_
 */
@ManagedBean(name = "detalleCobroMB")
@ViewScoped
public class DetalleCobroMB implements Serializable {

    private static final Logger log = Logger.getLogger(DetallePagoMB.class);

    @EJB
    private PagosBeanLocal pagosBean;

    private Integer idCobro;
    private Cobro cobro;

    public void cargarDatos() {
        cobro = pagosBean.findCobroByIdCobro(idCobro);
    }

    public void regresar() {
        JsfUtil.redirectTo("/cobros/lista.xhtml");
    }

    /*Metodos getters y setters*/
    public Cobro getCobro() {
        return cobro;
    }

    public void setCobro(Cobro cobro) {
        this.cobro = cobro;
    }

    public Integer getIdCobro() {
        return idCobro;
    }

    public void setIdCobro(Integer idCobro) {
        this.idCobro = idCobro;
    }

}
