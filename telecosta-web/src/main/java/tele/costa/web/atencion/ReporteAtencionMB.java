package tele.costa.web.atencion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
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
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import tele.costa.api.dto.ReporteAtencionDto;
import tele.costa.api.ejb.AtencionClienteLocal;
import tele.costa.api.ejb.CatalogoBeanLocal;
import tele.costa.api.entity.Ruta;
import telecosta.web.utils.JasperUtil;
import telecosta.web.utils.JsfUtil;
import telecosta.web.utils.ReporteJasper;
import telecosta.web.utils.SesionUsuarioMB;

/**
 *
 * @author rcacacho
 */
@ManagedBean(name = "reporteAtencionMB")
@ViewScoped
public class ReporteAtencionMB implements Serializable {

    private static final Logger log = Logger.getLogger(ReporteAtencionMB.class);

    @Resource(lookup = "jdbc/telecosta")
    private DataSource dataSource;

    @EJB
    private CatalogoBeanLocal catalogoBean;
    @EJB
    private AtencionClienteLocal atencionBean;

    private Integer idRuta;
    private List<Ruta> listRuta;
    private Date fechaIncio;
    private Date fechaFin;
    private final String LOGO = "logo.jpeg";
    private String realPath;
    private String dirSeparator;

    public ReporteAtencionMB() {
        fechaIncio = null;
        fechaFin = null;
    }

    @PostConstruct
    void cargarDatos() {
        listRuta = catalogoBean.listRuta();
    }

    public StreamedContent generarPdfFechas() {
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
            String fe = sdf.format(fechaIncio);
            String fe2 = sdf.format(fechaFin);

            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = servletContext.getRealPath("/");
            String nombreReporte = "rptAtencionFechas";
            String nombreArchivo = "Tickets.pdf";
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

    public String getImagesDir() {
        return String.format("%sresources%simages%s", getRealPath(), getDirSeparator(), getDirSeparator());
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

    public String getOutputDir() {
        return String.format("%sresources%sreports%sgenerated%s", getRealPath(), getDirSeparator(), getDirSeparator(), getDirSeparator());
    }

    class Fila {

        int cellIndex;
        Row fila;
        BigDecimal total;
        Integer cantidad;

        public Fila() {
            cellIndex = 0;
            total = new BigDecimal(0);
            cantidad = 0;
        }

        public Fila(Row fila) {
            this();
            this.fila = fila;
        }

        public void incrementarMonto(BigDecimal monto) {
            total = total.add(monto);
        }

        public void incrementarCantidad(Integer cantidad) {
            this.cantidad += cantidad;
        }

        public Integer nextIndex() {
            return cellIndex++;
        }

        public Integer getCellIndex() {
            return cellIndex;
        }

        public Row getFila() {
            return fila;
        }

        public void setFila(Row fila) {
            this.fila = fila;
        }

        public BigDecimal getTotal() {
            return total;
        }

        public Integer getCantidad() {
            return cantidad;
        }
    }

    public StreamedContent imprimirExcelAtencionFechas() throws IOException {
        if (fechaFin == null) {
            JsfUtil.addErrorMessage("Debe seleccionar una fecha inicio para generar el reporte");
            return null;
        }
        if (fechaIncio == null) {
            JsfUtil.addErrorMessage("Debe seleccionar una fecha fin para generar el reporte");
            return null;
        }

        StreamedContent content = null;
        List<ReporteAtencionDto> listAtencion = atencionBean.listAtencionFechas(fechaIncio, fechaFin);

        HashMap<Integer, Fila> mapaFilas = new HashMap<>();
        Workbook workbook = new SXSSFWorkbook(1000);
        Sheet sheet = workbook.createSheet("REPORTE_TICKET");

        int rownum = 0;
        int headerNum = 0;
        sheet.setColumnWidth(0, 900);
        sheet.setColumnWidth(1, 9000);
        sheet.setColumnWidth(2, 9000);
        sheet.setColumnWidth(3, 6000);
        sheet.setColumnWidth(4, 7000);
        sheet.setColumnWidth(5, 5000);
        sheet.setColumnWidth(6, 5000);
        sheet.setColumnWidth(7, 5000);
        sheet.setColumnWidth(8, 5000);
        sheet.setColumnWidth(9, 5000);
        sheet.setColumnWidth(10, 5000);
        sheet.setColumnWidth(11, 5000);
        sheet.setColumnWidth(12, 11000);
        sheet.setColumnWidth(13, 12000);
        sheet.setColumnWidth(14, 14000);
        sheet.setColumnWidth(15, 2000);
        sheet.setColumnWidth(16, 6000);
        sheet.setColumnWidth(17, 5000);
        sheet.setColumnWidth(18, 4000);
        sheet.setColumnWidth(19, 6000);
        sheet.setColumnWidth(20, 6000);
        sheet.setColumnWidth(21, 7000);
        sheet.setColumnWidth(22, 7000);
        sheet.setColumnWidth(23, 7000);
        sheet.setColumnWidth(24, 19000);

        CellStyle headerStyle = workbook.createCellStyle();
        XSSFColor color = new XSSFColor(new java.awt.Color(255, 250, 250));
        ((XSSFCellStyle) headerStyle).setFillForegroundColor(color);
        headerStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);

        CellStyle cellStyle = workbook.createCellStyle();
        XSSFColor colorBlanco = new XSSFColor(new java.awt.Color(255, 250, 250));
        ((XSSFCellStyle) cellStyle).setFillForegroundColor(colorBlanco);
        Font font = workbook.createFont();//Create font
        cellStyle.setFont(font);//set it to bold

        CellStyle cellStyleTitulo = workbook.createCellStyle();
        XSSFColor colorBlancoTitulo = new XSSFColor(new java.awt.Color(255, 250, 250));
        ((XSSFCellStyle) cellStyleTitulo).setFillForegroundColor(colorBlancoTitulo);
        Font fontTitulo = workbook.createFont();//Create font
        fontTitulo.setBoldweight(Font.BOLDWEIGHT_BOLD);//Make font bold
        fontTitulo.setFontName(HSSFFont.FONT_ARIAL);
        fontTitulo.setFontHeightInPoints((short) 14);
        cellStyleTitulo.setFont(fontTitulo);//set it to bold

        CellStyle cellStyle2 = workbook.createCellStyle();
        XSSFColor colorBlanco2 = new XSSFColor(new java.awt.Color(255, 250, 250));
        ((XSSFCellStyle) cellStyle2).setFillForegroundColor(colorBlanco2);

        CellStyle cellStyleNumero = workbook.createCellStyle();
        XSSFColor colorBlanco3 = new XSSFColor(new java.awt.Color(255, 250, 250));
        ((XSSFCellStyle) cellStyleNumero).setFillForegroundColor(colorBlanco3);
        DataFormat df = workbook.createDataFormat();
        cellStyleNumero.setDataFormat(df.getFormat("###,###,##0.00"));

        CellStyle cellStyleFecha = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        cellStyleFecha.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));

