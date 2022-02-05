package tele.costa.web.atencion;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import tele.costa.api.ejb.AtencionClienteLocal;
import tele.costa.api.ejb.CatalogoBeanLocal;
import tele.costa.api.ejb.ClienteBeanLocal;
import tele.costa.api.entity.Atencion;
import tele.costa.api.entity.Cliente;
import tele.costa.api.entity.Ruta;
import telecosta.web.utils.JsfUtil;
import telecosta.web.utils.SesionUsuarioMB;

/**
 *
 * @author rcacacho
 */
@ManagedBean(name = "registroAtencionMB")
@ViewScoped
public class RegistroAtencionMB implements Serializable {

    private static final Logger log = Logger.getLogger(RegistroAtencionMB.class);

    @EJB
    private AtencionClienteLocal atencionBean;
    @EJB
    private CatalogoBeanLocal catalogoBean;
    @EJB
    private ClienteBeanLocal clientesBean;

    private List<Ruta> listRuta;
    private Cliente clienteSelected;
    private List<Cliente> listCliente;
    private Atencion atencion;

    public RegistroAtencionMB() {
        atencion = new Atencion();
    }

    @PostConstruct
    void cargarDatos() {
        listRuta = catalogoBean.listRuta();
        if (SesionUsuarioMB.getRootUsuario()) {
            listCliente = clientesBean.ListClientes();
        } else if (SesionUsuarioMB.getIdMunicipio().equals(6)) {
            listCliente = clientesBean.listClientesByInMunucipio();
        } else {
            listCliente = clientesBean.ListClientesByIdMunucipio(SesionUsuarioMB.getIdMunicipio());
        }
    }

    public void cargarCliente() {
        atencion.setIdcliente(clienteSelected);
    }

    public void regresar() {
        JsfUtil.redirectTo("/atencion/lista.xhtml");
    }

    public void saveAtencion() throws IOException {
        atencion.setUsuariocreacion(SesionUsuarioMB.getUserName());
        atencion.setEstado(true);
        Atencion responseVerificacion = atencionBean.saveAtencion(atencion);
        if (responseVerificacion != null) {
            JsfUtil.addSuccessMessage("Ticket de atención creado exitosamente");
            Cliente responseUpdate = clientesBean.updateCliente(clienteSelected);
        } else {
            JsfUtil.addErrorMessage("Ocurrio un error verificar datos");
        }
        atencion = null;
        clienteSelected = null;
    }

    /*Metodos getters y setters*/
    public List<Ruta> getListRuta() {
        return listRuta;
    }

    public void setListRuta(List<Ruta> listRuta) {
        this.listRuta = listRuta;
    }

    public List<Cliente> getListCliente() {
        return listCliente;
    }

    public void setListCliente(List<Cliente> listCliente) {
        this.listCliente = listCliente;
    }

    public Atencion getAtencion() {
        return atencion;
    }

    public void setAtencion(Atencion atencion) {
        this.atencion = atencion;
    }

    public Cliente getClienteSelected() {
        return clienteSelected;
    }

    public void setClienteSelected(Cliente clienteSelected) {
        this.clienteSelected = clienteSelected;
    }

}
