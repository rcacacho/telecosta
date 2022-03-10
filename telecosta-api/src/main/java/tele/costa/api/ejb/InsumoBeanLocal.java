package tele.costa.api.ejb;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import tele.costa.api.entity.Insumos;
import tele.costa.api.entity.Inventario;

/**
 *
 * @author rcacacho
 */
@Local
public interface InsumoBeanLocal {

    List<Insumos> listInsumo();

    List<Insumos> listInsumoByFechaInicio(Date fechaInicio);

    List<Insumos> listInsumoByFechaFin(Date fechaFin);

    List<Insumos> listInsumoByFechaInicioAndFechaFin(Date fechaInicio, Date fechaFin);

    List<Insumos> listInsumoByIdAgencia(Integer idAgencia);

    List<Insumos> listInsumoByFechaInicioAndFechaFinAndIdAgencia(Date fechaInicio, Date fechaFin, Integer idAgencia);

    Insumos saveInsumo(Insumos insumo);

    Insumos updateInsumo(Insumos insumo);

    Insumos findInsumoById(Integer idinsumo);

    Insumos findInsumoByIdAgenciaAndCodigo(Integer idagencia, String codigo);

    Insumos deleteInsumo(Integer idinsumo, String usuario);

    Insumos findInsumoByCodigo(String codigo);

    Inventario saveInventario(Inventario inventario);

    Inventario updateInventario(Inventario inventario);

    Inventario findInventarioById(Integer idinventario);

}
