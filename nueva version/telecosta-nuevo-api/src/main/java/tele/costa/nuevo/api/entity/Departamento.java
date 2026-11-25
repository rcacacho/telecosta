/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tele.costa.nuevo.api.entity;

import java.io.Serializable;
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

/**
 *
 * @author rcacacho
 */
@Entity
@Table(name = "departamento", catalog = "telecosta", schema = "")
@NamedQueries({
    @NamedQuery(name = "Departamento.findAll", query = "SELECT d FROM Departamento d"),
    @NamedQuery(name = "Departamento.findByIddepartamento", query = "SELECT d FROM Departamento d WHERE d.iddepartamento = :iddepartamento"),
    @NamedQuery(name = "Departamento.findByNombre", query = "SELECT d FROM Departamento d WHERE d.nombre = :nombre"),
    @NamedQuery(name = "Departamento.findByActivo", query = "SELECT d FROM Departamento d WHERE d.activo = :activo")})
public class Departamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "iddepartamento", nullable = false)
    private Integer iddepartamento;
    @Basic(optional = false)
    @Column(name = "nombre", nullable = false, length = 500)
    private String nombre;
    @Basic(optional = false)
    @Column(name = "activo", nullable = false)
    private int activo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iddepartamento", fetch = FetchType.LAZY)
    private List<Municipio> municipioList;

    public Departamento() {
    }

    public Departamento(Integer iddepartamento) {
        this.iddepartamento = iddepartamento;
    }

    public Departamento(Integer iddepartamento, String nombre, int activo) {
        this.iddepartamento = iddepartamento;
        this.nombre = nombre;
        this.activo = activo;
    }

    public Integer getIddepartamento() {
        return iddepartamento;
    }

    public void setIddepartamento(Integer iddepartamento) {
        this.iddepartamento = iddepartamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public List<Municipio> getMunicipioList() {
        return municipioList;
    }

    public void setMunicipioList(List<Municipio> municipioList) {
        this.municipioList = municipioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddepartamento != null ? iddepartamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Departamento)) {
            return false;
        }
        Departamento other = (Departamento) object;
        if ((this.iddepartamento == null && other.iddepartamento != null) || (this.iddepartamento != null && !this.iddepartamento.equals(other.iddepartamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tele.costa.nuevo.api.entity.Departamento[ iddepartamento=" + iddepartamento + " ]";
    }
    
}
