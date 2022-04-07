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
import org.primefaces.event.RowEditEvent;
import tele.costa.api.ejb.CatalogoBeanLocal;
import tele.costa.api.entity.Agencia;
import tele.costa.api.entity.Ruta;
import telecosta.web.utils.JsfUtil;
import tele.costa.api.ejb.InsumoBeanLocal;
import tele.costa.api.entity.Bitacorainventario;
import tele.costa.api.entity.Insumos;
import tele.costa.api.entity.Inventario;
import tele.costa.api.entity.Tipocarga;
import tele.costa.api.enums.TipoCargaEnum;
import telecosta.web.utils.SesionUsuarioMB;

/**
 *
 * @author rcacacho
 */
@ManagedBean(name = "salidaInsumoMB")
@ViewScoped
public class SalidaInsumoMB implements Serializable {

    private static final Logger log = Logger.getLogger(SalidaInsumoMB.class);

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
    private Inventario inventarioSelectedSalida;
    private List<Ruta> listRuta;
    private Integer saldoSalida;
    private String codigoBusqueda;
    private Insumos insumo;

    public SalidaInsumoMB() {
        inventarioSelectedSalida = new Inventario();
        insumo = new Insumos();
    }

    @PostConstruct
    void cargarDatos() {
        listAgencia = catalogoBean.listAgencias();
        listRuta = catalogoBean.listRuta();
    }

    public void dialogSalida(Inventario inve) {
        inventarioSelectedSalida = inve;
        RequestContext.getCurrentInstance().execute("PF('dlgSalida').show()");
    }

    public void cerrarDialogSalida() {
        RequestContext.getCurrentInstance().execute("PF('dlgSalida').hide()");
    }

    public void salidaInsumo() throws IOException {
        if (saldoSalida == null) {
            JsfUtil.addErrorMessage("Debe de ingresar una cantidad");
            return;
        }

        if (inventarioSelectedSalida.getIdruta() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar una ruta");
            return;
        }

        if (inventarioSelectedSalida.getNodocumentosalida() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar un número de documento");
            return;
        }

        if (inventarioSelectedSalida.getResponsable() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar un responsable");
            return;
        }

        if (inventarioSelectedSalida.getObservacion() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar una observación");
            return;
        }

        inventarioSelectedSalida.setSalidas(saldoSalida);
        if (inventarioSelectedSalida.getSalidas() > inventarioSelectedSalida.getExistencia()) {
            JsfUtil.addErrorMessage("La cantidad de salida es mayor a la existencia");
            return;
        }

        Tipocarga tipo = catalogoBean.findTipoCarga(TipoCargaEnum.SALIDA.getId());
        inventarioSelectedSalida.setExistencia(inventarioSelectedSalida.getExistencia() - inventarioSelectedSalida.getSalidas());
        inventarioSelectedSalida.setTotal(inventarioSelectedSalida.getPrecio() * inventarioSelectedSalida.getExistencia());
        Inventario response = bodegaBeanLocal.updateInventario(inventarioSelectedSalida);
        if (response != null) {
            Bitacorainventario bitacora = new Bitacorainventario();
            bitacora.setCantidad(saldoSalida);
            bitacora.setCodigo(inventarioSelectedSalida.getIdinsumo().getCodigo());
            bitacora.setDescripcion(inventarioSelectedSalida.getIdinsumo().getDescripcion());
            bitacora.setDocumento(inventarioSelectedSalida.getNodocumento());
            bitacora.setFecha(inventarioSelectedSalida.getFecha());
            bitacora.setIdagencia(inventarioSelectedSalida.getIdagencia());
            bitacora.setIdtipocarga(tipo);
            bitacora.setPreciounitario(inventarioSelectedSalida.getPrecio());
            bitacora.setProveedor(inventarioSelectedSalida.getProveedor());
            bitacora.setTotal(inventarioSelectedSalida.getTotal());
            bitacora.setResponsable(inventarioSelectedSalida.getResponsable());
            bitacora.setSector(inventarioSelectedSalida.getIdruta().getRuta());
            bitacora.setUsuariocreacion(SesionUsuarioMB.getUserName());
            Bitacorainventario bitacoraSave = bodegaBeanLocal.saveBitacoraInventario(bitacora);
            JsfUtil.addSuccessMessage("Insumo actualizado exitosamente");
        } else {
            JsfUtil.addErrorMessage("Ocurrio un error verificar datos");
        }

        idAgenciaSelected = null;
        inventarioSelectedSalida = null;
        saldoSalida = null;
        RequestContext.getCurrentInstance().execute("PF('dlgSalida').hide()");
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
        } else if (idAgencia != null && codigoBusqueda != null) {
            List<Inventario> response = bodegaBeanLocal.listInsumoByIdAgenciaAndCodigo(idAgencia, codigoBusqueda);
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

    public void limpiarCampos() {
        idAgencia = null;
        fechaInicio = null;
        fechaFin = null;
        codigoBusqueda = null;
        listInventario = new ArrayList<>();
        cargarDatos();
    }

    public void detalle(Integer id) {
        JsfUtil.redirectTo("/inventario/detalle.xhtml?idinventario=" + id+"&idregresar=2");
    }

    public void saveInsumo() throws IOException {
        if (insumo.getCodigo() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar un código");
            return;
        }

        if (insumo.getDescripcion() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar una descripción");
            return;
        }

        insumo.setUsuariocreacion(SesionUsuarioMB.getUserName());

        Insumos response = bodegaBeanLocal.saveInsumo(insumo);
        if (response != null) {
            JsfUtil.addSuccessMessage("Insumo registrado exitosamente");
        } else {
            JsfUtil.addErrorMessage("Ocurrio un error verificar datos");
        }

        insumo = null;
    }

    public void cerrarDialogRegistro() {
        RequestContext.getCurrentInstance().execute("PF('dlgRegistro').hide()");
    }

    public void dialogRegistro() {
        RequestContext.getCurrentInstance().execute("PF('dlgRegistro').show()");
    }

    public void onRowEdit(RowEditEvent event) {
        Object value = event.getObject();
        Inventario inv = (Inventario) value;

        if (inv != null) {
            Inventario tt = bodegaBeanLocal.updateInventario(inv);
            JsfUtil.addSuccessMessage("Se actualizo la compra exitosamente");
            cargarDatos();
        } else {
            JsfUtil.addErrorMessage("Sucedio un error al actualizar el registro");
        }
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

    public Inventario getInventarioSelectedSalida() {
        return inventarioSelectedSalida;
    }

    public void setInventarioSelectedSalida(Inventario inventarioSelectedSalida) {
        this.inventarioSelectedSalida = inventarioSelectedSalida;
    }

    public List<Ruta> getListRuta() {
        return listRuta;
    }

    public void setListRuta(List<Ruta> listRuta) {
        this.listRuta = listRuta;
    }

    public Integer getSaldoSalida() {
        return saldoSalida;
    }

    public void setSaldoSalida(Integer saldoSalida) {
        this.saldoSalida = saldoSalida;
    }

    public String getCodigoBusqueda() {
        return codigoBusqueda;
    }

    public void setCodigoBusqueda(String codigoBusqueda) {
        this.codigoBusqueda = codigoBusqueda;
    }

    public Insumos getInsumo() {
        return insumo;
    }

    public void setInsumo(Insumos insumo) {
        this.insumo = insumo;
    }

}
