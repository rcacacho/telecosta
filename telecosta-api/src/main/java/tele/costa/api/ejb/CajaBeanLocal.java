package tele.costa.api.ejb;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import tele.costa.api.dto.CobradorDto;
import tele.costa.api.entity.Cajaagencia;
import tele.costa.api.entity.Detallepago;

/**
 *
 * @author rcacacho
 */
@Local
public interface CajaBeanLocal {

    List<Cajaagencia> listCajaAgencia();

    Cajaagencia saveCaja(Cajaagencia caja);

    Cajaagencia updateCaja(Cajaagencia atencion);

    Cajaagencia findCajaByIdCaja(Integer idcajaagencia);

    List<Cajaagencia> listCajaByFechaInicio(Date fechaIncio);

    List<Cajaagencia> listCajaByFechaFin(Date fechaFin);

    List<Cajaagencia> listCajaByFechas(Date fechaIncio, Date fechaFin);

    List<Cajaagencia> listCajaByFechaInicioAndSector(Date fechaIncio, Integer idSector);

    List<Cajaagencia> listCajaByFechaFinAndSector(Date fechaFin, Integer idSector);

    List<Cajaagencia> listCajaByFechasAndSector(Date fechaIncio, Date fechaFin, Integer idSector);

    List<Cajaagencia> listCajaBySector(Integer idSector);

    List<Detallepago> listNoFactura(Date fechaInicio, Date fechaFin, Integer idsectorpago);

    Long findMontoFacturaSerie(Integer inicio, Integer fin, Integer idsectorpago, Date fechaInicio, Date fechaFin);

    List<CobradorDto> listClientesByIdSectorFactura(Integer idsectorfactura, Date fechainicio, Date fechafin);
}
