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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "tipocompra", catalog = "telecosta", schema = "")
@NamedQueries({
    @NamedQuery(name = "Tipocompra.findAll", query = "SELECT t FROM Tipocompra t"),
    @NamedQuery(name = "Tipocompra.findByIdtipocompra", query = "SELECT t FROM Tipocompra t WHERE t.idtipocompra = :idtipocompra"),
    @NamedQuery(name = "Tipocompra.findByTipo", query = "SELECT t FROM Tipocompra t WHERE t.tipo = :tipo"),
    @NamedQuery(name = "Tipocompra.findByDescripcion", query = "SELECT t FROM Tipocompra t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "Tipocompra.findByFechacreacion", query = "SELECT t FROM Tipocompra t WHERE t.fechacreacion = :fechacreacion"),
    @NamedQuery(name = "Tipocompra.findByUsuariocreacion", query = "SELECT t FROM Tipocompra t WHERE t.usuariocreacion = :usuariocreacion"),
    @NamedQuery(name = "Tipocompra.findByFechamodificacion", query = "SELECT t FROM Tipocompra t WHERE t.fechamodificacion = :fechamodificacion"),
    @NamedQuery(name = "Tipocompra.findByUsuariomodificacion", query = "SELECT t FROM Tipocompra t WHERE t.usuariomodificacion = :usuariomodificacion"),
    @NamedQuery(name = "Tipocompra.findByActivo", query = "SELECT t FROM Tipocompra t WHERE t.activo = :activo")})
public class Tipocompra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtipocompra", nullable = false)
    private Integer idtipocompra;
    @Basic(optional = false)
    @Column(name = "tipo", nullable = false, length = 500)
    private String tipo;
    @Column(name = "descripcion", length = 500)
    private String descripcion;
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
    @Basic(optional = false)
    @Column(name = "activo", nullable = false)
    private int activo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idtipocompra", fetch = FetchType.LAZY)
    private List<Compra> compraList;

    public Tipocompra() {
    }

    public Tipocompra(Integer idtipocompra) {
        this.idtipocompra = idtipocompra;
    }

    public Tipocompra(Integer idtipocompra, String tipo, Date fechacreacion, String usuariocreacion, int activo) {
        this.idtipocompra = idtipocompra;
        this.tipo = tipo;
        this.fechacreacion = fechacreacion;
        this.usuariocreacion = usuariocreacion;
        this.activo = activo;
    }

    public Integer getIdtipocompra() {
        return idtipocompra;
    }

    public void setIdtipocompra(Integer idtipocompra) {
        this.idtipocompra = idtipocompra;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public List<Compra> getCompraList() {
        return compraList;
    }

    public void setCompraList(List<Compra> compraList) {
        this.compraList = compraList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipocompra != null ? idtipocompra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipocompra)) {
            return false;
        }
        Tipocompra other = (Tipocompra) object;
        if ((this.idtipocompra == null && other.idtipocompra != null) || (this.idtipocompra != null && !this.idtipocompra.equals(other.idtipocompra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tele.costa.nuevo.api.entity.Tipocompra[ idtipocompra=" + idtipocompra + " ]";
    }
    
}
