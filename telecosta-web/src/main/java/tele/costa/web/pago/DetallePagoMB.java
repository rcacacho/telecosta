package tele.costa.web.pago;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import org.primefaces.event.RowEditEvent;
import tele.costa.api.ejb.CatalogoBeanLocal;
import tele.costa.api.ejb.PagosBeanLocal;
import tele.costa.api.entity.Detallepago;
import tele.costa.api.entity.Pago;
import tele.costa.api.entity.Seriefactura;
import telecosta.web.utils.JsfUtil;
import telecosta.web.utils.SesionUsuarioMB;

/**
 *
 * @author rcacacho
 */
@ManagedBean(name = "detallePagoMB")
@ViewScoped
public class DetallePagoMB implements Serializable {

    private static final Logger log = Logger.getLogger(DetallePagoMB.class);

    @EJB
    private PagosBeanLocal pagosBean;
    @EJB
    private CatalogoBeanLocal catalogoBean;

    private Integer idPago;
    private Pago pago;
    private List<Detallepago> listDetalle;
    private List<Seriefactura> listSerieFactura;
    private Seriefactura serieFactura;

    public void cargarDatos() {
        if (listDetalle == null) {
            pago = pagosBean.findPagoByIdPago(idPago);
            listDetalle = pagosBean.listDetallePago(idPago);
            listSerieFactura = catalogoBean.listSerieFactura();
        }
    }

    public void regresar() {
        JsfUtil.redirectTo("/pagos/lista.xhtml");
    }

    public void editar() {
        JsfUtil.redirectTo("/pagos/editar.xhtml?idpago=" + idPago);
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
        Detallepago tipo = (Detallepago) value;

        if (tipo != null) {
            if (serieFactura != null) {
                tipo.setIdseriefactura(serieFactura);
                tipo.setSerie(serieFactura.getSerie());
            }

            Detallepago tt = pagosBean.updateDetallePago(tipo);
            JsfUtil.addSuccessMessage("Se actualizo el pago exitosamente");
        } else {
            JsfUtil.addErrorMessage("Sucedio un error al actualizar el registro");
        }
        listDetalle = pagosBean.listDetallePago(tipo.getIdpago().getIdpago());
    }

    public void cargarSerie(Seriefactura s) {
        serieFactura = s;
    }

    /*Metodos getters y setters*/
    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public Integer getIdPago() {
        return idPago;
    }

    public void setIdPago(Integer idPago) {
        this.idPago = idPago;
    }

    public List<Detallepago> getListDetalle() {
        return listDetalle;
    }

    public void setListDetalle(List<Detallepago> listDetalle) {
        this.listDetalle = listDetalle;
    }

    public List<Seriefactura> getListSerieFactura() {
        return listSerieFactura;
    }

    public void setListSerieFactura(List<Seriefactura> listSerieFactura) {
        this.listSerieFactura = listSerieFactura;
    }

    public Seriefactura getSerieFactura() {
        return serieFactura;
    }

    public void setSerieFactura(Seriefactura serieFactura) {
        this.serieFactura = serieFactura;
    }

}
