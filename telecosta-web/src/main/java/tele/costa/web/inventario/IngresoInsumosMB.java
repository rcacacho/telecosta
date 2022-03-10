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
import tele.costa.api.ejb.InsumoBeanLocal;
import tele.costa.api.entity.Inventario;
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
    private InsumoBeanLocal bodegaBeanLocal;

    private Integer idAgencia;
    private Agencia idAgenciaSelected;
    private List<Agencia> listAgencia;
    private Date fechaInicio;
    private Date fechaFin;
    private List<Inventario> listInventario;
    private String codigo;
    private String descripcion;
    private Inventario inventarioSelected;
    private Integer saldoInicial;
    private float precio;
    private float precioActualizado;
    private Integer saldoIngreso;
    private Insumos insumoSelected;
    private String codigoBusqueda;

    public IngresoInsumosMB() {
        idAgencia = null;
    }

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
        idAgenciaSelected = null;
        RequestContext.getCurrentInstance().execute("PF('dlgAgregar').show()");
    }

    public void saveInventario() throws IOException {
        if (insumoSelected == null) {
            JsfUtil.addErrorMessage("Debe buscar un insumo");
            return;
        }

        if (saldoInicial == null) {
            JsfUtil.addErrorMessage("Debe de ingresar un saldo inicial");
            return;
        }

        if (idAgenciaSelected.getIdagencia() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar una agencia");
            return;
        }

        Inventario inventario = new Inventario();
        inventario.setIdinsumo(insumoSelected);
        inventario.setSaldoinicial(saldoInicial);
        inventario.setIdagencia(idAgenciaSelected);
        inventario.setExistencia(saldoInicial);
        inventario.setPrecio(precio);
        inventario.setTotal(precio * saldoInicial);
        inventario.setUsuariocreacion(SesionUsuarioMB.getUserName());
        Inventario response = bodegaBeanLocal.saveInventario(inventario);
        if (response != null) {
            JsfUtil.addSuccessMessage("Insumo registrado exitosamente");
        } else {
            JsfUtil.addErrorMessage("Ocurrio un error verificar datos");
        }
        idAgenciaSelected = null;
        codigo = null;
        descripcion = null;
        insumoSelected = null;
        RequestContext.getCurrentInstance().execute("PF('dlgAgregar').hide()");
    }

    public void cerrarDialog() {
        RequestContext.getCurrentInstance().execute("PF('dlgAgregar').hide()");
    }

    public void buscarInventario() {
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
                //listinsumos = response;
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
            List<Inventario> response = bodegaBeanLocal.listInsumoByIdAgencia(idAgencia);
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

    public void dialogAgregar(Inventario invent) {
        inventarioSelected = invent;
        RequestContext.getCurrentInstance().execute("PF('dlgExistencia').show()");
    }

    public void cerrarDialogIngreso() {
        RequestContext.getCurrentInstance().execute("PF('dlgExistencia').hide()");
    }

    public void updateInsumo() throws IOException {
        if (saldoIngreso == null) {
            JsfUtil.addErrorMessage("Debe de ingresar una cantidad");
            return;
        }

        if (saldoIngreso == null) {
            JsfUtil.addErrorMessage("Debe de ingresar una cantidad a agregar");
            return;
        }

        if (saldoIngreso == null) {
            JsfUtil.addErrorMessage("Debe de ingresar una cantidad a agregar");
            return;
        }

        if (inventarioSelected.getNodocumento() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar un número de documento");
            return;
        }

        if (inventarioSelected.getProveedor() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar una agencia o Proveedor");
            return;
        }

        if (inventarioSelected.getFecha() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar una fecha");
            return;
        }

        if (inventarioSelected.getObservacion() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar una observación");
            return;
        }

        inventarioSelected.setEntradas(saldoIngreso);
        inventarioSelected.setPrecio((precioActualizado + inventarioSelected.getPrecio()) / 2);
        inventarioSelected.setExistencia(inventarioSelected.getEntradas() + inventarioSelected.getExistencia());
        inventarioSelected.setTotal(inventarioSelected.getPrecio() * inventarioSelected.getExistencia());
        Inventario response = bodegaBeanLocal.updateInventario(inventarioSelected);
        if (response != null) {
            JsfUtil.addSuccessMessage("Insumo actualizado exitosamente");
        } else {
            JsfUtil.addErrorMessage("Ocurrio un error verificar datos");
        }
        idAgenciaSelected = null;
        codigo = null;
        descripcion = null;
        inventarioSelected = null;
        saldoIngreso = null;
        RequestContext.getCurrentInstance().execute("PF('dlgExistencia').hide()");
    }

    public void detalle(Integer id) {
        JsfUtil.redirectTo("/bodega/detalle.xhtml?idinsumo=" + id);
    }

    public void buscarInsumo() {
        if (codigo == null) {
            JsfUtil.addErrorMessage("Debe ingresar un código para realizar la busqueda");
            return;
        }

        Insumos response = bodegaBeanLocal.findInsumoByCodigo(codigo);
        if (response != null) {
            insumoSelected = response;
        } else {
            codigo = null;
            JsfUtil.addErrorMessage("No se encontraron datos");
        }
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

    public Agencia getIdAgenciaSelected() {
        return idAgenciaSelected;
    }

    public void setIdAgenciaSelected(Agencia idAgenciaSelected) {
        this.idAgenciaSelected = idAgenciaSelected;
    }

    public Integer getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(Integer saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public float getPrecioActualizado() {
        return precioActualizado;
    }

    public void setPrecioActualizado(float precioActualizado) {
        this.precioActualizado = precioActualizado;
    }

    public Integer getSaldoIngreso() {
        return saldoIngreso;
    }

    public void setSaldoIngreso(Integer saldoIngreso) {
        this.saldoIngreso = saldoIngreso;
    }

    public List<Inventario> getListInventario() {
        return listInventario;
    }

    public void setListInventario(List<Inventario> listInventario) {
        this.listInventario = listInventario;
    }

    public Inventario getInventarioSelected() {
        return inventarioSelected;
    }

    public void setInventarioSelected(Inventario inventarioSelected) {
        this.inventarioSelected = inventarioSelected;
    }

    public Insumos getInsumoSelected() {
        return insumoSelected;
    }

    public void setInsumoSelected(Insumos insumoSelected) {
        this.insumoSelected = insumoSelected;
    }

    public String getCodigoBusqueda() {
        return codigoBusqueda;
    }

    public void setCodigoBusqueda(String codigoBusqueda) {
        this.codigoBusqueda = codigoBusqueda;
    }

}
