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
 * @author elfo_
 */
@Entity
@Table(name = "tipoatencion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipoatencion.findAll", query = "SELECT t FROM Tipoatencion t"),
    @NamedQuery(name = "Tipoatencion.findByIdtipoatencion", query = "SELECT t FROM Tipoatencion t WHERE t.idtipoatencion = :idtipoatencion"),
    @NamedQuery(name = "Tipoatencion.findByTipo", query = "SELECT t FROM Tipoatencion t WHERE t.tipo = :tipo"),
    @NamedQuery(name = "Tipoatencion.findByDescripcion", query = "SELECT t FROM Tipoatencion t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "Tipoatencion.findByFechacreacion", query = "SELECT t FROM Tipoatencion t WHERE t.fechacreacion = :fechacreacion"),
    @NamedQuery(name = "Tipoatencion.findByUsuariocreacion", query = "SELECT t FROM Tipoatencion t WHERE t.usuariocreacion = :usuariocreacion"),
    @NamedQuery(name = "Tipoatencion.findByActivo", query = "SELECT t FROM Tipoatencion t WHERE t.activo = :activo")})
public class Tipoatencion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtipoatencion")
    private Integer idtipoatencion;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "tipo")
    private String tipo;
    
    @Size(max = 200)
    @Column(name = "descripcion")
    private String descripcion;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechacreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacreacion;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "usuariocreacion")
    private String usuariocreacion;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "activo")
    private boolean activo;

    public Tipoatencion() {
    }

    public Tipoatencion(Integer idtipoatencion) {
        this.idtipoatencion = idtipoatencion;
    }

    public Tipoatencion(Integer idtipoatencion, String tipo, Date fechacreacion, String usuariocreacion, boolean activo) {
        this.idtipoatencion = idtipoatencion;
        this.tipo = tipo;
        this.fechacreacion = fechacreacion;
        this.usuariocreacion = usuariocreacion;
        this.activo = activo;
    }

    public Integer getIdtipoatencion() {
        return idtipoatencion;
    }

    public void setIdtipoatencion(Integer idtipoatencion) {
        this.idtipoatencion = idtipoatencion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipoatencion != null ? idtipoatencion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipoatencion)) {
            return false;
        }
        Tipoatencion other = (Tipoatencion) object;
        if ((this.idtipoatencion == null && other.idtipoatencion != null) || (this.idtipoatencion != null && !this.idtipoatencion.equals(other.idtipoatencion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tele.costa.api.entity.Tipoatencion[ idtipoatencion=" + idtipoatencion + " ]";
    }
    
}
