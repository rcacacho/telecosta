package tele.costa.web;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import tele.costa.api.ejb.CatalogoBeanLocal;
import tele.costa.api.ejb.ClienteBeanLocal;
import tele.costa.api.entity.Cliente;
import tele.costa.api.entity.Departamento;
import tele.costa.api.entity.Municipio;
import telecosta.web.utils.JsfUtil;

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
    private List<Municipio> listMunicipios;
    private Municipio municipioSelected;
    private List<Departamento> listDepartamento;

    public RegistroClienteMB() {
    }

    @PostConstruct
    void cargarDatos() {
        listDepartamento = catalogoBean.listDepartamentos();
    }

    public void cargarMunicipio() {
        if (departamentoSelected != null) {
            listMunicipios = catalogoBean.listMunicipioByIdDepartamento(departamentoSelected.getIddepartamento());
        } else {
            listMunicipios = null;
            municipioSelected = null;
        }
    }
    
    public void saveCliente() {
        Cliente responseVerificacion = clienteBean.saveCliente(cliente);
        if (responseVerificacion != null) {
            JsfUtil.addSuccessMessage("El usuario ya existe verifique");
            return;
        }
        
        departamentoSelected = null;
        municipioSelected = null;
        cliente = null;
    }

    public void regresar() {
        JsfUtil.redirectTo("index.xhtml");
    }

    /*Metodos getters y setters*/
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

    public Municipio getMunicipioSelected() {
        return municipioSelected;
    }

    public void setMunicipioSelected(Municipio municipioSelected) {
        this.municipioSelected = municipioSelected;
    }

    public List<Departamento> getListDepartamento() {
        return listDepartamento;
    }

    public void setListDepartamento(List<Departamento> listDepartamento) {
        this.listDepartamento = listDepartamento;
    }

    public Departamento getDepartamentoSelected() {
        return departamentoSelected;
    }

    public void setDepartamentoSelected(Departamento departamentoSelected) {
        this.departamentoSelected = departamentoSelected;
    }

}
