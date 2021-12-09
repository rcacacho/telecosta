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
import tele.costa.api.ejb.CatalogoBeanLocal;
import tele.costa.api.ejb.ClienteBeanLocal;
import tele.costa.api.ejb.PagosBeanLocal;
import tele.costa.api.entity.Cliente;
import tele.costa.api.entity.Configuracionpago;
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
    private Cliente cliente;
    private Tipopago tipoPago;
    private List<Configuracionpago> listConfiguracion;
    List<Cliente> listClientes;

    public RegistroPagoMB() {
        pago = new Pago();
    }

    @PostConstruct
    void cargarDatos() {
        listConfiguracion = catalogoBean.ListConfiguracionPago();
        listClientes = clienteBean.ListClientes();
    }

    public List<Cliente> completeCliente(String query) {
        List<Cliente> clientes = clienteBean.ListClientesByNombre(query);
        return clientes;
    }

    public void savePago() throws IOException {
        pago.setUsuariocreacion(SesionUsuarioMB.getUserName());
        tipoPago = catalogoBean.findTipoPago(TipoPagoEnum.PAGO.getId());
        pago.setIdcliente(cliente);
        pago.setIdtipopago(tipoPago);
        pago.setFechapago(new Date());
        Pago responseVerificacion = pagosBean.savePago(pago);
        if (responseVerificacion != null) {
            JsfUtil.addSuccessMessage("El pago se registro exitosamente");
            pago = null;
            cliente = null;
            return;
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

    public List<Configuracionpago> getListConfiguracion() {
        return listConfiguracion;
    }

    public void setListConfiguracion(List<Configuracionpago> listConfiguracion) {
        this.listConfiguracion = listConfiguracion;
    }

    public List<Cliente> getListClientes() {
        return listClientes;
    }

    public void setListClientes(List<Cliente> listClientes) {
        this.listClientes = listClientes;
    }

}
