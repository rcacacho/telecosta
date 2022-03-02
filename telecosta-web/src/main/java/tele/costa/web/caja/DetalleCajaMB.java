package tele.costa.web.caja;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import tele.costa.api.ejb.CajaBeanLocal;
import tele.costa.api.entity.Cajaagencia;
import telecosta.web.utils.JsfUtil;

/**
 *
 * @author rcacacho
 */
@ManagedBean(name = "detalleCajaMB")
@ViewScoped
public class DetalleCajaMB implements Serializable {

    private static final Logger log = Logger.getLogger(DetalleCajaMB.class);

    @EJB
    private CajaBeanLocal cajaBean;

    private Integer idcajaagencia;
    private Cajaagencia caja;
    
      public void cargarDatos() {
        caja = cajaBean.findCajaByIdCaja(idcajaagencia);
    }

    public void regresar() {
        JsfUtil.redirectTo("/caja/lista.xhtml");
    }
    
//    public void editar() {
//        JsfUtil.redirectTo("/clientes/editar.xhtml?idCliente=" + idcliente);
//    }

    /*Metodos getters y setters*/
    public Integer getIdcajaagencia() {
        return idcajaagencia;
    }

    public void setIdcajaagencia(Integer idcajaagencia) {
        this.idcajaagencia = idcajaagencia;
    }

    public Cajaagencia getCaja() {
        return caja;
    }

    public void setCaja(Cajaagencia caja) {
        this.caja = caja;
    }

}
