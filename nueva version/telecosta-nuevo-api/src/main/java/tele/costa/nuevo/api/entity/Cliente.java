/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tele.costa.nuevo.api.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author rcacacho
 */
@Entity
@Table(name = "cliente", catalog = "telecosta", schema = "")
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findByIdcliente", query = "SELECT c FROM Cliente c WHERE c.idcliente = :idcliente"),
    @NamedQuery(name = "Cliente.findByCui", query = "SELECT c FROM Cliente c WHERE c.cui = :cui"),
    @NamedQuery(name = "Cliente.findByNombres", query = "SELECT c FROM Cliente c WHERE c.nombres = :nombres"),
    @NamedQuery(name = "Cliente.findByApellidos", query = "SELECT c FROM Cliente c WHERE c.apellidos = :apellidos"),
    @NamedQuery(name = "Cliente.findByDireccion", query = "SELECT c FROM Cliente c WHERE c.direccion = :direccion"),
    @NamedQuery(name = "Cliente.findByTelefono", query = "SELECT c FROM Cliente c WHERE c.telefono = :telefono"),
    @NamedQuery(name = "Cliente.findByUsuariocreacion", query = "SELECT c FROM Cliente c WHERE c.usuariocreacion = :usuariocreacion"),
    @NamedQuery(name = "Cliente.findByFechacreacion", query = "SELECT c FROM Cliente c WHERE c.fechacreacion = :fechacreacion"),
    @NamedQuery(name = "Cliente.findByUsuariomodificacion", query = "SELECT c FROM Cliente c WHERE c.usuariomodificacion = :usuariomodificacion"),
    @NamedQuery(name = "Cliente.findByFechamodificacion", query = "SELECT c FROM Cliente c WHERE c.fechamodificacion = :fechamodificacion"),
    @NamedQuery(name = "Cliente.findByActivo", query = "SELECT c FROM Cliente c WHERE c.activo = :activo")})
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idcliente", nullable = false)
    private Integer idcliente;
    @Column(name = "cui", length = 20)
    private String cui;
    @Basic(optional = false)
    @Column(name = "nombres", nullable = false, length = 250)
    private String nombres;
    @Basic(optional = false)
    @Column(name = "apellidos", nullable = false, length = 250)
    private String apellidos;
    @Column(name = "direccion", length = 500)
    private String direccion;
    @Column(name = "telefono")
    private Integer telefono;
    @Basic(optional = false)
    @Column(name = "usuariocreacion", nullable = false, length = 50)
    private String usuariocreacion;
    @Basic(optional = false)
    @Column(name = "fechacreacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacreacion;
    @Column(name = "usuariomodificacion", length = 50)
    private String usuariomodificacion;
    @Column(name = "fechamodificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechamodificacion;
    @Basic(optional = false)
    @Column(name = "activo", nullable = false)
    private int activo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idcliente", fetch = FetchType.LAZY)
    private List<Pago> pagoList;
    @JoinColumn(name = "idconfiguracionpago", referencedColumnName = "idconfiguracionpago", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Configuracionpago idconfiguracionpago;
    @JoinColumn(name = "idmunicipio", referencedColumnName = "idmunicipio", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Municipio idmunicipio;

    public Cliente() {
    }

    public Cliente(Integer idcliente) {
        this.idcliente = idcliente;
    }

    public Cliente(Integer idcliente, String nombres, String apellidos, String usuariocreacion, Date fechacreacion, int activo) {
        this.idcliente = idcliente;
        this.nombres = nombres;
        this.apellidos = apellidos;
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

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
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

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public List<Pago> getPagoList() {
        return pagoList;
    }

    public void setPagoList(List<Pago> pagoList) {
        this.pagoList = pagoList;
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
        return "tele.costa.nuevo.api.entity.Cliente[ idcliente=" + idcliente + " ]";
    }
    
}
