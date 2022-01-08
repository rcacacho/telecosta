package tele.costa.web.compra;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import tele.costa.api.ejb.CatalogoBeanLocal;
import tele.costa.api.ejb.ComprasBeanLocal;
import tele.costa.api.entity.Compra;
import tele.costa.api.entity.Proveedor;
import telecosta.web.utils.JsfUtil;

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

    @PostConstruct
    void cargarDatos() {
        listCompra = compraBean.listCompra();
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

}
