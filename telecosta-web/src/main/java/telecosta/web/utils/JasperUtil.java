package telecosta.web.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;

/**
 *
 * @author aeruano
 */
public class JasperUtil {

    private static final String PREFIX = "/resources/reports/";
    private static final String SUFFIX = ".jasper";

    public JasperUtil() {
    }

    public static ReporteJasper jasperReportPDF(String reportName, String nombreArchivo, @SuppressWarnings("rawtypes") Map params, DataSource ds) throws SQLException {

        ReporteJasper reporte = null;
        String fileName;
        Connection conn = ds.getConnection();
        ExternalContext eContext = FacesContext.getCurrentInstance().getExternalContext();

        try {

            InputStream stream = eContext.getResourceAsStream(PREFIX + reportName + SUFFIX);

            if (stream != null) {

                ServletContext sContext = (ServletContext) eContext.getContext();
                String realPath = sContext.getRealPath("/resources/reports/");
                String fileSeparator = System.getProperty("file.separator");
                realPath += fileSeparator;
                params.put(JRParameter.REPORT_LOCALE, new Locale("es", "GT"));

                JasperPrint jasperPrint = JasperFillManager.fillReport(stream, params, conn);

                fileName = nombreArchivo;

                JRExporter exporter = new JRPdfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, realPath + fileName);
                exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");

                exporter.exportReport();

                reporte = new ReporteJasper();
                reporte.setFileName(fileName);
                reporte.setPages(jasperPrint.getPages().size());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            conn.close();
        }
        return reporte;
    }

    public static ReporteJasper jasperReportXLS(String reportName, String nombreArchivo, @SuppressWarnings("rawtypes") Map params, DataSource ds) throws SQLException {

        ReporteJasper reporte = null;

        String fileName;
        Connection conn = ds.getConnection();

        ExternalContext eContext = FacesContext.getCurrentInstance().getExternalContext();

        try {
            InputStream stream = eContext.getResourceAsStream(PREFIX + reportName + SUFFIX);

            if (stream != null) {

                ServletContext sContext = (ServletContext) eContext.getContext();
                String realPath = sContext.getRealPath("/resources/reports/");
                String fileSeparator = System.getProperty("file.separator");
                realPath += fileSeparator;
                params.put(JRParameter.REPORT_LOCALE, new Locale("es", "GT"));
                JasperPrint jasperPrint = JasperFillManager.fillReport(stream, params, conn);

                fileName = nombreArchivo;

                JRXlsExporter exporter = new JRXlsExporter();
                exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, realPath + fileName);
                exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
                exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
                exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
                exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);

                exporter.exportReport();

                reporte = new ReporteJasper();
                reporte.setFileName(fileName);
                reporte.setPages(jasperPrint.getPages().size());

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            conn.close();
        }

        return reporte;
    }

    public static ReporteJasper jasperReportTxt(String reportName, String nombreArchivo, @SuppressWarnings("rawtypes") Map params, DataSource ds) throws SQLException {

        ReporteJasper reporte = null;

        String fileName;
        Connection conn = ds.getConnection();

        ExternalContext eContext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            InputStream stream = eContext.getResourceAsStream(PREFIX + reportName + SUFFIX);

            if (stream != null) {

                ServletContext sContext = (ServletContext) eContext.getContext();
                String realPath = sContext.getRealPath("/resources/reports/");
                String fileSeparator = System.getProperty("file.separator");
                realPath += fileSeparator;

                JasperPrint jasperPrint = JasperFillManager.fillReport(stream, params, conn);

                fileName = nombreArchivo;

                JRExporter exporter = new JRTextExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                //exporter.setParameter(JRTextExporterParameter.LINE_SEPARATOR, "\r\n");
                exporter.setExporterOutput(new SimpleWriterExporterOutput(realPath + fileName));
                exporter.exportReport();

                reporte = new ReporteJasper();
                reporte.setFileName(fileName);
                reporte.setPages(jasperPrint.getPages().size());

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            conn.close();
        }
        return reporte;
    }

    public static ReporteJasper jasperReportCsv(String reportName, String nombreArchivo, @SuppressWarnings("rawtypes") Map params, DataSource ds) throws SQLException {

        ReporteJasper reporte = null;

        String fileName;
        Connection conn = ds.getConnection();

        ExternalContext eContext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            InputStream stream = eContext.getResourceAsStream(PREFIX + reportName + SUFFIX);

            if (stream != null) {

                ServletContext sContext = (ServletContext) eContext.getContext();
                String realPath = sContext.getRealPath("/resources/reports/");
                String fileSeparator = System.getProperty("file.separator");
                realPath += fileSeparator;

                JasperPrint jasperPrint = JasperFillManager.fillReport(stream, params, conn);

                fileName = nombreArchivo;

                JRExporter exporter = new JRCsvExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                //exporter.setParameter(JRTextExporterParameter.LINE_SEPARATOR, "\r\n");
                exporter.setExporterOutput(new SimpleWriterExporterOutput(realPath + fileName));
                exporter.exportReport();

                reporte = new ReporteJasper();
                reporte.setFileName(fileName);
                reporte.setPages(jasperPrint.getPages().size());

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            conn.close();
        }
        return reporte;
    }

    public static ReporteJasper jasperReportNewTxt(String reportName, String nombreArchivo, @SuppressWarnings("rawtypes") Map params, DataSource ds) throws SQLException {

        ReporteJasper reporte = null;

        String fileName;
        Connection conn = ds.getConnection();

        ExternalContext eContext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            InputStream stream = eContext.getResourceAsStream(PREFIX + reportName + SUFFIX);

            if (stream != null) {

                ServletContext sContext = (ServletContext) eContext.getContext();
                String realPath = sContext.getRealPath("/resources/reports/");
                String fileSeparator = System.getProperty("file.separator");
                realPath += fileSeparator;

                JasperPrint jasperPrint = JasperFillManager.fillReport(stream, params, conn);

                fileName = nombreArchivo;

                JRExporter exporter = new JRTextExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                //exporter.setParameter(JRTextExporterParameter.LINE_SEPARATOR, "\r\n");
                exporter.setExporterOutput(new SimpleWriterExporterOutput(realPath + fileName));
                exporter.exportReport();

                reporte = new ReporteJasper();
                reporte.setFileName(fileName);

                if (jasperPrint.getPages() != null) {
                    reporte.setPages(jasperPrint.getPages().size());
                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            conn.close();
        }
        return reporte;
    }
}
