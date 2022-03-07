package tele.costa.web.bodega;

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
import tele.costa.api.ejb.BodegaBeanLocal;
import tele.costa.api.ejb.CatalogoBeanLocal;
import tele.costa.api.entity.Agencia;
import tele.costa.api.entity.Insumos;
import tele.costa.api.entity.Ruta;
import telecosta.web.utils.JsfUtil;
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
    private Insumos insumoSelectedSalida;
    private List<Ruta> listRuta;
    private Integer saldoIngreso;
    private Integer saldoSalida;
    private Integer saldoTraslado;
    private Insumos insumoSelectedTraslado;

    public IngresoInsumosMB() {
        idAgencia = null;
    }

    @PostConstruct
    void cargarDatos() {
        listAgencia = catalogoBean.listAgencias();
        listRuta = catalogoBean.listRuta();
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
        insumo.setIdagencia(idAgenciaSelected);
        insumo.setSaldoinicial(saldoInicial);
        insumo.setExistencia(saldoInicial);
        insumo.setPrecio(precio);
        insumo.setTotal(precio * saldoInicial);
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
        if (insumoSelected.getEntradas() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar una cantidad");
            return;
        }

        if (insumoSelected.getPrecio() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar un precio");
            return;
        }

        if (insumoSelected.getNodocumento() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar un número de documento");
            return;
        }

        if (insumoSelected.getProveedor() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar una agencia o Proveedor");
            return;
        }

        if (insumoSelected.getFecha() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar una fecha");
            return;
        }

        if (insumoSelected.getObservacion() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar una observación");
            return;
        }

        insumoSelected.setEntradas(saldoIngreso);
        insumoSelected.setPrecio((precioActualizado + insumoSelected.getPrecio()) / 2);
        insumoSelected.setExistencia(insumoSelected.getEntradas() + insumoSelected.getExistencia());
        insumoSelected.setTotal(insumoSelected.getPrecio() * insumoSelected.getExistencia());
        Insumos response = bodegaBeanLocal.updateInsumo(insumoSelected);
        if (response != null) {
            JsfUtil.addSuccessMessage("Insumo actualizado exitosamente");
        } else {
            JsfUtil.addErrorMessage("Ocurrio un error verificar datos");
        }

        idAgenciaSelected = null;
        codigo = null;
        descripcion = null;
        insumoSelected = new Insumos();
        RequestContext.getCurrentInstance().execute("PF('dlgExistencia').hide()");
    }

    public void detalle(Integer id) {
        JsfUtil.redirectTo("/bodega/detalle.xhtml?idinsumo=" + id);
    }

    public void dialogSalida(Insumos insumo) {
        insumoSelectedSalida = insumo;
        RequestContext.getCurrentInstance().execute("PF('dlgSalida').show()");
    }

    public void cerrarDialogSalida() {
        RequestContext.getCurrentInstance().execute("PF('dlgSalida').hide()");
    }

    public void salidaInsumo() throws IOException {
        if (insumoSelectedSalida.getSalidas() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar una cantidad");
            return;
        }

        if (insumoSelectedSalida.getIdruta() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar una ruta");
            return;
        }

        if (insumoSelectedSalida.getNodocumento() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar un número de documento");
            return;
        }

        if (insumoSelectedSalida.getResponsable() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar un responsable");
            return;
        }

        if (insumoSelectedSalida.getObservacion() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar una observación");
            return;
        }

        if (insumoSelectedSalida.getSalidas() > insumoSelectedSalida.getExistencia()) {
            JsfUtil.addErrorMessage("La cantidad de salida es mayor a la existencia");
            return;
        }

        insumoSelectedSalida.setSalidas(saldoSalida);
        insumoSelectedSalida.setExistencia(insumoSelectedSalida.getExistencia() - insumoSelectedSalida.getSalidas());
        insumoSelectedSalida.setTotal(insumoSelectedSalida.getPrecio() * insumoSelectedSalida.getExistencia());
        Insumos response = bodegaBeanLocal.updateInsumo(insumoSelectedSalida);
        if (response != null) {
            JsfUtil.addSuccessMessage("Insumo actualizado exitosamente");
        } else {
            JsfUtil.addErrorMessage("Ocurrio un error verificar datos");
        }

        idAgenciaSelected = null;
        codigo = null;
        descripcion = null;
        insumoSelectedSalida = new Insumos();
        RequestContext.getCurrentInstance().execute("PF('dlgSalida').hide()");
    }

    public void dialogTraslado(Insumos insumo) {
        insumoSelectedTraslado = insumo;
        RequestContext.getCurrentInstance().execute("PF('dlgTraslado').show()");
    }

    public void cerrarDialogTraslado() {
        RequestContext.getCurrentInstance().execute("PF('dlgTraslado').hide()");
    }
    
    public void trasladoInsumo() throws IOException {
        if (insumoSelectedTraslado.getSalidas() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar una cantidad");
            return;
        }

        if (insumoSelectedTraslado.getIdruta() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar una ruta");
            return;
        }

        if (insumoSelectedTraslado.getNodocumento() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar un número de documento");
            return;
        }

        if (insumoSelectedTraslado.getResponsable() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar un responsable");
            return;
        }

        if (insumoSelectedTraslado.getObservacion() == null) {
            JsfUtil.addErrorMessage("Debe de ingresar una observación");
            return;
        }

        if (insumoSelectedTraslado.getSalidas() > insumoSelectedTraslado.getExistencia()) {
            JsfUtil.addErrorMessage("La cantidad de salida es mayor a la existencia");
            return;
        }

        insumoSelectedTraslado.setSalidas(saldoTraslado);
        insumoSelectedTraslado.setExistencia(insumoSelectedTraslado.getExistencia() - insumoSelectedTraslado.getSalidas());
        insumoSelectedTraslado.setTotal(insumoSelectedTraslado.getPrecio() * insumoSelectedTraslado.getExistencia());
        Insumos response = bodegaBeanLocal.updateInsumo(insumoSelectedTraslado);
        
        Insumos insumoSuma = bodegaBeanLocal.findInsumoById(idAgencia);
        
        
        if (response != null) {
            JsfUtil.addSuccessMessage("Insumo actualizado exitosamente");
        } else {
            JsfUtil.addErrorMessage("Ocurrio un error verificar datos");
        }

        idAgenciaSelected = null;
        codigo = null;
        descripcion = null;
        insumoSelectedSalida = new Insumos();
        RequestContext.getCurrentInstance().execute("PF('dlgSalida').hide()");
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

    public Insumos getInsumoSelectedSalida() {
        return insumoSelectedSalida;
    }

    public void setInsumoSelectedSalida(Insumos insumoSelectedSalida) {
        this.insumoSelectedSalida = insumoSelectedSalida;
    }

    public List<Ruta> getListRuta() {
        return listRuta;
    }

    public void setListRuta(List<Ruta> listRuta) {
        this.listRuta = listRuta;
    }

    public Integer getSaldoIngreso() {
        return saldoIngreso;
    }

    public void setSaldoIngreso(Integer saldoIngreso) {
        this.saldoIngreso = saldoIngreso;
    }

    public Integer getSaldoSalida() {
        return saldoSalida;
    }

    public void setSaldoSalida(Integer saldoSalida) {
        this.saldoSalida = saldoSalida;
    }

    public Integer getSaldoTraslado() {
        return saldoTraslado;
    }

    public void setSaldoTraslado(Integer saldoTraslado) {
        this.saldoTraslado = saldoTraslado;
    }

    public Insumos getInsumoSelectedTraslado() {
        return insumoSelectedTraslado;
    }

    public void setInsumoSelectedTraslado(Insumos insumoSelectedTraslado) {
        this.insumoSelectedTraslado = insumoSelectedTraslado;
    }

}
