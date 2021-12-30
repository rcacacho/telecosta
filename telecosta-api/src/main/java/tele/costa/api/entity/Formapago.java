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
@Table(name = "formapago")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Formapago.findAll", query = "SELECT f FROM Formapago f"),
    @NamedQuery(name = "Formapago.findByIdformapago", query = "SELECT f FROM Formapago f WHERE f.idformapago = :idformapago"),
    @NamedQuery(name = "Formapago.findByForma", query = "SELECT f FROM Formapago f WHERE f.forma = :forma"),
    @NamedQuery(name = "Formapago.findByDescripcion", query = "SELECT f FROM Formapago f WHERE f.descripcion = :descripcion"),
    @NamedQuery(name = "Formapago.findByUsuariocreacion", query = "SELECT f FROM Formapago f WHERE f.usuariocreacion = :usuariocreacion"),
    @NamedQuery(name = "Formapago.findByFechacreacion", query = "SELECT f FROM Formapago f WHERE f.fechacreacion = :fechacreacion"),
    @NamedQuery(name = "Formapago.findByActivo", query = "SELECT f FROM Formapago f WHERE f.activo = :activo")})
public class Formapago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idformapago")
    private Integer idformapago;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "forma")
    private String forma;
    
    @Column(name = "descripcion")
    private Integer descripcion;
    
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
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "activo")
    private boolean activo;
    
    @OneToMany(mappedBy = "idformapago", fetch = FetchType.LAZY)
    private List<Pago> pagoList;

    public Formapago() {
    }

    public Formapago(Integer idformapago) {
        this.idformapago = idformapago;
    }

    public Formapago(Integer idformapago, String forma, String usuariocreacion, Date fechacreacion, boolean activo) {
        this.idformapago = idformapago;
        this.forma = forma;
        this.usuariocreacion = usuariocreacion;
        this.fechacreacion = fechacreacion;
        this.activo = activo;
    }

    public Integer getIdformapago() {
        return idformapago;
    }

    public void setIdformapago(Integer idformapago) {
        this.idformapago = idformapago;
    }

    public String getForma() {
        return forma;
    }

    public void setForma(String forma) {
        this.forma = forma;
    }

    public Integer getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(Integer descripcion) {
        this.descripcion = descripcion;
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

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @XmlTransient
    public List<Pago> getPagoList() {
        return pagoList;
    }

    public void setPagoList(List<Pago> pagoList) {
        this.pagoList = pagoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idformapago != null ? idformapago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Formapago)) {
            return false;
        }
        Formapago other = (Formapago) object;
        if ((this.idformapago == null && other.idformapago != null) || (this.idformapago != null && !this.idformapago.equals(other.idformapago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tele.costa.api.entity.Formapago[ idformapago=" + idformapago + " ]";
    }
    
}
