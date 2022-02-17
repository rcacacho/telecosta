package tele.costa.web.compra;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import tele.costa.api.ejb.CatalogoBeanLocal;
import tele.costa.api.ejb.ComprasBeanLocal;
import tele.costa.api.entity.Compra;
import tele.costa.api.entity.Formapago;
import tele.costa.api.entity.Proveedor;
import tele.costa.api.entity.Tipocompra;
import tele.costa.api.entity.Tipodocumentocompra;
import telecosta.web.utils.JsfUtil;
import telecosta.web.utils.SesionUsuarioMB;

/**
 *
 * @author rcacacho
 */
@ManagedBean(name = "edicionCompraMB")
@ViewScoped
public class EdicionCompraMB implements Serializable {

    private static final Logger log = Logger.getLogger(DetalleCompraMB.class);

    @EJB
    private ComprasBeanLocal compraBean;
    @EJB
    private CatalogoBeanLocal catalogoBean;

    private Integer idCompra;
    private Compra compra;
    private List<Proveedor> listProveedor;
    private List<Tipodocumentocompra> listTipoDocumento;
    private List<Tipocompra> listTipoCompra;
    private List<Formapago> listFormaPago;

    public void cargarDatos() {
        compra = compraBean.findCompraById(idCompra);
        listProveedor = catalogoBean.listProveedor();
        listTipoCompra = catalogoBean.listTipoCompra();
        listTipoDocumento = catalogoBean.listTipoDocumento();
        listFormaPago = catalogoBean.listFormaPago();
    }

    public void regresar() {
        JsfUtil.redirectTo("/compras/detalle.xhtml?idcompra=" + idCompra);
    }

    
     public void actualizarCompra() throws IOException {
        compra.setUsuariomodificacion(SesionUsuarioMB.getUserName());
        compra.setFechamodificacion(new Date());
        Compra responseVerificacion = compraBean.updateCompra(compra);
        if (responseVerificacion != null) {
            JsfUtil.addSuccessMessage("Compra actualizada exitosamente");
            JsfUtil.redirectTo("/compra/detalle.xhtml?idcompra=" + compra.getIdcompra());
        } else {
            JsfUtil.addErrorMessage("Ocurrio un error verificar datos");
        }
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

    public List<Proveedor> getListProveedor() {
        return listProveedor;
    }

    public void setListProveedor(List<Proveedor> listProveedor) {
        this.listProveedor = listProveedor;
    }

    public List<Tipodocumentocompra> getListTipoDocumento() {
        return listTipoDocumento;
    }

    public void setListTipoDocumento(List<Tipodocumentocompra> listTipoDocumento) {
        this.listTipoDocumento = listTipoDocumento;
    }

    public List<Tipocompra> getListTipoCompra() {
        return listTipoCompra;
    }

    public void setListTipoCompra(List<Tipocompra> listTipoCompra) {
        this.listTipoCompra = listTipoCompra;
    }

    public List<Formapago> getListFormaPago() {
        return listFormaPago;
    }

    public void setListFormaPago(List<Formapago> listFormaPago) {
        this.listFormaPago = listFormaPago;
    }

}
