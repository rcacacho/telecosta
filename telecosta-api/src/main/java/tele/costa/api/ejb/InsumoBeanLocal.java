package tele.costa.api.ejb;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import tele.costa.api.entity.Bitacorainventario;
import tele.costa.api.entity.Insumos;
import tele.costa.api.entity.Inventario;

/**
 *
 * @author rcacacho
 */
@Local
public interface InsumoBeanLocal {

    List<Insumos> listInsumo();

    List<Inventario> listInsumoByFechaInicio(Date fechaInicio);

    List<Inventario> listInsumoByFechaFin(Date fechaFin);

    List<Inventario> listInsumoByFechaInicioAndFechaFin(Date fechaInicio, Date fechaFin);

    List<Inventario> listInsumoByIdAgencia(Integer idAgencia);

    List<Inventario> listInsumoByFechaInicioAndFechaFinAndIdAgencia(Date fechaInicio, Date fechaFin, Integer idAgencia);

    Insumos saveInsumo(Insumos insumo);

    Insumos updateInsumo(Insumos insumo);

    Insumos findInsumoById(Integer idinsumo);

    Inventario findInsumoByIdAgenciaAndCodigo(Integer idagencia, String codigo);

    Insumos deleteInsumo(Integer idinsumo, String usuario);

    Insumos findInsumoByCodigo(String codigo);

    Inventario saveInventario(Inventario inventario);

    Inventario updateInventario(Inventario inventario);

    Inventario findInventarioById(Integer idinventario);

    List<Inventario> listInsumoByCodigo(String codigo);

    List<Inventario> listInsumoByIdAgenciaAndCodigo(Integer idAgencia, String codigo);

    Bitacorainventario saveBitacoraInventario(Bitacorainventario bitacora);

}
