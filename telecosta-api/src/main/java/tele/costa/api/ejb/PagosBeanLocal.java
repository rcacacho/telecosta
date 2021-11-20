package tele.costa.api.ejb;

import java.util.Date;
import java.util.List;
import tele.costa.api.entity.Pago;

/**
 *
 * @author rcacacho
 */
public interface PagosBeanLocal {
    
    List<Pago> listPagos(Date fechainicio, Date fechafin);
    
    List<Pago> listCobros(Date fechainicio, Date fechafin);

    Pago savePago(Pago pago);

    Pago saveCobro(Pago pago);
    
    Pago findPagoByIdClienteAndAnioAndMes (Integer idCliente, Integer anio, String mes);
    
}
