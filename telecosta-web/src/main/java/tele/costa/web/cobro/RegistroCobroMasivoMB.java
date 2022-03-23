package tele.costa.web.cobro;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import tele.costa.api.ejb.CatalogoBeanLocal;
import tele.costa.api.ejb.ClienteBeanLocal;
import tele.costa.api.ejb.PagosBeanLocal;
import tele.costa.api.entity.Cliente;
import tele.costa.api.entity.Cobro;
import tele.costa.api.entity.Detallepago;
import tele.costa.api.entity.Municipio;
import tele.costa.api.entity.Pago;
import tele.costa.api.enums.TipoPagoEnum;
import telecosta.web.utils.JsfUtil;
import telecosta.web.utils.SesionUsuarioMB;

/**
 *
 * @author rcacacho
 */
@ManagedBean(name = "registroCobroMasivoMB")
@ViewScoped
public class RegistroCobroMasivoMB implements Serializable {

    private static final Logger log = Logger.getLogger(RegistroCobroMB.class);

    @EJB
    private PagosBeanLocal pagosBean;
    @EJB
    private ClienteBeanLocal clienteBean;
    @EJB
    private CatalogoBeanLocal catalogoBean;

    private Cobro cobro;
    private Municipio municipioSelected;
    private List<Municipio> listMunicipios;
    private Cliente cliente;
    private List<Cliente> listClientes;
    private Date fechaInicio;
    private Date fechaFin;

    public RegistroCobroMasivoMB() {
        cobro = new Cobro();
    }

    @PostConstruct
    void cargarDatos() {
        listMunicipios = catalogoBean.listMunicipioByIdDepartamento(1);
    }

    public void regresar() {
        JsfUtil.redirectTo("/cobros/lista.xhtml");
    }

    public void cargarClientesMunicipios() {
        if (municipioSelected != null) {
            listClientes = clienteBean.ListClientesByIdMunucipio(municipioSelected.getIdmunicipio());
        } else {
            listClientes = null;
            cliente = null;
        }
    }

    public void cargarCliente() {
        cobro.setIdcliente(cliente);
    }

