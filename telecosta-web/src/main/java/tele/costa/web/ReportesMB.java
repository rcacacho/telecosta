package tele.costa.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import tele.costa.api.ejb.PagosBeanLocal;
import telecosta.web.utils.JasperUtil;
import telecosta.web.utils.JsfUtil;
import telecosta.web.utils.ReporteJasper;
import telecosta.web.utils.SesionUsuarioMB;

/**
 *
 * @author elfo_
 */
@ManagedBean(name = "reporteMB")
@ViewScoped
public class ReportesMB implements Serializable {

    private static final Logger log = Logger.getLogger(ReportesMB.class);

    @Resource(lookup = "jdbc/telecosta")
    private DataSource dataSource;

    @EJB
    private PagosBeanLocal pagosBean;

    private Date fechaIncio;
    private Date fechaFin;
    private Date fechaIncioCobro;
    private Date fechaFinCobro;

    public ReportesMB() {
        fechaIncio = null;
        fechaFin = null;
    }

    public StreamedContent generarPdfPago() {
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(fechaIncio);
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            fechaIncio = c.getTime();

            Calendar c1 = Calendar.getInstance();
            c1.setTime(fechaFin);
            c1.set(Calendar.HOUR_OF_DAY, 23);
            c1.set(Calendar.MINUTE, 59);
            c1.set(Calendar.SECOND, 59);
            fechaFin = c1.getTime();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.format(fechaIncio);
            sdf.format(fechaFin);

            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = servletContext.getRealPath("/");
            String nombreReporte = "rpt_pagos";
            String nombreArchivo = "Pagos.pdf";
            HashMap parametros = new HashMap();
            parametros.put("IMAGE", "logo.jpeg");
            parametros.put("DIRECTORIO", realPath + File.separator + "resources" + File.separator + "images" + File.separator);
            parametros.put("USUARIO", SesionUsuarioMB.getUserName());
            parametros.put("FECHA_INICIO", fechaIncio);
            parametros.put("FECHA_FIN", fechaFin);

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
    
    public StreamedContent generarPdfCobro() {
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(fechaIncioCobro);
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            fechaIncioCobro = c.getTime();

            Calendar c1 = Calendar.getInstance();
            c1.setTime(fechaFinCobro);
            c1.set(Calendar.HOUR_OF_DAY, 23);
            c1.set(Calendar.MINUTE, 59);
            c1.set(Calendar.SECOND, 59);
            fechaFinCobro = c1.getTime();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.format(fechaIncioCobro);
            sdf.format(fechaFinCobro);

            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = servletContext.getRealPath("/");
            String nombreReporte = "rptCobros";
            String nombreArchivo = "Cobros.pdf";
            HashMap parametros = new HashMap();
            parametros.put("IMAGE", "logo.jpeg");
            parametros.put("DIRECTORIO", realPath + File.separator + "resources" + File.separator + "images" + File.separator);
            parametros.put("USUARIO", SesionUsuarioMB.getUserName());
            parametros.put("FECHA_INICIO", fechaIncioCobro);
            parametros.put("FECHA_FIN", fechaFinCobro);

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
    public Date getFechaIncio() {
        return fechaIncio;
    }

    public void setFechaIncio(Date fechaIncio) {
        this.fechaIncio = fechaIncio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaIncioCobro() {
        return fechaIncioCobro;
    }

    public void setFechaIncioCobro(Date fechaIncioCobro) {
        this.fechaIncioCobro = fechaIncioCobro;
    }

    public Date getFechaFinCobro() {
        return fechaFinCobro;
    }

    public void setFechaFinCobro(Date fechaFinCobro) {
        this.fechaFinCobro = fechaFinCobro;
    }

}
