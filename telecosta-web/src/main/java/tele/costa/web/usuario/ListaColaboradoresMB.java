package tele.costa.web.usuario;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import tele.costa.api.ejb.CatalogoBeanLocal;
import tele.costa.api.entity.Usuario;
import tele.costa.api.ejb.UsuarioBeanLocal;
import tele.costa.api.entity.Municipio;
import tele.costa.api.entity.Usuariomunicipio;
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
    @EJB
    private CatalogoBeanLocal catalogoBeanLocal;

    private List<Usuario> listUsuario;
    private Usuario selectedUsuario;
    private Usuario usuarioAsignacion;
    private List<Municipio> listMunicipio;
    private Municipio municipioAsignacion;
    private List<Usuariomunicipio> listUsuarioMunicipio;

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

    public void eliminarUsuario(Usuario us) throws IOException {
        us.setFechamodificacion(new Date());
        us.setUsuariomodificacion(SesionUsuarioMB.getUserName());
        us.setActivo(false);
        Usuario response = usuarioBeanLocal.updateUsuario(us);
        if (response != null) {
            JsfUtil.addSuccessMessage("Usuario eliminado exitosamente");
        } else {
            JsfUtil.addErrorMessage("Ocurrio un error al eliminar el usuario");
        }

        cargarDatos();
    }

    public void asignarMunicipio(Usuario id) {
        usuarioAsignacion = id;
        listMunicipio = catalogoBeanLocal.listMunicipioByIdDepartamento(1);
        cargarUsuarioMunicipio(id);
        RequestContext.getCurrentInstance().execute("PF('dlgAsignacion').show()");
    }

    public void eliminarMunicipio(Usuariomunicipio mun) throws IOException {
        mun.setFechaeliminacion(new Date());
        mun.setUsuarioeliminacion(SesionUsuarioMB.getUserName());
        mun.setActivo(false);
        Usuariomunicipio response = usuarioBeanLocal.updateUsuarioMunicipio(mun);
        if (response != null) {
            JsfUtil.addSuccessMessage("municipio eliminado exitosamente");
        } else {
            JsfUtil.addErrorMessage("Ocurrio un error al eliminar el municipio");
        }

        cargarUsuarioMunicipio(mun.getIdusuario());
    }

    public void cargarUsuarioMunicipio(Usuario id) {
        listUsuarioMunicipio = usuarioBeanLocal.listUsuarioMunicipio(id.getIdusuario());
    }

    public void cerrarDialogMuncipio() {
        usuarioAsignacion = null;
        RequestContext.getCurrentInstance().execute("PF('dlgAsignacion').hide()");
    }

    public void guardarMunicipio() throws IOException {
        if (municipioAsignacion == null) {
            JsfUtil.addErrorMessage("Debe seleccionar un municipio");
            return;
        }

        Usuariomunicipio mun = new Usuariomunicipio();
        mun.setIdusuario(usuarioAsignacion);
        mun.setIdmunicipio(municipioAsignacion);
        mun.setUsuariocreacion(SesionUsuarioMB.getUserName());
        Usuariomunicipio response = usuarioBeanLocal.saveUsuarioMunicipio(mun);
        if (response != null) {
            JsfUtil.addSuccessMessage("municipio registrado exitosamente");
        } else {
            JsfUtil.addErrorMessage("Ocurrio un error al registrar el municipio");
        }

        cargarUsuarioMunicipio(usuarioAsignacion);
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

    public Usuario getUsuarioAsignacion() {
        return usuarioAsignacion;
    }

    public void setUsuarioAsignacion(Usuario usuarioAsignacion) {
        this.usuarioAsignacion = usuarioAsignacion;
    }

    public List<Municipio> getListMunicipio() {
        return listMunicipio;
    }

    public void setListMunicipio(List<Municipio> listMunicipio) {
        this.listMunicipio = listMunicipio;
    }

    public Municipio getMunicipioAsignacion() {
        return municipioAsignacion;
    }

    public void setMunicipioAsignacion(Municipio municipioAsignacion) {
        this.municipioAsignacion = municipioAsignacion;
    }

    public List<Usuariomunicipio> getListUsuarioMunicipio() {
        return listUsuarioMunicipio;
    }

    public void setListUsuarioMunicipio(List<Usuariomunicipio> listUsuarioMunicipio) {
        this.listUsuarioMunicipio = listUsuarioMunicipio;
    }

}
