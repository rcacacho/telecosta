package tele.costa.web.cobro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import tele.costa.api.ejb.ClienteBeanLocal;
import tele.costa.api.ejb.PagosBeanLocal;
import tele.costa.api.entity.Cliente;
import tele.costa.api.entity.Pago;
import tele.costa.web.pago.ListaPagosMB;
import telecosta.web.utils.JsfUtil;

/**
 *
 * @author rcacacho
 */
@ManagedBean(name = "listaCobrosMB")
@ViewScoped
public class ListaCobrosMB implements Serializable {

    private static final Logger log = Logger.getLogger(ListaPagosMB.class);

    @EJB
    private PagosBeanLocal pagosBean;
    @EJB
    private ClienteBeanLocal clienteBean;

    private List<Pago> listPago;
    private Integer idcliente;
    private Integer anio;
    private Date fechaInicio;
    private Date fechaFin;
    private String mes;
    List<Cliente> listClientes;
    private Date fechaInicioBus;
    private Date fechaFinBus;

    public ListaCobrosMB() {
    }

    @PostConstruct
    void cargarDatos() {
        Date fInicio = new Date();
        Date fFin = new Date();

        listPago = pagosBean.listCobros(fInicio, fFin);
        listClientes = clienteBean.ListClientes();
    }

    public void buscarPago() {
        if (idcliente != 0) {
            List<Pago> response = pagosBean.listCobroByIdCliente(idcliente);
            if (response != null) {
                listPago = response;
            } else {
                listPago = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (anio != null) {
            List<Pago> response = pagosBean.listCobroByAnio(anio);
            if (response != null) {
                listPago = response;
            } else {
                listPago = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (mes != "") {
            List<Pago> response = pagosBean.listCobroByMes(mes);
            if (response != null) {
                listPago = response;
            } else {
                listPago = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (fechaInicioBus != null && fechaFinBus != null) {
            List<Pago> response = pagosBean.listCobros(fechaInicioBus, fechaFinBus);
            if (response != null) {
                listPago = response;
            } else {
                listPago = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else {
            listPago = new ArrayList<>();
            JsfUtil.addErrorMessage("No se encontraron datos");
        }
    }

    public void limpiarCampos() {
        anio = null;
        mes = "";
        idcliente = 0;
        fechaInicioBus = null;
        fechaFinBus = null;
    }

    public void detalle(Integer id) {
        JsfUtil.redirectTo("/cobros/detalle.xhtml?idcobro=" + id);
    }


    /*Metodos getters y setters*/
    public List<Pago> getListPago() {
        return listPago;
    }

    public void setListPago(List<Pago> listPago) {
        this.listPago = listPago;
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

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public List<Cliente> getListClientes() {
        return listClientes;
    }

    public void setListClientes(List<Cliente> listClientes) {
        this.listClientes = listClientes;
    }

    public Integer getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Integer idcliente) {
        this.idcliente = idcliente;
    }

    public Date getFechaInicioBus() {
        return fechaInicioBus;
    }

    public void setFechaInicioBus(Date fechaInicioBus) {
        this.fechaInicioBus = fechaInicioBus;
    }

    public Date getFechaFinBus() {
        return fechaFinBus;
    }

    public void setFechaFinBus(Date fechaFinBus) {
        this.fechaFinBus = fechaFinBus;
    }

}
