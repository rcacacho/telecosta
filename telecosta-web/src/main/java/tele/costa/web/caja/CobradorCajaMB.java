package tele.costa.web.caja;

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
import tele.costa.api.dto.CobradorDto;
import tele.costa.api.ejb.CajaBeanLocal;
import tele.costa.api.ejb.CatalogoBeanLocal;
import tele.costa.api.ejb.PagosBeanLocal;
import tele.costa.api.entity.Pago;
import tele.costa.api.entity.Seriefactura;
import telecosta.web.utils.JsfUtil;

/**
 *
 * @author elfo_
 */
@ManagedBean(name = "cobradorCajaMB")
@ViewScoped
public class CobradorCajaMB implements Serializable {

    private static final Logger log = Logger.getLogger(CobradorCajaMB.class);

    @EJB
    private CajaBeanLocal cajaBean;
    @EJB
    private CatalogoBeanLocal catalogoBean;
    @EJB
    private PagosBeanLocal pagosBean;

    private Integer idSerieFactura;
    private List<Seriefactura> listSerieFactura;
    private List<CobradorDto> listClientes;
    private Integer montoActual;
    private Integer montoMoroso;
    private Integer montoAdelantado;
    private Integer montoInternet;
    private Integer montoMorosoInternet;
    private Integer montoAdelantadoInternet;
    private Integer montoTotal;
    private Integer montoOtros;
    private Date fechaInicio;
    private Date fechaFin;

    public CobradorCajaMB() {
        montoActual = 0;
        montoMoroso = 0;
        montoAdelantado = 0;
        montoAdelantadoInternet = 0;
        montoInternet = 0;
        montoMorosoInternet = 0;
        montoOtros = 0;
    }

    @PostConstruct
    void cargarDatos() {
        listSerieFactura = catalogoBean.listSerieFactura();
    }

    public void limpiarCampos() {
        idSerieFactura = null;
        montoActual = 0;
        montoMoroso = 0;
        montoAdelantado = 0;
        montoAdelantadoInternet = 0;
        montoInternet = 0;
        montoMorosoInternet = 0;
        montoOtros = 0;
        fechaFin = null;
        fechaInicio = null;
    }

