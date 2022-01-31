package tele.costa.api.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author elfo_
 */
@Entity
@Table(name = "ruta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ruta.findAll", query = "SELECT r FROM Ruta r"),
    @NamedQuery(name = "Ruta.findByIdruta", query = "SELECT r FROM Ruta r WHERE r.idruta = :idruta"),
    @NamedQuery(name = "Ruta.findByRuta", query = "SELECT r FROM Ruta r WHERE r.ruta = :ruta"),
    @NamedQuery(name = "Ruta.findByDescripcion", query = "SELECT r FROM Ruta r WHERE r.descripcion = :descripcion"),
    @NamedQuery(name = "Ruta.findByFechacreacion", query = "SELECT r FROM Ruta r WHERE r.fechacreacion = :fechacreacion"),
    @NamedQuery(name = "Ruta.findByUsuariocreacion", query = "SELECT r FROM Ruta r WHERE r.usuariocreacion = :usuariocreacion"),
    @NamedQuery(name = "Ruta.findByFechamodificacion", query = "SELECT r FROM Ruta r WHERE r.fechamodificacion = :fechamodificacion"),
    @NamedQuery(name = "Ruta.findByUsuariomodificacion", query = "SELECT r FROM Ruta r WHERE r.usuariomodificacion = :usuariomodificacion"),
    @NamedQuery(name = "Ruta.findByActivo", query = "SELECT r FROM Ruta r WHERE r.activo = :activo")})
public class Ruta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idruta")
    private Integer idruta;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2000)
    @Column(name = "ruta")
    private String ruta;
    
    @Size(max = 1500)
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
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idruta", fetch = FetchType.LAZY)
    private List<Atencion> atencionList;

    public Ruta() {
    }

    public Ruta(Integer idruta) {
        this.idruta = idruta;
    }

    public Ruta(Integer idruta, String ruta, Date fechacreacion, String usuariocreacion, boolean activo) {
        this.idruta = idruta;
        this.ruta = ruta;
        this.fechacreacion = fechacreacion;
        this.usuariocreacion = usuariocreacion;
        this.activo = activo;
    }

    public Integer getIdruta() {
        return idruta;
    }

    public void setIdruta(Integer idruta) {
        this.idruta = idruta;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
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

    @XmlTransient
    public List<Atencion> getAtencionList() {
        return atencionList;
    }

    public void setAtencionList(List<Atencion> atencionList) {
        this.atencionList = atencionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idruta != null ? idruta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ruta)) {
            return false;
        }
        Ruta other = (Ruta) object;
        if ((this.idruta == null && other.idruta != null) || (this.idruta != null && !this.idruta.equals(other.idruta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tele.costa.api.entity.Ruta[ idruta=" + idruta + " ]";
    }

}
