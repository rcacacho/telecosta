package tele.costa.web.inventario;

import java.io.Serializable;
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
    private String numeroDocumentoIngreso;
    private Date fechaInicioIngreso;
    private Date fechaFinIngreso;
    private String insumoIngreso;
    private String insumoSalida;
    private String numeroDocumentoSalida;
    private Date fechaInicioSalida;
    private Date fechaFinSalida;
    private Date fechaInicioEnvio;
    private Date fechaFinEnvio;

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
    
    public void imprimirReporteIngresoPdf(){
        
    }
    
    public void imprimirReporteIngresoExcel(){
        
    }
    
    public void imprimirReporteSalidaPdf(){
        
    }
    
    public void imprimirReporteSalidaExcel(){
        
    }
    
    public void imprimirReporteEnvioPdf(){
        
    }
    
    public void imprimirReporteEnvioExcel(){
        
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

    public String getNumeroDocumentoIngreso() {
        return numeroDocumentoIngreso;
    }

    public void setNumeroDocumentoIngreso(String numeroDocumentoIngreso) {
        this.numeroDocumentoIngreso = numeroDocumentoIngreso;
    }

    public Date getFechaInicioIngreso() {
        return fechaInicioIngreso;
    }

    public void setFechaInicioIngreso(Date fechaInicioIngreso) {
        this.fechaInicioIngreso = fechaInicioIngreso;
    }

    public Date getFechaFinIngreso() {
        return fechaFinIngreso;
    }

    public void setFechaFinIngreso(Date fechaFinIngreso) {
        this.fechaFinIngreso = fechaFinIngreso;
    }

    public String getInsumoIngreso() {
        return insumoIngreso;
    }

    public void setInsumoIngreso(String insumoIngreso) {
        this.insumoIngreso = insumoIngreso;
    }

    public String getInsumoSalida() {
        return insumoSalida;
    }

    public void setInsumoSalida(String insumoSalida) {
        this.insumoSalida = insumoSalida;
    }

    public String getNumeroDocumentoSalida() {
        return numeroDocumentoSalida;
    }

    public void setNumeroDocumentoSalida(String numeroDocumentoSalida) {
        this.numeroDocumentoSalida = numeroDocumentoSalida;
    }

    public Date getFechaInicioSalida() {
        return fechaInicioSalida;
    }

    public void setFechaInicioSalida(Date fechaInicioSalida) {
        this.fechaInicioSalida = fechaInicioSalida;
    }

    public Date getFechaFinSalida() {
        return fechaFinSalida;
    }

    public void setFechaFinSalida(Date fechaFinSalida) {
        this.fechaFinSalida = fechaFinSalida;
    }

    public Date getFechaInicioEnvio() {
        return fechaInicioEnvio;
    }

    public void setFechaInicioEnvio(Date fechaInicioEnvio) {
        this.fechaInicioEnvio = fechaInicioEnvio;
    }

    public Date getFechaFinEnvio() {
        return fechaFinEnvio;
    }

    public void setFechaFinEnvio(Date fechaFinEnvio) {
        this.fechaFinEnvio = fechaFinEnvio;
    }

}
