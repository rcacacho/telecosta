package tele.costa.api.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * @author rcacacho
 */
@Entity
@Table(name = "tipocarga")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipocarga.findAll", query = "SELECT t FROM Tipocarga t"),
    @NamedQuery(name = "Tipocarga.findByIdtipocarga", query = "SELECT t FROM Tipocarga t WHERE t.idtipocarga = :idtipocarga"),
    @NamedQuery(name = "Tipocarga.findByCarga", query = "SELECT t FROM Tipocarga t WHERE t.carga = :carga"),
    @NamedQuery(name = "Tipocarga.findByDescripcion", query = "SELECT t FROM Tipocarga t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "Tipocarga.findByFechacreacion", query = "SELECT t FROM Tipocarga t WHERE t.fechacreacion = :fechacreacion"),
    @NamedQuery(name = "Tipocarga.findByUsuariocreacion", query = "SELECT t FROM Tipocarga t WHERE t.usuariocreacion = :usuariocreacion"),
    @NamedQuery(name = "Tipocarga.findByUsuariomodificacion", query = "SELECT t FROM Tipocarga t WHERE t.usuariomodificacion = :usuariomodificacion"),
    @NamedQuery(name = "Tipocarga.findByFechamodificacion", query = "SELECT t FROM Tipocarga t WHERE t.fechamodificacion = :fechamodificacion"),
    @NamedQuery(name = "Tipocarga.findByActivo", query = "SELECT t FROM Tipocarga t WHERE t.activo = :activo")})
public class Tipocarga implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtipocarga")
    private Integer idtipocarga;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "carga")
    private String carga;
    
    @Size(max = 300)
    @Column(name = "descripcion")
    private String descripcion;
    
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
    
    @Size(max = 50)
    @Column(name = "usuariomodificacion")
    private String usuariomodificacion;
    
    @Column(name = "fechamodificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechamodificacion;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "activo")
    private boolean activo;

    public Tipocarga() {
    }

    public Tipocarga(Integer idtipocarga) {
        this.idtipocarga = idtipocarga;
    }

    public Tipocarga(Integer idtipocarga, String carga, Date fechacreacion, String usuariocreacion, boolean activo) {
        this.idtipocarga = idtipocarga;
        this.carga = carga;
        this.fechacreacion = fechacreacion;
        this.usuariocreacion = usuariocreacion;
        this.activo = activo;
    }

    public Integer getIdtipocarga() {
        return idtipocarga;
    }

    public void setIdtipocarga(Integer idtipocarga) {
        this.idtipocarga = idtipocarga;
    }

    public String getCarga() {
        return carga;
    }

    public void setCarga(String carga) {
        this.carga = carga;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public String getUsuariomodificacion() {
        return usuariomodificacion;
    }

    public void setUsuariomodificacion(String usuariomodificacion) {
        this.usuariomodificacion = usuariomodificacion;
    }

    public Date getFechamodificacion() {
        return fechamodificacion;
    }

    public void setFechamodificacion(Date fechamodificacion) {
        this.fechamodificacion = fechamodificacion;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipocarga != null ? idtipocarga.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipocarga)) {
            return false;
        }
        Tipocarga other = (Tipocarga) object;
        if ((this.idtipocarga == null && other.idtipocarga != null) || (this.idtipocarga != null && !this.idtipocarga.equals(other.idtipocarga))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tele.costa.api.entity.Tipocarga[ idtipocarga=" + idtipocarga + " ]";
    }
    
}
