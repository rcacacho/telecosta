package tele.costa.api.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
@Table(name = "sectorpago")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sectorpago.findAll", query = "SELECT s FROM Sectorpago s"),
    @NamedQuery(name = "Sectorpago.findByIdsectorpago", query = "SELECT s FROM Sectorpago s WHERE s.idsectorpago = :idsectorpago"),
    @NamedQuery(name = "Sectorpago.findByNombre", query = "SELECT s FROM Sectorpago s WHERE s.nombre = :nombre"),
    @NamedQuery(name = "Sectorpago.findByDescripcion", query = "SELECT s FROM Sectorpago s WHERE s.descripcion = :descripcion"),
    @NamedQuery(name = "Sectorpago.findByFechacreacion", query = "SELECT s FROM Sectorpago s WHERE s.fechacreacion = :fechacreacion"),
    @NamedQuery(name = "Sectorpago.findByUsuariocreacion", query = "SELECT s FROM Sectorpago s WHERE s.usuariocreacion = :usuariocreacion"),
    @NamedQuery(name = "Sectorpago.findByFechamodificacion", query = "SELECT s FROM Sectorpago s WHERE s.fechamodificacion = :fechamodificacion"),
    @NamedQuery(name = "Sectorpago.findByUsuariomodificacion", query = "SELECT s FROM Sectorpago s WHERE s.usuariomodificacion = :usuariomodificacion"),
    @NamedQuery(name = "Sectorpago.findByActivo", query = "SELECT s FROM Sectorpago s WHERE s.activo = :activo")})
public class Sectorpago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idsectorpago")
    private Integer idsectorpago;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 600)
    @Column(name = "nombre")
    private String nombre;

    @Size(max = 600)
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

    @JoinColumn(name = "idmunicipio", referencedColumnName = "idmunicipio")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Municipio idmunicipio;

    @OneToMany(mappedBy = "idsectorpago", fetch = FetchType.LAZY)
    private List<Inventario> inventarioList;

    public Sectorpago() {
    }

    public Sectorpago(Integer idsectorpago) {
        this.idsectorpago = idsectorpago;
    }

    public Sectorpago(Integer idsectorpago, String nombre, Date fechacreacion, String usuariocreacion, boolean activo) {
        this.idsectorpago = idsectorpago;
        this.nombre = nombre;
        this.fechacreacion = fechacreacion;
        this.usuariocreacion = usuariocreacion;
        this.activo = activo;
    }

    public Integer getIdsectorpago() {
        return idsectorpago;
    }

    public void setIdsectorpago(Integer idsectorpago) {
        this.idsectorpago = idsectorpago;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Municipio getIdmunicipio() {
        return idmunicipio;
    }

    public void setIdmunicipio(Municipio idmunicipio) {
        this.idmunicipio = idmunicipio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsectorpago != null ? idsectorpago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sectorpago)) {
            return false;
        }
        Sectorpago other = (Sectorpago) object;
        if ((this.idsectorpago == null && other.idsectorpago != null) || (this.idsectorpago != null && !this.idsectorpago.equals(other.idsectorpago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tele.costa.api.entity.Sectorpago[ idsectorpago=" + idsectorpago + " ]";
    }

    @XmlTransient
    public List<Inventario> getInventarioList() {
        return inventarioList;
    }

    public void setInventarioList(List<Inventario> inventarioList) {
        this.inventarioList = inventarioList;
    }

}
