package tele.costa.bussines.bussines.ejb.imp;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import org.apache.log4j.Logger;
import tele.costa.api.ejb.CatalogoBeanLocal;
import tele.costa.api.ejb.ClienteBeanLocal;
import tele.costa.api.ejb.PagosBeanLocal;
import tele.costa.api.entity.Cliente;
import tele.costa.api.entity.Cobro;
import tele.costa.api.entity.Detallepago;
import tele.costa.api.entity.Pago;
import tele.costa.api.entity.Tipopago;
import tele.costa.api.enums.TipoPagoEnum;

/**
 *
 * @author rcacacho
 */
@Singleton
public class GeneracionCobro {

    private static final Logger log = Logger.getLogger(GeneracionCobro.class);

    @EJB
    private ClienteBeanLocal clienteBean;
    @EJB
    private PagosBeanLocal pagoBean;
    @EJB
    private CatalogoBeanLocal catalogoBean;

    @Schedule(month = "*", dayOfMonth = "*", hour = "1", persistent = false)
    //@Schedule(second = "0", minute = "40", hour = "*", persistent = false)
    //@Schedule(second = "0", minute = "30", hour = "22")
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
                Cobro responseVerCobro = pagoBean.findCobroByIdClienteAndAnioAndMes(cc.getIdcliente(), an, mes);
                if (cc.getIdconfiguracionpago() == null) {
                    continue;
                }

                if (responsePago == null && responseVerCobro == null) {
                    Tipopago tipo = catalogoBean.findTipoPago(TipoPagoEnum.COBRO.getId());

                    Cobro cobro = new Cobro();
                    cobro.setAnio(an);
                    cobro.setMes(mes);
                    cobro.setIdconfiguracionpago(cc.getIdconfiguracionpago());
                    cobro.setCobro(cc.getIdconfiguracionpago().getValor());
                    cobro.setFechacobro(new Date());
                    cobro.setIdcliente(cc);
                    cobro.setObservacion("Cobro automatico");
                    cobro.setUsuariocreacion("Cobro automatico");
                    cobro.setIdcliente(cc);
                    Cobro responseCobro = pagoBean.saveCobro(cobro);

                    Detallepago detalle = new Detallepago();
                    detalle.setIdcobro(cobro);
                    detalle.setMontocobrado(cc.getIdconfiguracionpago().getValor());
                    detalle.setUsuariocreacion("Cobro automatico");
                    detalle.setTotal(cc.getIdconfiguracionpago().getValor());
                    Detallepago responseDetalle = pagoBean.saveDetallepago(detalle);

                }
            }
        }
        System.out.println("Termino proceso");
    }

}
