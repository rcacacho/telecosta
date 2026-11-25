package telecosta.nuevo.web.login;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import tele.costa.nuevo.api.ejb.LoginBeanLocal;
import tele.costa.nuevo.api.entity.Usuario;
import telecosta.nuevo.web.utils.SesionUsuarioMB;

/**
 *
 * @author rcacacho
 */
@Named("loginMB")
@ViewScoped
public class LoginMB implements Serializable {

    private static final Logger logger = LogManager.getLogger(LoginMB.class);

    @EJB
    private LoginBeanLocal loginBeanLocal;

    private String usuario;
    private String password;
    private Usuario usu;
    private boolean root;
    private String rol;

    private static final class EncoderHolder {

        static final PasswordEncoder INSTANCE = new Argon2PasswordEncoder(16, 32, 1, 19456, 2);
    }

    private static String md5Hex(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] dig = md.digest(texto.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : dig) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

// ===== 4. Método de login =====
    public String loginProject() {
        Usuario usu = loginBeanLocal.buscarPorUsuario(usuario);
        boolean ok = false;

        if (usu != null && usu.getPassword() != null) {
            String guardado = usu.getPassword();

            if (guardado.startsWith("$argon2")) {
                // Hash nuevo
                ok = EncoderHolder.INSTANCE.matches(password, guardado);               
            } else {
                // Hash MD5 antiguo: valida y lo convierte al hash nuevo
                String md5Ingresado = md5Hex(password);
                ok = MessageDigest.isEqual(
                        guardado.toLowerCase().getBytes(StandardCharsets.UTF_8),
                        md5Ingresado.getBytes(StandardCharsets.UTF_8));
                if (ok) {
                     loginBeanLocal.actualizarPassword(usu.getIdusuario(), EncoderHolder.INSTANCE.encode(password));
                }
            }
        }
        password = null; // no dejar la clave en el bean

        if (!ok) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario o Contraseña invalida", "Intente de nuevo!"));
            return "login.xhtml";
        }

        // Evita fijación de sesión
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
        request.getSession(true);
        request.changeSessionId();

        HttpSession session = SesionUsuarioMB.getSession();
        session.setAttribute("usuario", usuario);
        session.setAttribute("idusuario", usu.getIdusuario());
        session.setAttribute("root", usu.getRoot());
        session.setAttribute("rol", usu.getTipousuario());
        if (usu.getIdmunicipio() != null) {
            session.setAttribute("idmunicipio", usu.getIdmunicipio());
        }
        usuario = "";
        return "/menu/menu.xhtml";
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
