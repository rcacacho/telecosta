package telecosta.web.login;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import tele.costa.api.ejb.LoginBeanLocal;
import tele.costa.api.entity.Usuario;
import telecosta.web.utils.JsfUtil;
import telecosta.web.utils.SesionUsuarioMB;

/**
 *
 * @author rcacacho
 */
@ManagedBean(name = "loginMB")
@ViewScoped
public class LoginMB implements Serializable {

    private static final Logger log = Logger.getLogger(LoginMB.class);

    @EJB
    private LoginBeanLocal loginBeanLocal;

    public static String usuario;
    private String password;
    private Usuario usu;
    private boolean root;
    private String rol;

    public LoginMB() {
        usu = new Usuario();
        usuario = "";
    }

    public String loginProject() {
        password = md5(password);
        usu = loginBeanLocal.verificarUsuario(usuario, password);
        if (usu != null) {
            HttpSession session = SesionUsuarioMB.getSession();
            session.setAttribute("usuario", usuario);
            session.setAttribute("idusuario", usu.getIdusuario());
            session.setAttribute("root", usu.getRoot());
            session.setAttribute("rol", usu.getTipousuario());
            if (usu.getIdmunicipio() != null) {
                session.setAttribute("idmunicipio", usu.getIdmunicipio().getIdmunicipio());
            }
            usuario = "";
            return "/menu/menu.xhtml";
        } else {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario o Contraseña invalida", "Intente de nuevo!"));
            return "login.xhtml";
        }
    }

    public void logout() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.invalidateSession();
        ec.redirect(ec.getRequestContextPath() + "/login.xhtml");
    }

    public void regresar() {
        JsfUtil.redirectTo("/index.xhtml");
    }

    public static String md5(String input) {
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

    public void regresarMenu() {
        JsfUtil.redirectTo("/menu/menu.xhtml");
    }

    public boolean validarRoot() {
        if (SesionUsuarioMB.getRootUsuario()) {
            return root = SesionUsuarioMB.getRootUsuario();
        }
        return false;
    }

    public String validarRol() throws IOException {
        return rol = SesionUsuarioMB.getRolUsuario();
    }

    public void renewSesionTimeOut(String pageTo) throws IOException {
        HttpSession session = SesionUsuarioMB.getSession();
        if (session == null) {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.invalidateSession();
            ec.redirect(ec.getRequestContextPath() + "/login.xhtml");
        } else {
            JsfUtil.redirectTo(pageTo);
        }
    }

    /*Metodos Getters y setters*/
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

}
