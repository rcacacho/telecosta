/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tele.costa.api.entity;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author elfo_
 */
@Entity
@Table(name = "pago", catalog = "telecosta", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pago.findAll", query = "SELECT p FROM Pago p"),
    @NamedQuery(name = "Pago.findByIdpago", query = "SELECT p FROM Pago p WHERE p.idpago = :idpago"),
    @NamedQuery(name = "Pago.findByMes", query = "SELECT p FROM Pago p WHERE p.mes = :mes"),
    @NamedQuery(name = "Pago.findByAnio", query = "SELECT p FROM Pago p WHERE p.anio = :anio"),
    @NamedQuery(name = "Pago.findByFechacreacion", query = "SELECT p FROM Pago p WHERE p.fechacreacion = :fechacreacion"),
    @NamedQuery(name = "Pago.findByFechapago", query = "SELECT p FROM Pago p WHERE p.fechapago = :fechapago"),
    @NamedQuery(name = "Pago.findByUsuariocreacion", query = "SELECT p FROM Pago p WHERE p.usuariocreacion = :usuariocreacion"),
    @NamedQuery(name = "Pago.findByFechamodificacion", query = "SELECT p FROM Pago p WHERE p.fechamodificacion = :fechamodificacion"),
    @NamedQuery(name = "Pago.findByUsuariomodificacion", query = "SELECT p FROM Pago p WHERE p.usuariomodificacion = :usuariomodificacion"),
    @NamedQuery(name = "Pago.findByActivo", query = "SELECT p FROM Pago p WHERE p.activo = :activo")})
public class Pago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpago", nullable = false)
    private Integer idpago;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "mes", nullable = false, length = 100)
    private String mes;
    @Basic(optional = false)
    @NotNull
    @Column(name = "anio", nullable = false)
    private int anio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechacreacion", nullable = false)
    private int fechacreacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechapago", nullable = false)
    private int fechapago;
    @Basic(optional = false)
    @NotNull
    @Column(name = "usuariocreacion", nullable = false)
    private int usuariocreacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechamodificacion", nullable = false)
    private int fechamodificacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "usuariomodificacion", nullable = false)
    private int usuariomodificacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "activo", nullable = false)
    private int activo;
    @JoinColumn(name = "idcliente", referencedColumnName = "idcliente", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Cliente idcliente;
    @JoinColumn(name = "idtipopago", referencedColumnName = "idtipopago", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Tipopago idtipopago;

    public Pago() {
    }

    public Pago(Integer idpago) {
        this.idpago = idpago;
    }

    public Pago(Integer idpago, String mes, int anio, int fechacreacion, int fechapago, int usuariocreacion, int fechamodificacion, int usuariomodificacion, int activo) {
        this.idpago = idpago;
        this.mes = mes;
        this.anio = anio;
        this.fechacreacion = fechacreacion;
        this.fechapago = fechapago;
        this.usuariocreacion = usuariocreacion;
        this.fechamodificacion = fechamodificacion;
        this.usuariomodificacion = usuariomodificacion;
        this.activo = activo;
    }

    public Integer getIdpago() {
        return idpago;
    }

    public void setIdpago(Integer idpago) {
        this.idpago = idpago;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(int fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public int getFechapago() {
        return fechapago;
    }

    public void setFechapago(int fechapago) {
        this.fechapago = fechapago;
    }

    public int getUsuariocreacion() {
        return usuariocreacion;
    }

    public void setUsuariocreacion(int usuariocreacion) {
        this.usuariocreacion = usuariocreacion;
    }

    public int getFechamodificacion() {
        return fechamodificacion;
    }

    public void setFechamodificacion(int fechamodificacion) {
        this.fechamodificacion = fechamodificacion;
    }

    public int getUsuariomodificacion() {
        return usuariomodificacion;
    }

    public void setUsuariomodificacion(int usuariomodificacion) {
        this.usuariomodificacion = usuariomodificacion;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public Cliente getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Cliente idcliente) {
        this.idcliente = idcliente;
    }

    public Tipopago getIdtipopago() {
        return idtipopago;
    }

    public void setIdtipopago(Tipopago idtipopago) {
        this.idtipopago = idtipopago;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpago != null ? idpago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pago)) {
            return false;
        }
        Pago other = (Pago) object;
        if ((this.idpago == null && other.idpago != null) || (this.idpago != null && !this.idpago.equals(other.idpago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tele.costa.api.entity.Pago[ idpago=" + idpago + " ]";
    }
    
}
