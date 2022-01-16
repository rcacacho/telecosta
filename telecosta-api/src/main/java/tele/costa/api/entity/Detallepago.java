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
@Table(name = "detallepago")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Detallepago.findAll", query = "SELECT d FROM Detallepago d"),
    @NamedQuery(name = "Detallepago.findByIddetallepago", query = "SELECT d FROM Detallepago d WHERE d.iddetallepago = :iddetallepago"),
    @NamedQuery(name = "Detallepago.findBySerie", query = "SELECT d FROM Detallepago d WHERE d.serie = :serie"),
    @NamedQuery(name = "Detallepago.findByNofactura", query = "SELECT d FROM Detallepago d WHERE d.nofactura = :nofactura"),
    @NamedQuery(name = "Detallepago.findByFechapago", query = "SELECT d FROM Detallepago d WHERE d.fechapago = :fechapago"),
    @NamedQuery(name = "Detallepago.findByMontocobrado", query = "SELECT d FROM Detallepago d WHERE d.montocobrado = :montocobrado"),
    @NamedQuery(name = "Detallepago.findByMontopagado", query = "SELECT d FROM Detallepago d WHERE d.montopagado = :montopagado"),
    @NamedQuery(name = "Detallepago.findByTotal", query = "SELECT d FROM Detallepago d WHERE d.total = :total"),
    @NamedQuery(name = "Detallepago.findByFechacreacion", query = "SELECT d FROM Detallepago d WHERE d.fechacreacion = :fechacreacion"),
    @NamedQuery(name = "Detallepago.findByUsuariocreacion", query = "SELECT d FROM Detallepago d WHERE d.usuariocreacion = :usuariocreacion"),
    @NamedQuery(name = "Detallepago.findByFechamodificacion", query = "SELECT d FROM Detallepago d WHERE d.fechamodificacion = :fechamodificacion"),
    @NamedQuery(name = "Detallepago.findByUsuariomodificacion", query = "SELECT d FROM Detallepago d WHERE d.usuariomodificacion = :usuariomodificacion"),
    @NamedQuery(name = "Detallepago.findByActivo", query = "SELECT d FROM Detallepago d WHERE d.activo = :activo")})
public class Detallepago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddetallepago")
    private Integer iddetallepago;

    @Size(max = 100)
    @Column(name = "serie")
    private String serie;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nofactura")
    private String nofactura;

    @Column(name = "fechapago")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechapago;

    @Column(name = "montocobrado")
    private Integer montocobrado;

    @Column(name = "montopagado")
    private Integer montopagado;

    @Column(name = "total")
    private Integer total;

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

    @Size(max = 50)
    @Column(name = "usuariomodificacion")
    private String usuariomodificacion;

    @Basic(optional = false)
    @NotNull
    @Column(name = "activo")
    private boolean activo;

    @JoinColumn(name = "idformapago", referencedColumnName = "idformapago")
    @ManyToOne(fetch = FetchType.LAZY)
    private Formapago idformapago;

    @JoinColumn(name = "idpago", referencedColumnName = "idpago")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Pago idpago;

    public Detallepago() {
    }

    public Detallepago(Integer iddetallepago) {
        this.iddetallepago = iddetallepago;
    }

    public Detallepago(Integer iddetallepago, String nofactura, Date fechacreacion, String usuariocreacion, boolean activo) {
        this.iddetallepago = iddetallepago;
        this.nofactura = nofactura;
        this.fechacreacion = fechacreacion;
        this.usuariocreacion = usuariocreacion;
        this.activo = activo;
    }

    public Integer getIddetallepago() {
        return iddetallepago;
    }

    public void setIddetallepago(Integer iddetallepago) {
        this.iddetallepago = iddetallepago;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getNofactura() {
        return nofactura;
    }

    public void setNofactura(String nofactura) {
        this.nofactura = nofactura;
    }

    public Date getFechapago() {
        return fechapago;
    }

    public void setFechapago(Date fechapago) {
        this.fechapago = fechapago;
    }

    public Integer getMontocobrado() {
        return montocobrado;
    }

    public void setMontocobrado(Integer montocobrado) {
        this.montocobrado = montocobrado;
    }

    public Integer getMontopagado() {
        return montopagado;
    }

    public void setMontopagado(Integer montopagado) {
        this.montopagado = montopagado;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
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

    public Formapago getIdformapago() {
        return idformapago;
    }

    public void setIdformapago(Formapago idformapago) {
        this.idformapago = idformapago;
    }

    public Pago getIdpago() {
        return idpago;
    }

    public void setIdpago(Pago idpago) {
        this.idpago = idpago;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddetallepago != null ? iddetallepago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Detallepago)) {
            return false;
        }
        Detallepago other = (Detallepago) object;
        if ((this.iddetallepago == null && other.iddetallepago != null) || (this.iddetallepago != null && !this.iddetallepago.equals(other.iddetallepago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tele.costa.api.entity.Detallepago[ iddetallepago=" + iddetallepago + " ]";
    }

}
