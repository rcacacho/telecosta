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
@Table(name = "tipodocumentocompra", catalog = "telecosta", schema = "")
@NamedQueries({
    @NamedQuery(name = "Tipodocumentocompra.findAll", query = "SELECT t FROM Tipodocumentocompra t"),
    @NamedQuery(name = "Tipodocumentocompra.findByIdtipodocumentocompra", query = "SELECT t FROM Tipodocumentocompra t WHERE t.idtipodocumentocompra = :idtipodocumentocompra"),
    @NamedQuery(name = "Tipodocumentocompra.findByDocumento", query = "SELECT t FROM Tipodocumentocompra t WHERE t.documento = :documento"),
    @NamedQuery(name = "Tipodocumentocompra.findByDescripcion", query = "SELECT t FROM Tipodocumentocompra t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "Tipodocumentocompra.findByFechacreacion", query = "SELECT t FROM Tipodocumentocompra t WHERE t.fechacreacion = :fechacreacion"),
    @NamedQuery(name = "Tipodocumentocompra.findByUsuariocreacion", query = "SELECT t FROM Tipodocumentocompra t WHERE t.usuariocreacion = :usuariocreacion"),
    @NamedQuery(name = "Tipodocumentocompra.findByFechamodificacion", query = "SELECT t FROM Tipodocumentocompra t WHERE t.fechamodificacion = :fechamodificacion"),
    @NamedQuery(name = "Tipodocumentocompra.findByUsuariomodificacion", query = "SELECT t FROM Tipodocumentocompra t WHERE t.usuariomodificacion = :usuariomodificacion"),
    @NamedQuery(name = "Tipodocumentocompra.findByActivo", query = "SELECT t FROM Tipodocumentocompra t WHERE t.activo = :activo")})
public class Tipodocumentocompra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtipodocumentocompra", nullable = false)
    private Integer idtipodocumentocompra;
    @Basic(optional = false)
    @Column(name = "documento", nullable = false, length = 500)
    private String documento;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idtipodocumentocompra", fetch = FetchType.LAZY)
    private List<Compra> compraList;

    public Tipodocumentocompra() {
    }

    public Tipodocumentocompra(Integer idtipodocumentocompra) {
        this.idtipodocumentocompra = idtipodocumentocompra;
    }

    public Tipodocumentocompra(Integer idtipodocumentocompra, String documento, Date fechacreacion, String usuariocreacion, int activo) {
        this.idtipodocumentocompra = idtipodocumentocompra;
        this.documento = documento;
        this.fechacreacion = fechacreacion;
        this.usuariocreacion = usuariocreacion;
        this.activo = activo;
    }

    public Integer getIdtipodocumentocompra() {
        return idtipodocumentocompra;
    }

    public void setIdtipodocumentocompra(Integer idtipodocumentocompra) {
        this.idtipodocumentocompra = idtipodocumentocompra;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
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
        hash += (idtipodocumentocompra != null ? idtipodocumentocompra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipodocumentocompra)) {
            return false;
        }
        Tipodocumentocompra other = (Tipodocumentocompra) object;
        if ((this.idtipodocumentocompra == null && other.idtipodocumentocompra != null) || (this.idtipodocumentocompra != null && !this.idtipodocumentocompra.equals(other.idtipodocumentocompra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tele.costa.nuevo.api.entity.Tipodocumentocompra[ idtipodocumentocompra=" + idtipodocumentocompra + " ]";
    }
    
}
