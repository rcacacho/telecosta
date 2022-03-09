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
@Table(name = "agencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Agencia.findAll", query = "SELECT a FROM Agencia a"),
    @NamedQuery(name = "Agencia.findByIdagencia", query = "SELECT a FROM Agencia a WHERE a.idagencia = :idagencia"),
    @NamedQuery(name = "Agencia.findByAgencia", query = "SELECT a FROM Agencia a WHERE a.agencia = :agencia"),
    @NamedQuery(name = "Agencia.findByDescripcion", query = "SELECT a FROM Agencia a WHERE a.descripcion = :descripcion"),
    @NamedQuery(name = "Agencia.findByFechacreacion", query = "SELECT a FROM Agencia a WHERE a.fechacreacion = :fechacreacion"),
    @NamedQuery(name = "Agencia.findByUsuariocreacion", query = "SELECT a FROM Agencia a WHERE a.usuariocreacion = :usuariocreacion"),
    @NamedQuery(name = "Agencia.findByActivo", query = "SELECT a FROM Agencia a WHERE a.activo = :activo")})
public class Agencia implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idagencia", fetch = FetchType.LAZY)
    private List<Inventario> inventarioList;
    @OneToMany(mappedBy = "idagenciaenvio", fetch = FetchType.LAZY)
    private List<Inventario> inventarioList1;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idagencia")
    private Integer idagencia;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "agencia")
    private String agencia;

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

    @Basic(optional = false)
    @NotNull
    @Column(name = "activo")
    private boolean activo;

    public Agencia() {
    }

    public Agencia(Integer idagencia) {
        this.idagencia = idagencia;
    }

    public Agencia(Integer idagencia, String agencia, Date fechacreacion, String usuariocreacion, boolean activo) {
        this.idagencia = idagencia;
        this.agencia = agencia;
        this.fechacreacion = fechacreacion;
        this.usuariocreacion = usuariocreacion;
        this.activo = activo;
    }

    public Integer getIdagencia() {
        return idagencia;
    }

    public void setIdagencia(Integer idagencia) {
        this.idagencia = idagencia;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
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
        hash += (idagencia != null ? idagencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Agencia)) {
            return false;
        }
        Agencia other = (Agencia) object;
        if ((this.idagencia == null && other.idagencia != null) || (this.idagencia != null && !this.idagencia.equals(other.idagencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tele.costa.api.entity.Agencia[ idagencia=" + idagencia + " ]";
    }

    @XmlTransient
    public List<Inventario> getInventarioList() {
        return inventarioList;
    }

    public void setInventarioList(List<Inventario> inventarioList) {
        this.inventarioList = inventarioList;
    }

    @XmlTransient
    public List<Inventario> getInventarioList1() {
        return inventarioList1;
    }

    public void setInventarioList1(List<Inventario> inventarioList1) {
        this.inventarioList1 = inventarioList1;
    }

}
