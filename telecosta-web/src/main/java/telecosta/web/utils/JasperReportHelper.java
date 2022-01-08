package telecosta.web.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleDocxReportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author rcacacho
 */
public abstract class JasperReportHelper<T> {

    private static final Logger log = Logger.getLogger(JasperReportHelper.class);

    private final String LOGO = "Logo_MP_Web.png";
    private String realPath;
    private String dirSeparator;
    private String jasperFileName;
    private String outputFileName;
    private ReportFormat format;
    private List documentos = new ArrayList();

    public JasperReportHelper(String jasperFileName, String outputFileName, ReportFormat format) {
        if (jasperFileName == null || jasperFileName.isEmpty()) {
            throw new IllegalArgumentException("jasperFileName can´t be null!");
        }

        if (outputFileName == null || outputFileName.isEmpty()) {
            throw new IllegalArgumentException("outputFileName can´t be null!");
        }

        if (format == null) {
            throw new IllegalArgumentException("format can´t be null!");
        }

        outputFileName = outputFileName.toLowerCase();

        if (!jasperFileName.contains(".jasper")) {
            jasperFileName = jasperFileName.concat(".jasper");
        }

        this.jasperFileName = jasperFileName;
        this.outputFileName = outputFileName.concat(format.getExtension());
        this.format = format;
    }

    public abstract Collection<T> getData();

    public StreamedContent fillReport(Map parameters) throws SQLException {
        return fillReport(parameters, null, null);
    }

    public StreamedContent fillReport(Map parameters, DataSource ds) throws SQLException {
        return fillReport(parameters, null, ds);
    }

    public StreamedContent fillManyReports(Map parameters, List<String> reports) throws SQLException {
        return fillManyReport(parameters, null, null, reports);
    }

    public StreamedContent fillReport(Map parameters, String outputFile) {
        return fillReportOutPutFile(parameters, null, null, outputFile);
    }

    public StreamedContent fillReportWithDs(Map parameters, String outputFile, DataSource ds) {
        return fillReportOutPutFile(parameters, null, ds, outputFile);
    }

