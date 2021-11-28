package tele.costa.web.pago;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
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
 * @author rcacacho
 */
@ManagedBean(name = "listaPagosMB")
@ViewScoped
public class ListaPagosMB implements Serializable {

    private static final Logger log = Logger.getLogger(ListaPagosMB.class);

    @EJB
    private PagosBeanLocal pagosBean;
    @EJB
    private ClienteBeanLocal clienteBean;

    private List<Pago> listPago;
    private Cliente cliente;
    private Integer anio;
    private Date fechaInicio;
    private Date fechaFin;
    private Integer mes;

    public ListaPagosMB() {
    }

    @PostConstruct
    void cargarDatos() {
        Date fInicio = new Date();
        Date fFin = new Date();

        listPago = pagosBean.listPagos(fInicio, fFin);
    }

    public void buscarPago() {
//        if (nombre != null) {
//            List<Pago> response = pagosBean.List
//            if (response.size() > 0) {
//                listCliente = response;
//            } else {
//                JsfUtil.addErrorMessage("No se encontraron datos");
//            }
//        } else if (cui != null) {
//            List<Cliente> response = clienteBean.ListClientesByCui(cui);
//            if (response.size() > 0) {
//                listCliente = response;
//            } else {
//                JsfUtil.addErrorMessage("No se encontraron datos");
//            }
//        } else {
//            JsfUtil.addErrorMessage("No se encontraron datos");
//        }
    }

    public void limpiarCampos() {
        cliente = null;
        anio = null;
    }

    public void detallePago(Integer id) {
        JsfUtil.redirectTo("/pagos/detalle.xhtml?idpago=" + id);
    }

    public List<Cliente> completeCliente(String query) {
        String queryLowerCase = query.toLowerCase();
        List<Cliente> clientes = clienteBean.ListClientesByNombre(query);
        return clientes;
    }

    /*Metodos getters y setters*/
    public List<Pago> getListPago() {
        return listPago;
    }

    public void setListPago(List<Pago> listPago) {
        this.listPago = listPago;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
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

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

}
