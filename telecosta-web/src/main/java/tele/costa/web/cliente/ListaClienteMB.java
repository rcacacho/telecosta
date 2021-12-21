package tele.costa.web.cliente;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import tele.costa.api.ejb.ClienteBeanLocal;
import tele.costa.api.entity.Cliente;
import telecosta.web.utils.JsfUtil;

/**
 *
 * @author elfo_
 */
@ManagedBean(name = "listaClienteMB")
@ViewScoped
public class ListaClienteMB implements Serializable {

    private static final Logger log = Logger.getLogger(ListaClienteMB.class);

    @EJB
    private ClienteBeanLocal clienteBean;

    private List<Cliente> listCliente;
    private String nombre;
    private String cui;

    public ListaClienteMB() {
    }

    @PostConstruct
    void cargarDatos() {
        listCliente = clienteBean.ListClientes();
    }

    public void buscarCliente() {
        if (nombre != null) {
            List<Cliente> response = clienteBean.ListClientesByNombre(nombre);
            if (response != null) {
                listCliente = response;
            } else {
                listCliente = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (cui != null) {
            List<Cliente> response = clienteBean.ListClientesByCui(cui);
            if (response.size() > 0) {
                listCliente = response;
            } else {
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else {
            JsfUtil.addErrorMessage("No se encontraron datos");
        }
    }

    public void limpiarCampos() {
        nombre = null;
        cui = null;
        cargarDatos();
    }
    
    public void detalle(Integer id) {
        JsfUtil.redirectTo("/clientes/detalle.xhtml?idCliente=" + id);
    }
    
    public void handleMainFileUpload(FileUploadEvent event) {

        String ubicacionArchivo = configuratorBean.findConfiguracionById(Configuracion.UPLOAD_DIRECTORY.getParametro()).getValor();
        file = event.getFile();
        String nombreArchivo = file.getFileName();

        cargaMasiva.setReferencia(JsfUtil.quitarExtension(nombreArchivo));
        nombreArchivo = JsfUtil.armarNombre(nombreArchivo, bundle.getString("cargaMasiva.nombreArchivo"));
        try {
            FileUtil.guardarArchivo(event.getFile().getInputstream(), nombreArchivo, ubicacionArchivo);
            cargaMasiva.setUbicacionArchivo(ubicacionArchivo);
            cargaMasiva.setNombreArchivo(nombreArchivo);

        } catch (IOException ioe) {
            log.error(ioe.getLocalizedMessage());
            cargaMasiva.setReferencia(null);
            cargaMasiva.setUbicacionArchivo(null);
            cargaMasiva.setNombreArchivo(null);
        }
    }

    /*Metodos getters y setters*/
    public List<Cliente> getListCliente() {
        return listCliente;
    }

    public void setListCliente(List<Cliente> listCliente) {
        this.listCliente = listCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

}
