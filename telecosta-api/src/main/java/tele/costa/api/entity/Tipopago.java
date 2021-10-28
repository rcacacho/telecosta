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
@Table(catalog = "telecosta", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipopago.findAll", query = "SELECT t FROM Tipopago t"),
    @NamedQuery(name = "Tipopago.findByIdtipopago", query = "SELECT t FROM Tipopago t WHERE t.idtipopago = :idtipopago"),
    @NamedQuery(name = "Tipopago.findByPago", query = "SELECT t FROM Tipopago t WHERE t.pago = :pago"),
    @NamedQuery(name = "Tipopago.findByFechacreacion", query = "SELECT t FROM Tipopago t WHERE t.fechacreacion = :fechacreacion"),
    @NamedQuery(name = "Tipopago.findByUsuariocreacion", query = "SELECT t FROM Tipopago t WHERE t.usuariocreacion = :usuariocreacion"),
    @NamedQuery(name = "Tipopago.findByActivo", query = "SELECT t FROM Tipopago t WHERE t.activo = :activo")})
public class Tipopago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTipopago;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(nullable = false, length = 50)
    private String pago;
    
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacreacion;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(nullable = false, length = 50)
    private String usuariocreacion;
    
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private boolean activo;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idtipopago", fetch = FetchType.LAZY)
    private List<Pago> pagoList;

    public Tipopago() {
    }

    public Tipopago(Integer idTipopago) {
        this.idTipopago = idTipopago;
    }

    public Tipopago(Integer idTipopago, String pago, Date fechacreacion, String usuariocreacion, boolean activo) {
        this.idTipopago = idTipopago;
        this.pago = pago;
        this.fechacreacion = fechacreacion;
        this.usuariocreacion = usuariocreacion;
        this.activo = activo;
    }

    public Integer getIdtipopago() {
        return idTipopago;
    }

    public void setIdtipopago(Integer idTipopago) {
        this.idTipopago = idTipopago;
    }

    public String getPago() {
        return pago;
    }

    public void setPago(String pago) {
        this.pago = pago;
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
        hash += (idTipopago != null ? idTipopago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipopago)) {
            return false;
        }
        Tipopago other = (Tipopago) object;
        if ((this.idTipopago == null && other.idTipopago != null) || (this.idTipopago != null && !this.idTipopago.equals(other.idTipopago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tele.costa.api.entity.Tipopago[ idtipopago=" + idTipopago + " ]";
    }

}
