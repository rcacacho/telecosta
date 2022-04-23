package tele.costa.api.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author elfo_
 */
@Entity
@SqlResultSetMapping(name = "ReporteAtencionDtoMapping",
        entities = {
            @EntityResult(entityClass = ReporteAtencionDto.class,
                    fields = {
                        @FieldResult(name = "idatencion", column = "idatencion"),
                        @FieldResult(name = "nombre", column = "nombre"),
                        @FieldResult(name = "direccion", column = "direccion"),
                        @FieldResult(name = "telefono", column = "telefono"),
                        @FieldResult(name = "motivo", column = "motivo"),
                        @FieldResult(name = "referencia", column = "referencia"),
                        @FieldResult(name = "materialutilizado", column = "materialutilizado"),
                        @FieldResult(name = "cantidad", column = "cantidad"),
                        @FieldResult(name = "observacion", column = "observacion"),
                        @FieldResult(name = "fechacreacion", column = "fechacreacion"),})})
@NamedNativeQueries({
    @NamedNativeQuery(
            name = "ReporteAtencionDto.atencionesFechas",
            query
            = "select a.idatencion, a.nombre, a.direccion, a.telefono, a.motivo, a.referencia, d.materialutilizado, a.cantidad, d.observacion, a.fechacreacion  \n"
            + "from atencion a\n"
            + "left join detalleatencion d on a.idatencion = d.idatencion and d.activo = 1\n"
            + "where  a.fechacreacion >= ? \n"
            + "and a.fechacreacion <= ? ",
            resultSetMapping = "ReporteAtencionDtoMapping"),

    @NamedNativeQuery(
            name = "ReporteAtencionDto.atencionesRuta",
            query
            = "select a.idatencion, a.nombre, a.direccion, a.telefono, a.motivo, a.referencia, d.materialutilizado, a.cantidad, d.observacion, a.fechacreacion  \n"
            + "from atencion a \n"
            + "left join detalleatencion d on a.idatencion = d.idatencion and d.activo = 1\n"
            + "join ruta r on a.idruta = r.idruta and r.activo = 1\n"
            + "where r.idruta = ? ",
            resultSetMapping = "ReporteAtencionDtoMapping")
})
@XmlRootElement
public class ReporteAtencionDto implements Serializable {

    @Id
    private Integer idatencion;
    private String nombre;
    private String direccion;
    private String telefono;
    private String motivo;
    private String referencia;
    private String materialutilizado;
    private String cantidad;
    private String observacion;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechacreacion;

    public Integer getIdatencion() {
        return idatencion;
    }

    public void setIdatencion(Integer idatencion) {
        this.idatencion = idatencion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getMaterialutilizado() {
        return materialutilizado;
    }

    public void setMaterialutilizado(String materialutilizado) {
        this.materialutilizado = materialutilizado;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(Date fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

}
