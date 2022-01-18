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
import tele.costa.api.entity.Detallepago;
import tele.costa.api.entity.Municipio;
import tele.costa.api.entity.Pago;
import tele.costa.api.enums.TipoPagoEnum;
import telecosta.web.utils.JsfUtil;
import telecosta.web.utils.SesionUsuarioMB;

/**
 *
 * @author rcacacho
 */
@ManagedBean(name = "registroCobro")
@ViewScoped
public class RegistroCobro implements Serializable {

    private static final Logger log = Logger.getLogger(RegistroCobro.class);

    @EJB
    private PagosBeanLocal pagosBean;
    @EJB
    private ClienteBeanLocal clienteBean;
    @EJB
    private CatalogoBeanLocal catalogoBean;

    private Pago pago;
    private Municipio municipioSelected;
    private List<Municipio> listMunicipios;
    private Cliente cliente;
    private List<Cliente> listClientes;

    public RegistroCobro() {
        pago = new Pago();
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
        pago.setIdcliente(cliente);
    }

    public void saveCobro() throws IOException {
        pago.setUsuariocreacion(SesionUsuarioMB.getUserName());
        pago.setIdcliente(cliente);
        pago.setIdtipopago(catalogoBean.findTipoPago(TipoPagoEnum.COBRO.getId()));
        Pago response = pagosBean.saveCobro(pago);
        if (response != null) {
            Detallepago detalle = new Detallepago();
            detalle.setIdpago(pago);
            detalle.setMontocobrado(pago.getTotal());
            detalle.setUsuariocreacion(SesionUsuarioMB.getUserName());
            //Detallepago responseDetalle = pagosBean.saveDetalleCobro(detalle);

            JsfUtil.addSuccessMessage("El cobro se registro exitosamente");
            pago = null;
            cliente = null;
            return;
        }

    }

    /*Metodos getters y setters*/
    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
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
