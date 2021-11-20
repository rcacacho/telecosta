package tele.costa.web.cliente;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import tele.costa.api.ejb.CatalogoBeanLocal;
import tele.costa.api.ejb.ClienteBeanLocal;
import tele.costa.api.entity.Cliente;
import tele.costa.api.entity.Municipio;
import telecosta.web.utils.JsfUtil;
import telecosta.web.utils.SesionUsuarioMB;

/**
 *
 * @author rcacacho
 */
@ManagedBean(name = "editarClienteMB")
@ViewScoped
public class EditarClienteMB implements Serializable {

    private static final Logger log = Logger.getLogger(EditarClienteMB.class);

    @EJB
    private ClienteBeanLocal clienteBean;
    @EJB
    private CatalogoBeanLocal catalogoBean;

    private Integer idcliente;
    private Cliente cliente;
    private List<Municipio> listMunicipios;

    public void cargarDatos() {
        cliente = clienteBean.findClienteById(idcliente);
        listMunicipios = catalogoBean.listMunicipioByIdDepartamento(1);
    }

    public void actualizarCliente() throws IOException {
        cliente.setUsuariomodificacion(SesionUsuarioMB.getUserName());
        cliente.setFechamodificacion(new Date());
        Cliente responseVerificacion = clienteBean.updateCliente(cliente);
        if (responseVerificacion != null) {
            JsfUtil.addSuccessMessage("Cliente actualizado exitosamente");
        } else {
            JsfUtil.addErrorMessage("Ocurrio un error verificar datos");
        }
    }

    public void regresar() {
        JsfUtil.redirectTo("/clientes/detalle.xhtml?idCliente=" + idcliente);
    }

    /*Metodos getters y setters*/
    public Integer getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Integer idcliente) {
        this.idcliente = idcliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Municipio> getListMunicipios() {
        return listMunicipios;
    }

    public void setListMunicipios(List<Municipio> listMunicipios) {
        this.listMunicipios = listMunicipios;
    }

}
