package tele.costa.api.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
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
@Table(name = "pago")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pago.findAll", query = "SELECT p FROM Pago p"),
    @NamedQuery(name = "Pago.findByIdpago", query = "SELECT p FROM Pago p WHERE p.idpago = :idpago"),
    @NamedQuery(name = "Pago.findByAnio", query = "SELECT p FROM Pago p WHERE p.anio = :anio"),
    @NamedQuery(name = "Pago.findByMes", query = "SELECT p FROM Pago p WHERE p.mes = :mes"),
    @NamedQuery(name = "Pago.findByTotal", query = "SELECT p FROM Pago p WHERE p.total = :total"),
    @NamedQuery(name = "Pago.findByFechapago", query = "SELECT p FROM Pago p WHERE p.fechapago = :fechapago"),
    @NamedQuery(name = "Pago.findByUsuariocreacion", query = "SELECT p FROM Pago p WHERE p.usuariocreacion = :usuariocreacion"),
    @NamedQuery(name = "Pago.findByFechacreacion", query = "SELECT p FROM Pago p WHERE p.fechacreacion = :fechacreacion"),
    @NamedQuery(name = "Pago.findByUsuariomodificacion", query = "SELECT p FROM Pago p WHERE p.usuariomodificacion = :usuariomodificacion"),
    @NamedQuery(name = "Pago.findByFechamodificacion", query = "SELECT p FROM Pago p WHERE p.fechamodificacion = :fechamodificacion"),
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
    @Column(name = "anio")
    private Integer anio;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "mes")
    private String mes;

    @Column(name = "nomes")
    private Integer nomes;

    @Column(name = "total")
    private Integer total;

    @Size(max = 2000)
    @Column(name = "observacion")
    private String observacion;

    @Column(name = "fechapago")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechapago;

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

    @Size(max = 50)
    @Column(name = "usuarioeliminacion")
    private String usuarioeliminacion;

    @Column(name = "fechaeliminacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaeliminacion;

    @Basic(optional = false)
    @NotNull
    @Column(name = "activo")
    private boolean activo;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idpago", fetch = FetchType.LAZY)
    private List<Detallepago> detallepagoList;

    @JoinColumn(name = "idcliente", referencedColumnName = "idcliente")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Cliente idcliente;

    @JoinColumn(name = "idtipopago", referencedColumnName = "idtipopago")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Tipopago idtipopago;

    public Pago() {
    }

    public Pago(Integer idpago) {
        this.idpago = idpago;
    }

    public Pago(Integer idpago, int anio, String mes, String usuariocreacion, Date fechacreacion, boolean activo) {
        this.idpago = idpago;
        this.anio = anio;
        this.mes = mes;
        this.usuariocreacion = usuariocreacion;
        this.fechacreacion = fechacreacion;
        this.activo = activo;
    }

    public Integer getIdpago() {
        return idpago;
    }

    public void setIdpago(Integer idpago) {
        this.idpago = idpago;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
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

    public Tipopago getIdtipopago() {
        return idtipopago;
    }

    public void setIdtipopago(Tipopago idtipopago) {
        this.idtipopago = idtipopago;
    }

    public String getUsuarioeliminacion() {
        return usuarioeliminacion;
    }

    public void setUsuarioeliminacion(String usuarioeliminacion) {
        this.usuarioeliminacion = usuarioeliminacion;
    }

    public Date getFechaeliminacion() {
        return fechaeliminacion;
    }

    public void setFechaeliminacion(Date fechaeliminacion) {
        this.fechaeliminacion = fechaeliminacion;
    }

    @XmlTransient
    public List<Detallepago> getDetallepagoList() {
        return detallepagoList;
    }

    public void setDetallepagoList(List<Detallepago> detallepagoList) {
        this.detallepagoList = detallepagoList;
    }

    public Cliente getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Cliente idcliente) {
        this.idcliente = idcliente;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Integer getNomes() {
        return nomes;
    }

    public void setNomes(Integer nomes) {
        this.nomes = nomes;
    }

    public String getStylePago() {
        if (fechapago != null) {
            Date fechaInicio = new Date();
            LocalDate startDate = fechapago.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate endDate = fechaInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            ZoneId defaultZoneId = ZoneId.systemDefault();
            Integer count = 0;
            for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
                count++;
            }

            if (count >= 90) {
                return "rowColorRojo";
            } else if (count >= 60 && count <= 89) {
                return "rowColorAmarillo";
            } else if (count >= 30 && count <= 59) {
                return "rowColorAnaranjado";
            }
        }
        return "";
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
