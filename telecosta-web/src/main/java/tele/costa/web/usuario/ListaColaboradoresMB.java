package tele.costa.web.usuario;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import tele.costa.api.entity.Usuario;
import tele.costa.api.ejb.UsuarioBeanLocal;
import telecosta.web.utils.JsfUtil;
import telecosta.web.utils.SesionUsuarioMB;

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

    public void detalle(Integer id) {
        JsfUtil.redirectTo("/usuario/detalle.xhtml?idusuario=" + id);
    }

    public void reinicioPassword(Usuario usu) {
        selectedUsuario = usu;
        RequestContext.getCurrentInstance().execute("PF('dlgReinicio').show()");
    }

    public void reinicioUsuario() throws IOException {
        String contra = md5(selectedUsuario.getPassword());
        selectedUsuario.setPassword(contra);
        selectedUsuario.setUsuariomodificacion(SesionUsuarioMB.getUserName());
        Usuario responseVerificacion = usuarioBeanLocal.reinicioPassword(selectedUsuario);
        if (responseVerificacion != null) {
            JsfUtil.addSuccessMessage("Se reinicio la contraseña exitosamente");
            selectedUsuario = null;
            return;
        }
    }

    public String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

     public void cerrarDialog() {
        selectedUsuario = null;
        RequestContext.getCurrentInstance().execute("PF('dlgReinicio').hide()");
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
