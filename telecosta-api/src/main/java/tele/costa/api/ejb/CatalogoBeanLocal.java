package tele.costa.api.ejb;

import java.util.List;
import javax.ejb.Local;
import tele.costa.api.entity.Configuracionpago;
import tele.costa.api.entity.Departamento;
import tele.costa.api.entity.Municipio;
import tele.costa.api.entity.Tipopago;

/**
 *
 * @author elfo_
 */
@Local
public interface CatalogoBeanLocal {

    List<Departamento> listDepartamentos();

    List<Municipio> listMunicipioByIdDepartamento(Integer iddepartamento);

    Tipopago findTipoPago(Integer idtipopago);

    Configuracionpago findConfiguracionPago(Integer idconfiguracionpago);

    List<Configuracionpago> ListConfiguracionPago();
}
