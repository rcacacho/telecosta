package tele.costa.web.bodega;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import tele.costa.api.ejb.BodegaBeanLocal;
import tele.costa.api.ejb.CatalogoBeanLocal;
import tele.costa.api.entity.Agencia;
import tele.costa.api.entity.Insumos;
import telecosta.web.utils.SesionUsuarioMB;

/**
 *
 * @author rcacacho
 */
@ManagedBean(name = "ingresoInsumosMB")
@ViewScoped
public class IngresoInsumosMB implements Serializable {

    private static final Logger log = Logger.getLogger(IngresoInsumosMB.class);

    @EJB
    private CatalogoBeanLocal catalogoBean;
    @EJB
    private BodegaBeanLocal bodegaBeanLocal;

    private Integer idAgencia;
    private List<Agencia> listAgencia;
    private Date fechaInicio;
    private Date fechaFin;
    private List<Insumos> listinsumos;
    private String codigo;
    private String descripcion;
    private Date fechaInsumo;

    @PostConstruct
    void cargarDatos() {
        listAgencia = catalogoBean.listAgencias();
    }

    public void limpiarCampos() {
        idAgencia = null;
        fechaInicio = null;
        fechaFin = null;
        cargarDatos();
    }

    public void dialogRegistro() {
        codigo = null;
        descripcion = null;
        RequestContext.getCurrentInstance().execute("PF('dlgAgregar').show()");
    }

    public void saveInsumo() throws IOException {
        Insumos insumo = new Insumos();
        insumo.setCodigo(codigo);
        insumo.setDescripcion(descripcion);
        insumo.setFecha(fechaInsumo);
        insumo.setUsuariocreacion(SesionUsuarioMB.getUserName());

        Insumos response = bodegaBeanLocal.saveInsumo(insumo);
    }

    public void cerrarDialog() {
        RequestContext.getCurrentInstance().execute("PF('dlgTicket').hide()");
    }

    /*Metodos getters y setters*/
    public Integer getIdAgencia() {
        return idAgencia;
    }

    public void setIdAgencia(Integer idAgencia) {
        this.idAgencia = idAgencia;
    }

    public List<Agencia> getListAgencia() {
        return listAgencia;
    }

    public void setListAgencia(List<Agencia> listAgencia) {
        this.listAgencia = listAgencia;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<Insumos> getListinsumos() {
        return listinsumos;
    }

    public void setListinsumos(List<Insumos> listinsumos) {
        this.listinsumos = listinsumos;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaInsumo() {
        return fechaInsumo;
    }

    public void setFechaInsumo(Date fechaInsumo) {
        this.fechaInsumo = fechaInsumo;
    }

}
