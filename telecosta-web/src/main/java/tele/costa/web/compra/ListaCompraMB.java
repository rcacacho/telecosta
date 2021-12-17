package tele.costa.web.compra;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import tele.costa.api.ejb.ComprasBeanLocal;
import tele.costa.api.entity.Compra;
import tele.costa.api.entity.Proveedor;

/**
 *
 * @author elfo_
 */
@ManagedBean(name = "listaCompraMB")
@ViewScoped
public class ListaCompraMB implements Serializable {

    @EJB
    private ComprasBeanLocal compraBean;

    private List<Compra> listCompra;
    private Date fechaInicio;
    private Date fechaFin;
    private List<Proveedor> listProveedor;

    @PostConstruct
    void cargarDatos() {
        Date fInicio = new Date();
        Date fFin = new Date();

        listCompra = compraBean.listCompra();
        listProveedor = compraBean.listProveedor();
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

    public List<Proveedor> getListProveedor() {
        return listProveedor;
    }

    public void setListProveedor(List<Proveedor> listProveedor) {
        this.listProveedor = listProveedor;
    }

}
