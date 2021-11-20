package tele.costa.api.ejb;

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

    List<Cliente> ListClientesByCui(String cui);

    Cliente findClienteById(Integer idcliente);

}
