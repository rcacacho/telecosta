package tele.costa.web.inventario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import tele.costa.api.ejb.CatalogoBeanLocal;
import tele.costa.api.ejb.InsumoBeanLocal;
import tele.costa.api.entity.Agencia;
import tele.costa.api.entity.Insumos;
import tele.costa.api.entity.Inventario;
import telecosta.web.utils.JsfUtil;

/**
 *
 * @author elfo_
 */
@ManagedBean(name = "tecnicosMB")
@ViewScoped
public class TecnicosMB implements Serializable {

    private static final Logger log = Logger.getLogger(TrasladoInsumoMB.class);

    @EJB
    private CatalogoBeanLocal catalogoBean;
    @EJB
    private InsumoBeanLocal bodegaBeanLocal;

    private Integer idAgencia;
    private Agencia idAgenciaSelected;
    private List<Agencia> listAgencia;
    private Date fechaInicio;
    private Date fechaFin;
    private List<Inventario> listInventario;
    private Inventario inventarioSelectedTraslado;
    private String codigoBusqueda;
    private Integer idinsumo;
    private List<Insumos> listInsumos;

    @PostConstruct
    void cargarDatos() {
        listAgencia = catalogoBean.listAgencias();
        listInsumos = catalogoBean.listInsumos();
    }

    public void buscarInsumo() {
        if (fechaInicio != null && fechaFin != null && idAgencia != null) {
            List<Inventario> response = bodegaBeanLocal.listInsumoTecnicoByFechaInicioAndFechaFinAndIdAgencia(fechaInicio, fechaFin, idAgencia);
            if (response != null) {
                listInventario = response;
            } else {
                listInventario = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (fechaInicio != null && fechaFin != null) {
            List<Inventario> response = bodegaBeanLocal.listInsumoByFechaInicioAndFechaFin(fechaInicio, fechaFin);
            if (response != null) {
                listInventario = response;
            } else {
                listInventario = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (idAgencia != null && codigoBusqueda != null) {
            List<Inventario> response = bodegaBeanLocal.listInsumoTecnicoByIdAgenciaAndCodigo(idAgencia, codigoBusqueda);
            if (response != null) {
                listInventario = response;
            } else {
                listInventario = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (fechaInicio != null) {
            List<Inventario> response = bodegaBeanLocal.listInsumoTecnicoByFechaInicio(fechaInicio);
            if (response != null) {
                listInventario = response;
            } else {
                listInventario = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (fechaFin != null) {
            List<Inventario> response = bodegaBeanLocal.listInsumoTecnicoByFechaFin(fechaFin);
            if (response != null) {
                listInventario = response;
            } else {
                listInventario = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (idAgencia != null) {
            List<Inventario> response = bodegaBeanLocal.listInsumoTecnicoByIdAgencia(idAgencia);
            if (response != null) {
                listInventario = response;
            } else {
                listInventario = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (codigoBusqueda != null) {
            List<Inventario> response = bodegaBeanLocal.listInsumoTecnicoByCodigo(codigoBusqueda);
            if (response != null) {
                listInventario = response;
            } else {
                listInventario = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (idinsumo != null) {
            List<Inventario> response = bodegaBeanLocal.listInsumoTecnicoByIdInsumo(idinsumo);
            if (response != null) {
                listInventario = response;
            } else {
                listInventario = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else {
            listInventario = new ArrayList<>();
            JsfUtil.addErrorMessage("No se encontraron datos");
        }
    }

    public void limpiarCampos() {
        idAgencia = null;
        fechaInicio = null;
        fechaFin = null;
        codigoBusqueda = null;
        listInventario = new ArrayList<>();
        cargarDatos();
        idinsumo = null;
    }

    public void detalle(Integer id) {
        JsfUtil.redirectTo("/inventario/detalle.xhtml?idinventario=" + id + "&idregresar=4");
    }

    /*Metodos getters y setters*/
    public CatalogoBeanLocal getCatalogoBean() {
        return catalogoBean;
    }

    public void setCatalogoBean(CatalogoBeanLocal catalogoBean) {
        this.catalogoBean = catalogoBean;
    }

    public InsumoBeanLocal getBodegaBeanLocal() {
        return bodegaBeanLocal;
    }

    public void setBodegaBeanLocal(InsumoBeanLocal bodegaBeanLocal) {
        this.bodegaBeanLocal = bodegaBeanLocal;
    }

    public Integer getIdAgencia() {
        return idAgencia;
    }

    public void setIdAgencia(Integer idAgencia) {
        this.idAgencia = idAgencia;
    }

    public Agencia getIdAgenciaSelected() {
        return idAgenciaSelected;
    }

    public void setIdAgenciaSelected(Agencia idAgenciaSelected) {
        this.idAgenciaSelected = idAgenciaSelected;
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

    public List<Inventario> getListInventario() {
        return listInventario;
    }

    public void setListInventario(List<Inventario> listInventario) {
        this.listInventario = listInventario;
    }

    public Inventario getInventarioSelectedTraslado() {
        return inventarioSelectedTraslado;
    }

    public void setInventarioSelectedTraslado(Inventario inventarioSelectedTraslado) {
        this.inventarioSelectedTraslado = inventarioSelectedTraslado;
    }

    public String getCodigoBusqueda() {
        return codigoBusqueda;
    }

    public void setCodigoBusqueda(String codigoBusqueda) {
        this.codigoBusqueda = codigoBusqueda;
    }

    public Integer getIdinsumo() {
        return idinsumo;
    }

    public void setIdinsumo(Integer idinsumo) {
        this.idinsumo = idinsumo;
    }

    public List<Insumos> getListInsumos() {
        return listInsumos;
    }

    public void setListInsumos(List<Insumos> listInsumos) {
        this.listInsumos = listInsumos;
    }

}
