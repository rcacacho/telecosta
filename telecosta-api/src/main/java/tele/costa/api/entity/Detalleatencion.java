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
@Table(name = "detalleatencion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Detalleatencion.findAll", query = "SELECT d FROM Detalleatencion d"),
    @NamedQuery(name = "Detalleatencion.findByIddetalleatencion", query = "SELECT d FROM Detalleatencion d WHERE d.iddetalleatencion = :iddetalleatencion"),
    @NamedQuery(name = "Detalleatencion.findByMaterialutilizado", query = "SELECT d FROM Detalleatencion d WHERE d.materialutilizado = :materialutilizado"),
    @NamedQuery(name = "Detalleatencion.findByCantidad", query = "SELECT d FROM Detalleatencion d WHERE d.cantidad = :cantidad"),
    @NamedQuery(name = "Detalleatencion.findByObservacion", query = "SELECT d FROM Detalleatencion d WHERE d.observacion = :observacion"),
    @NamedQuery(name = "Detalleatencion.findByFechacreacion", query = "SELECT d FROM Detalleatencion d WHERE d.fechacreacion = :fechacreacion"),
    @NamedQuery(name = "Detalleatencion.findByUsuariocreacion", query = "SELECT d FROM Detalleatencion d WHERE d.usuariocreacion = :usuariocreacion"),
    @NamedQuery(name = "Detalleatencion.findByFechamodificacion", query = "SELECT d FROM Detalleatencion d WHERE d.fechamodificacion = :fechamodificacion"),
    @NamedQuery(name = "Detalleatencion.findByUsuariomodificacion", query = "SELECT d FROM Detalleatencion d WHERE d.usuariomodificacion = :usuariomodificacion"),
    @NamedQuery(name = "Detalleatencion.findByActivo", query = "SELECT d FROM Detalleatencion d WHERE d.activo = :activo")})
public class Detalleatencion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddetalleatencion")
    private Integer iddetalleatencion;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "materialutilizado")
    private String materialutilizado;
    
    @Size(max = 250)
    @Column(name = "cantidad")
    private String cantidad;
    
    @Size(max = 2000)
    @Column(name = "observacion")
    private String observacion;
    
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
    private Integer fechamodificacion;
    
    @Column(name = "usuariomodificacion")
    private Integer usuariomodificacion;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "activo")
    private boolean activo;
    
    @JoinColumn(name = "idatencion", referencedColumnName = "idatencion")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Atencion idatencion;

    public Detalleatencion() {
    }

    public Detalleatencion(Integer iddetalleatencion) {
        this.iddetalleatencion = iddetalleatencion;
    }

    public Detalleatencion(Integer iddetalleatencion, String materialutilizado, Date fechacreacion, String usuariocreacion, boolean activo) {
        this.iddetalleatencion = iddetalleatencion;
        this.materialutilizado = materialutilizado;
        this.fechacreacion = fechacreacion;
        this.usuariocreacion = usuariocreacion;
        this.activo = activo;
    }

    public Integer getIddetalleatencion() {
        return iddetalleatencion;
    }

    public void setIddetalleatencion(Integer iddetalleatencion) {
        this.iddetalleatencion = iddetalleatencion;
    }

    public String getMaterialutilizado() {
        return materialutilizado;
    }

    public void setMaterialutilizado(String materialutilizado) {
        this.materialutilizado = materialutilizado;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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

    public Integer getFechamodificacion() {
        return fechamodificacion;
    }

    public void setFechamodificacion(Integer fechamodificacion) {
        this.fechamodificacion = fechamodificacion;
    }

    public Integer getUsuariomodificacion() {
        return usuariomodificacion;
    }

    public void setUsuariomodificacion(Integer usuariomodificacion) {
        this.usuariomodificacion = usuariomodificacion;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Atencion getIdatencion() {
        return idatencion;
    }

    public void setIdatencion(Atencion idatencion) {
        this.idatencion = idatencion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddetalleatencion != null ? iddetalleatencion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Detalleatencion)) {
            return false;
        }
        Detalleatencion other = (Detalleatencion) object;
        if ((this.iddetalleatencion == null && other.iddetalleatencion != null) || (this.iddetalleatencion != null && !this.iddetalleatencion.equals(other.iddetalleatencion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tele.costa.api.entity.Detalleatencion[ iddetalleatencion=" + iddetalleatencion + " ]";
    }
    
}
