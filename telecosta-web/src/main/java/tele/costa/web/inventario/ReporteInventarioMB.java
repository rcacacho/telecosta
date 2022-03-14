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
    private Boolean mostrarSalida;
    private Boolean mostrarTraslado;
    private List<Agencia> listAgencia;

    @PostConstruct
    void cargarDatos() {
        listAgencia = catalogoBean.listAgencias();
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
    
    

}
