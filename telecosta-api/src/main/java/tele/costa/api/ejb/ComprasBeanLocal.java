package tele.costa.api.ejb;

import java.util.List;
import javax.ejb.Local;
import tele.costa.api.entity.Compra;

/**
 *
 * @author elfo_
 */
@Local
public interface ComprasBeanLocal {
    
    List<Compra> listCompra();
    
}