    public StreamedContent fillManyReport(Map parameters, String outputDirectory, DataSource ds, List<String> reports) throws SQLException {

        if (jasperFileName == null || jasperFileName.isEmpty()) {
            return null;
        }

        if (outputFileName == null || outputFileName.isEmpty()) {
            return null;
        }

        StreamedContent generatedReport = null;
        Connection connection;
        connection = null;

        try {

            if (ds != null) {
                connection = ds.getConnection();
            }

            //System.out.print("ENTROOOOO A PARAMETROS.");
            if (parameters == null) {
                parameters = new HashMap<>();
            }

            Exporter exporter = null;

            if (format == ReportFormat.EXCEL) {
                parameters.put("IS_IGNORE_PAGINATION", Boolean.TRUE);

                SimpleXlsxReportConfiguration config = new SimpleXlsxReportConfiguration();
                config.setDetectCellType(Boolean.TRUE);
                config.setOnePagePerSheet(Boolean.TRUE);
                config.setIgnorePageMargins(Boolean.TRUE);
                config.setSheetNames(new String[]{"matriz-requerimientos"});

                exporter = new JRXlsxExporter();
                exporter.setConfiguration(config);

            } else if (format == ReportFormat.WORD) {
                SimpleDocxReportConfiguration config = new SimpleDocxReportConfiguration();
                config.setFlexibleRowHeight(true);
                config.setFramesAsNestedTables(Boolean.FALSE);

                exporter = new JRDocxExporter();
                exporter.setConfiguration(config);
            } else {
                exporter = new JRPdfExporter();
            }

            //si outputDirectory es null se obtiene directorios por defecto
            String outputDir;
            String reportsDir;
            String imagesDir;

            if (outputDirectory == null) {
                outputDir = getOutputDir();
                reportsDir = getReportsDir();
                imagesDir = getImagesDir();
            } else {
                outputDir = outputDirectory;
                reportsDir = outputDirectory;
                imagesDir = outputDirectory;
            }

            parameters.put("P_SUBRPT_DIR", reportsDir);
            parameters.put("P_LOGO_PATH", imagesDir + LOGO);
            parameters.put(JRParameter.REPORT_LOCALE, new Locale("es", "GT"));

            InputStream reportStream = new FileInputStream(reportsDir + jasperFileName);

            JRDataSource datasource = null;

            JasperPrint print;

            // si el datasource es null, utiliza el metodo abstracto getData() para uso de beans            
            if (ds == null) {
                datasource = new JRBeanCollectionDataSource(getData(), true);
                print = JasperFillManager.fillReport(reportStream, parameters, datasource);

                for (String report : reports) {
                    InputStream reportStreamV = new FileInputStream(reportsDir + report);

                    JRDataSource datasourceC = new JRBeanCollectionDataSource(getData(), true);

                    JasperPrint jasperPrint_next = JasperFillManager.fillReport(reportStreamV, parameters, datasourceC);

                    if (jasperPrint_next.getPages() != null) {
                        List pages = jasperPrint_next.getPages();
                        for (int j = 0; j < pages.size(); j++) {
                            JRPrintPage object = (JRPrintPage) pages.get(j);
                            print.addPage(object);
                        }
                    }

                }

            } else {
                print = JasperFillManager.fillReport(reportStream, parameters, connection);

                for (String report : reports) {
                    InputStream reportStreamV = new FileInputStream(reportsDir + report);

                    JasperPrint jasperPrint_next = JasperFillManager.fillReport(reportStreamV, parameters, connection);

                    if (jasperPrint_next.getPages() != null) {
                        List pages = jasperPrint_next.getPages();
                        for (int j = 0; j < pages.size(); j++) {
                            JRPrintPage object = (JRPrintPage) pages.get(j);
                            print.addPage(object);
                        }
                    }
                }
            }

            String outputReportFile = outputDir + outputFileName;

            exporter.setExporterInput(new SimpleExporterInput(print));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputReportFile));
            exporter.exportReport();

