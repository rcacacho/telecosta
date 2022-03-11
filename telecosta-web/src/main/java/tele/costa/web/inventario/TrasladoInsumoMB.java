package tele.costa.web.inventario;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import tele.costa.api.ejb.CatalogoBeanLocal;
import tele.costa.api.entity.Agencia;
import tele.costa.api.entity.Insumos;
import telecosta.web.utils.JsfUtil;
import telecosta.web.utils.SesionUsuarioMB;
import tele.costa.api.ejb.InsumoBeanLocal;
import tele.costa.api.entity.Inventario;

/**
 *
 * @author rcacacho
 */
@ManagedBean(name = "trasladoInsumoMB")
@ViewScoped
public class TrasladoInsumoMB implements Serializable {

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
    private Integer saldoTraslado;
    private Inventario inventarioSelectedTraslado;
    private String codigoBusqueda;

    @PostConstruct
    void cargarDatos() {
        listAgencia = catalogoBean.listAgencias();
    }

    public void buscarInsumo() {
        if (fechaInicio != null && fechaFin != null && idAgencia != null) {
            List<Inventario> response = bodegaBeanLocal.listInsumoByFechaInicioAndFechaFinAndIdAgencia(fechaInicio, fechaFin, idAgencia);
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
        } else if (fechaInicio != null) {
            List<Inventario> response = bodegaBeanLocal.listInsumoByFechaInicio(fechaInicio);
            if (response != null) {
                listInventario = response;
            } else {
                listInventario = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (fechaFin != null) {
            List<Inventario> response = bodegaBeanLocal.listInsumoByFechaFin(fechaFin);
            if (response != null) {
                listInventario = response;
            } else {
                listInventario = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (idAgencia != null) {
            List<Inventario> response = bodegaBeanLocal.listInsumoByIdAgencia(idAgencia);
            if (response != null) {
                listInventario = response;
            } else {
                listInventario = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (codigoBusqueda != null) {
            List<Inventario> response = bodegaBeanLocal.listInsumoByCodigo(codigoBusqueda);
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

    public void dialogTraslado(Inventario insumo) {
        inventarioSelectedTraslado = insumo;
        RequestContext.getCurrentInstance().execute("PF('dlgTraslado').show()");
    }

    public void cerrarDialogTraslado() {
        RequestContext.getCurrentInstance().execute("PF('dlgTraslado').hide()");
    }

    public void trasladoInsumo() throws IOException {
        if (inventarioSelectedTraslado.getSalidas() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar una cantidad");
            return;
        }

        if (inventarioSelectedTraslado.getIdruta() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar una ruta");
            return;
        }

        if (inventarioSelectedTraslado.getNodocumento() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar un número de documento");
            return;
        }

        if (inventarioSelectedTraslado.getResponsable() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar un responsable");
            return;
        }

        if (inventarioSelectedTraslado.getObservacion() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar una observación");
            return;
        }

        if (inventarioSelectedTraslado.getSalidas() > inventarioSelectedTraslado.getExistencia()) {
            JsfUtil.addErrorMessage("La cantidad de salida es mayor a la existencia");
            return;
        }

        inventarioSelectedTraslado.setSalidas(saldoTraslado);
        inventarioSelectedTraslado.setExistencia(inventarioSelectedTraslado.getExistencia() - inventarioSelectedTraslado.getSalidas());
        inventarioSelectedTraslado.setTotal(inventarioSelectedTraslado.getPrecio() * inventarioSelectedTraslado.getExistencia());
        Inventario response = bodegaBeanLocal.updateInventario(inventarioSelectedTraslado);

        Inventario insumoSuma = bodegaBeanLocal.findInsumoByIdAgenciaAndCodigo(inventarioSelectedTraslado.getIdagenciaenvio().getIdagencia(), inventarioSelectedTraslado.getIdinsumo().getCodigo());
        if (insumoSuma != null) {
            insumoSuma.setEntradas(saldoTraslado);
            insumoSuma.setExistencia(insumoSuma.getEntradas() + insumoSuma.getExistencia());
            insumoSuma.setTotal(insumoSuma.getPrecio() * insumoSuma.getExistencia());
            Inventario responseUpdate = bodegaBeanLocal.updateInventario(insumoSuma);
        } else {
            Inventario insumo = new Inventario();
            insumo.setIdinsumo(inventarioSelectedTraslado.getIdinsumo());
            insumo.setIdagencia(inventarioSelectedTraslado.getIdagenciaenvio());
            insumo.setSaldoinicial(saldoTraslado);
            insumo.setExistencia(saldoTraslado);
            insumo.setPrecio(inventarioSelectedTraslado.getPrecio());
            insumo.setTotal(inventarioSelectedTraslado.getPrecio() * saldoTraslado);
            insumo.setUsuariocreacion(SesionUsuarioMB.getUserName());

            Inventario responseCreate = bodegaBeanLocal.saveInventario(insumo);
        }
        if (response != null) {
            JsfUtil.addSuccessMessage("Insumo trasladado exitosamente");
        } else {
            JsfUtil.addErrorMessage("Ocurrio un error verificar datos");
        }

        idAgenciaSelected = null;
        inventarioSelectedTraslado = null;
        RequestContext.getCurrentInstance().execute("PF('dlgTraslado').hide()");
    }

    public void detalle(Integer id) {
        JsfUtil.redirectTo("/bodega/detalle.xhtml?idinsumo=" + id);
    }

    public void limpiarCampos() {
        idAgencia = null;
        fechaInicio = null;
        fechaFin = null;
        cargarDatos();
    }

    /*Metodos getters y setters*/
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

    public Integer getSaldoTraslado() {
        return saldoTraslado;
    }

    public void setSaldoTraslado(Integer saldoTraslado) {
        this.saldoTraslado = saldoTraslado;
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

}
