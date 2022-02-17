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
 * @author elfo_
 */
@Entity
@Table(name = "atencion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Atencion.findAll", query = "SELECT a FROM Atencion a"),
    @NamedQuery(name = "Atencion.findByIdatencion", query = "SELECT a FROM Atencion a WHERE a.idatencion = :idatencion"),
    @NamedQuery(name = "Atencion.findByMotivo", query = "SELECT a FROM Atencion a WHERE a.motivo = :motivo"),
    @NamedQuery(name = "Atencion.findByReferencia", query = "SELECT a FROM Atencion a WHERE a.referencia = :referencia"),
    @NamedQuery(name = "Atencion.findByFechacreacion", query = "SELECT a FROM Atencion a WHERE a.fechacreacion = :fechacreacion"),
    @NamedQuery(name = "Atencion.findByUsuariocreacion", query = "SELECT a FROM Atencion a WHERE a.usuariocreacion = :usuariocreacion"),
    @NamedQuery(name = "Atencion.findByFechamodificacion", query = "SELECT a FROM Atencion a WHERE a.fechamodificacion = :fechamodificacion"),
    @NamedQuery(name = "Atencion.findByUsuariomodificacion", query = "SELECT a FROM Atencion a WHERE a.usuariomodificacion = :usuariomodificacion"),
    @NamedQuery(name = "Atencion.findByActivo", query = "SELECT a FROM Atencion a WHERE a.activo = :activo")})
public class Atencion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idatencion")
    private Integer idatencion;

    @Size(max = 500)
    @Column(name = "nombre")
    private String nombre;

    @Size(max = 2000)
    @Column(name = "direccion")
    private String direccion;

    @Size(max = 100)
    @Column(name = "telefono")
    private String telefono;

    @Size(max = 2000)
    @Column(name = "motivo")
    private String motivo;

    @Size(max = 2000)
    @Column(name = "referencia")
    private String referencia;

    @Column(name = "estado")
    private boolean estado;

    @Size(max = 500)
    @Column(name = "material")
    private String material;

    @Size(max = 50)
    @Column(name = "cantidad")
    private String cantidad;

    @Size(max = 2000)
    @Column(name = "observaciones")
    private String observaciones;

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

    @Basic(optional = false)
    @NotNull
    @Column(name = "activo")
    private boolean activo;

    @JoinColumn(name = "idcliente", referencedColumnName = "idcliente")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Cliente idcliente;

    @JoinColumn(name = "idruta", referencedColumnName = "idruta")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Ruta idruta;

    @JoinColumn(name = "idtipoatencion", referencedColumnName = "idtipoatencion")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Tipoatencion idtipoatencion;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idatencion", fetch = FetchType.LAZY)
    private List<Detalleatencion> detalleatencionList;

    public Atencion() {
    }

    public Atencion(Integer idatencion) {
        this.idatencion = idatencion;
    }

    public Atencion(Integer idatencion, Date fechacreacion, String usuariocreacion, boolean activo) {
        this.idatencion = idatencion;
        this.fechacreacion = fechacreacion;
        this.usuariocreacion = usuariocreacion;
        this.activo = activo;
    }

    public Integer getIdatencion() {
        return idatencion;
    }

    public void setIdatencion(Integer idatencion) {
        this.idatencion = idatencion;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
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

    public Cliente getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Cliente idcliente) {
        this.idcliente = idcliente;
    }

    public Ruta getIdruta() {
        return idruta;
    }

    public void setIdruta(Ruta idruta) {
        this.idruta = idruta;
    }

    public Tipoatencion getIdtipoatencion() {
        return idtipoatencion;
    }

    public void setIdtipoatencion(Tipoatencion idtipoatencion) {
        this.idtipoatencion = idtipoatencion;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idatencion != null ? idatencion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Atencion)) {
            return false;
        }
        Atencion other = (Atencion) object;
        if ((this.idatencion == null && other.idatencion != null) || (this.idatencion != null && !this.idatencion.equals(other.idatencion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tele.costa.api.entity.Atencion[ idatencion=" + idatencion + " ]";
    }

    @XmlTransient
    public List<Detalleatencion> getDetalleatencionList() {
        return detalleatencionList;
    }

    public void setDetalleatencionList(List<Detalleatencion> detalleatencionList) {
        this.detalleatencionList = detalleatencionList;
    }

}