        for (int i = 1; i < 4; i++) {
            Fila fila = new Fila(sheet.createRow(rownum++));
            Cell cell = fila.getFila().createCell(fila.nextIndex().shortValue());
            cell.setCellValue("");
            cell.setCellStyle(cellStyle);
        }

        for (int i = 0; i < 1; i++) {
            Fila fila = new Fila(sheet.createRow(rownum++));
            Cell cell = fila.getFila().createCell(fila.nextIndex().shortValue());
            cell.setCellValue("");
            cell.setCellStyle(cellStyle);

            Cell cell13 = fila.getFila().createCell(fila.nextIndex().shortValue());
            cell13.setCellValue("");
            cell13.setCellStyle(cellStyle);

            Cell cell11 = fila.getFila().createCell(fila.nextIndex().shortValue());
            cell11.setCellValue("TELECOSTA");
            CellUtil.setAlignment(cell11, workbook, CellStyle.ALIGN_LEFT);
            cell11.setCellStyle(cellStyleTitulo);
        }

        for (int i = 0; i < 1; i++) {
            Fila fila = new Fila(sheet.createRow(rownum++));
            Cell cell12 = fila.getFila().createCell(fila.nextIndex().shortValue());
            cell12.setCellValue("");
            cell12.setCellStyle(cellStyle);

            Cell cell17 = fila.getFila().createCell(fila.nextIndex().shortValue());
            cell17.setCellValue("");
            cell17.setCellStyle(cellStyle);

            Cell cell15 = fila.getFila().createCell(fila.nextIndex().shortValue());
            cell15.setCellValue("Ticket de Atenci??n");
            cell15.setCellStyle(cellStyleTitulo);
        }

