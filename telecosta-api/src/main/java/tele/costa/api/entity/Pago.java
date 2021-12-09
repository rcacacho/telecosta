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
 * @author elfo_
 */
@Entity
@Table(name = "pago")
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
    @Basic(optional = false)
    @Column(name = "idpago")
    private Integer idpago;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "mes")
    private String mes;

    @Basic(optional = false)
    @NotNull
    @Column(name = "anio")
    private int anio;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Basic(optional = false)
    @NotNull
    @Column(name = "fechacreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacreacion;

    @Column(name = "fechapago")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechapago;

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

    @JoinColumn(name = "idcliente", referencedColumnName = "idcliente")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Cliente idcliente;

    @JoinColumn(name = "idtipopago", referencedColumnName = "idtipopago")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Tipopago idtipopago;

    @JoinColumn(name = "idconfiguracionpago", referencedColumnName = "idconfiguracionpago")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Configuracionpago idconfiguracionpago;

    public Pago() {
    }

    public Pago(Integer idpago) {
        this.idpago = idpago;
    }

    public Pago(Integer idpago, String mes, int anio, Date fechacreacion, String usuariocreacion, boolean activo) {
        this.idpago = idpago;
        this.mes = mes;
        this.anio = anio;
        this.fechacreacion = fechacreacion;
        this.usuariocreacion = usuariocreacion;
        this.activo = activo;
    }

    public Integer getIdpago() {
        return idpago;
    }

    public void setIdpago(Integer idpago) {
        this.idpago = idpago;
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
        return fechacreacion;
    }

    public void setFechacreacion(Date fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public Date getFechapago() {
        return fechapago;
    }

    public void setFechapago(Date fechapago) {
        this.fechapago = fechapago;
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

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Cliente getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Cliente idcliente) {
        this.idcliente = idcliente;
    }

    public Tipopago getIdtipopago() {
        return idtipopago;
    }

    public void setIdtipopago(Tipopago idtipopago) {
        this.idtipopago = idtipopago;
    }

    public Configuracionpago getIdconfiguracionpago() {
        return idconfiguracionpago;
    }

    public void setIdconfiguracionpago(Configuracionpago idconfiguracionpago) {
        this.idconfiguracionpago = idconfiguracionpago;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpago != null ? idpago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pago)) {
            return false;
        }
        Pago other = (Pago) object;
        if ((this.idpago == null && other.idpago != null) || (this.idpago != null && !this.idpago.equals(other.idpago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tele.costa.api.entity.Pago[ idpago=" + idpago + " ]";
    }

}
