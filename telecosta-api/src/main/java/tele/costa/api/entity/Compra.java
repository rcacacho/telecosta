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
@Table(name = "compra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Compra.findAll", query = "SELECT c FROM Compra c"),
    @NamedQuery(name = "Compra.findByIdcompra", query = "SELECT c FROM Compra c WHERE c.idcompra = :idcompra"),
    @NamedQuery(name = "Compra.findByFechacompra", query = "SELECT c FROM Compra c WHERE c.fechacompra = :fechacompra"),
    @NamedQuery(name = "Compra.findByNodocumento", query = "SELECT c FROM Compra c WHERE c.nodocumento = :nodocumento"),
    @NamedQuery(name = "Compra.findByMontocompra", query = "SELECT c FROM Compra c WHERE c.montocompra = :montocompra"),
    @NamedQuery(name = "Compra.findByFechacreacion", query = "SELECT c FROM Compra c WHERE c.fechacreacion = :fechacreacion"),
    @NamedQuery(name = "Compra.findByUsuariocreacion", query = "SELECT c FROM Compra c WHERE c.usuariocreacion = :usuariocreacion"),
    @NamedQuery(name = "Compra.findByFechamodificacion", query = "SELECT c FROM Compra c WHERE c.fechamodificacion = :fechamodificacion"),
    @NamedQuery(name = "Compra.findByUsuariomodificacion", query = "SELECT c FROM Compra c WHERE c.usuariomodificacion = :usuariomodificacion"),
    @NamedQuery(name = "Compra.findByActivo", query = "SELECT c FROM Compra c WHERE c.activo = :activo")})
public class Compra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcompra")
    private Integer idcompra;

    @Basic(optional = false)
    @NotNull
    @Column(name = "fechacompra")
    @Temporal(TemporalType.DATE)
    private Date fechacompra;

    @Size(max = 100)
    @Column(name = "bienoservicio")
    private String bienoservicio;

    @Size(max = 100)
    @Column(name = "nocomprobanteegreso")
    private String nocomprobanteegreso;

    @Size(max = 100)
    @Column(name = "serie")
    private String serie;

    @Size(max = 200)
    @Column(name = "nodocumento")
    private String nodocumento;

    @Basic(optional = false)
    @NotNull
    @Column(name = "montocompra")
    private double montocompra;

    @Size(max = 2000)
    @Column(name = "descripcion")
    private String descripcion;

    @Size(max = 100)
    @Column(name = "nocheque")
    private String nocheque;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "total")
    private double total;

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
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechamodificacion;

    @Size(max = 50)
    @Column(name = "usuariomodificacion")
    private String usuariomodificacion;

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

    @JoinColumn(name = "idproveedor", referencedColumnName = "idproveedor")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Proveedor idproveedor;

    @JoinColumn(name = "idtipocompra", referencedColumnName = "idtipocompra")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Tipocompra idtipocompra;

    @JoinColumn(name = "idtipodocumentocompra", referencedColumnName = "idtipodocumentocompra")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Tipodocumentocompra idtipodocumentocompra;

    @JoinColumn(name = "idformapago", referencedColumnName = "idformapago")
    @ManyToOne(fetch = FetchType.LAZY)
    private Formapago idformapago;

    public Compra() {
    }

    public Compra(Integer idcompra) {
        this.idcompra = idcompra;
    }

    public Compra(Integer idcompra, Date fechacompra, int montocompra, Date fechacreacion, String usuariocreacion, boolean activo) {
        this.idcompra = idcompra;
        this.fechacompra = fechacompra;
        this.montocompra = montocompra;
        this.fechacreacion = fechacreacion;
        this.usuariocreacion = usuariocreacion;
        this.activo = activo;
    }

    public Integer getIdcompra() {
        return idcompra;
    }

    public void setIdcompra(Integer idcompra) {
        this.idcompra = idcompra;
    }

    public Date getFechacompra() {
        return fechacompra;
    }

    public void setFechacompra(Date fechacompra) {
        this.fechacompra = fechacompra;
    }

    public String getNodocumento() {
        return nodocumento;
    }

    public void setNodocumento(String nodocumento) {
        this.nodocumento = nodocumento;
    }

    public double getMontocompra() {
        return montocompra;
    }

    public void setMontocompra(double montocompra) {
        this.montocompra = montocompra;
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

    public Proveedor getIdproveedor() {
        return idproveedor;
    }

    public void setIdproveedor(Proveedor idproveedor) {
        this.idproveedor = idproveedor;
    }

    public Tipocompra getIdtipocompra() {
        return idtipocompra;
    }

    public void setIdtipocompra(Tipocompra idtipocompra) {
        this.idtipocompra = idtipocompra;
    }

    public Tipodocumentocompra getIdtipodocumentocompra() {
        return idtipodocumentocompra;
    }

    public void setIdtipodocumentocompra(Tipodocumentocompra idtipodocumentocompra) {
        this.idtipodocumentocompra = idtipodocumentocompra;
    }

    public String getBienoservicio() {
        return bienoservicio;
    }

    public void setBienoservicio(String bienoservicio) {
        this.bienoservicio = bienoservicio;
    }

    public String getNocomprobanteegreso() {
        return nocomprobanteegreso;
    }

    public void setNocomprobanteegreso(String nocomprobanteegreso) {
        this.nocomprobanteegreso = nocomprobanteegreso;
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

    public Formapago getIdformapago() {
        return idformapago;
    }

    public void setIdformapago(Formapago idformapago) {
        this.idformapago = idformapago;
    }

    public String getNocheque() {
        return nocheque;
    }

    public void setNocheque(String nocheque) {
        this.nocheque = nocheque;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcompra != null ? idcompra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Compra)) {
            return false;
        }
        Compra other = (Compra) object;
        if ((this.idcompra == null && other.idcompra != null) || (this.idcompra != null && !this.idcompra.equals(other.idcompra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tele.costa.api.entity.Compra[ idcompra=" + idcompra + " ]";
    }

}
