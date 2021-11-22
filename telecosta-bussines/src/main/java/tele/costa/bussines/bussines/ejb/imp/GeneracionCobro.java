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
                Integer month = localDate.getMonthValue();
                Integer an = localDate.getYear();
                String mes = "";

                if (month.equals(1)) {
                    mes = "enero";
                } else if (month.equals(2)) {
                    mes = "febrero";
                } else if (month.equals(3)) {
                    mes = "marzo";
                } else if (month.equals(4)) {
                    mes = "abril";
                } else if (month.equals(5)) {
                    mes = "mayo";
                } else if (month.equals(6)) {
                    mes = "junio";
                } else if (month.equals(7)) {
                    mes = "julio";
                } else if (month.equals(8)) {
                    mes = "agosto";
                } else if (month.equals(9)) {
                    mes = "septiembre";
                } else if (month.equals(10)) {
                    mes = "octubre";
                } else if (month.equals(11)) {
                    mes = "noviembre";
                } else if (month.equals(12)) {
                    mes = "diciembre";
                }

                Pago responsePago = pagoBean.findPagoByIdClienteAndAnioAndMes(cc.getIdcliente(), an, mes);

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
