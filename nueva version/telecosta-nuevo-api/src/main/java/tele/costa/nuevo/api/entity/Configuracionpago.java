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
@Table(name = "configuracionpago", catalog = "telecosta", schema = "")
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
    @Basic(optional = false)
    @Column(name = "idconfiguracionpago", nullable = false)
    private Integer idconfiguracionpago;
    @Basic(optional = false)
    @Column(name = "valor", nullable = false)
    private int valor;
    @Column(name = "descripcion", length = 1000)
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "usuariocreacion", nullable = false, length = 50)
    private String usuariocreacion;
    @Basic(optional = false)
    @Column(name = "fechacreacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacreacion;
    @Column(name = "usuarioeliminacion", length = 50)
    private String usuarioeliminacion;
    @Column(name = "fechaeliminacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaeliminacion;
    @Basic(optional = false)
    @Column(name = "activo", nullable = false)
    private int activo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idconfiguracionpago", fetch = FetchType.LAZY)
    private List<Pago> pagoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idconfiguracionpago", fetch = FetchType.LAZY)
    private List<Cliente> clienteList;

    public Configuracionpago() {
    }

    public Configuracionpago(Integer idconfiguracionpago) {
        this.idconfiguracionpago = idconfiguracionpago;
    }

    public Configuracionpago(Integer idconfiguracionpago, int valor, String usuariocreacion, Date fechacreacion, int activo) {
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

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
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

    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
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
        return "tele.costa.nuevo.api.entity.Configuracionpago[ idconfiguracionpago=" + idconfiguracionpago + " ]";
    }
    
}
