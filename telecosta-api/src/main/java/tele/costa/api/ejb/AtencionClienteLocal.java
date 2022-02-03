package tele.costa.api.ejb;

import java.util.List;
import javax.ejb.Local;
import tele.costa.api.entity.Atencion;

/**
 *
 * @author rcacacho
 */
@Local
public interface AtencionClienteLocal {

    List<Atencion> listAtenciones();

    Atencion saveAtencion(Atencion atencion);

    Atencion updateAtencion(Atencion cliente);

    List<Atencion> listAtencionByIdMunicipio(Integer idmunicipio);
    
    List<Atencion> listAtencionByMunicipio();

}
