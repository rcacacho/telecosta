package tele.costa.api.ejb;

import java.util.List;
import javax.ejb.Local;
import tele.costa.api.entity.Departamento;
import tele.costa.api.entity.Municipio;

/**
 *
 * @author elfo_
 */
@Local
public interface CatalogoBeanLocal {

    List<Departamento> listDepartamentos();

    List<Municipio> listMunicipioByIdDepartamento(Integer iddepartamento);

}
