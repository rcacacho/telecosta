package tele.costa.web.pago;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import tele.costa.api.ejb.CatalogoBeanLocal;
import tele.costa.api.ejb.ClienteBeanLocal;
import tele.costa.api.ejb.PagosBeanLocal;
import tele.costa.api.entity.Cliente;
import tele.costa.api.entity.Cobro;
import tele.costa.api.entity.Detallepago;
import tele.costa.api.entity.Formapago;
import tele.costa.api.entity.Pago;
import tele.costa.api.entity.Seriefactura;
import tele.costa.api.entity.Tipopago;
import tele.costa.api.enums.TipoPagoEnum;
import telecosta.web.utils.JsfUtil;
import telecosta.web.utils.SesionUsuarioMB;

/**
 *
 * @author rcacacho
 */
@ManagedBean(name = "registroPagoClienteMB")
@ViewScoped
public class RegistroPagoClienteMB implements Serializable {

    private static final Logger log = Logger.getLogger(RegistroPagoClienteMB.class);

    @EJB
    private PagosBeanLocal pagosBean;
    @EJB
    private ClienteBeanLocal clienteBean;
    @EJB
    private CatalogoBeanLocal catalogoBean;

    private Pago pago;
    private Detallepago detalle;
    private Cliente cliente;
    private Tipopago tipoPago;
    private List<Formapago> listFormaPago;
    private Integer idcliente;
    private Pago pagoVerificacion;
    private List<Seriefactura> listSerieFactura;
    private Seriefactura serieFactura;

    public RegistroPagoClienteMB() {
        pago = new Pago();
        detalle = new Detallepago();
    }

    public void cargarDatos() {
        if (cliente == null) {
            listFormaPago = catalogoBean.listFormaPago();
            cliente = clienteBean.findClienteById(idcliente);
        }
        listSerieFactura = catalogoBean.listSerieFactura();
    }

    public void savePago() throws IOException {
        if (SesionUsuarioMB.getUserName() == null) {
            JsfUtil.redirectTo("/login.xhtml");
        }

        Pago responseVerificacion = pagosBean.findPagoByIdClienteAndAnioAndMes(cliente.getIdcliente(), pago.getAnio(), pago.getMes());
        if (responseVerificacion != null) {
            pagoVerificacion = responseVerificacion;
            RequestContext.getCurrentInstance().execute("PF('dlgConfirmacion').show()");
        } else {
            pago.setUsuariocreacion(SesionUsuarioMB.getUserName());
            tipoPago = catalogoBean.findTipoPago(TipoPagoEnum.PAGO.getId());
            pago.setIdcliente(cliente);
            pago.setIdtipopago(tipoPago);
            pago.setFechapago(new Date());

            switch (pago.getMes()) {
                case "enero":
                    pago.setNomes(1);
                    break;
                case "febrero":
                    pago.setNomes(2);
                    break;
                case "marzo":
                    pago.setNomes(3);
                    break;
                case "abril":
                    pago.setNomes(4);
                    break;
                case "mayo":
                    pago.setNomes(5);
                    break;
                case "junio":
                    pago.setNomes(6);
                    break;
                case "julio":
                    pago.setNomes(7);
                    break;
                case "agosto":
                    pago.setNomes(8);
                    break;
                case "septiembre":
                    pago.setNomes(9);
                    break;
                case "octubre":
                    pago.setNomes(10);
                    break;
                case "noviembre":
                    pago.setNomes(11);
                    break;
                case "diciembre":
                    pago.setNomes(12);
                    break;
            }

            Pago responsePago = pagosBean.savePago(pago);
            if (responsePago != null) {
                detalle.setUsuariocreacion(SesionUsuarioMB.getUserName());
                detalle.setMontopagado(pago.getTotal());
                detalle.setIdpago(pago);
                detalle.setIdseriefactura(serieFactura);
                detalle.setSerie(serieFactura.getSerie());
                Detallepago responseDet = pagosBean.saveDetallepago(detalle);

                Cobro findCobro = pagosBean.findCobroByIdClienteAndAnioAndMes(pago.getIdcliente().getIdcliente(), pago.getAnio(), pago.getMes());
                if (findCobro != null) {
                    findCobro.setIdpago(pago);
                    findCobro.setCobro(0);
                    findCobro.setFechamodificacion(new Date());
                    findCobro.setUsuariomodificacion(SesionUsuarioMB.getUserName());
                    Cobro responseCobro = pagosBean.updateCobro(findCobro);
                }

                JsfUtil.redirectTo("/clientes/lista.xhtml");
                JsfUtil.addSuccessMessage("El pago se registro exitosamente");
                pago = null;
                cliente = null;
                detalle = null;
                return;
            }
        }
    }

