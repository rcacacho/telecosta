package tele.costa.api.dto;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rcacacho
 */
@Entity
@SqlResultSetMapping(name = "ReporteCobrosDtoMapping",
        entities = {
            @EntityResult(entityClass = ReporteCobrosDto.class,
                    fields = {
                        @FieldResult(name = "idpago", column = "idpago"),
                        @FieldResult(name = "codigo", column = "codigo"),
                        @FieldResult(name = "nombres", column = "nombres"),
                        @FieldResult(name = "telefono", column = "telefono"),
                        @FieldResult(name = "direccion", column = "direccion"),
                        @FieldResult(name = "sector", column = "sector"),
                        @FieldResult(name = "fechapago", column = "fechapago"),
                        @FieldResult(name = "valor", column = "valor"),
                        @FieldResult(name = "observacion", column = "observacion"),})})
@NamedNativeQueries({
    @NamedNativeQuery(
            name = "ReporteCobrosDto.cobrosector",
            query
            = "SELECT p.idpago, c.codigo, c.nombres, c.telefono,  c.direccion, s.sector, concat(p.mes, '-',p.anio) fechapago, c2.valor, p.observacion \n"
            + "FROM pago p \n"
            + "join cliente c on p.idcliente = c.idcliente and c.activo = 1\n"
            + "join sector s on c.idsector = s.idsector and s.activo = 1\n"
            + "left join configuracionpago c2 on c.idconfiguracionpago = c2.idconfiguracionpago and c2.activo = 1\n"
            + "where p.activo =1 \n"
            + "and p.fechapago = (SELECT max(pp.fechapago) from pago pp where pp.idpago = p.idpago GROUP by pp.fechapago) \n"
            + "and c.idsector = ? \n"
            + "order by fechapago asc",
            resultSetMapping = "ReporteCobrosDtoMapping"),

    @NamedNativeQuery(
            name = "ReporteCobrosDto.cobroMunicipio",
            query
            = "SELECT p.idpago, c.codigo, c.nombres, c.direccion, c.telefono, concat(p.mes, '-',p.anio) fechapago, c2.valor, p.observacion\n"
            + "FROM pago p \n"
            + "join cliente c on p.idcliente = c.idcliente and c.activo = 1 \n"
            + "join configuracionpago c2 on c.idconfiguracionpago = c2.idconfiguracionpago\n"
            + "where p.activo =1 \n"
            + "and p.fechapago = (SELECT max(pp.fechapago) from pago pp where pp.idpago = p.idpago GROUP by pp.fechapago) \n"
            + "and c.idmunicipio = ? \n"
            + "order by fechapago asc",
            resultSetMapping = "ReporteCobrosDtoMapping")
})
@XmlRootElement
public class ReporteCobrosDto implements Serializable {

    @Id
    private Integer idpago;
    private String codigo;
    private String nombres;
    private String telefono;
    private String direccion;
    private String sector;
    private String fechapago;
    private Integer valor;
    private String observacion;

    public Integer getIdpago() {
        return idpago;
    }

    public void setIdpago(Integer idpago) {
        this.idpago = idpago;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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

    public String getFechapago() {
        return fechapago;
    }

    public void setFechapago(String fechapago) {
        this.fechapago = fechapago;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

}
