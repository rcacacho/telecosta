package tele.costa.api.ejb;

import java.util.Date;
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

    Proveedor saveProveedor(Proveedor proveedor);

    List<Compra> listCompraByFechaInicio(Date fechaInicio);

    List<Compra> listCompraByFechaFin(Date fechaFin);

    List<Compra> listCompraByFechaInicioFechaFin(Date fechaInicio, Date fechaFin);

    Compra eliminarCompra(Integer idcompra, String usuario);

    Compra updateCompra(Compra compra);

    List<Compra> listCompraByFechaInicioAndTipoCompra(Date fechaInicio, Integer idTipoCompra);

    List<Compra> listCompraByFechaFinAndTipoCompra(Date fechaFin, Integer idTipoCompra);

    List<Compra> listCompraByFechaInicioFechaFinAndTipoCompra(Date fechaInicio, Date fechaFin, Integer idTipoCompra);

    List<Compra> listCompraByidTipoCompra(Integer idTipoCompra);

}
