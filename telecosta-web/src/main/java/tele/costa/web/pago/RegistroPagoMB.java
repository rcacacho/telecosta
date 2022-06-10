package tele.costa.web.pago;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
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
import tele.costa.api.entity.Municipio;
import tele.costa.api.entity.Pago;
import tele.costa.api.entity.Tipopago;
import tele.costa.api.enums.TipoPagoEnum;
import telecosta.web.utils.JsfUtil;
import telecosta.web.utils.SesionUsuarioMB;

/**
 *
 * @author elfo_
 */
@ManagedBean(name = "registroPagoMB")
@ViewScoped
public class RegistroPagoMB implements Serializable {

    private static final Logger log = Logger.getLogger(ListaPagosMB.class);

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
    private List<Cliente> listClientes;
    private Municipio municipioSelected;
    private List<Municipio> listMunicipios;
    private Pago pagoVerificacion;

    public RegistroPagoMB() {
        pago = new Pago();
        detalle = new Detallepago();
    }

    @PostConstruct
    void cargarDatos() {
        listFormaPago = catalogoBean.listFormaPago();
        listMunicipios = catalogoBean.listMunicipioByIdDepartamento(1);
    }

    public List<Cliente> completeCliente(String query) {
        List<Cliente> clientes = clienteBean.ListClientesByNombre(query);
        return clientes;
    }

    public void savePago() throws IOException {
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
            Pago responsePago = pagosBean.savePago(pago);
            if (responsePago != null) {
                detalle.setUsuariocreacion(SesionUsuarioMB.getUserName());
                detalle.setMontopagado(pago.getTotal());
                detalle.setIdpago(pago);
                Detallepago responseDet = pagosBean.saveDetallepago(detalle);

                Cobro findCobro = pagosBean.findCobroByIdClienteAndAnioAndMes(pago.getIdcliente().getIdcliente(), pago.getAnio(), pago.getMes());
                if (findCobro != null) {
                    findCobro.setIdpago(pago);
                    findCobro.setCobro(0);
                    findCobro.setFechamodificacion(new Date());
                    findCobro.setUsuariomodificacion(SesionUsuarioMB.getUserName());
                    Cobro responseCobro = pagosBean.updateCobro(findCobro);
                }

                JsfUtil.addSuccessMessage("El pago se registro exitosamente");
                pago = null;
                cliente = null;
                detalle = null;
                municipioSelected = null;
                return;
            }
        }
    }

    public void regresar() {
        JsfUtil.redirectTo("/pagos/lista.xhtml");
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

    public void cargarClientesMunicipios() {
        if (municipioSelected != null) {
            listClientes = clienteBean.ListClientesByIdMunucipio(municipioSelected.getIdmunicipio());
        } else {
            listClientes = null;
            cliente = null;
        }
    }

    public void cerrarDialog() {
        RequestContext.getCurrentInstance().execute("PF('dlgConfirmacion').hide()");
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
        Detallepago responseDet = pagosBean.saveDetallepago(detalle);

        Cobro findCobro = pagosBean.findCobroByIdClienteAndAnioAndMes(pago.getIdcliente().getIdcliente(), pago.getAnio(), pago.getMes());
        if (findCobro != null) {
            findCobro.setIdpago(pago);
            findCobro.setCobro(0);
            findCobro.setFechamodificacion(new Date());
            findCobro.setUsuariomodificacion(SesionUsuarioMB.getUserName());
            Cobro responseCobro = pagosBean.updateCobro(findCobro);
        }

        JsfUtil.addSuccessMessage("El pago se registro exitosamente");
        pago = null;
        cliente = null;
        detalle = null;
        municipioSelected = null;
        return;
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

    public List<Cliente> getListClientes() {
        return listClientes;
    }

    public void setListClientes(List<Cliente> listClientes) {
        this.listClientes = listClientes;
    }

    public List<Municipio> getListMunicipios() {
        return listMunicipios;
    }

    public void setListMunicipios(List<Municipio> listMunicipios) {
        this.listMunicipios = listMunicipios;
    }

    public Tipopago getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(Tipopago tipoPago) {
        this.tipoPago = tipoPago;
    }

    public Municipio getMunicipioSelected() {
        return municipioSelected;
    }

    public void setMunicipioSelected(Municipio municipioSelected) {
        this.municipioSelected = municipioSelected;
    }

    public Detallepago getDetalle() {
        return detalle;
    }

    public void setDetalle(Detallepago detalle) {
        this.detalle = detalle;
    }

    public Pago getPagoVerificacion() {
        return pagoVerificacion;
    }

    public void setPagoVerificacion(Pago pagoVerificacion) {
        this.pagoVerificacion = pagoVerificacion;
    }

}
