/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tele.costa.nuevo.api.entity;

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

/**
 *
 * @author rcacacho
 */
@Entity
@Table(name = "compra", catalog = "telecosta", schema = "")
@NamedQueries({
    @NamedQuery(name = "Compra.findAll", query = "SELECT c FROM Compra c"),
    @NamedQuery(name = "Compra.findByIdcompra", query = "SELECT c FROM Compra c WHERE c.idcompra = :idcompra"),
    @NamedQuery(name = "Compra.findByIdformapago", query = "SELECT c FROM Compra c WHERE c.idformapago = :idformapago"),
    @NamedQuery(name = "Compra.findByBienoservicio", query = "SELECT c FROM Compra c WHERE c.bienoservicio = :bienoservicio"),
    @NamedQuery(name = "Compra.findByNocomprobanteegreso", query = "SELECT c FROM Compra c WHERE c.nocomprobanteegreso = :nocomprobanteegreso"),
    @NamedQuery(name = "Compra.findByFechacompra", query = "SELECT c FROM Compra c WHERE c.fechacompra = :fechacompra"),
    @NamedQuery(name = "Compra.findBySerie", query = "SELECT c FROM Compra c WHERE c.serie = :serie"),
    @NamedQuery(name = "Compra.findByNodocumento", query = "SELECT c FROM Compra c WHERE c.nodocumento = :nodocumento"),
    @NamedQuery(name = "Compra.findByMontocompra", query = "SELECT c FROM Compra c WHERE c.montocompra = :montocompra"),
    @NamedQuery(name = "Compra.findByDescripcion", query = "SELECT c FROM Compra c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "Compra.findByNocheque", query = "SELECT c FROM Compra c WHERE c.nocheque = :nocheque"),
    @NamedQuery(name = "Compra.findByCantidad", query = "SELECT c FROM Compra c WHERE c.cantidad = :cantidad"),
    @NamedQuery(name = "Compra.findByTotal", query = "SELECT c FROM Compra c WHERE c.total = :total"),
    @NamedQuery(name = "Compra.findByObservacion", query = "SELECT c FROM Compra c WHERE c.observacion = :observacion"),
    @NamedQuery(name = "Compra.findByFechacreacion", query = "SELECT c FROM Compra c WHERE c.fechacreacion = :fechacreacion"),
    @NamedQuery(name = "Compra.findByUsuariocreacion", query = "SELECT c FROM Compra c WHERE c.usuariocreacion = :usuariocreacion"),
    @NamedQuery(name = "Compra.findByFechamodificacion", query = "SELECT c FROM Compra c WHERE c.fechamodificacion = :fechamodificacion"),
    @NamedQuery(name = "Compra.findByUsuariomodificacion", query = "SELECT c FROM Compra c WHERE c.usuariomodificacion = :usuariomodificacion"),
    @NamedQuery(name = "Compra.findByUsuarioeliminacion", query = "SELECT c FROM Compra c WHERE c.usuarioeliminacion = :usuarioeliminacion"),
    @NamedQuery(name = "Compra.findByFechaeliminacion", query = "SELECT c FROM Compra c WHERE c.fechaeliminacion = :fechaeliminacion"),
    @NamedQuery(name = "Compra.findByActivo", query = "SELECT c FROM Compra c WHERE c.activo = :activo")})
public class Compra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcompra", nullable = false)
    private Integer idcompra;
    @Column(name = "idformapago")
    private Integer idformapago;
    @Column(name = "bienoservicio", length = 100)
    private String bienoservicio;
    @Column(name = "nocomprobanteegreso", length = 100)
    private String nocomprobanteegreso;
    @Basic(optional = false)
    @Column(name = "fechacompra", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechacompra;
    @Column(name = "serie", length = 100)
    private String serie;
    @Column(name = "nodocumento", length = 200)
    private String nodocumento;
    @Basic(optional = false)
    @Column(name = "montocompra", nullable = false)
    private double montocompra;
    @Column(name = "descripcion", length = 2000)
    private String descripcion;
    @Column(name = "nocheque", length = 100)
    private String nocheque;
    @Column(name = "cantidad")
    private Integer cantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total", precision = 22)
    private Double total;
    @Column(name = "observacion", length = 2000)
    private String observacion;
    @Basic(optional = false)
    @Column(name = "fechacreacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacreacion;
    @Basic(optional = false)
    @Column(name = "usuariocreacion", nullable = false, length = 50)
    private String usuariocreacion;
    @Column(name = "fechamodificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechamodificacion;
    @Column(name = "usuariomodificacion", length = 50)
    private String usuariomodificacion;
    @Column(name = "usuarioeliminacion", length = 50)
    private String usuarioeliminacion;
    @Column(name = "fechaeliminacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaeliminacion;
    @Basic(optional = false)
    @Column(name = "activo", nullable = false)
    private int activo;
    @JoinColumn(name = "idproveedor", referencedColumnName = "idproveedor", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Proveedor idproveedor;
    @JoinColumn(name = "idtipocompra", referencedColumnName = "idtipocompra", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Tipocompra idtipocompra;
    @JoinColumn(name = "idtipodocumentocompra", referencedColumnName = "idtipodocumentocompra", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Tipodocumentocompra idtipodocumentocompra;

    public Compra() {
    }

    public Compra(Integer idcompra) {
        this.idcompra = idcompra;
    }

    public Compra(Integer idcompra, Date fechacompra, double montocompra, Date fechacreacion, String usuariocreacion, int activo) {
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

    public Integer getIdformapago() {
        return idformapago;
    }

    public void setIdformapago(Integer idformapago) {
        this.idformapago = idformapago;
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

    public Date getFechacompra() {
        return fechacompra;
    }

    public void setFechacompra(Date fechacompra) {
        this.fechacompra = fechacompra;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
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

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
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
        return "tele.costa.nuevo.api.entity.Compra[ idcompra=" + idcompra + " ]";
    }
    
}
