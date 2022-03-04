package tele.costa.api.ejb;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import tele.costa.api.entity.Insumos;

/**
 *
 * @author rcacacho
 */
@Local
public interface BodegaBeanLocal {

    List<Insumos> listInsumoByFechaInicio(Date fechaInicio);

    List<Insumos> listInsumoByFechaFin(Date fechaFin);

    List<Insumos> listInsumoByFechaInicioAndFechaFin(Date fechaInicio, Date fechaFin);

    List<Insumos> listInsumoByIdAgencia(Integer idAgencia);

    List<Insumos> listInsumoByFechaInicioAndFechaFinAndIdAgencia(Date fechaInicio, Date fechaFin, Integer idAgencia);

    Insumos saveInsumo(Insumos insumo);

    Insumos updateInsumo(Insumos insumo);

}
