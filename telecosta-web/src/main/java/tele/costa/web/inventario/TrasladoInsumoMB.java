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
    private List<Insumos> listinsumos;
    private Integer saldoTraslado;
    private Insumos insumoSelectedTraslado;

    @PostConstruct
    void cargarDatos() {
        listAgencia = catalogoBean.listAgencias();
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

    public void dialogTraslado(Insumos insumo) {
        insumoSelectedTraslado = insumo;
        RequestContext.getCurrentInstance().execute("PF('dlgTraslado').show()");
    }

    public void cerrarDialogTraslado() {
        RequestContext.getCurrentInstance().execute("PF('dlgTraslado').hide()");
    }

    public void trasladoInsumo() throws IOException {
//        if (insumoSelectedTraslado.getSalidas() == null) {
//            JsfUtil.addErrorMessage("Debe de ingresar una cantidad");
//            return;
//        }
//
//        if (insumoSelectedTraslado.getIdruta() == null) {
//            JsfUtil.addErrorMessage("Debe de ingresar una ruta");
//            return;
//        }
//
//        if (insumoSelectedTraslado.getNodocumento() == null) {
//            JsfUtil.addErrorMessage("Debe de ingresar un número de documento");
//            return;
//        }
//
//        if (insumoSelectedTraslado.getResponsable() == null) {
//            JsfUtil.addErrorMessage("Debe de ingresar un responsable");
//            return;
//        }
//
//        if (insumoSelectedTraslado.getObservacion() == null) {
//            JsfUtil.addErrorMessage("Debe de ingresar una observación");
//            return;
//        }
//
//        if (insumoSelectedTraslado.getSalidas() > insumoSelectedTraslado.getExistencia()) {
//            JsfUtil.addErrorMessage("La cantidad de salida es mayor a la existencia");
//            return;
//        }

        //insumoSelectedTraslado.setSalidas(saldoTraslado);
        //insumoSelectedTraslado.setExistencia(insumoSelectedTraslado.getExistencia() - insumoSelectedTraslado.getSalidas());
        //insumoSelectedTraslado.setTotal(insumoSelectedTraslado.getPrecio() * insumoSelectedTraslado.getExistencia());
        Insumos response = bodegaBeanLocal.updateInsumo(insumoSelectedTraslado);

        //Insumos insumoSuma = bodegaBeanLocal.findInsumoByIdAgenciaAndCodigo(insumoSelectedTraslado.getIdagenciaenvio().getIdagencia(), insumoSelectedTraslado.getCodigo());
//        if (insumoSuma != null) {
//            //insumoSuma.setEntradas(saldoTraslado);
//            //insumoSuma.setExistencia(insumoSuma.getEntradas() + insumoSuma.getExistencia());
//            //insumoSuma.setTotal(insumoSuma.getPrecio() * insumoSuma.getExistencia());
//            Insumos responseUpdate = bodegaBeanLocal.updateInsumo(insumoSuma);
//        } else {
//            Insumos insumo = new Insumos();
//            insumo.setCodigo(insumoSelectedTraslado.getCodigo());
//            insumo.setDescripcion(insumoSelectedTraslado.getDescripcion());
//            //insumo.setIdagencia(insumoSelectedTraslado.getIdagenciaenvio());
//            insumo.setSaldoinicial(saldoTraslado);
//            //insumo.setExistencia(saldoTraslado);
//            //insumo.setPrecio(insumoSelectedTraslado.getPrecio());
//            //insumo.setTotal(insumoSelectedTraslado.getPrecio() * saldoTraslado);
//            insumo.setUsuariocreacion(SesionUsuarioMB.getUserName());
//
//            Insumos responseCreate = bodegaBeanLocal.saveInsumo(insumo);
//        }

        if (response != null) {
            JsfUtil.addSuccessMessage("Insumo trasladado exitosamente");
        } else {
            JsfUtil.addErrorMessage("Ocurrio un error verificar datos");
        }

        idAgenciaSelected = null;
        insumoSelectedTraslado = null;
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

    public List<Insumos> getListinsumos() {
        return listinsumos;
    }

    public void setListinsumos(List<Insumos> listinsumos) {
        this.listinsumos = listinsumos;
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
