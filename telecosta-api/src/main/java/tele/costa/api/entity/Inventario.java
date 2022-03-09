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
@Table(name = "inventario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inventario.findAll", query = "SELECT i FROM Inventario i"),
    @NamedQuery(name = "Inventario.findByIdinventario", query = "SELECT i FROM Inventario i WHERE i.idinventario = :idinventario"),
    @NamedQuery(name = "Inventario.findBySaldoinicial", query = "SELECT i FROM Inventario i WHERE i.saldoinicial = :saldoinicial"),
    @NamedQuery(name = "Inventario.findByEntradas", query = "SELECT i FROM Inventario i WHERE i.entradas = :entradas"),
    @NamedQuery(name = "Inventario.findBySalidas", query = "SELECT i FROM Inventario i WHERE i.salidas = :salidas"),
    @NamedQuery(name = "Inventario.findByExistencia", query = "SELECT i FROM Inventario i WHERE i.existencia = :existencia"),
    @NamedQuery(name = "Inventario.findByPrecio", query = "SELECT i FROM Inventario i WHERE i.precio = :precio"),
    @NamedQuery(name = "Inventario.findByTotal", query = "SELECT i FROM Inventario i WHERE i.total = :total"),
    @NamedQuery(name = "Inventario.findByProveedor", query = "SELECT i FROM Inventario i WHERE i.proveedor = :proveedor"),
    @NamedQuery(name = "Inventario.findByNodocumento", query = "SELECT i FROM Inventario i WHERE i.nodocumento = :nodocumento"),
    @NamedQuery(name = "Inventario.findByFecha", query = "SELECT i FROM Inventario i WHERE i.fecha = :fecha"),
    @NamedQuery(name = "Inventario.findByResponsable", query = "SELECT i FROM Inventario i WHERE i.responsable = :responsable"),
    @NamedQuery(name = "Inventario.findByObservacion", query = "SELECT i FROM Inventario i WHERE i.observacion = :observacion"),
    @NamedQuery(name = "Inventario.findByUsuariocreacion", query = "SELECT i FROM Inventario i WHERE i.usuariocreacion = :usuariocreacion"),
    @NamedQuery(name = "Inventario.findByFechacreacion", query = "SELECT i FROM Inventario i WHERE i.fechacreacion = :fechacreacion"),
    @NamedQuery(name = "Inventario.findByUsuariomodificacion", query = "SELECT i FROM Inventario i WHERE i.usuariomodificacion = :usuariomodificacion"),
    @NamedQuery(name = "Inventario.findByFechamodificacion", query = "SELECT i FROM Inventario i WHERE i.fechamodificacion = :fechamodificacion"),
    @NamedQuery(name = "Inventario.findByActivo", query = "SELECT i FROM Inventario i WHERE i.activo = :activo")})
public class Inventario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idinventario")
    private Integer idinventario;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "saldoinicial")
    private int saldoinicial;
    
    @Column(name = "entradas")
    private Integer entradas;
    
    @Column(name = "salidas")
    private Integer salidas;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "existencia")
    private int existencia;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "precio")
    private float precio;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "total")
    private float total;
    
    @Size(max = 500)
    @Column(name = "proveedor")
    private String proveedor;
    
    @Size(max = 200)
    @Column(name = "nodocumento")
    private String nodocumento;
    
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    
    @Size(max = 250)
    @Column(name = "responsable")
    private String responsable;
    
    @Size(max = 1000)
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
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Agencia idagencia;
    
    @JoinColumn(name = "idagenciaenvio", referencedColumnName = "idagencia")
    @ManyToOne(fetch = FetchType.LAZY)
    private Agencia idagenciaenvio;
    
    @JoinColumn(name = "idinsumo", referencedColumnName = "idinsumo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Insumos idinsumo;
    
    @JoinColumn(name = "idruta", referencedColumnName = "idruta")
    @ManyToOne(fetch = FetchType.LAZY)
    private Ruta idruta;
    
    @JoinColumn(name = "idsectorpago", referencedColumnName = "idsectorpago")
    @ManyToOne(fetch = FetchType.LAZY)
    private Sectorpago idsectorpago;

    public Inventario() {
    }

    public Inventario(Integer idinventario) {
        this.idinventario = idinventario;
    }

    public Inventario(Integer idinventario, int saldoinicial, int existencia, float precio, float total, String usuariocreacion, Date fechacreacion, boolean activo) {
        this.idinventario = idinventario;
        this.saldoinicial = saldoinicial;
        this.existencia = existencia;
        this.precio = precio;
        this.total = total;
        this.usuariocreacion = usuariocreacion;
        this.fechacreacion = fechacreacion;
        this.activo = activo;
    }

    public Integer getIdinventario() {
        return idinventario;
    }

    public void setIdinventario(Integer idinventario) {
        this.idinventario = idinventario;
    }

    public int getSaldoinicial() {
        return saldoinicial;
    }

    public void setSaldoinicial(int saldoinicial) {
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

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
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

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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

    public Agencia getIdagenciaenvio() {
        return idagenciaenvio;
    }

    public void setIdagenciaenvio(Agencia idagenciaenvio) {
        this.idagenciaenvio = idagenciaenvio;
    }

    public Insumos getIdinsumo() {
        return idinsumo;
    }

    public void setIdinsumo(Insumos idinsumo) {
        this.idinsumo = idinsumo;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idinventario != null ? idinventario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inventario)) {
            return false;
        }
        Inventario other = (Inventario) object;
        if ((this.idinventario == null && other.idinventario != null) || (this.idinventario != null && !this.idinventario.equals(other.idinventario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tele.costa.api.entity.Inventario[ idinventario=" + idinventario + " ]";
    }
    
}
