package tele.costa.api.ejb;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import tele.costa.api.entity.Atencion;
import tele.costa.api.entity.Detalleatencion;

/**
 *
 * @author rcacacho
 */
@Local
public interface AtencionClienteLocal {

    List<Atencion> listAtenciones();

    Atencion saveAtencion(Atencion atencion);

    Atencion updateAtencion(Atencion atencion);

    List<Atencion> listAtencionByIdMunicipio(Integer idmunicipio);

    List<Atencion> listAtencionByMunicipio();

    Atencion findAtencionById(Integer idatencion);

    List<Atencion> listAtencionByFechas(Date fechainicio, Date fechafin);

    List<Atencion> listAtencionByFechaInicio(Date fechainicio);

    List<Atencion> listAtencionByFechaFin(Date fechafin);

    List<Atencion> listAtencionByFechasAndRuta(Date fechainicio, Date fechafin, Integer idruta);

    List<Atencion> listAtencionByRuta(Integer idruta);
    
    Detalleatencion saveDetalleAtencion(Detalleatencion detalle);
    
    List<Detalleatencion> listDetalleAtencioByIdAtencion(Integer idatencion);
    
    List<Detalleatencion> listDetalleAtencioByFechas(Date fechainicio, Date fechafin);
}