    public void saveCobro() throws IOException {
        LocalDate startDate = fechaInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = fechaFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        ZoneId defaultZoneId = ZoneId.systemDefault();

        Integer count = 0;
        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            count++;
            if (count.equals(30)) {
                LocalDate fPago = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
                Date fechaPago = Date.from(fPago.atStartOfDay(defaultZoneId).toInstant());
                registrarCobro(date.getYear(), obtenerMes(date.getMonthValue()), fechaPago);
            } else if (count.equals(60)) {
                LocalDate fPago = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
                Date fechaPago = Date.from(fPago.atStartOfDay(defaultZoneId).toInstant());
                registrarCobro(date.getYear(), obtenerMes(date.getMonthValue()), fechaPago);
            } else if (count.equals(90)) {
                LocalDate fPago = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
                Date fechaPago = Date.from(fPago.atStartOfDay(defaultZoneId).toInstant());
                registrarCobro(date.getYear(), obtenerMes(date.getMonthValue()), fechaPago);
                System.out.println("entro al pago 3");
            } else if (count.equals(120)) {
                LocalDate fPago = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
                Date fechaPago = Date.from(fPago.atStartOfDay(defaultZoneId).toInstant());
                registrarCobro(date.getYear(), obtenerMes(date.getMonthValue()), fechaPago);
                System.out.println("entro al pago 4");
            } else if (count.equals(150)) {
                LocalDate fPago = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
                Date fechaPago = Date.from(fPago.atStartOfDay(defaultZoneId).toInstant());
                registrarCobro(date.getYear(), obtenerMes(date.getMonthValue()), fechaPago);
                //registrarCobro();
                System.out.println("entro al pago 5");
            } else if (count.equals(180)) {
                LocalDate fPago = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
                Date fechaPago = Date.from(fPago.atStartOfDay(defaultZoneId).toInstant());
                registrarCobro(date.getYear(), obtenerMes(date.getMonthValue()), fechaPago);
                //registrarCobro();
                System.out.println("entro al pago 6");
            } else if (count.equals(210)) {
                LocalDate fPago = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
                Date fechaPago = Date.from(fPago.atStartOfDay(defaultZoneId).toInstant());
                registrarCobro(date.getYear(), obtenerMes(date.getMonthValue()), fechaPago);
                //registrarCobro();
                System.out.println("entro al pago 7");
            } else if (count.equals(240)) {
                LocalDate fPago = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
                Date fechaPago = Date.from(fPago.atStartOfDay(defaultZoneId).toInstant());
                registrarCobro(date.getYear(), obtenerMes(date.getMonthValue()), fechaPago);
                //registrarCobro();
                System.out.println("entro al pago 8");
            } else if (count.equals(270)) {
                LocalDate fPago = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
                Date fechaPago = Date.from(fPago.atStartOfDay(defaultZoneId).toInstant());
                registrarCobro(date.getYear(), obtenerMes(date.getMonthValue()), fechaPago);
                //registrarCobro();
            } else if (count.equals(300)) {
                LocalDate fPago = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
                Date fechaPago = Date.from(fPago.atStartOfDay(defaultZoneId).toInstant());
                registrarCobro(date.getYear(), obtenerMes(date.getMonthValue()), fechaPago);
                //registrarCobro();
            } else if (count.equals(330)) {
                LocalDate fPago = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
                Date fechaPago = Date.from(fPago.atStartOfDay(defaultZoneId).toInstant());
                registrarCobro(date.getYear(), obtenerMes(date.getMonthValue()), fechaPago);
                //registrarCobro();
            } else if (count.equals(360)) {
                LocalDate fPago = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
                Date fechaPago = Date.from(fPago.atStartOfDay(defaultZoneId).toInstant());
                registrarCobro(date.getYear(), obtenerMes(date.getMonthValue()), fechaPago);
                //registrarCobro();
            } else if (count.equals(390)) {
                LocalDate fPago = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
                Date fechaPago = Date.from(fPago.atStartOfDay(defaultZoneId).toInstant());
                registrarCobro(date.getYear(), obtenerMes(date.getMonthValue()), fechaPago);
                //registrarCobro();
            } else if (count.equals(420)) {
                LocalDate fPago = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
                Date fechaPago = Date.from(fPago.atStartOfDay(defaultZoneId).toInstant());
                registrarCobro(date.getYear(), obtenerMes(date.getMonthValue()), fechaPago);
                //registrarCobro();
            } else if (count.equals(450)) {
                LocalDate fPago = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
                Date fechaPago = Date.from(fPago.atStartOfDay(defaultZoneId).toInstant());
                registrarCobro(date.getYear(), obtenerMes(date.getMonthValue()), fechaPago);
                //registrarCobro();
            } else if (count.equals(480)) {
                LocalDate fPago = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
                Date fechaPago = Date.from(fPago.atStartOfDay(defaultZoneId).toInstant());
                registrarCobro(date.getYear(), obtenerMes(date.getMonthValue()), fechaPago);
                //registrarCobro();
            } else if (count.equals(510)) {
                LocalDate fPago = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
                Date fechaPago = Date.from(fPago.atStartOfDay(defaultZoneId).toInstant());
                registrarCobro(date.getYear(), obtenerMes(date.getMonthValue()), fechaPago);
                //registrarCobro();
            } else if (count.equals(540)) {
                LocalDate fPago = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
                Date fechaPago = Date.from(fPago.atStartOfDay(defaultZoneId).toInstant());
                registrarCobro(date.getYear(), obtenerMes(date.getMonthValue()), fechaPago);
                //registrarCobro();
            } else if (count.equals(570)) {
                LocalDate fPago = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
                Date fechaPago = Date.from(fPago.atStartOfDay(defaultZoneId).toInstant());
                registrarCobro(date.getYear(), obtenerMes(date.getMonthValue()), fechaPago);
                //registrarCobro();
            } else if (count.equals(600)) {
                LocalDate fPago = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
                Date fechaPago = Date.from(fPago.atStartOfDay(defaultZoneId).toInstant());
                registrarCobro(date.getYear(), obtenerMes(date.getMonthValue()), fechaPago);
                //registrarCobro();
            } else if (count.equals(630)) {
                LocalDate fPago = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
                Date fechaPago = Date.from(fPago.atStartOfDay(defaultZoneId).toInstant());
                registrarCobro(date.getYear(), obtenerMes(date.getMonthValue()), fechaPago);
                //registrarCobro();
            } else if (count.equals(660)) {
                LocalDate fPago = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
                Date fechaPago = Date.from(fPago.atStartOfDay(defaultZoneId).toInstant());
                registrarCobro(date.getYear(), obtenerMes(date.getMonthValue()), fechaPago);
                //registrarCobro();
            } else if (count.equals(690)) {
                LocalDate fPago = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
                Date fechaPago = Date.from(fPago.atStartOfDay(defaultZoneId).toInstant());
                registrarCobro(date.getYear(), obtenerMes(date.getMonthValue()), fechaPago);
            }
        }

