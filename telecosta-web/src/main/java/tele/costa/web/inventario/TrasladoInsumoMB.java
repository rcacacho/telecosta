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
import telecosta.web.utils.JsfUtil;
import telecosta.web.utils.SesionUsuarioMB;
import tele.costa.api.ejb.InsumoBeanLocal;
import tele.costa.api.entity.Bitacorainventario;
import tele.costa.api.entity.Insumos;
import tele.costa.api.entity.Inventario;
import tele.costa.api.entity.Tipocarga;
import tele.costa.api.enums.TipoCargaEnum;

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
    private Inventario inventarioSelectedTraslado;
    private String codigoBusqueda;
    private Integer idinsumo;
    private List<Insumos> listInsumos;

    public TrasladoInsumoMB() {
        inventarioSelectedTraslado = new Inventario();
    }

    @PostConstruct
    void cargarDatos() {
        listAgencia = catalogoBean.listAgencias();
        listInsumos = catalogoBean.listInsumos();
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
        } else if (idinsumo != null) {
            List<Inventario> response = bodegaBeanLocal.listInsumoByIdInsumo(idinsumo);
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
        if (inventarioSelectedTraslado.getTraslado() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar una cantidad");
            return;
        }

        if (inventarioSelectedTraslado.getIdruta() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar una ruta");
            return;
        }

        if (inventarioSelectedTraslado.getNodocumentotraslado() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar un número de documento");
            return;
        }

        if (inventarioSelectedTraslado.getResponsabletraslado() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar un responsable");
            return;
        }

        if (inventarioSelectedTraslado.getObservaciontraslado() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar una observación");
            return;
        }

        if (inventarioSelectedTraslado.getTraslado() > inventarioSelectedTraslado.getExistencia()) {
            JsfUtil.addErrorMessage("La cantidad de salida es mayor a la existencia");
            return;
        }

        inventarioSelectedTraslado.setExistencia(inventarioSelectedTraslado.getExistencia() - inventarioSelectedTraslado.getTraslado());
        inventarioSelectedTraslado.setTotal(inventarioSelectedTraslado.getPrecio() * inventarioSelectedTraslado.getExistencia());
        Inventario response = bodegaBeanLocal.updateInventario(inventarioSelectedTraslado);

        Inventario insumoSuma = bodegaBeanLocal.findInsumoByIdAgenciaAndCodigo(inventarioSelectedTraslado.getIdagenciaenvio().getIdagencia(), inventarioSelectedTraslado.getIdinsumo().getCodigo());
        if (insumoSuma != null) {
            insumoSuma.setEntradas(inventarioSelectedTraslado.getTraslado());
            insumoSuma.setExistencia(insumoSuma.getEntradas() + insumoSuma.getExistencia());
            insumoSuma.setTotal(insumoSuma.getPrecio() * insumoSuma.getExistencia());
            Inventario responseUpdate = bodegaBeanLocal.updateInventario(insumoSuma);
        } else {
            Inventario insumo = new Inventario();
            insumo.setIdinsumo(inventarioSelectedTraslado.getIdinsumo());
            insumo.setIdagencia(inventarioSelectedTraslado.getIdagenciaenvio());
            insumo.setSaldoinicial(inventarioSelectedTraslado.getTraslado());
            insumo.setExistencia(inventarioSelectedTraslado.getTraslado());
            insumo.setPrecio(inventarioSelectedTraslado.getPrecio());
            insumo.setTotal(inventarioSelectedTraslado.getPrecio() * inventarioSelectedTraslado.getTraslado());
            insumo.setUsuariocreacion(SesionUsuarioMB.getUserName());

            Inventario responseCreate = bodegaBeanLocal.saveInventario(insumo);
        }
        if (response != null) {
            Tipocarga tipo = catalogoBean.findTipoCarga(TipoCargaEnum.ENVIOS.getId());
            Bitacorainventario bitacora = new Bitacorainventario();
            bitacora.setCantidad(inventarioSelectedTraslado.getTraslado());
            bitacora.setCodigo(inventarioSelectedTraslado.getIdinsumo().getCodigo());
            bitacora.setDescripcion(inventarioSelectedTraslado.getIdinsumo().getDescripcion());
            bitacora.setDocumento(inventarioSelectedTraslado.getNodocumento());
            bitacora.setFecha(inventarioSelectedTraslado.getFecha());
            bitacora.setIdagencia(inventarioSelectedTraslado.getIdagencia());
            bitacora.setIdtipocarga(tipo);
            bitacora.setPreciounitario(inventarioSelectedTraslado.getPrecio());
            bitacora.setProveedor(inventarioSelectedTraslado.getProveedor());
            bitacora.setTotal(inventarioSelectedTraslado.getTotal());
            bitacora.setDestino(inventarioSelectedTraslado.getIdagenciaenvio().getAgencia());
            bitacora.setUsuariocreacion(SesionUsuarioMB.getUserName());
            Bitacorainventario bitacoraSave = bodegaBeanLocal.saveBitacoraInventario(bitacora);
            JsfUtil.addSuccessMessage("Insumo trasladado exitosamente");
        } else {
            JsfUtil.addErrorMessage("Ocurrio un error verificar datos");
        }

        idAgenciaSelected = null;
        inventarioSelectedTraslado = null;
        RequestContext.getCurrentInstance().execute("PF('dlgTraslado').hide()");
    }

    public void detalle(Integer id) {
        JsfUtil.redirectTo("/inventario/detalle.xhtml?idinventario=" + id + "&idregresar=3");
    }

    public void limpiarCampos() {
        idAgencia = null;
        fechaInicio = null;
        fechaFin = null;
        cargarDatos();
        idinsumo = null;
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
