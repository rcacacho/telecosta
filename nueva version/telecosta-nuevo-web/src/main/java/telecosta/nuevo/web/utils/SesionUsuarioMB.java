package telecosta.nuevo.web.utils;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;

/**
 *
 * @author rcacacho
 */
@Named("utilMB")
@SessionScoped
public class SesionUsuarioMB implements Serializable{
    
    public static HttpSession getSession() {
        return (HttpSession) FacesContext.
                getCurrentInstance().
                getExternalContext().
                getSession(false);
    }

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.
                getCurrentInstance().
                getExternalContext().getRequest();
    }

    public static String getUserName() throws IOException {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session.getAttribute("usuario") == null) {
            FacesContext.getCurrentInstance().getExternalContext().dispatch("/index.xhtml");
            return null;
        }
        return session.getAttribute("usuario").toString();
    }

    public static Integer getUserId() {
        HttpSession session = getSession();
        if (session.getAttribute("idusuario") != null) {
            return (Integer) session.getAttribute("idusuario");
        }
        return null;
    }

    public static boolean getRootUsuario() {
        HttpSession session = getSession();
        if (session.getAttribute("root") != null) {
            return (boolean) session.getAttribute("root");
        }
        return false;
    }

    public static String getRolUsuario() throws IOException {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session.getAttribute("rol") != null) {
            return (String) session.getAttribute("rol");
        }
        return null;
    }

    public static Integer getIdMunicipio() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session.getAttribute("idmunicipio") != null) {
            return (Integer) session.getAttribute("idmunicipio");
        }
        return null;
    }
    
}
