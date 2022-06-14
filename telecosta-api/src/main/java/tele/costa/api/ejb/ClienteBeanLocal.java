package tele.costa.api.ejb;

import java.util.Date;
import java.util.List;
import tele.costa.api.entity.Cliente;

/**
 *
 * @author elfo_
 */
public interface ClienteBeanLocal {

    List<Cliente> ListClientes();

    Cliente saveCliente(Cliente cliente);

    Cliente updateCliente(Cliente cliente);

    List<Cliente> ListClientesByNombre(String nombre);

    List<Cliente> ListClientesByCodigo(String cui);

    Cliente findClienteById(Integer idcliente);

    List<Cliente> ListClientesByIdMunucipio(Integer idmunicipio);

    List<Cliente> ListClientesByNombreAndSectorAndMunicipio(String nombre, String sector, Integer idmunicipio);

    List<Cliente> ListClientesByNombreAndSector(String nombre, String sector);

    List<Cliente> ListClientesByNombreAndMunicipio(String nombre, Integer idmunicipio);

    List<Cliente> ListClientesBySectorAndMunicipio(String sector, Integer idmunicipio);

    List<Cliente> ListClientesBySector(String sector);

    List<Cliente> listClientesByInMunucipio();

    List<Cliente> listClientesByInMunucipioSanPabloRodeoSanRafael();

    List<Cliente> ListClientesByIdSector(Integer idSector);

    List<Cliente> ListClientesCobro();

    List<Cliente> ListClientesByTipoCliente(String tipocliente);

    List<Cliente> ListClientesByIdMunucipioAndTipo(Integer idmunicipio, String tipocliente);

    List<Cliente> ListClientesInactivos();

    List<Cliente> listClientesByInMunucipioInactivos();

    List<Cliente> listClientesByInMunucipioSanPabloRodeoSanRafaelInactivo();

    List<Cliente> ListClientesByIdMunicipioInactivo(Integer idmunicipio);

    List<Cliente> ListClientesByListMunucipioInactivo(List<Integer> listIdmunicipio);

    List<Cliente> ListClientesByIdMunucipioCobro(Integer idmunicipio);

    List<Cliente> ListClientesEstadoActivo();

    List<Cliente> ListClientesByListMunucipioEstadoActivo(List<Integer> listIdmunicipio);

    List<Cliente> ListClientesByIdMunicipioEstadoActivo(Integer idmunicipio);
    
    List<Cliente> ListClientesNoCorte();
    
    List<Cliente> ListClientesByListMunucipioInactivoNoCorte(List<Integer> listIdmunicipio);
    
    List<Cliente> ListClientesByIdMunicipioInactivoNoCorte(Integer idmunicipio);

    List<Cliente> ListClientesByIdMunicipioByIdEstado(Integer idmunicipio, Integer idestadocliente);

    List<Cliente> ListClientesByIdMunicipioByIdEstadoAndFechas(Integer idmunicipio, Integer idestadocliente, Date fechainicio, Date fechafin);

    List<Cliente> ListClientesByIdEstadoAndFechas(Integer idestadocliente, Date fechainicio, Date fechafin);

}
