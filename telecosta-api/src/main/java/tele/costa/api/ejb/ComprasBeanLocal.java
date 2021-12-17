package tele.costa.api.ejb;

import java.util.List;
import javax.ejb.Local;
import tele.costa.api.entity.Compra;
import tele.costa.api.entity.Proveedor;

/**
 *
 * @author elfo_
 */
@Local
public interface ComprasBeanLocal {

    List<Compra> listCompra();

    Compra saveCompra(Compra compra);

    Compra findCompraById(Integer idcompra);

    List<Proveedor> listProveedor();

    Proveedor saveProveedor(Proveedor proveedor);

}
