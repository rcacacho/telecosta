package tele.costa.api.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * @author elfo_
 */
@Entity
@Table(name = "pago", catalog = "telecosta", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pago.findAll", query = "SELECT p FROM Pago p"),
    @NamedQuery(name = "Pago.findByIdpago", query = "SELECT p FROM Pago p WHERE p.idpago = :idpago"),
    @NamedQuery(name = "Pago.findByMes", query = "SELECT p FROM Pago p WHERE p.mes = :mes"),
    @NamedQuery(name = "Pago.findByAnio", query = "SELECT p FROM Pago p WHERE p.anio = :anio"),
    @NamedQuery(name = "Pago.findByFechacreacion", query = "SELECT p FROM Pago p WHERE p.fechacreacion = :fechacreacion"),
    @NamedQuery(name = "Pago.findByFechapago", query = "SELECT p FROM Pago p WHERE p.fechapago = :fechapago"),
    @NamedQuery(name = "Pago.findByUsuariocreacion", query = "SELECT p FROM Pago p WHERE p.usuariocreacion = :usuariocreacion"),
    @NamedQuery(name = "Pago.findByFechamodificacion", query = "SELECT p FROM Pago p WHERE p.fechamodificacion = :fechamodificacion"),
    @NamedQuery(name = "Pago.findByUsuariomodificacion", query = "SELECT p FROM Pago p WHERE p.usuariomodificacion = :usuariomodificacion"),
    @NamedQuery(name = "Pago.findByActivo", query = "SELECT p FROM Pago p WHERE p.activo = :activo")})
public class Pago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPago;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "mes", nullable = false, length = 100)
    private String mes;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "anio", nullable = false)
    private int anio;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechacreacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    
    @Column(name = "fechapago")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPago;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "usuariocreacion", nullable = false, length = 50)
    private String usuarioCreacion;
    
    @Column(name = "fechamodificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    
    @Size(max = 50)
    @Column(name = "usuariomodificacion", length = 50)
    private String usuarioModificacion;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "activo", nullable = false)
    private boolean activo;
    
    @JoinColumn(name = "idcliente", referencedColumnName = "idcliente", nullable = false)
    @ManyToOne(optional = false)
    private Cliente idCliente;
    
    @JoinColumn(name = "idtipopago", referencedColumnName = "idtipopago", nullable = false)
    @ManyToOne(optional = false)
    private Tipopago idTipopago;

    public Pago() {
    }

    public Pago(Integer idPago) {
        this.idPago = idPago;
    }

    public Pago(Integer idPago, String mes, int anio, Date fechaCreacion, String usuarioCreacion, boolean activo) {
        this.idPago = idPago;
        this.mes = mes;
        this.anio = anio;
        this.fechaCreacion = fechaCreacion;
        this.usuarioCreacion = usuarioCreacion;
        this.activo = activo;
    }

    public Integer getIdpago() {
        return idPago;
    }

    public void setIdpago(Integer idPago) {
        this.idPago = idPago;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public Date getFechacreacion() {
        return fechaCreacion;
    }

    public void setFechacreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechapago() {
        return fechaPago;
    }

    public void setFechapago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getUsuariocreacion() {
        return usuarioCreacion;
    }

    public void setUsuariocreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public Date getFechamodificacion() {
        return fechaModificacion;
    }

    public void setFechamodificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getUsuariomodificacion() {
        return usuarioModificacion;
    }

    public void setUsuariomodificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Cliente getIdcliente() {
        return idCliente;
    }

    public void setIdcliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    public Tipopago getIdtipopago() {
        return idTipopago;
    }

    public void setIdtipopago(Tipopago idTipopago) {
        this.idTipopago = idTipopago;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPago != null ? idPago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pago)) {
            return false;
        }
        Pago other = (Pago) object;
        if ((this.idPago == null && other.idPago != null) || (this.idPago != null && !this.idPago.equals(other.idPago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tele.costa.api.entity.Pago[ idpago=" + idPago + " ]";
    }

}
