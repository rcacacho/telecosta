package tele.costa.web.cobro;

import java.io.IOException;
import java.io.Serializable;
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
import tele.costa.api.entity.Cobro;
import tele.costa.api.entity.Detallepago;
import tele.costa.api.entity.Municipio;
import tele.costa.api.enums.TipoPagoEnum;
import telecosta.web.utils.JsfUtil;
import telecosta.web.utils.SesionUsuarioMB;

/**
 *
 * @author rcacacho
 */
@ManagedBean(name = "registroCobroMB")
@ViewScoped
public class RegistroCobroMB implements Serializable {

    private static final Logger log = Logger.getLogger(RegistroCobroMB.class);

    @EJB
    private PagosBeanLocal pagosBean;
    @EJB
    private ClienteBeanLocal clienteBean;
    @EJB
    private CatalogoBeanLocal catalogoBean;

    private Cobro cobro;
    private Municipio municipioSelected;
    private List<Municipio> listMunicipios;
    private Cliente cliente;
    private List<Cliente> listClientes;

    public RegistroCobroMB() {
        cobro = new Cobro();
    }

    @PostConstruct
    void cargarDatos() {
        listMunicipios = catalogoBean.listMunicipioByIdDepartamento(1);
    }

    public void regresar() {
        JsfUtil.redirectTo("/cobros/lista.xhtml");
    }

    public void cargarClientesMunicipios() {
        if (municipioSelected != null) {
            listClientes = clienteBean.ListClientesByIdMunucipio(municipioSelected.getIdmunicipio());
        } else {
            listClientes = null;
            cliente = null;
        }
    }

    public void cargarCliente() {
        cobro.setIdcliente(cliente);
    }

    public void saveCobro() throws IOException {
        if (cliente.getIdconfiguracionpago().getValor() == null) {
            JsfUtil.addErrorMessage("El cliente debe tener un cobro configurado");
            return;
        }

        cobro.setUsuariocreacion(SesionUsuarioMB.getUserName());
        cobro.setIdcliente(cliente);
        cobro.setIdconfiguracionpago(catalogoBean.findConfiguracionPago(TipoPagoEnum.COBRO.getId()));
        cobro.setCobro(cliente.getIdconfiguracionpago().getValor());

        Cobro response = pagosBean.saveCobro(cobro);
        if (response != null) {
            Detallepago detalle = new Detallepago();
            detalle.setIdcobro(cobro);
            if (cobro.getCobro() != null) {
                detalle.setMontocobrado(cobro.getCobro());
            } else {
                detalle.setMontocobrado(cliente.getIdconfiguracionpago().getValor());
            }

            if (detalle.getMontopagado() != null) {
                detalle.setTotal(detalle.getMontocobrado() - detalle.getMontopagado());
            } else {
                detalle.setTotal(detalle.getMontocobrado());
            }

            detalle.setUsuariocreacion(SesionUsuarioMB.getUserName());
            Detallepago responseDetalle = pagosBean.saveDetallepago(detalle);

            JsfUtil.addSuccessMessage("El cobro se registro exitosamente");
            cobro = null;
            cliente = null;
            return;
        }
    }

    /*Metodos getters y setters*/
    public Cobro getCobro() {
        return cobro;
    }

    public void setCobro(Cobro cobro) {
        this.cobro = cobro;
    }

    public Municipio getMunicipioSelected() {
        return municipioSelected;
    }

    public void setMunicipioSelected(Municipio municipioSelected) {
        this.municipioSelected = municipioSelected;
    }

    public List<Municipio> getListMunicipios() {
        return listMunicipios;
    }

    public void setListMunicipios(List<Municipio> listMunicipios) {
        this.listMunicipios = listMunicipios;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Cliente> getListClientes() {
        return listClientes;
    }

    public void setListClientes(List<Cliente> listClientes) {
        this.listClientes = listClientes;
    }

}
