package tele.costa.web.pago;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import tele.costa.api.ejb.ClienteBeanLocal;
import tele.costa.api.ejb.PagosBeanLocal;
import tele.costa.api.entity.Cliente;
import tele.costa.api.entity.Pago;
import telecosta.web.utils.JsfUtil;

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

    private Pago pago;
    private Cliente cliente;

    public RegistroPagoMB() {
        pago = new Pago();
        cliente = new Cliente();
    }

    public List<Cliente> completeCliente(String query) {
        List<Cliente> clientes = clienteBean.ListClientesByNombre(query);
        return clientes;
    }

    public void savePago() {
        pago.setIdcliente(cliente);
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

    public List<Cliente> completeCountry(String query) {
        String queryLowerCase = query.toLowerCase();
        List<Cliente> clientes = clienteBean.ListClientesByNombre(query);
        return clientes;
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

}
