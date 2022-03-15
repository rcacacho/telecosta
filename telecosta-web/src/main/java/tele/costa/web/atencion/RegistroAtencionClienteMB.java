package tele.costa.web.atencion;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import tele.costa.api.ejb.AtencionClienteLocal;
import tele.costa.api.ejb.CatalogoBeanLocal;
import tele.costa.api.ejb.ClienteBeanLocal;
import tele.costa.api.entity.Atencion;
import tele.costa.api.entity.Cliente;
import tele.costa.api.entity.Ruta;
import tele.costa.api.entity.Tipoatencion;
import tele.costa.api.enums.TipoAtencion;
import telecosta.web.utils.JsfUtil;
import telecosta.web.utils.SesionUsuarioMB;

/**
 *
 * @author elfo_
 */
@ManagedBean(name = "registroAtencionClienteMB")
@ViewScoped
public class RegistroAtencionClienteMB implements Serializable {

    @EJB
    private AtencionClienteLocal atencionBean;
    @EJB
    private CatalogoBeanLocal catalogoBean;
    @EJB
    private ClienteBeanLocal clientesBean;

    private List<Ruta> listRuta;
    private Cliente clienteSelected;
    private Atencion atencion;
    private List<Tipoatencion> listTipoAtencion;
    private Integer idcliente;

    public RegistroAtencionClienteMB() {
        atencion = new Atencion();
    }


    public void cargarDatos() {
        listRuta = catalogoBean.listRuta();
        listTipoAtencion = catalogoBean.listTipoAtencion();
        clienteSelected = clientesBean.findClienteById(idcliente);
    }

    public void cargarCliente() {
        atencion.setIdcliente(clienteSelected);
    }

    public void regresar() {
        JsfUtil.redirectTo("/cliente/lista.xhtml");
    }

    public void saveAtencion() throws IOException {
        atencion.setUsuariocreacion(SesionUsuarioMB.getUserName());
        atencion.setEstado(true);
        atencion.setNombre(clienteSelected.getNombres());
        atencion.setDireccion(clienteSelected.getDireccion());
        atencion.setTelefono(clienteSelected.getTelefono());
        Atencion responseVerificacion = atencionBean.saveAtencion(atencion);
        if (responseVerificacion != null) {
            if (atencion.getIdtipoatencion().getIdtipoatencion().equals(TipoAtencion.SUSPENSION.getId())) {
                clienteSelected.setFechamodificacion(new Date());
                clienteSelected.setUsuariomodificacion(SesionUsuarioMB.getUserName());
                clienteSelected.setSuspendido(true);
            } else if (atencion.getIdtipoatencion().getIdtipoatencion().equals(TipoAtencion.CORTE.getId())) {
                clienteSelected.setMotivoeliminacion(atencion.getMotivo());
                clienteSelected.setFechaeliminacion(new Date());
                clienteSelected.setUsuarioeliminacion(SesionUsuarioMB.getUserName());
                clienteSelected.setActivo(false);
            }
            Cliente responseUpdate = clientesBean.updateCliente(clienteSelected);

            JsfUtil.addSuccessMessage("Ticket de atención creado exitosamente");
        } else {
            JsfUtil.addErrorMessage("Ocurrio un error verificar datos");
        }
        atencion = null;
        clienteSelected = null;
        atencion = new Atencion();
        clienteSelected = new Cliente();
    }

    /*Metodos geeters y setters*/
    public List<Ruta> getListRuta() {
        return listRuta;
    }

    public void setListRuta(List<Ruta> listRuta) {
        this.listRuta = listRuta;
    }

    public Cliente getClienteSelected() {
        return clienteSelected;
    }

    public void setClienteSelected(Cliente clienteSelected) {
        this.clienteSelected = clienteSelected;
    }

    public Atencion getAtencion() {
        return atencion;
    }

    public void setAtencion(Atencion atencion) {
        this.atencion = atencion;
    }

    public List<Tipoatencion> getListTipoAtencion() {
        return listTipoAtencion;
    }

    public void setListTipoAtencion(List<Tipoatencion> listTipoAtencion) {
        this.listTipoAtencion = listTipoAtencion;
    }

    public Integer getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Integer idcliente) {
        this.idcliente = idcliente;
    }

}
