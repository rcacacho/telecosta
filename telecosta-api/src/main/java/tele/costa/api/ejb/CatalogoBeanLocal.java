package tele.costa.api.ejb;

import java.util.List;
import tele.costa.api.entity.Municipio;

/**
 *
 * @author elfo_
 */
public interface CatalogoBeanLocal {
    
    List<Municipio> listMunicipioByIdDepartamento(Integer iddepartamento);
    
}
