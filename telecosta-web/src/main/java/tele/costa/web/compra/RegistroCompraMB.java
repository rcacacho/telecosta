package tele.costa.web.compra;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
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
@ManagedBean(name = "registroCompraMB")
@ViewScoped
public class RegistroCompraMB implements Serializable {

    @EJB
    private ComprasBeanLocal compraBean;
    @EJB
    private CatalogoBeanLocal catalogoBean;

    private Compra compra;
    private Proveedor proveedor;
    private List<Proveedor> listProveedor;
    private List<Tipodocumentocompra> listTipoDocumento;
    private List<Tipocompra> listTipoCompra;
    private List<Formapago> listFormaPago;
    private boolean mostrarCheque;

    public RegistroCompraMB() {
        compra = new Compra();
        proveedor = new Proveedor();
        mostrarCheque = Boolean.FALSE;
    }

    @PostConstruct
    void cargarDatos() {
        listProveedor = catalogoBean.listProveedor();
        listTipoCompra = catalogoBean.listTipoCompra();
        listTipoDocumento = catalogoBean.listTipoDocumento();
        listFormaPago = catalogoBean.listFormaPago();
    }

    public void saveCompra() throws IOException {
        if (compra.getIdtipocompra() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar un tipo de compra");
            return;
        }

        if (compra.getIdtipodocumentocompra() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar un tipo de documento");
            return;
        }

        if (compra.getIdproveedor() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar un proveedor");
            return;
        }

        if (compra.getBienoservicio() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar un o servicio");
            return;
        }

        if (compra.getNocomprobanteegreso() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar un comprobante de egreso");
            return;
        }

        if (compra.getNodocumento() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar un n??mero de documento");
            return;
        }

        if (compra.getSerie() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar una  serie");
            return;
        }

        if (compra.getFechacompra() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar una fecha de compra");
            return;
        }

        if (compra.getIdformapago() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar una forma de pago");
            return;
        }

        compra.setUsuariocreacion(SesionUsuarioMB.getUserName());
        compra.setFechacreacion(new Date());
        Compra responseVerificacion = compraBean.saveCompra(compra);
        if (responseVerificacion != null) {
            JsfUtil.addSuccessMessage("Compra registrada exitosamente");
        } else {
            JsfUtil.addErrorMessage("Ocurrio un error verificar datos");
        }

        compra = null;
    }

    public void saveProveedor() throws IOException {
        proveedor.setUsuariocreacion(SesionUsuarioMB.getUserName());
        proveedor.setFechacreacion(new Date());
        Proveedor responseVerificacion = compraBean.saveProveedor(proveedor);
        if (responseVerificacion != null) {
            JsfUtil.addSuccessMessage("Proveedor registrado exitosamente");
        } else {
            JsfUtil.addErrorMessage("Ocurrio un error verificar datos");
        }

        proveedor = null;
        cargarDatos();
    }

    public void regresar() {
        JsfUtil.redirectTo("/compras/lista.xhtml");
    }

    public void cargarDialog() {
        RequestContext.getCurrentInstance().execute("PF('dlgProveedor').show()");
    }

    public void cerrarDialog() {
        RequestContext.getCurrentInstance().execute("PF('dlgProveedor').hide()");
    }

    public void cargarForma() {
        if (compra.getIdformapago().getIdformapago().equals(5)) {
            mostrarCheque = Boolean.TRUE;
        } else {
            mostrarCheque = Boolean.FALSE;
        }
    }

    /*Metodos getters y setters*/
    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
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

    public boolean isMostrarCheque() {
        return mostrarCheque;
    }

    public void setMostrarCheque(boolean mostrarCheque) {
        this.mostrarCheque = mostrarCheque;
    }

}