    public void buscarFacturas() {
        Date fInicio = new Date();
        LocalDate endDate = fInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        listClientes = cajaBean.listClientesByIdSectorFactura(idSerieFactura, fechaInicio, fechaFin);

        if (listClientes == null || listClientes.isEmpty()) {
            JsfUtil.addErrorMessage("No se encontraron registros con esas fechas");
            return;
        }

        for (CobradorDto cc : listClientes) {
            Pago response = pagosBean.findUltimoPago(cc.getIdcliente());

             if (cc.getMontopagado() == null) {
                 continue;
             }
            
            if (response.getFechapago() != null) {
                LocalDate startDate = response.getFechapago().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                ZoneId defaultZoneId = ZoneId.systemDefault();
                Integer count = 0;
                Integer adel = 0;
                for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
                    count++;
                }

                if (count >= 30 && !response.getMes().equals("Tv adicional/Material")) {
                    if (cc.getIdconfiguracionpago() != null) {
                        if (cc.getIdconfiguracionpago().equals(1) || cc.getIdconfiguracionpago().equals(4) || cc.getIdconfiguracionpago().equals(5)
                                || cc.getIdconfiguracionpago().equals(6) || cc.getIdconfiguracionpago().equals(7) || cc.getIdconfiguracionpago().equals(8)
                                || cc.getIdconfiguracionpago().equals(9) || cc.getIdconfiguracionpago().equals(13) || cc.getIdconfiguracionpago().equals(16)
                                || cc.getIdconfiguracionpago().equals(17) || cc.getIdconfiguracionpago().equals(19) || cc.getIdconfiguracionpago().equals(20)
                                || cc.getIdconfiguracionpago().equals(21) || cc.getIdconfiguracionpago().equals(22)) {

                            montoMoroso = montoMoroso + cc.getMontopagado();
                        } else {
                            montoMorosoInternet = montoMorosoInternet + cc.getMontopagado();
                        }
                    }
                }

                if (response.getMes().equals("Tv adicional/Material")) {
                    montoOtros = montoOtros + cc.getMontopagado();
                } else if (response.getMes().equals("otro")) {
                    montoOtros = montoOtros + cc.getMontopagado();
                } else if (response.getIdtipopago().getIdtipopago().equals(3)) {
                    montoOtros = montoOtros + cc.getMontopagado();
                }

                Integer anio = endDate.getYear();
                Integer mes = endDate.getMonthValue();
                String mesLetra = "";
                String mesLetraMa = "";
                Integer mesNumero = 0;

                switch (mes) {
                    case 1:
                        mesLetra = "enero";
                        break;
                    case 2:
                        mesLetra = "febrero";
                        break;
                    case 3:
                        mesLetra = "marzo";
                        break;
                    case 4:
                        mesLetra = "abril";
                        break;
                    case 5:
                        mesLetra = "mayo";
                        break;
                    case 6:
                        mesLetra = "junio";
                        break;
                    case 7:
                        mesLetra = "julio";
                        break;
                    case 8:
                        mesLetra = "agosto";
                        break;
                    case 9:
                        mesLetra = "septiembre";
                        break;
                    case 10:
                        mesLetra = "octubre";
                        break;
                    case 11:
                        mesLetra = "noviembre";
                        break;
                    case 12:
                        mesLetra = "diciembre";
                        break;
                }

                switch (mes) {
                    case 1:
                        mesLetraMa = "Enero";
                        break;
                    case 2:
                        mesLetraMa = "Febrero";
                        break;
                    case 3:
                        mesLetraMa = "Marzo";
                        break;
                    case 4:
                        mesLetraMa = "Abril";
                        break;
                    case 5:
                        mesLetraMa = "Mayo";
                        break;
                    case 6:
                        mesLetraMa = "Junio";
                        break;
                    case 7:
                        mesLetraMa = "Julio";
                        break;
                    case 8:
                        mesLetraMa = "Agosto";
                        break;
                    case 9:
                        mesLetraMa = "Septiembre";
                        break;
                    case 10:
                        mesLetraMa = "Octubre";
                        break;
                    case 11:
                        mesLetraMa = "Noviembre";
                        break;
                    case 12:
                        mesLetraMa = "Diciembre";
                        break;
                }

                switch (response.getMes()) {
                    case "Enero":
                        mesNumero = 1;
                        break;
                    case "Febrero":
                        mesNumero = 2;
                        break;
                    case "Marzo":
                        mesNumero = 3;
                        break;
                    case "Abril":
                        mesNumero = 4;
                        break;
                    case "Mayo":
                        mesNumero = 5;
                        break;
                    case "Junio":
                        mesNumero = 6;
                        break;
                    case "Julio":
                        mesNumero = 7;
                        break;
                    case "Agosto":
                        mesNumero = 8;
                        break;
                    case "Septiembre":
                        mesNumero = 9;
                        break;
                    case "Octubre":
                        mesNumero = 10;
                        break;
                    case "Noviembre":
                        mesNumero = 11;
                        break;
                    case "Diciembre":
                        mesNumero = 12;
                        break;
                }
                if ((mesLetra.equals(response.getMes()) || mesLetraMa.equals(response.getMes())) && anio.equals(response.getAnio())) {
                    if (cc.getIdconfiguracionpago() != null) {
                        if (cc.getIdconfiguracionpago().equals(1) || cc.getIdconfiguracionpago().equals(4) || cc.getIdconfiguracionpago().equals(5)
                                || cc.getIdconfiguracionpago().equals(6) || cc.getIdconfiguracionpago().equals(7) || cc.getIdconfiguracionpago().equals(8)
                                || cc.getIdconfiguracionpago().equals(9) || cc.getIdconfiguracionpago().equals(13) || cc.getIdconfiguracionpago().equals(16)
                                || cc.getIdconfiguracionpago().equals(17) || cc.getIdconfiguracionpago().equals(19) || cc.getIdconfiguracionpago().equals(20)
                                || cc.getIdconfiguracionpago().equals(21) || cc.getIdconfiguracionpago().equals(22)) {
                            montoActual = montoActual + cc.getMontopagado();
                        } else {
                            montoInternet = montoInternet + cc.getMontopagado();
                        }
                    }
                }
                if (mesNumero > mes && anio.equals(response.getAnio())) {
                    montoAdelantado = montoAdelantado + cc.getMontopagado();
                }
            }
        }
        montoTotal = montoAdelantado + montoInternet + montoActual + montoAdelantadoInternet + montoMoroso + montoMorosoInternet + montoOtros;
    }

    /*Metodos getters y setters*/
    public Integer getIdSerieFactura() {
        return idSerieFactura;
    }

    public void setIdSerieFactura(Integer idSerieFactura) {
        this.idSerieFactura = idSerieFactura;
    }

    public List<Seriefactura> getListSerieFactura() {
        return listSerieFactura;
    }

    public void setListSerieFactura(List<Seriefactura> listSerieFactura) {
        this.listSerieFactura = listSerieFactura;
    }

    public CajaBeanLocal getCajaBean() {
        return cajaBean;
    }

    public void setCajaBean(CajaBeanLocal cajaBean) {
        this.cajaBean = cajaBean;
    }

    public CatalogoBeanLocal getCatalogoBean() {
        return catalogoBean;
    }

    public void setCatalogoBean(CatalogoBeanLocal catalogoBean) {
        this.catalogoBean = catalogoBean;
    }

    public PagosBeanLocal getPagosBean() {
        return pagosBean;
    }

    public void setPagosBean(PagosBeanLocal pagosBean) {
        this.pagosBean = pagosBean;
    }

    public List<CobradorDto> getListClientes() {
        return listClientes;
    }

    public void setListClientes(List<CobradorDto> listClientes) {
        this.listClientes = listClientes;
    }

    public Integer getMontoActual() {
        return montoActual;
    }

    public void setMontoActual(Integer montoActual) {
        this.montoActual = montoActual;
    }

    public Integer getMontoMoroso() {
        return montoMoroso;
    }

    public void setMontoMoroso(Integer montoMoroso) {
        this.montoMoroso = montoMoroso;
    }

    public Integer getMontoAdelantado() {
        return montoAdelantado;
    }

    public void setMontoAdelantado(Integer montoAdelantado) {
        this.montoAdelantado = montoAdelantado;
    }

    public Integer getMontoInternet() {
        return montoInternet;
    }

    public void setMontoInternet(Integer montoInternet) {
        this.montoInternet = montoInternet;
    }

    public Integer getMontoMorosoInternet() {
        return montoMorosoInternet;
    }

    public void setMontoMorosoInternet(Integer montoMorosoInternet) {
        this.montoMorosoInternet = montoMorosoInternet;
    }

    public Integer getMontoAdelantadoInternet() {
        return montoAdelantadoInternet;
    }

    public void setMontoAdelantadoInternet(Integer montoAdelantadoInternet) {
        this.montoAdelantadoInternet = montoAdelantadoInternet;
    }

    public Integer getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Integer montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Integer getMontoOtros() {
        return montoOtros;
    }

    public void setMontoOtros(Integer montoOtros) {
        this.montoOtros = montoOtros;
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
