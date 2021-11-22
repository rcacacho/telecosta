package tele.costa.web.usuario;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import tele.costa.api.ejb.UsuarioBeanLocal;
import tele.costa.api.entity.Usuario;
import telecosta.web.utils.JsfUtil;

/**
 *
 * @author elfo_
 */
@ManagedBean(name = "registroUsuarioMB")
@ViewScoped
public class RegistroUsuarioMB implements Serializable {

    private static final Logger log = Logger.getLogger(RegistroUsuarioMB.class);

    @EJB
    private UsuarioBeanLocal usuarioBean;

    private Usuario usuario;

    public void saveCliente() {
        Usuario responseVerificacion = usuarioBean.saveUsuario(usuario);
        if (responseVerificacion != null) {
            JsfUtil.addSuccessMessage("Usuario registrado exitosamente");
            usuario = null;
            return;
        }
    }
    
     public void regresar() {
        JsfUtil.redirectTo("/usuario/lista.xhtml");
    }

    /*Metodos getters y setters*/
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
