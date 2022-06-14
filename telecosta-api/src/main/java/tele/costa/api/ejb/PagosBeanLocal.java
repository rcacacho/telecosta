package tele.costa.api.ejb;

import java.util.Date;
import java.util.List;
import tele.costa.api.dto.ReporteCobrosDto;
import tele.costa.api.entity.Cobro;
import tele.costa.api.entity.Detallepago;
import tele.costa.api.entity.Pago;

/**
 *
 * @author rcacacho
 */
public interface PagosBeanLocal {

    List<Pago> listPagosByFechaInicioAndFin(Date fechainicio, Date fechafin);

    List<Cobro> listCobros(Date fechainicio, Date fechafin);

    Pago savePago(Pago pago);

    Cobro saveCobro(Cobro cobro);

    Pago findPagoByIdClienteAndAnioAndMes(Integer idCliente, Integer anio, String mes);

    List<Pago> listPagoByIdCliente(Integer idcliente);

    List<Pago> listPagoByAnio(Integer anio);

    List<Pago> listPagoByMes(String mes);

    List<Cobro> listCobroByIdCliente(Integer idcliente);

    List<Cobro> listCobroByAnio(Integer anio);

    List<Cobro> listCobroByMes(String mes);

    Pago findPagoByIdPago(Integer idPago);

    List<Pago> listPagos();

    Detallepago saveDetallepago(Detallepago detalle);

    List<Detallepago> listDetallePago(Integer idPago);

    Pago updatePago(Pago pago);

    List<Pago> listPagosByIdMunicipio(Integer idmunicicpio);

    List<Pago> listPagosByInIdMunicipios();

    Pago findUltimoPago(Integer idcliente);

    Pago eliminarPago(Integer idpago, String usuario);

    List<Pago> listPagoByIdClienteAndAnio(Integer idcliente, Integer anio);

    List<Pago> listPagosByFechaInicioAndFinandUsuario(Date fechainicio, Date fechafin, String usuario);

    List<ReporteCobrosDto> listCobrosByIdSector(Integer idsector);

    List<ReporteCobrosDto> listCobrosByIdMunicipio(Integer idmunicipio);

    List<Pago> listPagosByInIdMunicipiosSanRafaelSanPabloRodeo();

    List<Pago> listPagosByFechaInicioAndFinAndIdMunicipio(Date fechainicio, Date fechafin, Integer idMunicipio);

    List<Pago> listPagosByMunicipio(Integer idMunicipio);

    List<Pago> listPagosByAnioAndMesAndMunicipio(Integer anio, String mes, Integer idMunicipio);

    List<Pago> listPagoByAnioAndMes(Integer anio, String mes);

    List<Pago> listPagosByFechaInicio(Date fechainicio);

    List<Pago> listPagosByFechaFin(Date fechaFin);

    Detallepago updateDetallePago(Detallepago detalle);

    Detallepago eliminarDetallePago(Integer idpago, String usuario);

    Cobro findCobroByIdClienteAndAnioAndMes(Integer idcliente, Integer anio, String mes);

    Cobro updateCobro(Cobro cobro);

    Cobro findCobroByIdCobro(Integer idCobro);

    List<Pago> listPagosByIdMunicipioByList(List<Integer> listIdmunicipio);

    Detallepago findDetallePago(Integer idpago);

    List<Pago> listPagoByIdClienteByIdEstadoCliente(Integer idcliente, Integer idestadocliente);
    
    List<Pago> listPagosByFechaInicioAndFinAndIdMunicipioAndIdEstadoCliente(Date fechainicio, Date fechafin, Integer idMunicipio, Integer idestadocliente);
    
    List<Pago> listPagosByAnioAndMesAndMunicipioByIdEstadoCliente(Integer anio, String mes, Integer idMunicipio, Integer idestadocliente);
    
    List<Pago> listPagosByFechaInicioAndFinByIdEstadoCliente(Date fechainicio, Date fechafin, Integer idestadocliente);
    
    List<Pago> listPagoByAnioAndMesAndIdEstadoCliente(Integer anio, String mes, Integer idestadocliente);
    
    List<Pago> listPagoByIdClienteAndAnioAndIdEstadoCliente(Integer idcliente, Integer anio, Integer idestadocliente);
    
    List<Pago> listPagoByAnioAndIdEstadoCliente(Integer anio, Integer idestadocliente);
    
    List<Pago> listPagoByMesAndIdEstadoCliente(String mes, Integer idestadocliente);
    
    List<Pago> listPagosByMunicipioAndEstadoCliente(Integer idMunicipio, Integer idestadocliente);
    
    List<Pago> listPagosByFechaInicioAndEstadoCliente(Date fechainicio, Integer idestadocliente);
    
    List<Pago> listPagosByFechaFinAdnEstadoCliente(Date fechaFin, Integer idestadocliente);

}
