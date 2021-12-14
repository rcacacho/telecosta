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
 * @author rcacacho
 */
@Entity
@Table(name = "tipocompra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipocompra.findAll", query = "SELECT t FROM Tipocompra t"),
    @NamedQuery(name = "Tipocompra.findByIdtipocompra", query = "SELECT t FROM Tipocompra t WHERE t.idtipocompra = :idtipocompra"),
    @NamedQuery(name = "Tipocompra.findByTipo", query = "SELECT t FROM Tipocompra t WHERE t.tipo = :tipo"),
    @NamedQuery(name = "Tipocompra.findByDescripcion", query = "SELECT t FROM Tipocompra t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "Tipocompra.findByFechacreacion", query = "SELECT t FROM Tipocompra t WHERE t.fechacreacion = :fechacreacion"),
    @NamedQuery(name = "Tipocompra.findByUsuariocreacion", query = "SELECT t FROM Tipocompra t WHERE t.usuariocreacion = :usuariocreacion"),
    @NamedQuery(name = "Tipocompra.findByFechamodificacion", query = "SELECT t FROM Tipocompra t WHERE t.fechamodificacion = :fechamodificacion"),
    @NamedQuery(name = "Tipocompra.findByUsuariomodificacion", query = "SELECT t FROM Tipocompra t WHERE t.usuariomodificacion = :usuariomodificacion"),
    @NamedQuery(name = "Tipocompra.findByActivo", query = "SELECT t FROM Tipocompra t WHERE t.activo = :activo")})
public class Tipocompra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtipocompra")
    private Integer idtipocompra;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "tipo")
    private String tipo;
    
    @Size(max = 500)
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
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idtipocompra", fetch = FetchType.LAZY)
    private List<Compra> compraList;

    public Tipocompra() {
    }

    public Tipocompra(Integer idtipocompra) {
        this.idtipocompra = idtipocompra;
    }

    public Tipocompra(Integer idtipocompra, String tipo, Date fechacreacion, String usuariocreacion, boolean activo) {
        this.idtipocompra = idtipocompra;
        this.tipo = tipo;
        this.fechacreacion = fechacreacion;
        this.usuariocreacion = usuariocreacion;
        this.activo = activo;
    }

    public Integer getIdtipocompra() {
        return idtipocompra;
    }

    public void setIdtipocompra(Integer idtipocompra) {
        this.idtipocompra = idtipocompra;
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
    public List<Compra> getCompraList() {
        return compraList;
    }

    public void setCompraList(List<Compra> compraList) {
        this.compraList = compraList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipocompra != null ? idtipocompra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipocompra)) {
            return false;
        }
        Tipocompra other = (Tipocompra) object;
        if ((this.idtipocompra == null && other.idtipocompra != null) || (this.idtipocompra != null && !this.idtipocompra.equals(other.idtipocompra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tele.costa.api.entity.Tipocompra[ idtipocompra=" + idtipocompra + " ]";
    }
    
}
