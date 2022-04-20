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
@Table(name = "estadocliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estadocliente.findAll", query = "SELECT e FROM Estadocliente e"),
    @NamedQuery(name = "Estadocliente.findByIdestadocliente", query = "SELECT e FROM Estadocliente e WHERE e.idestadocliente = :idestadocliente"),
    @NamedQuery(name = "Estadocliente.findByEstado", query = "SELECT e FROM Estadocliente e WHERE e.estado = :estado"),
    @NamedQuery(name = "Estadocliente.findByDescripcion", query = "SELECT e FROM Estadocliente e WHERE e.descripcion = :descripcion"),
    @NamedQuery(name = "Estadocliente.findByFechacreacion", query = "SELECT e FROM Estadocliente e WHERE e.fechacreacion = :fechacreacion"),
    @NamedQuery(name = "Estadocliente.findByUsuariocreacion", query = "SELECT e FROM Estadocliente e WHERE e.usuariocreacion = :usuariocreacion"),
    @NamedQuery(name = "Estadocliente.findByActivo", query = "SELECT e FROM Estadocliente e WHERE e.activo = :activo")})
public class Estadocliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idestadocliente")
    private Integer idestadocliente;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "estado")
    private String estado;
    
    @Size(max = 250)
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
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "activo")
    private boolean activo;

    public Estadocliente() {
    }

    public Estadocliente(Integer idestadocliente) {
        this.idestadocliente = idestadocliente;
    }

    public Estadocliente(Integer idestadocliente, String estado, Date fechacreacion, String usuariocreacion, boolean activo) {
        this.idestadocliente = idestadocliente;
        this.estado = estado;
        this.fechacreacion = fechacreacion;
        this.usuariocreacion = usuariocreacion;
        this.activo = activo;
    }

    public Integer getIdestadocliente() {
        return idestadocliente;
    }

    public void setIdestadocliente(Integer idestadocliente) {
        this.idestadocliente = idestadocliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
        hash += (idestadocliente != null ? idestadocliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estadocliente)) {
            return false;
        }
        Estadocliente other = (Estadocliente) object;
        if ((this.idestadocliente == null && other.idestadocliente != null) || (this.idestadocliente != null && !this.idestadocliente.equals(other.idestadocliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tele.costa.api.entity.Estadocliente[ idestadocliente=" + idestadocliente + " ]";
    }
    
}
