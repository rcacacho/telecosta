package tele.costa.web.cliente;

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
import tele.costa.api.entity.Cliente;
import tele.costa.api.entity.Configuracionpago;
import tele.costa.api.entity.Departamento;
import tele.costa.api.entity.Estadocliente;
import tele.costa.api.entity.Municipio;
import tele.costa.api.entity.Sector;
import tele.costa.api.enums.EstadoClienteEnum;
import telecosta.web.utils.JsfUtil;
import telecosta.web.utils.SesionUsuarioMB;

/**
 *
 * @author elfo_
 */
@ManagedBean(name = "registroClienteMB")
@ViewScoped
public class RegistroClienteMB implements Serializable {
    
    private static final Logger log = Logger.getLogger(RegistroClienteMB.class);
    
    @EJB
    private CatalogoBeanLocal catalogoBean;
    @EJB
    private ClienteBeanLocal clienteBean;
    
    private Cliente cliente;
    private Departamento departamentoSelected;
    private List<Departamento> listDepartamento;
    private Municipio municipioSelected;
    private List<Municipio> listMunicipios;
    private List<Configuracionpago> listConfiguracionPago;
    private List<Sector> listSector;
    
    public RegistroClienteMB() {
        cliente = new Cliente();
    }
    
    @PostConstruct
    void cargarDatos() {
        listMunicipios = catalogoBean.listMunicipioByIdDepartamento(1);
        listConfiguracionPago = catalogoBean.ListConfiguracionPago();
        listSector = catalogoBean.listSector();
    }
    
    public void saveCliente() throws IOException {
        Estadocliente estado = catalogoBean.findEstadoCliente(EstadoClienteEnum.ACTIVO.getId());
        cliente.setUsuariocreacion(SesionUsuarioMB.getUserName());
        cliente.setIdestadocliente(estado);
        Cliente responseVerificacion = clienteBean.saveCliente(cliente);
        if (responseVerificacion != null) {
            JsfUtil.addSuccessMessage("Cliente creado exitosamente");
        } else {
            JsfUtil.addErrorMessage("Ocurrio un error verificar datos");
        }
        cliente = null;
        departamentoSelected = null;
        municipioSelected = null;
    }
    
    public void regresar() {
        JsfUtil.redirectTo("/clientes/lista.xhtml");
    }

    /*Metodos getters y setters*/
    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public Departamento getDepartamentoSelected() {
        return departamentoSelected;
    }
    
    public void setDepartamentoSelected(Departamento departamentoSelected) {
        this.departamentoSelected = departamentoSelected;
    }
    
    public List<Departamento> getListDepartamento() {
        return listDepartamento;
    }
    
    public void setListDepartamento(List<Departamento> listDepartamento) {
        this.listDepartamento = listDepartamento;
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
    
    public List<Configuracionpago> getListConfiguracionPago() {
        return listConfiguracionPago;
    }
    
    public void setListConfiguracionPago(List<Configuracionpago> listConfiguracionPago) {
        this.listConfiguracionPago = listConfiguracionPago;
    }
    
    public List<Sector> getListSector() {
        return listSector;
    }
    
    public void setListSector(List<Sector> listSector) {
        this.listSector = listSector;
    }
    
}