        Row encabezados = sheet.createRow(rownum++);
        InputStream inputStream = new FileInputStream(getImagesDir() + LOGO);
        byte[] bytes = IOUtils.toByteArray(inputStream);
        //Adds a picture to the workbook
        int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
        //close the input stream
        inputStream.close();
        //Returns an object that handles instantiating concrete classes
        CreationHelper helper = workbook.getCreationHelper();
        //Creates the top-level drawing patriarch.
        Drawing drawing = sheet.createDrawingPatriarch();
        //Create an anchor that is attached to the worksheet
        ClientAnchor anchor = helper.createClientAnchor();
        //set top-left corner for the image
        anchor.setCol1(3);
        anchor.setRow1(1);
        //Creates a picture
        Picture pict = drawing.createPicture(anchor, pictureIdx);
        //Reset the image to the original size
        pict.resize(0.6, 4);

        Cell celda0 = encabezados.createCell(headerNum++);
        celda0.setCellValue("No.");
        celda0.setCellStyle(headerStyle);
        Cell celda1 = encabezados.createCell(headerNum++);
        celda1.setCellValue("FECHA");
        celda1.setCellStyle(headerStyle);
        Cell celda2 = encabezados.createCell(headerNum++);
        celda2.setCellValue("NOMBRE DEL USUARIO");
        celda2.setCellStyle(headerStyle);
        Cell celda3 = encabezados.createCell(headerNum++);
        celda3.setCellValue("DIRECCI??N");
        celda3.setCellStyle(headerStyle);
        Cell celda4 = encabezados.createCell(headerNum++);
        celda4.setCellValue("TEL??FONO");
        celda4.setCellStyle(headerStyle);
        Cell celda5 = encabezados.createCell(headerNum++);
        celda5.setCellValue("MOTIVO DEL REPORTE");
        celda5.setCellStyle(headerStyle);
        Cell celda6 = encabezados.createCell(headerNum++);
        celda6.setCellValue("REFERENCIA");
        celda6.setCellStyle(headerStyle);
        Cell celda7 = encabezados.createCell(headerNum++);
        celda7.setCellValue("MATERIAL UTILIZADO");
        celda7.setCellStyle(headerStyle);
        Cell celda8 = encabezados.createCell(headerNum++);
        celda8.setCellValue("CANTIDAD");
        celda8.setCellStyle(headerStyle);
        Cell celda9 = encabezados.createCell(headerNum++);
        celda9.setCellValue("OBSERVACIONES");
        celda9.setCellStyle(headerStyle);
        int correlativo = 1;

