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
import tele.costa.api.entity.Tipocarga;
import tele.costa.api.enums.TipoCarga;
import telecosta.web.utils.JsfUtil;

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
    private BodegaBeanLocal bodegaBeanLocal;

    private Integer idAgencia;
    private Agencia idAgenciaSelected;
    private List<Agencia> listAgencia;
    private Date fechaInicio;
    private Date fechaFin;
    private List<Insumos> listinsumos;
    private Insumos insumoSelectedSalida;
    private List<Ruta> listRuta;
    private Integer saldoSalida;

    @PostConstruct
    void cargarDatos() {
        listAgencia = catalogoBean.listAgencias();
        listRuta = catalogoBean.listRuta();
    }

    public void dialogSalida(Insumos insumo) {
        insumoSelectedSalida = insumo;
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

        Tipocarga findTipo = catalogoBean.findTipoCarga(TipoCarga.SALIDA.getId());
        insumoSelectedSalida.setIdtipocarga(findTipo);
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
        insumoSelectedSalida = null;
        RequestContext.getCurrentInstance().execute("PF('dlgSalida').hide()");
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

    public void limpiarCampos() {
        idAgencia = null;
        fechaInicio = null;
        fechaFin = null;
        cargarDatos();
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

    public Integer getSaldoSalida() {
        return saldoSalida;
    }

    public void setSaldoSalida(Integer saldoSalida) {
        this.saldoSalida = saldoSalida;
    }

}
