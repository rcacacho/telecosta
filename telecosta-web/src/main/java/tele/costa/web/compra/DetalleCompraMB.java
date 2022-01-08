package tele.costa.web.compra;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import tele.costa.api.ejb.ComprasBeanLocal;
import tele.costa.api.entity.Compra;
import telecosta.web.utils.JsfUtil;

/**
 *
 * @author rcacacho
 */
@ManagedBean(name = "detalleCompraMB")
@ViewScoped
public class DetalleCompraMB implements Serializable {

    private static final Logger log = Logger.getLogger(DetalleCompraMB.class);

    @EJB
    private ComprasBeanLocal compraBean;

    private Integer idCompra;
    private Compra compra;

    public void cargarDatos() {
        compra = compraBean.findCompraById(idCompra);
    }

    public void regresar() {
        JsfUtil.redirectTo("/compras/lista.xhtml");
    }

    /*Metodos getters y setters*/
    public Integer getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Integer idCompra) {
        this.idCompra = idCompra;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

}
