package tele.costa.api.ejb;

import java.util.List;
import javax.ejb.Local;
import tele.costa.api.entity.Configuracionpago;

/**
 *
 * @author rcacacho
 */
@Local
public interface ConfiguracionBeanLocal {

    List<Configuracionpago> listConfiguracionPago();

    Configuracionpago saveConfiguracionPago(Configuracionpago configuracion);

    Configuracionpago deleteConfiguracionPago(Integer idconfiguracionpago, String usuario);

    Configuracionpago findConfiguracionPago(Integer idconfiguracionpago);

    Configuracionpago actualizarConfiguracion(Configuracionpago configuracion);

}
