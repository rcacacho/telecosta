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
@Table(name = "configuracionpago")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Configuracionpago.findAll", query = "SELECT c FROM Configuracionpago c"),
    @NamedQuery(name = "Configuracionpago.findByIdconfiguracionpago", query = "SELECT c FROM Configuracionpago c WHERE c.idconfiguracionpago = :idconfiguracionpago"),
    @NamedQuery(name = "Configuracionpago.findByValor", query = "SELECT c FROM Configuracionpago c WHERE c.valor = :valor"),
    @NamedQuery(name = "Configuracionpago.findByDescripcion", query = "SELECT c FROM Configuracionpago c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "Configuracionpago.findByUsuariocreacion", query = "SELECT c FROM Configuracionpago c WHERE c.usuariocreacion = :usuariocreacion"),
    @NamedQuery(name = "Configuracionpago.findByFechacreacion", query = "SELECT c FROM Configuracionpago c WHERE c.fechacreacion = :fechacreacion"),
    @NamedQuery(name = "Configuracionpago.findByUsuarioeliminacion", query = "SELECT c FROM Configuracionpago c WHERE c.usuarioeliminacion = :usuarioeliminacion"),
    @NamedQuery(name = "Configuracionpago.findByFechaeliminacion", query = "SELECT c FROM Configuracionpago c WHERE c.fechaeliminacion = :fechaeliminacion"),
    @NamedQuery(name = "Configuracionpago.findByActivo", query = "SELECT c FROM Configuracionpago c WHERE c.activo = :activo")})
public class Configuracionpago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idconfiguracionpago")
    private Integer idconfiguracionpago;

    @Size(max = 1000)
    @Column(name = "descripcion")
    private String descripcion;

    @Basic(optional = false)
    @NotNull
    @Column(name = "valor")
    private Integer valor;

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
    @Column(name = "usuarioeliminacion")
    private String usuarioeliminacion;

    @Column(name = "fechaeliminacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaeliminacion;

    @Basic(optional = false)
    @NotNull
    @Column(name = "activo")
    private boolean activo;

    @OneToMany(mappedBy = "idconfiguracionpago", fetch = FetchType.LAZY)
    private List<Cliente> clienteList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idconfiguracionpago", fetch = FetchType.LAZY)
    private List<Cobro> cobroList;

    public Configuracionpago() {
    }

    public Configuracionpago(Integer idconfiguracionpago) {
        this.idconfiguracionpago = idconfiguracionpago;
    }

    public Configuracionpago(Integer idconfiguracionpago, int valor, String usuariocreacion, Date fechacreacion, boolean activo) {
        this.idconfiguracionpago = idconfiguracionpago;
        this.valor = valor;
        this.usuariocreacion = usuariocreacion;
        this.fechacreacion = fechacreacion;
        this.activo = activo;
    }

    public Integer getIdconfiguracionpago() {
        return idconfiguracionpago;
    }

    public void setIdconfiguracionpago(Integer idconfiguracionpago) {
        this.idconfiguracionpago = idconfiguracionpago;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        hash += (idconfiguracionpago != null ? idconfiguracionpago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Configuracionpago)) {
            return false;
        }
        Configuracionpago other = (Configuracionpago) object;
        if ((this.idconfiguracionpago == null && other.idconfiguracionpago != null) || (this.idconfiguracionpago != null && !this.idconfiguracionpago.equals(other.idconfiguracionpago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tele.costa.api.entity.Configuracionpago[ idconfiguracionpago=" + idconfiguracionpago + " ]";
    }

    @XmlTransient
    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    @XmlTransient
    public List<Cobro> getCobroList() {
        return cobroList;
    }

    public void setCobroList(List<Cobro> cobroList) {
        this.cobroList = cobroList;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

}
