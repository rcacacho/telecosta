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
@Table(name = "configuracion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Configuracion.findAll", query = "SELECT c FROM Configuracion c"),
    @NamedQuery(name = "Configuracion.findByIdconfiguracion", query = "SELECT c FROM Configuracion c WHERE c.idconfiguracion = :idconfiguracion"),
    @NamedQuery(name = "Configuracion.findByDescripcion", query = "SELECT c FROM Configuracion c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "Configuracion.findByValor", query = "SELECT c FROM Configuracion c WHERE c.valor = :valor"),
    @NamedQuery(name = "Configuracion.findByFechacreacion", query = "SELECT c FROM Configuracion c WHERE c.fechacreacion = :fechacreacion"),
    @NamedQuery(name = "Configuracion.findByUsuariocreacion", query = "SELECT c FROM Configuracion c WHERE c.usuariocreacion = :usuariocreacion"),
    @NamedQuery(name = "Configuracion.findByFechamodificacion", query = "SELECT c FROM Configuracion c WHERE c.fechamodificacion = :fechamodificacion"),
    @NamedQuery(name = "Configuracion.findByUsuariomodificacion", query = "SELECT c FROM Configuracion c WHERE c.usuariomodificacion = :usuariomodificacion"),
    @NamedQuery(name = "Configuracion.findByActivo", query = "SELECT c FROM Configuracion c WHERE c.activo = :activo")})
public class Configuracion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idconfiguracion")
    private Integer idconfiguracion;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "descripcion")
    private String descripcion;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor")
    private Integer valor;
    
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
    private boolean activo;

    public Configuracion() {
    }

    public Configuracion(Integer idconfiguracion) {
        this.idconfiguracion = idconfiguracion;
    }

    public Configuracion(Integer idconfiguracion, String descripcion, int valor, Date fechacreacion, String usuariocreacion, boolean activo) {
        this.idconfiguracion = idconfiguracion;
        this.descripcion = descripcion;
        this.valor = valor;
        this.fechacreacion = fechacreacion;
        this.usuariocreacion = usuariocreacion;
        this.activo = activo;
    }

    public Integer getIdconfiguracion() {
        return idconfiguracion;
    }

    public void setIdconfiguracion(Integer idconfiguracion) {
        this.idconfiguracion = idconfiguracion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
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

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idconfiguracion != null ? idconfiguracion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Configuracion)) {
            return false;
        }
        Configuracion other = (Configuracion) object;
        if ((this.idconfiguracion == null && other.idconfiguracion != null) || (this.idconfiguracion != null && !this.idconfiguracion.equals(other.idconfiguracion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tele.costa.api.entity.Configuracion[ idconfiguracion=" + idconfiguracion + " ]";
    }
    
}
