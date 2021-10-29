package telecosta.web.login;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import telecosta.web.utils.SesionUsuarioMB;

/**
 *
 * @author rcacacho
 */
@ManagedBean(name = "menuMB")
@ViewScoped
public class MenuMB implements Serializable {

    private static final Logger log = Logger.getLogger(MenuMB.class);

    private String usuario;

    public MenuMB() {
    }

    public void cargarDatos() throws IOException {
        if (SesionUsuarioMB.getUserName() != null) {
            usuario = SesionUsuarioMB.getUserName();
        }
    }

    /*Metodos getters y setters*/
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

}
