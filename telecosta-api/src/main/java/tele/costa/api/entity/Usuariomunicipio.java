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
 * @author rcacacho
 */
@Entity
@Table(name = "usuariomunicipio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuariomunicipio.findAll", query = "SELECT u FROM Usuariomunicipio u"),
    @NamedQuery(name = "Usuariomunicipio.findByIdusuariomunicipio", query = "SELECT u FROM Usuariomunicipio u WHERE u.idusuariomunicipio = :idusuariomunicipio"),
    @NamedQuery(name = "Usuariomunicipio.findByFechacreacion", query = "SELECT u FROM Usuariomunicipio u WHERE u.fechacreacion = :fechacreacion"),
    @NamedQuery(name = "Usuariomunicipio.findByUsuariocreacion", query = "SELECT u FROM Usuariomunicipio u WHERE u.usuariocreacion = :usuariocreacion"),
    @NamedQuery(name = "Usuariomunicipio.findByFechaeliminacion", query = "SELECT u FROM Usuariomunicipio u WHERE u.fechaeliminacion = :fechaeliminacion"),
    @NamedQuery(name = "Usuariomunicipio.findByUsuarioeliminacion", query = "SELECT u FROM Usuariomunicipio u WHERE u.usuarioeliminacion = :usuarioeliminacion"),
    @NamedQuery(name = "Usuariomunicipio.findByActivo", query = "SELECT u FROM Usuariomunicipio u WHERE u.activo = :activo")})
public class Usuariomunicipio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idusuariomunicipio")
    private Integer idusuariomunicipio;
    
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
    
    @Column(name = "fechaeliminacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaeliminacion;
    
    @Size(max = 50)
    @Column(name = "usuarioeliminacion")
    private String usuarioeliminacion;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "activo")
    private boolean activo;
    
    @JoinColumn(name = "idmunicipio", referencedColumnName = "idmunicipio")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Municipio idmunicipio;
    
    @JoinColumn(name = "idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario idusuario;

    public Usuariomunicipio() {
    }

    public Usuariomunicipio(Integer idusuariomunicipio) {
        this.idusuariomunicipio = idusuariomunicipio;
    }

    public Usuariomunicipio(Integer idusuariomunicipio, Date fechacreacion, String usuariocreacion, boolean activo) {
        this.idusuariomunicipio = idusuariomunicipio;
        this.fechacreacion = fechacreacion;
        this.usuariocreacion = usuariocreacion;
        this.activo = activo;
    }

    public Integer getIdusuariomunicipio() {
        return idusuariomunicipio;
    }

    public void setIdusuariomunicipio(Integer idusuariomunicipio) {
        this.idusuariomunicipio = idusuariomunicipio;
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

    public Date getFechaeliminacion() {
        return fechaeliminacion;
    }

    public void setFechaeliminacion(Date fechaeliminacion) {
        this.fechaeliminacion = fechaeliminacion;
    }

    public String getUsuarioeliminacion() {
        return usuarioeliminacion;
    }

    public void setUsuarioeliminacion(String usuarioeliminacion) {
        this.usuarioeliminacion = usuarioeliminacion;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Municipio getIdmunicipio() {
        return idmunicipio;
    }

    public void setIdmunicipio(Municipio idmunicipio) {
        this.idmunicipio = idmunicipio;
    }

    public Usuario getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Usuario idusuario) {
        this.idusuario = idusuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idusuariomunicipio != null ? idusuariomunicipio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuariomunicipio)) {
            return false;
        }
        Usuariomunicipio other = (Usuariomunicipio) object;
        if ((this.idusuariomunicipio == null && other.idusuariomunicipio != null) || (this.idusuariomunicipio != null && !this.idusuariomunicipio.equals(other.idusuariomunicipio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tele.costa.api.entity.Usuariomunicipio[ idusuariomunicipio=" + idusuariomunicipio + " ]";
    }
    
}
