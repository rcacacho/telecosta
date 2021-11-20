package tele.costa.bussines.bussines.ejb.imp;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import org.apache.log4j.Logger;
import tele.costa.api.ejb.PagosBeanLocal;
import tele.costa.api.entity.Cliente;
import tele.costa.api.entity.Pago;

/**
 *
 * @author rcacacho
 */
@Singleton
public class GeneracionCobro {

    private static final Logger log = Logger.getLogger(GeneracionCobro.class);

    @EJB
    private ClienteBean clienteBean;
    @EJB
    private PagosBeanLocal pagoBean;

    @Schedule(month = "*", dayOfMonth = "1", hour = "1", persistent = false)
    public void registroCobro() {
        List<Cliente> response = clienteBean.ListClientes();
        if (response.size() > 0) {
            for (Cliente cc : response) {
                Date fechaActual = new Date();
                LocalDate localDate = fechaActual.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int month = localDate.getMonthValue();
                int an = localDate.getYear();

                
                
                Pago responsePago = pagoBean.findPagoByIdClienteAndAnioAndMes(cc.getIdcliente(), an, month);

            }
        }

//        Response<List<AccDetalleAccionEmpleado>> responseDesignacion = movimientosEmpleadoBean.findDesignacionEmpleadoByFechaFin(new Date());
//        if (responseDesignacion.isOk()) {
//            List<PerfilEmpleadoDto> listEmpleado = empleado.listPerfilDtoByIdEmpleado(responseDesignacion.getValue().get(0).getIdAccionEmpleado().getIdEmpleado());
//
//            JsonObject jsonEmpleado;
//            jsonEmpleado = Json.createObjectBuilder()
//                    .add("nip", listEmpleado.get(0).getNipHistorial().toString())
//                    .build();
//            JsonObject res = MiDependenciaWsSDK.cancelarTrasladarResponsable(jsonEmpleado);
//            bitacoraBean.infoMiDependencia(responseDesignacion.getValue().get(0).getIdAccionEmpleado().getIdAccion().getNombre(), listEmpleado.get(0).getNombreCompleto(), listEmpleado.get(0).getDependencia(), listEmpleado.get(0).getPuesto(), "Cancelar designación", jsonEmpleado.toString(), res.toString(), sesion.getUsuario());
//            System.out.println("Entro a la actualización de Designaciones Job");
//        } else {
//            System.out.println("No encontro registro para actualizar las designaciones Job");
//        }
    }

}
