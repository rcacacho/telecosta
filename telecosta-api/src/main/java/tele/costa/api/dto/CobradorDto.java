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
                        @FieldResult(name = "montopagado", column = "montopagado"),})})
@NamedNativeQueries({
    @NamedNativeQuery(
            name = "CobradorDto.actual",
            query
            = "select c.idcliente, d.montopagado, c.idconfiguracionpago "
            + "from detallepago d\n"
            + "join pago p on d.idpago = p.idpago\n"
            + "join cliente c on p.idcliente = c.idcliente\n"
            + "where d.idseriefactura = ? "
            + "and c.idestadocliente = 1 "
            + "and d.activo = 1 "
            + "and p.mes like ? \n"
            + "and p.anio = ? \n",
            resultSetMapping = "CobradorDtoMapping"),})
@XmlRootElement
public class CobradorDto implements Serializable {

    @Id
    private Integer idcliente;
    private Integer idconfiguracionpago;
    private Integer montopagado;

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

}
