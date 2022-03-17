package tele.costa.web.inventario;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import tele.costa.api.ejb.CatalogoBeanLocal;
import tele.costa.api.ejb.InsumoBeanLocal;
import tele.costa.api.entity.Agencia;

/**
 *
 * @author elfo_
 */
@ManagedBean(name = "reporteInventarioMB")
@ViewScoped

public class ReporteInventarioMB implements Serializable {

    private static final Logger log = Logger.getLogger(ReporteInventarioMB.class);

    @EJB
    private InsumoBeanLocal bodegaBeanLocal;
    @EJB
    private CatalogoBeanLocal catalogoBean;

    private String tipoCarga;
    private String mes;
    private Integer idAgencia;
    private Integer anio;
    private Boolean mostrarIngreso;
    private Boolean mostrarIngresoFechaInicio;
    private Boolean mostrarIngresoFechaFin;
    private Boolean mostrarIngresoInsumo;
    private Boolean mostrarSalida;
    private Boolean mostrarTraslado;
    private List<Agencia> listAgencia;

    public ReporteInventarioMB() {
        mostrarIngreso = Boolean.FALSE;
        mostrarSalida = Boolean.FALSE;
        mostrarTraslado = Boolean.FALSE;
        mostrarIngresoFechaInicio = Boolean.FALSE;
        mostrarIngresoFechaFin = Boolean.FALSE;
        mostrarIngresoInsumo = Boolean.FALSE;
    }

    @PostConstruct
    void cargarDatos() {
        listAgencia = catalogoBean.listAgencias();
    }

    public void cargarIngreso() {
        if (tipoCarga.equals("Ingreso")) {
            if (idAgencia != null) {
                mostrarIngresoFechaInicio = Boolean.TRUE;
            }

            if (mes != null) {
                mostrarIngresoFechaFin = Boolean.TRUE;
            }

            if (anio != null) {
                mostrarIngresoInsumo = Boolean.TRUE;
            }

            mostrarIngreso = Boolean.TRUE;
        } else {
            mostrarIngreso = Boolean.FALSE;
        }

        if (tipoCarga.equals("Salida")) {
            mostrarSalida = Boolean.TRUE;
        } else {
            mostrarSalida = Boolean.FALSE;
        }

        if (tipoCarga.equals("Envio")) {
            mostrarTraslado = Boolean.TRUE;
        } else {
            mostrarTraslado = Boolean.FALSE;
        }
    }

    public void cargarAgencia() {
        if (tipoCarga.equals("Ingreso")) {
            mostrarIngreso = Boolean.TRUE;
        } else {
            mostrarIngreso = Boolean.FALSE;
        }

        if (tipoCarga.equals("Salida")) {
            mostrarSalida = Boolean.TRUE;
        } else {
            mostrarSalida = Boolean.FALSE;
        }

        if (tipoCarga.equals("Envio")) {
            mostrarTraslado = Boolean.TRUE;
        } else {
            mostrarTraslado = Boolean.FALSE;
        }
    }

    /*Metodos getters y setters*/
    public String getTipoCarga() {
        return tipoCarga;
    }

    public void setTipoCarga(String tipoCarga) {
        this.tipoCarga = tipoCarga;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Integer getIdAgencia() {
        return idAgencia;
    }

    public void setIdAgencia(Integer idAgencia) {
        this.idAgencia = idAgencia;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Boolean getMostrarIngreso() {
        return mostrarIngreso;
    }

    public void setMostrarIngreso(Boolean mostrarIngreso) {
        this.mostrarIngreso = mostrarIngreso;
    }

    public Boolean getMostrarSalida() {
        return mostrarSalida;
    }

    public void setMostrarSalida(Boolean mostrarSalida) {
        this.mostrarSalida = mostrarSalida;
    }

    public Boolean getMostrarTraslado() {
        return mostrarTraslado;
    }

    public void setMostrarTraslado(Boolean mostrarTraslado) {
        this.mostrarTraslado = mostrarTraslado;
    }

    public List<Agencia> getListAgencia() {
        return listAgencia;
    }

    public void setListAgencia(List<Agencia> listAgencia) {
        this.listAgencia = listAgencia;
    }

    public Boolean getMostrarIngresoFechaInicio() {
        return mostrarIngresoFechaInicio;
    }

    public void setMostrarIngresoFechaInicio(Boolean mostrarIngresoFechaInicio) {
        this.mostrarIngresoFechaInicio = mostrarIngresoFechaInicio;
    }

    public Boolean getMostrarIngresoFechaFin() {
        return mostrarIngresoFechaFin;
    }

    public void setMostrarIngresoFechaFin(Boolean mostrarIngresoFechaFin) {
        this.mostrarIngresoFechaFin = mostrarIngresoFechaFin;
    }

    public Boolean getMostrarIngresoInsumo() {
        return mostrarIngresoInsumo;
    }

    public void setMostrarIngresoInsumo(Boolean mostrarIngresoInsumo) {
        this.mostrarIngresoInsumo = mostrarIngresoInsumo;
    }

}
