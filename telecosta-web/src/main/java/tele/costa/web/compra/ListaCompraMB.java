package tele.costa.web.compra;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.RowEditEvent;
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
 * @author elfo_
 */
@ManagedBean(name = "listaCompraMB")
@ViewScoped
public class ListaCompraMB implements Serializable {

    @EJB
    private ComprasBeanLocal compraBean;
    @EJB
    private CatalogoBeanLocal catalogoBean;

    private List<Compra> listCompra;
    private Date fechaInicio;
    private Date fechaFin;
    private List<Tipocompra> listTipoCompra;
    private List<Proveedor> listProveedor;
    private List<Tipodocumentocompra> listTipoDocumento;
    private List<Formapago> listFormaPago;

    @PostConstruct
    void cargarDatos() {
        listCompra = compraBean.listCompra();
        listTipoCompra = catalogoBean.listTipoCompra();
        listTipoDocumento = catalogoBean.listTipoDocumento();
        listFormaPago = catalogoBean.listFormaPago();
        listProveedor = catalogoBean.listProveedor();
    }

    public void buscarCompra() {
        if (fechaInicio != null && fechaFin != null) {
            List<Compra> response = compraBean.listCompraByFechaInicioFechaFin(fechaInicio, fechaFin);
            if (response != null) {
                listCompra = response;
            } else {
                listCompra = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (fechaInicio != null && fechaFin == null) {
            List<Compra> response = compraBean.listCompraByFechaInicio(fechaInicio);
            if (response != null) {
                listCompra = response;
            } else {
                listCompra = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (fechaInicio == null && fechaFin != null) {
            List<Compra> response = compraBean.listCompraByFechaFin(fechaFin);
            if (response != null) {
                listCompra = response;
            } else {
                listCompra = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else {
            listCompra = new ArrayList<>();
            JsfUtil.addErrorMessage("No se encontraron datos");
        }
    }

    public void limpiarCampos() {
        fechaInicio = null;
        fechaFin = null;
    }

    public void detalle(Integer id) {
        JsfUtil.redirectTo("/compras/detalle.xhtml?idcompra=" + id);
    }

    public void eliminarCompra(Integer id) throws IOException {
        Compra response = compraBean.eliminarCompra(id, SesionUsuarioMB.getUserName());
        if (response != null) {
            cargarDatos();
            JsfUtil.addSuccessMessage("Se elimino el pago exitosamente");
            return;
        }

        JsfUtil.addErrorMessage("Sucedio un error al elimnar");
    }

    public boolean obtenerRoot() {
        if (SesionUsuarioMB.getRootUsuario()) {
            return true;
        } else {
            return false;
        }
    }

    public void onRowEdit(RowEditEvent event) {
        Object value = event.getObject();
        Compra tipo = (Compra) value;

        if (tipo != null) {
            Compra tt = compraBean.updateCompra(tipo);
            JsfUtil.addSuccessMessage("Se actualizo la compra exitosamente");
            cargarDatos();
        } else {
            JsfUtil.addErrorMessage("Sucedio un error al actualizar el registro");
        }
    }


    /*Metodos getters y setters*/
    public List<Compra> getListCompra() {
        return listCompra;
    }

    public void setListCompra(List<Compra> listCompra) {
        this.listCompra = listCompra;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<Tipocompra> getListTipoCompra() {
        return listTipoCompra;
    }

    public void setListTipoCompra(List<Tipocompra> listTipoCompra) {
        this.listTipoCompra = listTipoCompra;
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

    public List<Formapago> getListFormaPago() {
        return listFormaPago;
    }

    public void setListFormaPago(List<Formapago> listFormaPago) {
        this.listFormaPago = listFormaPago;
    }

}
