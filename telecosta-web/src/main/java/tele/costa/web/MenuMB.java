package tele.costa.web;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import telecosta.web.utils.SesionUsuarioMB;

/**
 *
 * @author rcacacho
 */
@ManagedBean(name = "menuMB")
@ViewScoped
public class MenuMB implements Serializable {

    private boolean root;

    @PostConstruct
    void cargarDatos() {
        root = SesionUsuarioMB.getRootUsuario();
    }

    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

}
