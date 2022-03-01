package tele.costa.api.entity;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "cajaagencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cajaagencia.findAll", query = "SELECT c FROM Cajaagencia c"),
    @NamedQuery(name = "Cajaagencia.findByIdcajaagencia", query = "SELECT c FROM Cajaagencia c WHERE c.idcajaagencia = :idcajaagencia"),
    @NamedQuery(name = "Cajaagencia.findByCorrelativodel", query = "SELECT c FROM Cajaagencia c WHERE c.correlativodel = :correlativodel"),
    @NamedQuery(name = "Cajaagencia.findByCorrelativoal", query = "SELECT c FROM Cajaagencia c WHERE c.correlativoal = :correlativoal"),
    @NamedQuery(name = "Cajaagencia.findByIngreso", query = "SELECT c FROM Cajaagencia c WHERE c.ingreso = :ingreso"),
    @NamedQuery(name = "Cajaagencia.findByEgreso", query = "SELECT c FROM Cajaagencia c WHERE c.egreso = :egreso"),
    @NamedQuery(name = "Cajaagencia.findBySaldo", query = "SELECT c FROM Cajaagencia c WHERE c.saldo = :saldo"),
    @NamedQuery(name = "Cajaagencia.findByForma", query = "SELECT c FROM Cajaagencia c WHERE c.forma = :forma"),
    @NamedQuery(name = "Cajaagencia.findByNodocumento", query = "SELECT c FROM Cajaagencia c WHERE c.nodocumento = :nodocumento"),
    @NamedQuery(name = "Cajaagencia.findByObservaciones", query = "SELECT c FROM Cajaagencia c WHERE c.observaciones = :observaciones"),
    @NamedQuery(name = "Cajaagencia.findByUsuariocreacion", query = "SELECT c FROM Cajaagencia c WHERE c.usuariocreacion = :usuariocreacion"),
    @NamedQuery(name = "Cajaagencia.findByFechacreacion", query = "SELECT c FROM Cajaagencia c WHERE c.fechacreacion = :fechacreacion"),
    @NamedQuery(name = "Cajaagencia.findByUsuariomodificacion", query = "SELECT c FROM Cajaagencia c WHERE c.usuariomodificacion = :usuariomodificacion"),
    @NamedQuery(name = "Cajaagencia.findByFechamodificacion", query = "SELECT c FROM Cajaagencia c WHERE c.fechamodificacion = :fechamodificacion"),
    @NamedQuery(name = "Cajaagencia.findByActivo", query = "SELECT c FROM Cajaagencia c WHERE c.activo = :activo")})
public class Cajaagencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcajaagencia")
    private Integer idcajaagencia;

    @Column(name = "correlativodel")
    private Integer correlativodel;

    @Column(name = "correlativoal")
    private Integer correlativoal;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ingreso")
    private BigInteger ingreso;

    @Column(name = "egreso")
    private BigInteger egreso;

    @Column(name = "saldo")
    private BigInteger saldo;

    @Size(max = 500)
    @Column(name = "forma")
    private String forma;

    @Size(max = 500)
    @Column(name = "nodocumento")
    private String nodocumento;

    @Size(max = 200)
    @Column(name = "observaciones")
    private String observaciones;

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

    @Size(min = 1, max = 50)
    @Column(name = "usuariomodificacion")
    private String usuariomodificacion;

    @Column(name = "fechamodificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechamodificacion;

    @Basic(optional = false)
    @NotNull
    @Column(name = "activo")
    private boolean activo;

    @JoinColumn(name = "idsectorpago", referencedColumnName = "idsectorpago")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Sectorpago idsectorpago;

    public Cajaagencia() {
    }

    public Cajaagencia(Integer idcajaagencia) {
        this.idcajaagencia = idcajaagencia;
    }

    public Cajaagencia(Integer idcajaagencia, BigInteger ingreso, String usuariocreacion, Date fechacreacion, String usuariomodificacion, Date fechamodificacion, boolean activo) {
        this.idcajaagencia = idcajaagencia;
        this.ingreso = ingreso;
        this.usuariocreacion = usuariocreacion;
        this.fechacreacion = fechacreacion;
        this.usuariomodificacion = usuariomodificacion;
        this.fechamodificacion = fechamodificacion;
        this.activo = activo;
    }

    public Integer getIdcajaagencia() {
        return idcajaagencia;
    }

    public void setIdcajaagencia(Integer idcajaagencia) {
        this.idcajaagencia = idcajaagencia;
    }

    public Integer getCorrelativodel() {
        return correlativodel;
    }

    public void setCorrelativodel(Integer correlativodel) {
        this.correlativodel = correlativodel;
    }

    public Integer getCorrelativoal() {
        return correlativoal;
    }

    public void setCorrelativoal(Integer correlativoal) {
        this.correlativoal = correlativoal;
    }

    public BigInteger getIngreso() {
        return ingreso;
    }

    public void setIngreso(BigInteger ingreso) {
        this.ingreso = ingreso;
    }

    public BigInteger getEgreso() {
        return egreso;
    }

    public void setEgreso(BigInteger egreso) {
        this.egreso = egreso;
    }

    public BigInteger getSaldo() {
        return saldo;
    }

    public void setSaldo(BigInteger saldo) {
        this.saldo = saldo;
    }

    public String getForma() {
        return forma;
    }

    public void setForma(String forma) {
        this.forma = forma;
    }

    public String getNodocumento() {
        return nodocumento;
    }

    public void setNodocumento(String nodocumento) {
        this.nodocumento = nodocumento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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

    public Sectorpago getIdsectorpago() {
        return idsectorpago;
    }

    public void setIdsectorpago(Sectorpago idsectorpago) {
        this.idsectorpago = idsectorpago;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcajaagencia != null ? idcajaagencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cajaagencia)) {
            return false;
        }
        Cajaagencia other = (Cajaagencia) object;
        if ((this.idcajaagencia == null && other.idcajaagencia != null) || (this.idcajaagencia != null && !this.idcajaagencia.equals(other.idcajaagencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tele.costa.api.entity.Cajaagencia[ idcajaagencia=" + idcajaagencia + " ]";
    }

}
