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
@Table(name = "seriefactura")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Seriefactura.findAll", query = "SELECT s FROM Seriefactura s"),
    @NamedQuery(name = "Seriefactura.findByIdseriefactura", query = "SELECT s FROM Seriefactura s WHERE s.idseriefactura = :idseriefactura"),
    @NamedQuery(name = "Seriefactura.findBySerie", query = "SELECT s FROM Seriefactura s WHERE s.serie = :serie"),
    @NamedQuery(name = "Seriefactura.findByDescripcion", query = "SELECT s FROM Seriefactura s WHERE s.descripcion = :descripcion"),
    @NamedQuery(name = "Seriefactura.findByFechacreacion", query = "SELECT s FROM Seriefactura s WHERE s.fechacreacion = :fechacreacion"),
    @NamedQuery(name = "Seriefactura.findByUsuariocreacion", query = "SELECT s FROM Seriefactura s WHERE s.usuariocreacion = :usuariocreacion"),
    @NamedQuery(name = "Seriefactura.findByFechaeliminacion", query = "SELECT s FROM Seriefactura s WHERE s.fechaeliminacion = :fechaeliminacion"),
    @NamedQuery(name = "Seriefactura.findByUsuarioeliminacion", query = "SELECT s FROM Seriefactura s WHERE s.usuarioeliminacion = :usuarioeliminacion"),
    @NamedQuery(name = "Seriefactura.findByActivo", query = "SELECT s FROM Seriefactura s WHERE s.activo = :activo")})
public class Seriefactura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idseriefactura")
    private Integer idseriefactura;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "serie")
    private String serie;
    
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
    
    @Column(name = "fechaeliminacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaeliminacion;
    
    @Size(max = 50)
    @Column(name = "usuarioeliminacion")
    private String usuarioeliminacion;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "activo")
    private int activo;

    public Seriefactura() {
    }

    public Seriefactura(Integer idseriefactura) {
        this.idseriefactura = idseriefactura;
    }

    public Seriefactura(Integer idseriefactura, String serie, Date fechacreacion, String usuariocreacion, int activo) {
        this.idseriefactura = idseriefactura;
        this.serie = serie;
        this.fechacreacion = fechacreacion;
        this.usuariocreacion = usuariocreacion;
        this.activo = activo;
    }

    public Integer getIdseriefactura() {
        return idseriefactura;
    }

    public void setIdseriefactura(Integer idseriefactura) {
        this.idseriefactura = idseriefactura;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
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

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idseriefactura != null ? idseriefactura.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Seriefactura)) {
            return false;
        }
        Seriefactura other = (Seriefactura) object;
        if ((this.idseriefactura == null && other.idseriefactura != null) || (this.idseriefactura != null && !this.idseriefactura.equals(other.idseriefactura))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tele.costa.api.entity.Seriefactura[ idseriefactura=" + idseriefactura + " ]";
    }
    
}
