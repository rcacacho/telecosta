package tele.costa.web.usuario;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import tele.costa.api.entity.Usuario;
import tele.costa.api.ejb.UsuarioBeanLocal;
import telecosta.web.utils.JsfUtil;

/**
 *
 * @author rcacacho
 */
@ManagedBean(name = "listaColaboradoresMB")
@ViewScoped
public class ListaColaboradoresMB implements Serializable {

    private static final Logger log = Logger.getLogger(ListaColaboradoresMB.class);

    @EJB
    private UsuarioBeanLocal usuarioBeanLocal;

    private List<Usuario> listUsuario;
    private Usuario selectedUsuario;

    public ListaColaboradoresMB() {
    }

    public void cargarDatos() {
        listUsuario = usuarioBeanLocal.listaUsuarios();
    }

    public void linkRegistro() {
        JsfUtil.redirectTo("/usuario/registro.xhtml");
    }

    public String verDetalle() {
        return "detalle.xhtml?faces-redirect=true&idColaborador=" + selectedUsuario.getIdusuario();
    }

    /*Metodos getters y setteres*/
    public List<Usuario> getListUsuario() {
        return listUsuario;
    }

    public void setListUsuario(List<Usuario> listUsuario) {
        this.listUsuario = listUsuario;
    }

    public Usuario getSelectedUsuario() {
        return selectedUsuario;
    }

    public void setSelectedUsuario(Usuario selectedUsuario) {
        this.selectedUsuario = selectedUsuario;
    }
    
    
    

}