        for (ReporteAtencionDto reporte : listAtencion) {
            if (!mapaFilas.containsKey(reporte.getIdatencion())) {
                Fila fila = new Fila(sheet.createRow(rownum++));
                mapaFilas.put(reporte.getIdatencion(), fila);

                Cell cell = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell.setCellValue(correlativo++);
                cell.setCellStyle(cellStyle);

                Cell cell1 = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell1.setCellValue(reporte.getFechacreacion());
                cell1.setCellStyle(cellStyleFecha);

                Cell cell2 = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell2.setCellValue(reporte.getNombre());
                cell2.setCellStyle(cellStyle);

                Cell cell3 = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell3.setCellValue(reporte.getDireccion());
                cell3.setCellStyle(cellStyle);

                if (reporte.getTelefono() != null) {
                    Cell cell4 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell4.setCellValue(reporte.getTelefono());
                    cell4.setCellStyle(cellStyleNumero);
                } else {
                    Cell cell4 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell4.setCellValue("");
                    cell4.setCellStyle(cellStyle);
                }

                if (reporte.getMotivo() != null) {
                    Cell cell5 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell5.setCellValue(reporte.getMotivo());
                    cell5.setCellStyle(cellStyle);
                } else {
                    Cell cell5 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell5.setCellValue("");
                    cell5.setCellStyle(cellStyle);
                }

                if (reporte.getReferencia() != null) {
                    Cell cell6 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell6.setCellValue(reporte.getReferencia());
                    cell6.setCellStyle(cellStyle);
                } else {
                    Cell cell6 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell6.setCellValue("");
                    cell6.setCellStyle(cellStyle);
                }

                if (reporte.getMaterialutilizado() != null) {
                    Cell cell7 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell7.setCellValue(reporte.getMaterialutilizado());
                    cell7.setCellStyle(cellStyle);
                } else {
                    Cell cell7 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell7.setCellValue("");
                    cell7.setCellStyle(cellStyle);
                }

                if (reporte.getCantidad() != null) {
                    Cell cell8 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell8.setCellValue(reporte.getCantidad());
                    cell8.setCellStyle(cellStyle);
                } else {
                    Cell cell8 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell8.setCellValue("");
                    cell8.setCellStyle(cellStyle);
                }

                if (reporte.getObservacion() != null) {
                    Cell cell9 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell9.setCellValue(reporte.getObservacion());
                    cell9.setCellStyle(cellStyle);
                } else {
                    Cell cell9 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell9.setCellValue("");
                    cell9.setCellStyle(cellStyle);
                }
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String outputFileName = "Reporte_" + sdf.format(new Date()) + ".xlsx";
        String outputReportFile = getOutputDir() + outputFileName;
        FileOutputStream out = new FileOutputStream(outputReportFile);
        workbook.write(out);
        out.close();
        ((SXSSFWorkbook) workbook).dispose();
        FileInputStream stream = new FileInputStream(outputReportFile);
        content = new DefaultStreamedContent(stream, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", outputFileName);

        return content;
    }

    public StreamedContent generarPdfRuta() {
        try {
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = servletContext.getRealPath("/");
            String nombreReporte = "rptAtencionRuta";
            String nombreArchivo = "Tickets.pdf";
            HashMap parametros = new HashMap();
            parametros.put("IMAGE", "logo.jpeg");
            parametros.put("DIRECTORIO", realPath + File.separator + "resources" + File.separator + "images" + File.separator);
            parametros.put("USUARIO", SesionUsuarioMB.getUserName());
            parametros.put("ID_RUTA", idRuta);

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

    public StreamedContent imprimirExcelAtencionRuta() throws IOException {
        if (idRuta == null) {
            JsfUtil.addErrorMessage("Debe seleccionar una ruta");
            return null;
        }

        StreamedContent content = null;
        List<ReporteAtencionDto> listAtencion = atencionBean.listAtencionByIdRuta(idRuta);

        HashMap<Integer, Fila> mapaFilas = new HashMap<>();
        Workbook workbook = new SXSSFWorkbook(1000);
        Sheet sheet = workbook.createSheet("REPORTE_TICKET");

        int rownum = 0;
        int headerNum = 0;
        sheet.setColumnWidth(0, 900);
        sheet.setColumnWidth(1, 9000);
        sheet.setColumnWidth(2, 9000);
        sheet.setColumnWidth(3, 6000);
        sheet.setColumnWidth(4, 7000);
        sheet.setColumnWidth(5, 5000);
        sheet.setColumnWidth(6, 5000);
        sheet.setColumnWidth(7, 5000);
        sheet.setColumnWidth(8, 5000);
        sheet.setColumnWidth(9, 5000);
        sheet.setColumnWidth(10, 5000);
        sheet.setColumnWidth(11, 5000);
        sheet.setColumnWidth(12, 11000);
        sheet.setColumnWidth(13, 12000);
        sheet.setColumnWidth(14, 14000);
        sheet.setColumnWidth(15, 2000);
        sheet.setColumnWidth(16, 6000);
        sheet.setColumnWidth(17, 5000);
        sheet.setColumnWidth(18, 4000);
        sheet.setColumnWidth(19, 6000);
        sheet.setColumnWidth(20, 6000);
        sheet.setColumnWidth(21, 7000);
        sheet.setColumnWidth(22, 7000);
        sheet.setColumnWidth(23, 7000);
        sheet.setColumnWidth(24, 19000);

        CellStyle headerStyle = workbook.createCellStyle();
        XSSFColor color = new XSSFColor(new java.awt.Color(255, 250, 250));
        ((XSSFCellStyle) headerStyle).setFillForegroundColor(color);
        headerStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);

        CellStyle cellStyle = workbook.createCellStyle();
        XSSFColor colorBlanco = new XSSFColor(new java.awt.Color(255, 250, 250));
        ((XSSFCellStyle) cellStyle).setFillForegroundColor(colorBlanco);
        Font font = workbook.createFont();//Create font
        cellStyle.setFont(font);//set it to bold

        CellStyle cellStyleTitulo = workbook.createCellStyle();
        XSSFColor colorBlancoTitulo = new XSSFColor(new java.awt.Color(255, 250, 250));
        ((XSSFCellStyle) cellStyleTitulo).setFillForegroundColor(colorBlancoTitulo);
        Font fontTitulo = workbook.createFont();//Create font
        fontTitulo.setBoldweight(Font.BOLDWEIGHT_BOLD);//Make font bold
        fontTitulo.setFontName(HSSFFont.FONT_ARIAL);
        fontTitulo.setFontHeightInPoints((short) 14);
        cellStyleTitulo.setFont(fontTitulo);//set it to bold

        CellStyle cellStyle2 = workbook.createCellStyle();
        XSSFColor colorBlanco2 = new XSSFColor(new java.awt.Color(255, 250, 250));
        ((XSSFCellStyle) cellStyle2).setFillForegroundColor(colorBlanco2);

        CellStyle cellStyleNumero = workbook.createCellStyle();
        XSSFColor colorBlanco3 = new XSSFColor(new java.awt.Color(255, 250, 250));
        ((XSSFCellStyle) cellStyleNumero).setFillForegroundColor(colorBlanco3);
        DataFormat df = workbook.createDataFormat();
        cellStyleNumero.setDataFormat(df.getFormat("###,###,##0.00"));

        CellStyle cellStyleFecha = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        cellStyleFecha.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));

        for (int i = 1; i < 4; i++) {
            Fila fila = new Fila(sheet.createRow(rownum++));
            Cell cell = fila.getFila().createCell(fila.nextIndex().shortValue());
            cell.setCellValue("");
            cell.setCellStyle(cellStyle);
        }

        for (int i = 0; i < 1; i++) {
            Fila fila = new Fila(sheet.createRow(rownum++));
            Cell cell = fila.getFila().createCell(fila.nextIndex().shortValue());
            cell.setCellValue("");
            cell.setCellStyle(cellStyle);

            Cell cell13 = fila.getFila().createCell(fila.nextIndex().shortValue());
            cell13.setCellValue("");
            cell13.setCellStyle(cellStyle);

            Cell cell11 = fila.getFila().createCell(fila.nextIndex().shortValue());
            cell11.setCellValue("TELECOSTA");
            CellUtil.setAlignment(cell11, workbook, CellStyle.ALIGN_LEFT);
            cell11.setCellStyle(cellStyleTitulo);
        }

        for (int i = 0; i < 1; i++) {
            Fila fila = new Fila(sheet.createRow(rownum++));
            Cell cell12 = fila.getFila().createCell(fila.nextIndex().shortValue());
            cell12.setCellValue("");
            cell12.setCellStyle(cellStyle);

            Cell cell17 = fila.getFila().createCell(fila.nextIndex().shortValue());
            cell17.setCellValue("");
            cell17.setCellStyle(cellStyle);

            Cell cell15 = fila.getFila().createCell(fila.nextIndex().shortValue());
            cell15.setCellValue("Ticket de Atenci??n");
            cell15.setCellStyle(cellStyleTitulo);
        }

        Row encabezados = sheet.createRow(rownum++);
        InputStream inputStream = new FileInputStream(getImagesDir() + LOGO);
        byte[] bytes = IOUtils.toByteArray(inputStream);
        //Adds a picture to the workbook
        int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
        //close the input stream
        inputStream.close();
        //Returns an object that handles instantiating concrete classes
        CreationHelper helper = workbook.getCreationHelper();
        //Creates the top-level drawing patriarch.
        Drawing drawing = sheet.createDrawingPatriarch();
        //Create an anchor that is attached to the worksheet
        ClientAnchor anchor = helper.createClientAnchor();
        //set top-left corner for the image
        anchor.setCol1(3);
        anchor.setRow1(1);
        //Creates a picture
        Picture pict = drawing.createPicture(anchor, pictureIdx);
        //Reset the image to the original size
        pict.resize(0.6, 4);

        Cell celda0 = encabezados.createCell(headerNum++);
        celda0.setCellValue("No.");
        celda0.setCellStyle(headerStyle);
        Cell celda1 = encabezados.createCell(headerNum++);
        celda1.setCellValue("FECHA");
        celda1.setCellStyle(headerStyle);
        Cell celda2 = encabezados.createCell(headerNum++);
        celda2.setCellValue("NOMBRE DEL USUARIO");
        celda2.setCellStyle(headerStyle);
        Cell celda3 = encabezados.createCell(headerNum++);
        celda3.setCellValue("DIRECCI??N");
        celda3.setCellStyle(headerStyle);
        Cell celda4 = encabezados.createCell(headerNum++);
        celda4.setCellValue("TEL??FONO");
        celda4.setCellStyle(headerStyle);
        Cell celda5 = encabezados.createCell(headerNum++);
        celda5.setCellValue("MOTIVO DEL REPORTE");
        celda5.setCellStyle(headerStyle);
        Cell celda6 = encabezados.createCell(headerNum++);
        celda6.setCellValue("REFERENCIA");
        celda6.setCellStyle(headerStyle);
        Cell celda7 = encabezados.createCell(headerNum++);
        celda7.setCellValue("MATERIAL UTILIZADO");
        celda7.setCellStyle(headerStyle);
        Cell celda8 = encabezados.createCell(headerNum++);
        celda8.setCellValue("CANTIDAD");
        celda8.setCellStyle(headerStyle);
        Cell celda9 = encabezados.createCell(headerNum++);
        celda9.setCellValue("OBSERVACIONES");
        celda9.setCellStyle(headerStyle);
        int correlativo = 1;

        for (ReporteAtencionDto reporte : listAtencion) {
            if (!mapaFilas.containsKey(reporte.getIdatencion())) {
                Fila fila = new Fila(sheet.createRow(rownum++));
                mapaFilas.put(reporte.getIdatencion(), fila);

                Cell cell = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell.setCellValue(correlativo++);
                cell.setCellStyle(cellStyle);

                Cell cell1 = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell1.setCellValue(reporte.getFechacreacion());
                cell1.setCellStyle(cellStyleFecha);

                Cell cell2 = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell2.setCellValue(reporte.getNombre());
                cell2.setCellStyle(cellStyle);

                Cell cell3 = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell3.setCellValue(reporte.getDireccion());
                cell3.setCellStyle(cellStyle);

                if (reporte.getTelefono() != null) {
                    Cell cell4 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell4.setCellValue(reporte.getTelefono());
                    cell4.setCellStyle(cellStyleNumero);
                } else {
                    Cell cell4 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell4.setCellValue("");
                    cell4.setCellStyle(cellStyle);
                }

                if (reporte.getMotivo() != null) {
                    Cell cell5 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell5.setCellValue(reporte.getMotivo());
                    cell5.setCellStyle(cellStyle);
                } else {
                    Cell cell5 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell5.setCellValue("");
                    cell5.setCellStyle(cellStyle);
                }

                if (reporte.getReferencia() != null) {
                    Cell cell6 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell6.setCellValue(reporte.getReferencia());
                    cell6.setCellStyle(cellStyle);
                } else {
                    Cell cell6 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell6.setCellValue("");
                    cell6.setCellStyle(cellStyle);
                }

                if (reporte.getMaterialutilizado() != null) {
                    Cell cell7 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell7.setCellValue(reporte.getMaterialutilizado());
                    cell7.setCellStyle(cellStyle);
                } else {
                    Cell cell7 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell7.setCellValue("");
                    cell7.setCellStyle(cellStyle);
                }

                if (reporte.getCantidad() != null) {
                    Cell cell8 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell8.setCellValue(reporte.getCantidad());
                    cell8.setCellStyle(cellStyle);
                } else {
                    Cell cell8 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell8.setCellValue("");
                    cell8.setCellStyle(cellStyle);
                }

                if (reporte.getObservacion() != null) {
                    Cell cell9 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell9.setCellValue(reporte.getObservacion());
                    cell9.setCellStyle(cellStyle);
                } else {
                    Cell cell9 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell9.setCellValue("");
                    cell9.setCellStyle(cellStyle);
                }
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String outputFileName = "Reporte_" + sdf.format(new Date()) + ".xlsx";
        String outputReportFile = getOutputDir() + outputFileName;
        FileOutputStream out = new FileOutputStream(outputReportFile);
        workbook.write(out);
        out.close();
        ((SXSSFWorkbook) workbook).dispose();
        FileInputStream stream = new FileInputStream(outputReportFile);
        content = new DefaultStreamedContent(stream, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", outputFileName);

        return content;
    }


    /*Metodos getters y setters*/
    public List<Ruta> getListRuta() {
        return listRuta;
    }

    public void setListRuta(List<Ruta> listRuta) {
        this.listRuta = listRuta;
    }

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

    public Integer getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(Integer idRuta) {
        this.idRuta = idRuta;
    }

}
