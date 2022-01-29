package tele.costa.api.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author elfo_
 */
@Entity
@Table(name = "cobro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cobro.findAll", query = "SELECT c FROM Cobro c"),
    @NamedQuery(name = "Cobro.findByIdcobro", query = "SELECT c FROM Cobro c WHERE c.idcobro = :idcobro"),
    @NamedQuery(name = "Cobro.findByMes", query = "SELECT c FROM Cobro c WHERE c.mes = :mes"),
    @NamedQuery(name = "Cobro.findByAnio", query = "SELECT c FROM Cobro c WHERE c.anio = :anio"),
    @NamedQuery(name = "Cobro.findByFechacobro", query = "SELECT c FROM Cobro c WHERE c.fechacobro = :fechacobro"),
    @NamedQuery(name = "Cobro.findByObservacion", query = "SELECT c FROM Cobro c WHERE c.observacion = :observacion"),
    @NamedQuery(name = "Cobro.findByFechacreacion", query = "SELECT c FROM Cobro c WHERE c.fechacreacion = :fechacreacion"),
    @NamedQuery(name = "Cobro.findByUsuariocreacion", query = "SELECT c FROM Cobro c WHERE c.usuariocreacion = :usuariocreacion"),
    @NamedQuery(name = "Cobro.findByFechamodificacion", query = "SELECT c FROM Cobro c WHERE c.fechamodificacion = :fechamodificacion"),
    @NamedQuery(name = "Cobro.findByUsuariomodificacion", query = "SELECT c FROM Cobro c WHERE c.usuariomodificacion = :usuariomodificacion"),
    @NamedQuery(name = "Cobro.findByActivo", query = "SELECT c FROM Cobro c WHERE c.activo = :activo")})
public class Cobro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcobro")
    private Integer idcobro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "mes")
    private String mes;
    @Basic(optional = false)
    @NotNull
    @Column(name = "anio")
    private int anio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechacobro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacobro;
    @Size(max = 500)
    @Column(name = "observacion")
    private String observacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechacreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacreacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "usuariocreacion")
    private String usuariocreacion;
    @Column(name = "fechamodificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechamodificacion;
    @Size(max = 50)
    @Column(name = "usuariomodificacion")
    private String usuariomodificacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "activo")
    private int activo;
    @JoinColumn(name = "idcliente", referencedColumnName = "idcliente")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Cliente idcliente;
    @JoinColumn(name = "idconfiguracionpago", referencedColumnName = "idconfiguracionpago")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Configuracionpago idconfiguracionpago;

    public Cobro() {
    }

    public Cobro(Integer idcobro) {
        this.idcobro = idcobro;
    }

    public Cobro(Integer idcobro, String mes, int anio, Date fechacobro, Date fechacreacion, String usuariocreacion, int activo) {
        this.idcobro = idcobro;
        this.mes = mes;
        this.anio = anio;
        this.fechacobro = fechacobro;
        this.fechacreacion = fechacreacion;
        this.usuariocreacion = usuariocreacion;
        this.activo = activo;
    }

    public Integer getIdcobro() {
        return idcobro;
    }

    public void setIdcobro(Integer idcobro) {
        this.idcobro = idcobro;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public Date getFechacobro() {
        return fechacobro;
    }

    public void setFechacobro(Date fechacobro) {
        this.fechacobro = fechacobro;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(Date fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public String getUsuariocreacion() {
        return usuariocreacion;
    }

    public void setUsuariocreacion(String usuariocreacion) {
        this.usuariocreacion = usuariocreacion;
    }

    public Date getFechamodificacion() {
        return fechamodificacion;
    }

    public void setFechamodificacion(Date fechamodificacion) {
        this.fechamodificacion = fechamodificacion;
    }

    public String getUsuariomodificacion() {
        return usuariomodificacion;
    }

    public void setUsuariomodificacion(String usuariomodificacion) {
        this.usuariomodificacion = usuariomodificacion;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public Cliente getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Cliente idcliente) {
        this.idcliente = idcliente;
    }

    public Configuracionpago getIdconfiguracionpago() {
        return idconfiguracionpago;
    }

    public void setIdconfiguracionpago(Configuracionpago idconfiguracionpago) {
        this.idconfiguracionpago = idconfiguracionpago;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcobro != null ? idcobro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cobro)) {
            return false;
        }
        Cobro other = (Cobro) object;
        if ((this.idcobro == null && other.idcobro != null) || (this.idcobro != null && !this.idcobro.equals(other.idcobro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tele.costa.api.entity.Cobro[ idcobro=" + idcobro + " ]";
    }
    
}
