package tele.costa.web.bodega.compras;

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
 * @author elfo_
 */
@ManagedBean(name = "registroBodegaCompraMB")
@ViewScoped
public class RegistroBodegaCompraMB implements Serializable {
    
    @EJB
    private ComprasBeanLocal compraBean;
    @EJB
    private CatalogoBeanLocal catalogoBean;
    
    private Compra compra;
    private Proveedor proveedor;
    private List<Proveedor> listProveedor;
    
    public RegistroBodegaCompraMB() {
        compra = new Compra();
        proveedor = new Proveedor();
    }
    
    @PostConstruct
    void cargarDatos() {
        listProveedor = catalogoBean.listProveedor();
    }
    
    public void saveCompra() throws IOException {
        Tipocompra tipo = catalogoBean.findTipoCompra(60);
        compra.setIdtipocompra(tipo);
        
        Tipodocumentocompra documento = catalogoBean.findTipoDocumentoCompra(1);
        compra.setIdtipodocumentocompra(documento);
        
        Formapago forma = catalogoBean.findFormaPago(1);
        compra.setIdformapago(forma);
        
        compra.setBienoservicio("Bienes");
        
        if (compra.getIdproveedor() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar un proveedor");
            return;
        }
        
        if (compra.getNodocumento() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar un número de documento");
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
    
    public void cargarTotal() {
        Double dnum = Double.valueOf(compra.getCantidad());
        compra.setTotal(dnum * compra.getMontocompra());   
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
    
}