        cobro = null;
        cliente = null;
    }

    public void registrarCobro(Integer anio, String mes, Date fechaPago) throws IOException {
        if (cliente.getIdconfiguracionpago().getValor() == null) {
            JsfUtil.addErrorMessage("El cliente debe tener un cobro configurado");
            return;
        }

        cobro.setUsuariocreacion(SesionUsuarioMB.getUserName());
        cobro.setIdcliente(cliente);
        cobro.setIdconfiguracionpago(catalogoBean.findConfiguracionPago(TipoPagoEnum.COBRO.getId()));
        cobro.setAnio(anio);
        cobro.setMes(mes);
        cobro.setFechacobro(fechaPago);
        cobro.setCobro(cliente.getIdconfiguracionpago().getValor());

        Cobro response = pagosBean.saveCobro(cobro);
        if (response != null) {
            Detallepago detalle = new Detallepago();
            detalle.setIdcobro(cobro);
            detalle.setFechapago(fechaPago);
            if (cobro.getCobro() != null) {
                detalle.setMontocobrado(cobro.getCobro());
            } else {
                detalle.setMontocobrado(cliente.getIdconfiguracionpago().getValor());
            }

            if (detalle.getMontopagado() != null) {
                detalle.setTotal(detalle.getMontocobrado() - detalle.getMontopagado());
            } else {
                detalle.setTotal(detalle.getMontocobrado());
            }

            detalle.setUsuariocreacion(SesionUsuarioMB.getUserName());
            Detallepago responseDetalle = pagosBean.saveDetallepago(detalle);

            JsfUtil.addSuccessMessage("El cobro se registro exitosamente");
            cobro = null;
            cliente = null;
            return;
        }
    }

    public String obtenerMes(Integer mes) {
        if (mes.equals(1)) {
            return "enero";
        } else if (mes.equals(2)) {
            return "febrero";
        } else if (mes.equals(3)) {
            return "marzo";
        } else if (mes.equals(4)) {
            return "abril";
        } else if (mes.equals(5)) {
            return "mayo";
        } else if (mes.equals(6)) {
            return "junio";
        } else if (mes.equals(7)) {
            return "julio";
        } else if (mes.equals(8)) {
            return "agosto";
        } else if (mes.equals(9)) {
            return "septiembre";
        } else if (mes.equals(10)) {
            return "octubre";
        } else if (mes.equals(11)) {
            return "noviembre";
        } else if (mes.equals(12)) {
            return "diciembre";
        }
        return null;
    }

    /*Metodos getters y setters*/
    public Cobro getCobro() {
        return cobro;
    }

    public void setCobro(Cobro cobro) {
        this.cobro = cobro;
    }

    public Municipio getMunicipioSelected() {
        return municipioSelected;
    }

    public void setMunicipioSelected(Municipio municipioSelected) {
        this.municipioSelected = municipioSelected;
    }

    public List<Municipio> getListMunicipios() {
        return listMunicipios;
    }

    public void setListMunicipios(List<Municipio> listMunicipios) {
        this.listMunicipios = listMunicipios;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Cliente> getListClientes() {
        return listClientes;
    }

    public void setListClientes(List<Cliente> listClientes) {
        this.listClientes = listClientes;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

}
