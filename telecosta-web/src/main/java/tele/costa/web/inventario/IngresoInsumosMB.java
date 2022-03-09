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
    private List<Insumos> listinsumos;
    private String codigo;
    private String descripcion;
    private Insumos insumoSelected;
    private Integer saldoInicial;
    private float precio;
    private float precioActualizado;
    private Integer saldoIngreso;

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

    public void saveInsumo() throws IOException {
        if (codigo == null) {
            JsfUtil.addErrorMessage("Debe de ingresar un código");
            return;
        }

        if (descripcion == null) {
            JsfUtil.addErrorMessage("Debe de ingresar una descripción");
            return;
        }

        if (idAgenciaSelected.getIdagencia() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar una agencia");
            return;
        }

        Insumos insumo = new Insumos();
        insumo.setCodigo(codigo);
        insumo.setDescripcion(descripcion);
        //insumo.setIdagencia(idAgenciaSelected);
        //insumo.setSaldoinicial(saldoInicial);
        //insumo.setExistencia(saldoInicial);
        //insumo.setPrecio(precio);
        //insumo.setTotal(precio * saldoInicial);
        insumo.setUsuariocreacion(SesionUsuarioMB.getUserName());

        Insumos response = bodegaBeanLocal.saveInsumo(insumo);

        if (response != null) {
            JsfUtil.addSuccessMessage("Insumo registrado exitosamente");
        } else {
            JsfUtil.addErrorMessage("Ocurrio un error verificar datos");
        }

        idAgenciaSelected = null;
        codigo = null;
        descripcion = null;
        RequestContext.getCurrentInstance().execute("PF('dlgAgregar').hide()");
    }

    public void cerrarDialog() {
        RequestContext.getCurrentInstance().execute("PF('dlgAgregar').hide()");
    }

    public void buscarInsumo() {
        if (fechaInicio != null && fechaFin != null && idAgencia != null) {
            List<Insumos> response = bodegaBeanLocal.listInsumoByFechaInicioAndFechaFinAndIdAgencia(fechaInicio, fechaFin, idAgencia);
            if (response != null) {
                listinsumos = response;
            } else {
                listinsumos = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (fechaInicio != null && fechaFin != null) {
            List<Insumos> response = bodegaBeanLocal.listInsumoByFechaInicioAndFechaFin(fechaInicio, fechaFin);
            if (response != null) {
                listinsumos = response;
            } else {
                listinsumos = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (fechaInicio != null) {
            List<Insumos> response = bodegaBeanLocal.listInsumoByFechaInicio(fechaInicio);
            if (response != null) {
                listinsumos = response;
            } else {
                listinsumos = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (fechaFin != null) {
            List<Insumos> response = bodegaBeanLocal.listInsumoByFechaFin(fechaFin);
            if (response != null) {
                listinsumos = response;
            } else {
                listinsumos = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (idAgencia != null) {
            List<Insumos> response = bodegaBeanLocal.listInsumoByIdAgencia(idAgencia);
            if (response != null) {
                listinsumos = response;
            } else {
                listinsumos = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else {
            listinsumos = new ArrayList<>();
            JsfUtil.addErrorMessage("No se encontraron datos");
        }
    }

    public void dialogAgregar(Insumos insumo) {
        insumoSelected = insumo;
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

//        if (insumoSelected.getPrecio() == null) {
//            JsfUtil.addErrorMessage("Debe de ingresar un precio");
//            return;
//        }
//
//        if (insumoSelected.getNodocumento() == null) {
//            JsfUtil.addErrorMessage("Debe de ingresar un número de documento");
//            return;
//        }
//
//        if (insumoSelected.getProveedor() == null) {
//            JsfUtil.addErrorMessage("Debe de ingresar una agencia o Proveedor");
//            return;
//        }
//
//        if (insumoSelected.getFecha() == null) {
//            JsfUtil.addErrorMessage("Debe de ingresar una fecha");
//            return;
//        }
//
//        if (insumoSelected.getObservacion() == null) {
//            JsfUtil.addErrorMessage("Debe de ingresar una observación");
//            return;
//        }
//
//        insumoSelected.setEntradas(saldoIngreso);
        //insumoSelected.setPrecio((precioActualizado + insumoSelected.getPrecio()) / 2);
        //insumoSelected.setExistencia(insumoSelected.getEntradas() + insumoSelected.getExistencia());
        //insumoSelected.setTotal(insumoSelected.getPrecio() * insumoSelected.getExistencia());
        Insumos response = bodegaBeanLocal.updateInsumo(insumoSelected);
        if (response != null) {
            JsfUtil.addSuccessMessage("Insumo actualizado exitosamente");
        } else {
            JsfUtil.addErrorMessage("Ocurrio un error verificar datos");
        }

        idAgenciaSelected = null;
        codigo = null;
        descripcion = null;
        insumoSelected = null;
        saldoIngreso = null;
        RequestContext.getCurrentInstance().execute("PF('dlgExistencia').hide()");
    }

    public void detalle(Integer id) {
        JsfUtil.redirectTo("/bodega/detalle.xhtml?idinsumo=" + id);
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

    public Agencia getIdAgenciaSelected() {
        return idAgenciaSelected;
    }

    public void setIdAgenciaSelected(Agencia idAgenciaSelected) {
        this.idAgenciaSelected = idAgenciaSelected;
    }

    public Insumos getInsumoSelected() {
        return insumoSelected;
    }

    public void setInsumoSelected(Insumos insumoSelected) {
        this.insumoSelected = insumoSelected;
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

}
