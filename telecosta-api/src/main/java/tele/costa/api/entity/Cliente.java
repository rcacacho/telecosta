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
@Table(name = "cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findByIdcliente", query = "SELECT c FROM Cliente c WHERE c.idcliente = :idcliente"),
    @NamedQuery(name = "Cliente.findByCui", query = "SELECT c FROM Cliente c WHERE c.cui = :cui"),
    @NamedQuery(name = "Cliente.findByNit", query = "SELECT c FROM Cliente c WHERE c.nit = :nit"),
    @NamedQuery(name = "Cliente.findByNombres", query = "SELECT c FROM Cliente c WHERE c.nombres = :nombres"),
    @NamedQuery(name = "Cliente.findByDireccion", query = "SELECT c FROM Cliente c WHERE c.direccion = :direccion"),
    @NamedQuery(name = "Cliente.findBySector", query = "SELECT c FROM Cliente c WHERE c.sector = :sector"),
    @NamedQuery(name = "Cliente.findByObservacion", query = "SELECT c FROM Cliente c WHERE c.observacion = :observacion"),
    @NamedQuery(name = "Cliente.findByTelefono", query = "SELECT c FROM Cliente c WHERE c.telefono = :telefono"),
    @NamedQuery(name = "Cliente.findByFechainicioservicio", query = "SELECT c FROM Cliente c WHERE c.fechainicioservicio = :fechainicioservicio"),
    @NamedQuery(name = "Cliente.findByUsuariocreacion", query = "SELECT c FROM Cliente c WHERE c.usuariocreacion = :usuariocreacion"),
    @NamedQuery(name = "Cliente.findByFechacreacion", query = "SELECT c FROM Cliente c WHERE c.fechacreacion = :fechacreacion"),
    @NamedQuery(name = "Cliente.findByUsuariomodificacion", query = "SELECT c FROM Cliente c WHERE c.usuariomodificacion = :usuariomodificacion"),
    @NamedQuery(name = "Cliente.findByFechamodificacion", query = "SELECT c FROM Cliente c WHERE c.fechamodificacion = :fechamodificacion"),
    @NamedQuery(name = "Cliente.findByActivo", query = "SELECT c FROM Cliente c WHERE c.activo = :activo")})
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcliente")
    private Integer idcliente;

    @Size(max = 20)
    @Column(name = "cui")
    private String cui;

    @Size(max = 250)
    @Column(name = "codigo")
    private String codigo;

    @Size(max = 50)
    @Column(name = "nit")
    private String nit;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "nombres")
    private String nombres;

    @Size(max = 2000)
    @Column(name = "direccion")
    private String direccion;

    @Size(max = 400)
    @Column(name = "sector")
    private String sector;

    @Size(max = 500)
    @Column(name = "observacion")
    private String observacion;

    @Size(max = 100)
    @Column(name = "telefono")
    private String telefono;

    @Column(name = "suspendido")
    private boolean suspendido;

    @Size(max = 100)
    @Column(name = "fechainicioservicio")
    private String fechainicioservicio;

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

    @Size(max = 1500)
    @Column(name = "motivoeliminacion")
    private String motivoeliminacion;

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

    @JoinColumn(name = "idconfiguracionpago", referencedColumnName = "idconfiguracionpago")
    @ManyToOne(fetch = FetchType.LAZY)
    private Configuracionpago idconfiguracionpago;

    @JoinColumn(name = "idmunicipio", referencedColumnName = "idmunicipio")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Municipio idmunicipio;

    @JoinColumn(name = "idsector", referencedColumnName = "idsector")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Sector idSector;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idcliente", fetch = FetchType.LAZY)
    private List<Pago> pagoList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idcliente", fetch = FetchType.LAZY)
    private List<Cobro> cobroList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idcliente", fetch = FetchType.LAZY)
    private List<Atencion> atencionList;

    public Cliente() {
    }

    public Cliente(Integer idcliente) {
        this.idcliente = idcliente;
    }

    public Cliente(Integer idcliente, String nombres, String usuariocreacion, Date fechacreacion, boolean activo) {
        this.idcliente = idcliente;
        this.nombres = nombres;
        this.usuariocreacion = usuariocreacion;
        this.fechacreacion = fechacreacion;
        this.activo = activo;
    }

    public Integer getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Integer idcliente) {
        this.idcliente = idcliente;
    }

    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getFechainicioservicio() {
        return fechainicioservicio;
    }

    public void setFechainicioservicio(String fechainicioservicio) {
        this.fechainicioservicio = fechainicioservicio;
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

    public Configuracionpago getIdconfiguracionpago() {
        return idconfiguracionpago;
    }

    public void setIdconfiguracionpago(Configuracionpago idconfiguracionpago) {
        this.idconfiguracionpago = idconfiguracionpago;
    }

    public Municipio getIdmunicipio() {
        return idmunicipio;
    }

    public void setIdmunicipio(Municipio idmunicipio) {
        this.idmunicipio = idmunicipio;
    }

    @XmlTransient
    public List<Pago> getPagoList() {
        return pagoList;
    }

    public void setPagoList(List<Pago> pagoList) {
        this.pagoList = pagoList;
    }

    public boolean getSuspendido() {
        return suspendido;
    }

    public void setSuspendido(boolean suspendido) {
        this.suspendido = suspendido;
    }

    public String getMotivoeliminacion() {
        return motivoeliminacion;
    }

    public void setMotivoeliminacion(String motivoeliminacion) {
        this.motivoeliminacion = motivoeliminacion;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcliente != null ? idcliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.idcliente == null && other.idcliente != null) || (this.idcliente != null && !this.idcliente.equals(other.idcliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tele.costa.api.entity.Cliente[ idcliente=" + idcliente + " ]";
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @XmlTransient
    public List<Cobro> getCobroList() {
        return cobroList;
    }

    public void setCobroList(List<Cobro> cobroList) {
        this.cobroList = cobroList;
    }

    @XmlTransient
    public List<Atencion> getAtencionList() {
        return atencionList;
    }

    public void setAtencionList(List<Atencion> atencionList) {
        this.atencionList = atencionList;
    }

    public Sector getIdSector() {
        return idSector;
    }

    public void setIdSector(Sector idSector) {
        this.idSector = idSector;
    }

}