            FileInputStream stream = new FileInputStream(outputReportFile);
            generatedReport = new DefaultStreamedContent(stream, format.getFileContentType(), outputFileName);

        } catch (FileNotFoundException | JRException e) {
            log.error(String.format("Error al generar reporte %s ", jasperFileName));
            log.error(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

        return generatedReport;
    }

    public StreamedContent fillReport(Map parameters, String outputDirectory, DataSource ds) throws SQLException {

        if (jasperFileName == null || jasperFileName.isEmpty()) {
            return null;
        }

        if (outputFileName == null || outputFileName.isEmpty()) {
            return null;
        }

        StreamedContent generatedReport = null;
        Connection connection;
        connection = null;

        try {

            if (ds != null) {
                connection = ds.getConnection();
            }

            //System.out.print("ENTROOOOO A PARAMETROS.");
            if (parameters == null) {
                parameters = new HashMap<>();
            }

            Exporter exporter = null;

            if (format == ReportFormat.EXCEL) {
                parameters.put("IS_IGNORE_PAGINATION", Boolean.TRUE);

                SimpleXlsxReportConfiguration config = new SimpleXlsxReportConfiguration();
                config.setDetectCellType(Boolean.TRUE);
                config.setOnePagePerSheet(Boolean.TRUE);
                config.setIgnorePageMargins(Boolean.TRUE);
                config.setSheetNames(new String[]{"matriz-requerimientos"});

                exporter = new JRXlsxExporter();
                exporter.setConfiguration(config);

            } else if (format == ReportFormat.WORD) {
                SimpleDocxReportConfiguration config = new SimpleDocxReportConfiguration();
                config.setFlexibleRowHeight(true);
                config.setFramesAsNestedTables(Boolean.FALSE);

                exporter = new JRDocxExporter();
                exporter.setConfiguration(config);
            } else {
                exporter = new JRPdfExporter();
            }

            //si outputDirectory es null se obtiene directorios por defecto
            String outputDir;
            String reportsDir;
            String imagesDir;

            if (outputDirectory == null) {
                outputDir = getOutputDir();
                reportsDir = getReportsDir();
                imagesDir = getImagesDir();
            } else {
                outputDir = outputDirectory;
                reportsDir = outputDirectory;
                imagesDir = outputDirectory;
            }

            parameters.put("P_SUBRPT_DIR", reportsDir);
            parameters.put("P_LOGO_PATH", imagesDir + LOGO);
            parameters.put(JRParameter.REPORT_LOCALE, new Locale("es", "GT"));

            InputStream reportStream = new FileInputStream(reportsDir + jasperFileName);

            JRDataSource datasource;
            JasperPrint print;

            // si el datasource es null, utiliza el metodo abstracto getData() para uso de beans            
            if (ds == null) {
                datasource = new JRBeanCollectionDataSource(getData(), true);
                print = JasperFillManager.fillReport(reportStream, parameters, datasource);
            } else {
                print = JasperFillManager.fillReport(reportStream, parameters, connection);

            }

            String outputReportFile = outputDir + outputFileName;

            exporter.setExporterInput(new SimpleExporterInput(print));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputReportFile));
            exporter.exportReport();

            FileInputStream stream = new FileInputStream(outputReportFile);
            generatedReport = new DefaultStreamedContent(stream, format.getFileContentType(), outputFileName);

        } catch (FileNotFoundException | JRException e) {
            log.error(String.format("Error al generar reporte %s ", jasperFileName));
            log.error(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

        return generatedReport;
    }

    public StreamedContent fillReportOutPutFile(Map parameters, String outputDirectory, DataSource ds, String outputFile) {

        if (jasperFileName == null || jasperFileName.isEmpty()) {
            return null;
        }

        if (outputFileName == null || outputFileName.isEmpty()) {
            return null;
        }

        StreamedContent generatedReport = null;

        try {
            if (parameters == null) {
                parameters = new HashMap<>();
            }

            Exporter exporter = null;

            if (format == ReportFormat.EXCEL) {
                parameters.put("IS_IGNORE_PAGINATION", Boolean.TRUE);

                SimpleXlsxReportConfiguration config = new SimpleXlsxReportConfiguration();
                config.setDetectCellType(Boolean.TRUE);
                config.setOnePagePerSheet(Boolean.TRUE);
                config.setIgnorePageMargins(Boolean.TRUE);
                config.setSheetNames(new String[]{"matriz-requerimientos"});

                exporter = new JRXlsxExporter();
                exporter.setConfiguration(config);

            } else if (format == ReportFormat.WORD) {
                SimpleDocxReportConfiguration config = new SimpleDocxReportConfiguration();
                config.setFlexibleRowHeight(true);
                config.setFramesAsNestedTables(Boolean.FALSE);

                exporter = new JRDocxExporter();
                exporter.setConfiguration(config);
            } else {
                exporter = new JRPdfExporter();
            }

            //si outputDirectory es null se obtiene directorios por defecto
            String outputDir;
            String reportsDir;
            String imagesDir;

            if (outputDirectory == null) {
                outputDir = getOutputDir();
                reportsDir = getReportsDir();
                imagesDir = getImagesDir();
            } else {
                outputDir = outputDirectory;
                reportsDir = outputDirectory;
                imagesDir = outputDirectory;
            }

            parameters.put("P_SUBRPT_DIR", reportsDir);
            parameters.put("P_LOGO_PATH", imagesDir + LOGO);
            parameters.put(JRParameter.REPORT_LOCALE, new Locale("es", "GT"));

            InputStream reportStream = new FileInputStream(reportsDir + jasperFileName);

            JRDataSource datasource;
            JasperPrint print;

            // si el datasource es null, utiliza el metodo abstracto getData() para uso de beans            
            if (ds == null) {
                datasource = new JRBeanCollectionDataSource(getData(), true);
                print = JasperFillManager.fillReport(reportStream, parameters, datasource);
            } else {

                try (Connection connection = ds.getConnection()) {
                    print = JasperFillManager.fillReport(reportStream, parameters, connection);
                } catch (SQLException ex) {
                    return null;
                }

            }

            String outputReportFile = outputFile + outputFileName;

            exporter.setExporterInput(new SimpleExporterInput(print));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputReportFile));
            exporter.exportReport();

            FileInputStream stream = new FileInputStream(outputReportFile);
            generatedReport = new DefaultStreamedContent(stream, format.getFileContentType(), outputFileName);

        } catch (FileNotFoundException | JRException e) {
            log.error(String.format("Error al generar reporte %s ", jasperFileName));
            log.error(e);
        }
        return generatedReport;
    }

    public StreamedContent fillReportCon(Map parameters, DataSource ds) throws SQLException {

        Connection conn = ds.getConnection();

        if (jasperFileName == null || jasperFileName.isEmpty()) {
            return null;
        }

        if (outputFileName == null || outputFileName.isEmpty()) {
            return null;
        }

        StreamedContent generatedReport = null;

        try {
            if (parameters == null) {
                parameters = new HashMap<>();
            }

            Exporter exporter = null;

            if (format == ReportFormat.EXCEL) {
                parameters.put("IS_IGNORE_PAGINATION", Boolean.TRUE);

                SimpleXlsxReportConfiguration config = new SimpleXlsxReportConfiguration();
                config.setDetectCellType(Boolean.TRUE);
                config.setOnePagePerSheet(Boolean.TRUE);
                config.setIgnorePageMargins(Boolean.TRUE);
                config.setSheetNames(new String[]{"matriz-requerimientos"});

                exporter = new JRXlsxExporter();
                exporter.setConfiguration(config);

            } else if (format == ReportFormat.WORD) {
                SimpleDocxReportConfiguration config = new SimpleDocxReportConfiguration();
                config.setFlexibleRowHeight(true);
                config.setFramesAsNestedTables(Boolean.FALSE);

                exporter = new JRDocxExporter();
                exporter.setConfiguration(config);
            } else {
                exporter = new JRPdfExporter();
            }

            parameters.put("P_SUBRPT_DIR", getReportsDir());
            parameters.put("P_LOGO_PATH", getImagesDir() + LOGO);
            parameters.put(JRParameter.REPORT_LOCALE, new Locale("es", "GT"));

            InputStream reportStream = new FileInputStream(getReportsDir() + jasperFileName);

            JRDataSource datasource = new JRBeanCollectionDataSource(getData(), true);
            JasperPrint print = JasperFillManager.fillReport(reportStream, parameters, conn);

            String outputReportFile = getOutputDir() + outputFileName;
            exporter.setExporterInput(new SimpleExporterInput(print));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputReportFile));

            exporter.exportReport();

            FileInputStream stream = new FileInputStream(outputReportFile);
            generatedReport = new DefaultStreamedContent(stream, format.getFileContentType(), outputFileName);

        } catch (FileNotFoundException | JRException e) {
            log.error(String.format("Error al generar reporte %s ", jasperFileName));
            log.error(e);
        } finally {
            conn.close();
        }

        return null;

    }

    public void parametrosReporte(String vReporte, String vNombreDocumento, String... vParametros) {
        List reporte = new ArrayList();
        List parametros = new ArrayList();
        int contador = 0;
        String tipoDato = "";
        for (String parametro : vParametros) {
            switch (contador) {
                case 0:
                    parametros.add(parametro);
                    break;
                case 1:
                    tipoDato = parametro;
                    break;
                case 2:
                    if (tipoDato.equals("Integer")) {
                        parametros.add(Integer.parseInt(parametro));
                    }
                    break;
                default:
                    break;

            }
            if (contador == 2) {
                contador = 0;
            } else {
                contador++;
            }
        }

        reporte.add(vReporte);
        reporte.add(vNombreDocumento);
        reporte.add(parametros);

        documentos.add(reporte);
    }

    public StreamedContent fillReportZip(DataSource ds) throws SQLException {
        Connection conn = ds.getConnection();
        StreamedContent generatedReport = null;

        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ZipOutputStream zipOut = new ZipOutputStream(baos);
            HashMap parameters;
            InputStream reportStream;
            JasperPrint print;
            File file;
            OutputStream output;
            InputStream input;
            byte[] buf = new byte[2048];
            ArrayList documento = new ArrayList();
            String nombreReporte;
            String nombreDocumento;
            ArrayList parametros = new ArrayList();
            int numeroDocumento = 0;
            file = File.createTempFile("archivoTemporal", ".pdf");
            //file.createNewFile();

            Iterator iteraDocs = documentos.iterator();
            while (iteraDocs.hasNext()) {
                numeroDocumento++;
                documento = (ArrayList) iteraDocs.next();
                nombreReporte = (String) documento.get(0);
                reportStream = new FileInputStream(getReportsDir() + nombreReporte);
                nombreDocumento = (String) documento.get(1);
                nombreDocumento = nombreDocumento + numeroDocumento + ".pdf";
                parametros = (ArrayList) documento.get(2);
                parameters = new HashMap();
                parameters.put("P_SUBRPT_DIR", getReportsDir());
                parameters.put("P_LOGO_PATH", getImagesDir() + LOGO);
                parameters.put(JRParameter.REPORT_LOCALE, new Locale("es", "GT"));
                parameters.put(parametros.get(0), parametros.get(1));
                print = JasperFillManager.fillReport(reportStream, parameters, conn);
                output = new BufferedOutputStream(new FileOutputStream(file));
                JasperExportManager.exportReportToPdfStream(print, output);
                input = new BufferedInputStream(new FileInputStream(file));

                zipOut.putNextEntry(new ZipEntry(nombreDocumento));

                int bytesRead;
                while ((bytesRead = input.read(buf)) != -1) {
                    zipOut.write(buf, 0, bytesRead);
                }
                output.close();
                zipOut.closeEntry();
                input.close();

            }

            zipOut.flush();
            baos.flush();
            zipOut.close();
            baos.close();
            file.delete();
            InputStream isZipDoc = new ByteArrayInputStream(baos.toByteArray());

            generatedReport = new DefaultStreamedContent(isZipDoc, format.getFileContentType(), outputFileName);

        } catch (JRException e) {
            log.error("Error en generar el reporte en Jasper Report", e);
        } catch (Exception e) {
            log.error("Error en generar el reporte en Jasper Report", e);
        }
        return null;

    }

    public String getRealPath() {
        if (realPath == null) {
            realPath = ((ServletContext) (FacesContext.getCurrentInstance()).getExternalContext().getContext()).getRealPath("/");
        }
        return this.realPath;
    }

    public String getDirSeparator() {
        if (dirSeparator == null) {
            dirSeparator = System.getProperty("file.separator");
        }
        return this.dirSeparator;
    }

    public String getImagesDir() {
        return String.format("%sresources%simages%s", getRealPath(), getDirSeparator(), getDirSeparator());

    }

    public String getReportsDir() {
        return String.format("%sresources%sreports%s", getRealPath(), getDirSeparator(), getDirSeparator());
    }

    public String getOutputDir() {
        return String.format("%sresources%sreports%sgenerated%s", getRealPath(), getDirSeparator(), getDirSeparator(), getDirSeparator());
    }

    public String getJasperFileName() {
        return jasperFileName;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

}
