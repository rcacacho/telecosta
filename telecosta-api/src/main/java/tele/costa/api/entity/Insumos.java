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
@Table(name = "insumos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Insumos.findAll", query = "SELECT i FROM Insumos i"),
    @NamedQuery(name = "Insumos.findByIdinsumo", query = "SELECT i FROM Insumos i WHERE i.idinsumo = :idinsumo"),
    @NamedQuery(name = "Insumos.findByCodigo", query = "SELECT i FROM Insumos i WHERE i.codigo = :codigo"),
    @NamedQuery(name = "Insumos.findByDescripcion", query = "SELECT i FROM Insumos i WHERE i.descripcion = :descripcion"),
    @NamedQuery(name = "Insumos.findByPrecio", query = "SELECT i FROM Insumos i WHERE i.precio = :precio"),
    @NamedQuery(name = "Insumos.findByProveedor", query = "SELECT i FROM Insumos i WHERE i.proveedor = :proveedor"),
    @NamedQuery(name = "Insumos.findByNodocumento", query = "SELECT i FROM Insumos i WHERE i.nodocumento = :nodocumento"),
    @NamedQuery(name = "Insumos.findByFecha", query = "SELECT i FROM Insumos i WHERE i.fecha = :fecha"),
    @NamedQuery(name = "Insumos.findByUsuariocreacion", query = "SELECT i FROM Insumos i WHERE i.usuariocreacion = :usuariocreacion"),
    @NamedQuery(name = "Insumos.findByFechacreacion", query = "SELECT i FROM Insumos i WHERE i.fechacreacion = :fechacreacion"),
    @NamedQuery(name = "Insumos.findByUsuariomodificacion", query = "SELECT i FROM Insumos i WHERE i.usuariomodificacion = :usuariomodificacion"),
    @NamedQuery(name = "Insumos.findByFechamodificacion", query = "SELECT i FROM Insumos i WHERE i.fechamodificacion = :fechamodificacion"),
    @NamedQuery(name = "Insumos.findByActivo", query = "SELECT i FROM Insumos i WHERE i.activo = :activo")})
public class Insumos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idinsumo")
    private Integer idinsumo;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "codigo")
    private String codigo;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "saldoinicial")
    private Integer saldoinicial;

    @Column(name = "entradas")
    private Integer entradas;

    @Column(name = "salidas")
    private Integer salidas;

    @Column(name = "existencia")
    private Integer existencia;

    @Column(name = "precio")
    private Float precio;

    @Column(name = "total")
    private Float total;

    @Size(max = 500)
    @Column(name = "proveedor")
    private String proveedor;

    @Size(max = 200)
    @Column(name = "nodocumento")
    private String nodocumento;

    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @Size(min = 1, max = 1000)
    @Column(name = "observacion")
    private String observacion;

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

    @Basic(optional = false)
    @NotNull
    @Column(name = "activo")
    private boolean activo;

    @JoinColumn(name = "idagencia", referencedColumnName = "idagencia")
    @ManyToOne(fetch = FetchType.LAZY)
    private Agencia idagencia;

    @JoinColumn(name = "idagenciaenvio", referencedColumnName = "idagencia")
    @ManyToOne(fetch = FetchType.LAZY)
    private Agencia idagenciaenvio;

    @JoinColumn(name = "idruta", referencedColumnName = "idruta")
    @ManyToOne(fetch = FetchType.LAZY)
    private Ruta idruta;

    @JoinColumn(name = "idsectorpago", referencedColumnName = "idsectorpago")
    @ManyToOne(fetch = FetchType.LAZY)
    private Sectorpago idsectorpago;

    public Insumos() {
    }

    public Insumos(Integer idinsumo) {
        this.idinsumo = idinsumo;
    }

    public Insumos(Integer idinsumo, String codigo, String descripcion, Date fecha, String usuariocreacion, Date fechacreacion, boolean activo) {
        this.idinsumo = idinsumo;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.usuariocreacion = usuariocreacion;
        this.fechacreacion = fechacreacion;
        this.activo = activo;
    }

    public Integer getIdinsumo() {
        return idinsumo;
    }

    public void setIdinsumo(Integer idinsumo) {
        this.idinsumo = idinsumo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getSaldoinicial() {
        return saldoinicial;
    }

    public void setSaldoinicial(Integer saldoinicial) {
        this.saldoinicial = saldoinicial;
    }

    public Integer getEntradas() {
        return entradas;
    }

    public void setEntradas(Integer entradas) {
        this.entradas = entradas;
    }

    public Integer getSalidas() {
        return salidas;
    }

    public void setSalidas(Integer salidas) {
        this.salidas = salidas;
    }

    public Integer getExistencia() {
        return existencia;
    }

    public void setExistencia(Integer existencia) {
        this.existencia = existencia;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getNodocumento() {
        return nodocumento;
    }

    public void setNodocumento(String nodocumento) {
        this.nodocumento = nodocumento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public Agencia getIdagencia() {
        return idagencia;
    }

    public void setIdagencia(Agencia idagencia) {
        this.idagencia = idagencia;
    }

    public Ruta getIdruta() {
        return idruta;
    }

    public void setIdruta(Ruta idruta) {
        this.idruta = idruta;
    }

    public Sectorpago getIdsectorpago() {
        return idsectorpago;
    }

    public void setIdsectorpago(Sectorpago idsectorpago) {
        this.idsectorpago = idsectorpago;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Agencia getIdagenciaenvio() {
        return idagenciaenvio;
    }

    public void setIdagenciaenvio(Agencia idagenciaenvio) {
        this.idagenciaenvio = idagenciaenvio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idinsumo != null ? idinsumo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Insumos)) {
            return false;
        }
        Insumos other = (Insumos) object;
        if ((this.idinsumo == null && other.idinsumo != null) || (this.idinsumo != null && !this.idinsumo.equals(other.idinsumo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tele.costa.api.entity.Insumos[ idinsumo=" + idinsumo + " ]";
    }

}
