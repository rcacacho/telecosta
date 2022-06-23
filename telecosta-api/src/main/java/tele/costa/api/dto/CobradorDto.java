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
 * @author elfo_
 */
@Entity
@SqlResultSetMapping(name = "CobradorDtoMapping",
        entities = {
            @EntityResult(entityClass = CobradorDto.class,
                    fields = {
                        @FieldResult(name = "idcliente", column = "idcliente"),
                        @FieldResult(name = "idconfiguracionpago", column = "idconfiguracionpago"),
                        @FieldResult(name = "nofactura", column = "nofactura"),
                        @FieldResult(name = "nombres", column = "nombres"),
                        @FieldResult(name = "montopagado", column = "montopagado"),})})
@NamedNativeQueries({
    @NamedNativeQuery(
            name = "CobradorDto.actual",
            query
            = "select c.idcliente, d.montopagado, c.idconfiguracionpago, d.nofactura, c.nombres "
            + "from detallepago d\n"
            + "join pago p on d.idpago = p.idpago\n"
            + "join cliente c on p.idcliente = c.idcliente\n"
            + "where d.idseriefactura = ? "
            + "and d.activo = 1 "
            + "and d.fechapago >= ? \n"
            + "and d.fechapago <= ? "
            + "order by d.nofactura, d.fechacreacion asc \n",
            resultSetMapping = "CobradorDtoMapping"),})
@XmlRootElement
public class CobradorDto implements Serializable {

    @Id
    private Integer idcliente;
    private Integer idconfiguracionpago;
    private Integer montopagado;
    private Integer nofactura;
    private String nombres;

    /*Metodos getters y setters*/
    public Integer getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Integer idcliente) {
        this.idcliente = idcliente;
    }

    public Integer getMontopagado() {
        return montopagado;
    }

    public void setMontopagado(Integer montopagado) {
        this.montopagado = montopagado;
    }

    public Integer getIdconfiguracionpago() {
        return idconfiguracionpago;
    }

    public void setIdconfiguracionpago(Integer idconfiguracionpago) {
        this.idconfiguracionpago = idconfiguracionpago;
    }

    public Integer getNofactura() {
        return nofactura;
    }

    public void setNofactura(Integer nofactura) {
        this.nofactura = nofactura;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

}