    public void regresar() {
        JsfUtil.redirectTo("/clientes/lista.xhtml");
    }

    public List<Cliente> findPersona(String search) {
        List<Cliente> listado = new ArrayList();
        try {
            if (!search.isEmpty()) {
                List<Cliente> response = clienteBean.ListClientesByNombre(search);
                if (response != null) {
                    listado = response;
                }
            }

            return listado;
        } catch (Exception ex) {
            JsfUtil.addErrorMessage("No encontrado");
            return null;
        }
    }

    public void cargarCliente() {
        pago.setIdcliente(cliente);
    }

    public void actualizarPago() throws IOException {
        pagoVerificacion.setAnio(pago.getAnio());
        pagoVerificacion.setMes(pago.getMes());
        pagoVerificacion.setIdcliente(cliente);
        pagoVerificacion.setFechapago(new Date());

        Integer total = 0;
        if (cliente.getIdconfiguracionpago() != null) {
            total = cliente.getIdconfiguracionpago().getValor();
        }

        if (pago.getTotal() != null) {
            pago.setTotal(total - pago.getTotal());
        }

        Pago updatePago = pagosBean.updatePago(pagoVerificacion);

        detalle.setUsuariocreacion(SesionUsuarioMB.getUserName());
        detalle.setMontopagado(pago.getTotal());
        detalle.setIdpago(pagoVerificacion);
        detalle.setIdseriefactura(serieFactura);
        detalle.setSerie(serieFactura.getSerie());
        Detallepago responseDet = pagosBean.saveDetallepago(detalle);

        Cobro findCobro = pagosBean.findCobroByIdClienteAndAnioAndMes(pagoVerificacion.getIdcliente().getIdcliente(), pagoVerificacion.getAnio(), pagoVerificacion.getMes());
        if (findCobro != null) {
            findCobro.setIdpago(pagoVerificacion);
            findCobro.setCobro(0);
            findCobro.setFechamodificacion(new Date());
            findCobro.setUsuariomodificacion(SesionUsuarioMB.getUserName());
            Cobro responseCobro = pagosBean.updateCobro(findCobro);
        }

        JsfUtil.addSuccessMessage("El pago se registro exitosamente");
        pago = null;
        cliente = null;
        detalle = null;
        JsfUtil.redirectTo("/clientes/lista.xhtml");
        return;
    }

    public void cerrarDialog() {
        RequestContext.getCurrentInstance().execute("PF('dlgConfirmacion').hide()");
    }

    /*Metodos getters y setters*/
    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Formapago> getListFormaPago() {
        return listFormaPago;
    }

    public void setListFormaPago(List<Formapago> listFormaPago) {
        this.listFormaPago = listFormaPago;
    }

    public Tipopago getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(Tipopago tipoPago) {
        this.tipoPago = tipoPago;
    }

    public Detallepago getDetalle() {
        return detalle;
    }

    public void setDetalle(Detallepago detalle) {
        this.detalle = detalle;
    }

    public Integer getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Integer idcliente) {
        this.idcliente = idcliente;
    }

    public Pago getPagoVerificacion() {
        return pagoVerificacion;
    }

    public void setPagoVerificacion(Pago pagoVerificacion) {
        this.pagoVerificacion = pagoVerificacion;
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
