package tele.costa.web.caja;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import tele.costa.api.ejb.CajaBeanLocal;
import tele.costa.api.ejb.CatalogoBeanLocal;
import tele.costa.api.entity.Municipio;
import tele.costa.api.entity.Sectorpago;
import telecosta.web.utils.JasperUtil;
import telecosta.web.utils.JsfUtil;
import telecosta.web.utils.ReporteJasper;
import telecosta.web.utils.SesionUsuarioMB;

/**
 *
 * @author rcacacho
 */
@ManagedBean(name = "reporteCajaMB")
@ViewScoped
public class ReporteCajaMB implements Serializable {

    private static final Logger log = Logger.getLogger(ReporteCajaMB.class);

    @EJB
    private CatalogoBeanLocal catalogoBean;
    @EJB
    private CajaBeanLocal cajaBean;

    @Resource(lookup = "jdbc/telecosta")
    private DataSource dataSource;

    private Date fechaInicio;
    private Date fechaFin;
    private List<Municipio> listMunicipios;
    private Municipio municipioSelected;
    private Integer idSectorPago;
    private List<Sectorpago> listSectorPago;

    @PostConstruct
    void cargarDatos() {
        listMunicipios = catalogoBean.listMunicipioByIdDepartamento(1);
    }

    public void cargarSector() {
        listSectorPago = catalogoBean.listSectorPagoByIdMunicipio(municipioSelected.getIdmunicipio());
    }

    public StreamedContent reporteCaja() {
        try {

            String nombreReporte = "";
            String fe = "";
            String fe2 = "";
            
            if (fechaInicio != null && fechaFin != null) {
                if (idSectorPago != null){
                    nombreReporte = "rptCajaAgenciaFechasSector";
                }else{
                    nombreReporte = "rptCajaAgenciaFechas";
                }
                
                Calendar c = Calendar.getInstance();
                c.setTime(fechaInicio);
                c.set(Calendar.HOUR_OF_DAY, 0);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
                fechaInicio = c.getTime();

                Calendar c1 = Calendar.getInstance();
                c1.setTime(fechaFin);
                c1.set(Calendar.HOUR_OF_DAY, 23);
                c1.set(Calendar.MINUTE, 59);
                c1.set(Calendar.SECOND, 59);
                fechaFin = c1.getTime();
                
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                fe = sdf.format(fechaInicio);
                fe2 = sdf.format(fechaFin);
            }

            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = servletContext.getRealPath("/");

            String nombreArchivo = "CajaAgencia.pdf";
            HashMap parametros = new HashMap();
            parametros.put("IMAGE", "logo.jpeg");
            parametros.put("DIRECTORIO", realPath + File.separator + "resources" + File.separator + "images" + File.separator);
            parametros.put("USUARIO", SesionUsuarioMB.getUserName());
            parametros.put("FECHA_INICIO", fe);
            parametros.put("FECHA_FIN", fe2);

            ReporteJasper reporteJasper = JasperUtil.jasperReportPDF(nombreReporte, nombreArchivo, parametros, dataSource);
            StreamedContent streamedContent;
            FileInputStream stream = new FileInputStream(realPath + "resources/reports/" + reporteJasper.getFileName());
            streamedContent = new DefaultStreamedContent(stream, "application/pdf", reporteJasper.getFileName());
            return streamedContent;
        } catch (Exception ex) {
            log.error(ex);
            JsfUtil.addErrorMessage("Ocurrio un error al generar el pdf del reporte");
        }
        return null;
    }

    /*Metodos getters y setters*/
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

    public List<Municipio> getListMunicipios() {
        return listMunicipios;
    }

    public void setListMunicipios(List<Municipio> listMunicipios) {
        this.listMunicipios = listMunicipios;
    }

    public Municipio getMunicipioSelected() {
        return municipioSelected;
    }

    public void setMunicipioSelected(Municipio municipioSelected) {
        this.municipioSelected = municipioSelected;
    }

    public Integer getIdSectorPago() {
        return idSectorPago;
    }

    public void setIdSectorPago(Integer idSectorPago) {
        this.idSectorPago = idSectorPago;
    }

    public List<Sectorpago> getListSectorPago() {
        return listSectorPago;
    }

    public void setListSectorPago(List<Sectorpago> listSectorPago) {
        this.listSectorPago = listSectorPago;
    }

}
