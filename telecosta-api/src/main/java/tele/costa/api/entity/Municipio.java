package tele.costa.api.entity;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author elfo_
 */
@Entity
@Table(catalog = "telecosta", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Municipio.findAll", query = "SELECT m FROM Municipio m"),
    @NamedQuery(name = "Municipio.findByIdmunicipio", query = "SELECT m FROM Municipio m WHERE m.idmunicipio = :idmunicipio"),
    @NamedQuery(name = "Municipio.findByMunicipio", query = "SELECT m FROM Municipio m WHERE m.municipio = :municipio"),
    @NamedQuery(name = "Municipio.findByActivo", query = "SELECT m FROM Municipio m WHERE m.activo = :activo")})
public class Municipio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMunicipio;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(nullable = false, length = 500)
    private String municipio;
    
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int activo;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idmunicipio", fetch = FetchType.LAZY)
    private List<Cliente> clienteList;
    
    @JoinColumn(name = "iddepartamento", referencedColumnName = "iddepartamento", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Departamento idDepartamento;

    public Municipio() {
    }

    public Municipio(Integer idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public Municipio(Integer idMunicipio, String municipio, int activo) {
        this.idMunicipio = idMunicipio;
        this.municipio = municipio;
        this.activo = activo;
    }

    public Integer getIdmunicipio() {
        return idMunicipio;
    }

    public void setIdmunicipio(Integer idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    @XmlTransient
    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    public Departamento getIddepartamento() {
        return idDepartamento;
    }

    public void setIddepartamento(Departamento idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMunicipio != null ? idMunicipio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Municipio)) {
            return false;
        }
        Municipio other = (Municipio) object;
        if ((this.idMunicipio == null && other.idMunicipio != null) || (this.idMunicipio != null && !this.idMunicipio.equals(other.idMunicipio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tele.costa.api.entity.Municipio[ idmunicipio=" + idMunicipio + " ]";
    }

}
