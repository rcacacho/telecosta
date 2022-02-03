package tele.costa.web.atencion;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import tele.costa.api.ejb.AtencionClienteLocal;
import tele.costa.api.entity.Atencion;
import telecosta.web.utils.JsfUtil;

/**
 *
 * @author rcacacho
 */
@ManagedBean(name = "detalleAtencionMB")
@ViewScoped
public class DetalleAtencionMB implements Serializable {

    @EJB
    private AtencionClienteLocal atencionBean;

    private Integer idAtencion;
    private Atencion atencion;

    public void cargarDatos() {
        atencion = atencionBean.findAtencionById(idAtencion);
    }

    public void regresar() {
        JsfUtil.redirectTo("/atencion/lista.xhtml");
    }

    /*Metodos getters y setters*/
    public Atencion getAtencion() {
        return atencion;
    }

    public void setAtencion(Atencion atencion) {
        this.atencion = atencion;
    }

    public Integer getIdAtencion() {
        return idAtencion;
    }

    public void setIdAtencion(Integer idAtencion) {
        this.idAtencion = idAtencion;
    }

}
