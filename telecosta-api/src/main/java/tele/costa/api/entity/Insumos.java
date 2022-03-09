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
@Table(name = "insumos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Insumos.findAll", query = "SELECT i FROM Insumos i"),
    @NamedQuery(name = "Insumos.findByIdinsumo", query = "SELECT i FROM Insumos i WHERE i.idinsumo = :idinsumo"),
    @NamedQuery(name = "Insumos.findByCodigo", query = "SELECT i FROM Insumos i WHERE i.codigo = :codigo"),
    @NamedQuery(name = "Insumos.findByDescripcion", query = "SELECT i FROM Insumos i WHERE i.descripcion = :descripcion"),
    @NamedQuery(name = "Insumos.findBySaldoinicial", query = "SELECT i FROM Insumos i WHERE i.saldoinicial = :saldoinicial"),
    @NamedQuery(name = "Insumos.findByUsuariocreacion", query = "SELECT i FROM Insumos i WHERE i.usuariocreacion = :usuariocreacion"),
    @NamedQuery(name = "Insumos.findByFechacreacion", query = "SELECT i FROM Insumos i WHERE i.fechacreacion = :fechacreacion"),
    @NamedQuery(name = "Insumos.findByUsuariomodificacion", query = "SELECT i FROM Insumos i WHERE i.usuariomodificacion = :usuariomodificacion"),
    @NamedQuery(name = "Insumos.findByFechamodificacion", query = "SELECT i FROM Insumos i WHERE i.fechamodificacion = :fechamodificacion"),
    @NamedQuery(name = "Insumos.findByActivo", query = "SELECT i FROM Insumos i WHERE i.activo = :activo")})
public class Insumos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idinsumo")
    private Integer idinsumo;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "codigo")
    private String codigo;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "saldoinicial")
    private Integer saldoinicial;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "usuariocreacion")
    private String usuariocreacion;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechacreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacreacion;
    
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
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idinsumo", fetch = FetchType.LAZY)
    private List<Inventario> inventarioList;

    public Insumos() {
    }

    public Insumos(Integer idinsumo) {
        this.idinsumo = idinsumo;
    }

    public Insumos(Integer idinsumo, String codigo, String descripcion, String usuariocreacion, Date fechacreacion, boolean activo) {
        this.idinsumo = idinsumo;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.usuariocreacion = usuariocreacion;
        this.fechacreacion = fechacreacion;
        this.activo = activo;
    }

    public Integer getIdinsumo() {
        return idinsumo;
    }

    public void setIdinsumo(Integer idinsumo) {
        this.idinsumo = idinsumo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getSaldoinicial() {
        return saldoinicial;
    }

    public void setSaldoinicial(Integer saldoinicial) {
        this.saldoinicial = saldoinicial;
    }

    public String getUsuariocreacion() {
        return usuariocreacion;
    }

    public void setUsuariocreacion(String usuariocreacion) {
        this.usuariocreacion = usuariocreacion;
    }

    public Date getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(Date fechacreacion) {
        this.fechacreacion = fechacreacion;
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

    @XmlTransient
    public List<Inventario> getInventarioList() {
        return inventarioList;
    }

    public void setInventarioList(List<Inventario> inventarioList) {
        this.inventarioList = inventarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idinsumo != null ? idinsumo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Insumos)) {
            return false;
        }
        Insumos other = (Insumos) object;
        if ((this.idinsumo == null && other.idinsumo != null) || (this.idinsumo != null && !this.idinsumo.equals(other.idinsumo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tele.costa.api.entity.Insumos[ idinsumo=" + idinsumo + " ]";
    }
    
}
