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
@Table(name = "bitacorainventario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bitacorainventario.findAll", query = "SELECT b FROM Bitacorainventario b"),
    @NamedQuery(name = "Bitacorainventario.findByIdbitacorainventario", query = "SELECT b FROM Bitacorainventario b WHERE b.idbitacorainventario = :idbitacorainventario"),
    @NamedQuery(name = "Bitacorainventario.findByCodigo", query = "SELECT b FROM Bitacorainventario b WHERE b.codigo = :codigo"),
    @NamedQuery(name = "Bitacorainventario.findByFecha", query = "SELECT b FROM Bitacorainventario b WHERE b.fecha = :fecha"),
    @NamedQuery(name = "Bitacorainventario.findByDescripcion", query = "SELECT b FROM Bitacorainventario b WHERE b.descripcion = :descripcion"),
    @NamedQuery(name = "Bitacorainventario.findByCantidad", query = "SELECT b FROM Bitacorainventario b WHERE b.cantidad = :cantidad"),
    @NamedQuery(name = "Bitacorainventario.findByPreciounitario", query = "SELECT b FROM Bitacorainventario b WHERE b.preciounitario = :preciounitario"),
    @NamedQuery(name = "Bitacorainventario.findByTotal", query = "SELECT b FROM Bitacorainventario b WHERE b.total = :total"),
    @NamedQuery(name = "Bitacorainventario.findByProveedor", query = "SELECT b FROM Bitacorainventario b WHERE b.proveedor = :proveedor"),
    @NamedQuery(name = "Bitacorainventario.findByDocumento", query = "SELECT b FROM Bitacorainventario b WHERE b.documento = :documento"),
    @NamedQuery(name = "Bitacorainventario.findByResponsable", query = "SELECT b FROM Bitacorainventario b WHERE b.responsable = :responsable"),
    @NamedQuery(name = "Bitacorainventario.findBySector", query = "SELECT b FROM Bitacorainventario b WHERE b.sector = :sector"),
    @NamedQuery(name = "Bitacorainventario.findByDestino", query = "SELECT b FROM Bitacorainventario b WHERE b.destino = :destino"),
    @NamedQuery(name = "Bitacorainventario.findByFechacreacion", query = "SELECT b FROM Bitacorainventario b WHERE b.fechacreacion = :fechacreacion"),
    @NamedQuery(name = "Bitacorainventario.findByUsuariocreacion", query = "SELECT b FROM Bitacorainventario b WHERE b.usuariocreacion = :usuariocreacion"),
    @NamedQuery(name = "Bitacorainventario.findByActivo", query = "SELECT b FROM Bitacorainventario b WHERE b.activo = :activo")})
public class Bitacorainventario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idbitacorainventario")
    private Integer idbitacorainventario;
    
    @Size(max = 200)
    @Column(name = "codigo")
    private String codigo;
    
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    
    @Size(max = 500)
    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "cantidad")
    private Integer cantidad;
    
    @Column(name = "preciounitario")
    private Float preciounitario;
    
    @Column(name = "total")
    private Float total;
    
    @Size(max = 500)
    @Column(name = "proveedor")
    private String proveedor;
    
    @Size(max = 200)
    @Column(name = "documento")
    private String documento;
    
    @Size(max = 250)
    @Column(name = "responsable")
    private String responsable;
    
    @Size(max = 500)
    @Column(name = "sector")
    private String sector;
    
    @Size(max = 250)
    @Column(name = "destino")
    private String destino;
    
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
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "activo")
    private boolean activo;
    
    @JoinColumn(name = "idagencia", referencedColumnName = "idagencia")
    @ManyToOne(fetch = FetchType.LAZY)
    private Agencia idagencia;
    
    @JoinColumn(name = "idtipocarga", referencedColumnName = "idtipocarga")
    @ManyToOne(fetch = FetchType.LAZY)
    private Tipocarga idtipocarga;

    public Bitacorainventario() {
    }

    public Bitacorainventario(Integer idbitacorainventario) {
        this.idbitacorainventario = idbitacorainventario;
    }

    public Bitacorainventario(Integer idbitacorainventario, Date fechacreacion, String usuariocreacion, boolean activo) {
        this.idbitacorainventario = idbitacorainventario;
        this.fechacreacion = fechacreacion;
        this.usuariocreacion = usuariocreacion;
        this.activo = activo;
    }

    public Integer getIdbitacorainventario() {
        return idbitacorainventario;
    }

    public void setIdbitacorainventario(Integer idbitacorainventario) {
        this.idbitacorainventario = idbitacorainventario;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Float getPreciounitario() {
        return preciounitario;
    }

    public void setPreciounitario(Float preciounitario) {
        this.preciounitario = preciounitario;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
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

    public Tipocarga getIdtipocarga() {
        return idtipocarga;
    }

    public void setIdtipocarga(Tipocarga idtipocarga) {
        this.idtipocarga = idtipocarga;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idbitacorainventario != null ? idbitacorainventario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bitacorainventario)) {
            return false;
        }
        Bitacorainventario other = (Bitacorainventario) object;
        if ((this.idbitacorainventario == null && other.idbitacorainventario != null) || (this.idbitacorainventario != null && !this.idbitacorainventario.equals(other.idbitacorainventario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tele.costa.api.entity.Bitacorainventario[ idbitacorainventario=" + idbitacorainventario + " ]";
    }
    
}
