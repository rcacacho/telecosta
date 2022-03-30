package tele.costa.web.cobro;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import tele.costa.api.dto.ReporteCobrosDto;
import tele.costa.api.ejb.CatalogoBeanLocal;
import tele.costa.api.ejb.ClienteBeanLocal;
import tele.costa.api.ejb.PagosBeanLocal;
import tele.costa.api.entity.Cliente;
import tele.costa.api.entity.Municipio;
import tele.costa.api.entity.Pago;
import tele.costa.api.entity.Sector;
import tele.costa.api.wrapper.CobroWrapper;
import telecosta.web.utils.JasperReportHelper;
import telecosta.web.utils.JasperUtil;
import telecosta.web.utils.JsfUtil;
import telecosta.web.utils.ReportFormat;
import telecosta.web.utils.ReporteJasper;
import telecosta.web.utils.SesionUsuarioMB;

/**
 *
 * @author rcacacho
 */
@ManagedBean(name = "reporteCobroMB")
@ViewScoped
public class ReporteCobroMB {

    private static final Logger log = Logger.getLogger(ReporteCobroMB.class);

    @Resource(lookup = "jdbc/telecosta")
    private DataSource dataSource;

    @EJB
    private PagosBeanLocal pagosBean;
    @EJB
    private CatalogoBeanLocal catalogoBean;
    @EJB
    private ClienteBeanLocal clienteBean;

    private Date fechaIncioCobro;
    private Date fechaFinCobro;
    private final String LOGO = "logo.jpeg";
    private String realPath;
    private String dirSeparator;
    private List<Municipio> listMunicipio;
    private Integer idMunicipio;
    private Integer idSector;
    private List<Sector> listSector;

    public ReporteCobroMB() {

    }

    @PostConstruct
    void cargarDatos() {
        listMunicipio = catalogoBean.listMunicipioByIdDepartamento(1);
        listSector = catalogoBean.listSector();
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

    public StreamedContent generarPdfCobroMunicipio() {
        List<Cliente> listCliente = clienteBean.ListClientesByIdMunucipio(idMunicipio);
        List<CobroWrapper> listCobro = new ArrayList<>();

        final List<CobroWrapper> data = new ArrayList<>();

        String nombreArchivo = "CobroMunicipio";
        ReportFormat format = ReportFormat.PDF;

        try {
            //PREPARANDO REPORTE
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = servletContext.getRealPath("/");
            String nombreReporte;
            String siaf = null;
            nombreReporte = "rptCobroMunicipio";

            for (Cliente cl : listCliente) {
                Pago pag = pagosBean.findUltimoPago(cl.getIdcliente());
                if (pag != null) {
                    CobroWrapper cobroWrapper = new CobroWrapper();
                    cobroWrapper.setCodigo(cl.getCodigo());
                    cobroWrapper.setDireccion(cl.getDireccion());
                    cobroWrapper.setFechapago(pag.getMes() + '-' + pag.getAnio());
                    cobroWrapper.setIdpago(pag.getIdpago());
                    cobroWrapper.setNombres(cl.getNombres());
                    cobroWrapper.setObservacion(pag.getObservacion());
                    if (cl.getIdSector() != null) {
                        cobroWrapper.setSector(cl.getIdSector().getSector());
                    }

                    cobroWrapper.setTelefono(cl.getTelefono());
                    if (cl.getIdconfiguracionpago() != null) {
                        cobroWrapper.setValor(cl.getIdconfiguracionpago().getValor());
                    }

                    listCobro.add(cobroWrapper);
                }
            }

            JasperReportHelper helper;
            Map parameters = new HashMap<>();

            helper = new JasperReportHelper(nombreReporte, nombreArchivo, format) {
                @Override
                public Collection getData() {
                    return data;
                }
            };

            //LLENANDO LOS PARAMETROS
            HashMap parametros = new HashMap();
            parametros.put("IMAGE", "logo.jpeg");
            parametros.put("DIRECTORIO", realPath + File.separator + "resources" + File.separator + "images" + File.separator);
            parametros.put("USUARIO", SesionUsuarioMB.getUserName());
            parametros.put("ID_MUNICIPIO", idMunicipio);

            StreamedContent response = helper.fillReport(parameters);

            if (response == null) {
                return null;
            }
            return response;

        } catch (Exception ex) {
            log.error(ex);
            ex.printStackTrace();
        }

        return null;
    }

    public StreamedContent generarPdfCobroSector() {
        try {
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = servletContext.getRealPath("/");
            String nombreReporte = "rptCobroSector";
            String nombreArchivo = "CobroSector.pdf";
            HashMap parametros = new HashMap();
            parametros.put("IMAGE", "logo.jpeg");
            parametros.put("DIRECTORIO", realPath + File.separator + "resources" + File.separator + "images" + File.separator);
            parametros.put("USUARIO", SesionUsuarioMB.getUserName());
            parametros.put("ID_SECTOR", idSector);

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

    public StreamedContent imprimirExcelCobroSector() throws IOException {
        StreamedContent content = null;

        List<Cliente> listCliente = clienteBean.ListClientesByIdSector(idSector);
        List<CobroWrapper> listCobro = new ArrayList<>();

        for (Cliente cl : listCliente) {
            Pago pag = pagosBean.findUltimoPago(cl.getIdcliente());
            if (pag != null) {
                CobroWrapper cobroWrapper = new CobroWrapper();
                cobroWrapper.setCodigo(cl.getCodigo());
                cobroWrapper.setDireccion(cl.getDireccion());
                cobroWrapper.setFechapago(pag.getMes() + '-' + pag.getAnio());
                cobroWrapper.setIdpago(pag.getIdpago());
                cobroWrapper.setNombres(cl.getNombres());
                cobroWrapper.setObservacion(pag.getObservacion());
                if (cl.getIdSector() != null) {
                    cobroWrapper.setSector(cl.getIdSector().getSector());
                }

                cobroWrapper.setTelefono(cl.getTelefono());
                if (cl.getIdconfiguracionpago() != null) {
                    cobroWrapper.setValor(cl.getIdconfiguracionpago().getValor());
                }
                cobroWrapper.setValor(pag.getTotal());
                listCobro.add(cobroWrapper);
            }
        }

        HashMap<Integer, Fila> mapaFilas = new HashMap<>();
        Workbook workbook = new SXSSFWorkbook(1000);
        Sheet sheet = workbook.createSheet("REPORTE_COBROS");

        int rownum = 0;
        int headerNum = 0;
        sheet.setColumnWidth(0, 900);
        sheet.setColumnWidth(1, 3000);
        sheet.setColumnWidth(2, 9000);
        sheet.setColumnWidth(3, 6000);
        sheet.setColumnWidth(4, 7000);
        sheet.setColumnWidth(5, 5000);
        sheet.setColumnWidth(6, 5000);
        sheet.setColumnWidth(7, 5000);
        sheet.setColumnWidth(8, 5000);

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
            cell15.setCellValue("COBROS");
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
        celda1.setCellValue("CÓDIGO");
        celda1.setCellStyle(headerStyle);
        Cell celda2 = encabezados.createCell(headerNum++);
        celda2.setCellValue("NOMBRES");
        celda2.setCellStyle(headerStyle);
        Cell celda3 = encabezados.createCell(headerNum++);
        celda3.setCellValue("TELÉFONO");
        celda3.setCellStyle(headerStyle);
        Cell celda4 = encabezados.createCell(headerNum++);
        celda4.setCellValue("DIRECCIÓN");
        celda4.setCellStyle(headerStyle);
        Cell celda5 = encabezados.createCell(headerNum++);
        celda5.setCellValue("SECTOR");
        celda5.setCellStyle(headerStyle);
        Cell celda6 = encabezados.createCell(headerNum++);
        celda6.setCellValue("ÚLTIMO PAGO");
        celda6.setCellStyle(headerStyle);
        Cell celda7 = encabezados.createCell(headerNum++);
        celda7.setCellValue("CONFIG PAGO/ CUOTA");
        celda7.setCellStyle(headerStyle);
        Cell celda8 = encabezados.createCell(headerNum++);
        celda8.setCellValue("OBSERVACIONES");
        celda8.setCellStyle(headerStyle);
        int correlativo = 1;

        for (CobroWrapper reporte : listCobro) {
            if (!mapaFilas.containsKey(reporte.getIdpago())) {
                Fila fila = new Fila(sheet.createRow(rownum++));
                mapaFilas.put(reporte.getIdpago(), fila);

                Cell cell = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell.setCellValue(correlativo++);
                cell.setCellStyle(cellStyle);

                Cell cell1 = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell1.setCellValue(reporte.getCodigo());
                cell1.setCellStyle(cellStyle);

                if (reporte.getNombres() != null) {
                    Cell cell2 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell2.setCellValue(reporte.getNombres());
                    cell2.setCellStyle(cellStyle);
                } else {
                    Cell cell2 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell2.setCellValue("");
                    cell2.setCellStyle(cellStyle);
                }

                if (reporte.getTelefono() != null) {
                    Cell cell3 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell3.setCellValue(reporte.getTelefono());
                    cell3.setCellStyle(cellStyle);
                } else {
                    Cell cell3 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell3.setCellValue("");
                    cell3.setCellStyle(cellStyle);
                }

                Cell cell4 = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell4.setCellValue(reporte.getDireccion());
                cell4.setCellStyle(cellStyle);

                if (reporte.getSector() != null) {
                    Cell cell5 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell5.setCellValue(reporte.getSector());
                    cell5.setCellStyle(cellStyleNumero);
                } else {
                    Cell cell5 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell5.setCellValue("");
                    cell5.setCellStyle(cellStyle);
                }

                if (reporte.getFechapago() != null) {
                    Cell cell6 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell6.setCellValue(reporte.getFechapago());
                    cell6.setCellStyle(cellStyle);
                } else {
                    Cell cell6 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell6.setCellValue("");
                    cell6.setCellStyle(cellStyle);
                }

                if (reporte.getValor() != null) {
                    Cell cell7 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell7.setCellValue(reporte.getValor());
                    cell7.setCellStyle(cellStyle);
                } else {
                    Cell cell7 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell7.setCellValue("");
                    cell7.setCellStyle(cellStyle);
                }

                if (reporte.getObservacion() != null) {
                    Cell cell8 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell8.setCellValue(reporte.getObservacion());
                    cell8.setCellStyle(cellStyle);
                } else {
                    Cell cell8 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell8.setCellValue("");
                    cell8.setCellStyle(cellStyle);
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

    public StreamedContent imprimirExcelCobroMunicipio() throws IOException {
        StreamedContent content = null;
        List<Cliente> listCliente = clienteBean.ListClientesByIdMunucipio(idMunicipio);
        List<CobroWrapper> listCobro = new ArrayList<>();

        for (Cliente cl : listCliente) {
            Pago pag = pagosBean.findUltimoPago(cl.getIdcliente());
            if (pag != null) {
                CobroWrapper cobroWrapper = new CobroWrapper();
                cobroWrapper.setCodigo(cl.getCodigo());
                cobroWrapper.setDireccion(cl.getDireccion());
                cobroWrapper.setFechapago(pag.getMes() + '-' + pag.getAnio());
                cobroWrapper.setIdpago(pag.getIdpago());
                cobroWrapper.setNombres(cl.getNombres());
                cobroWrapper.setObservacion(pag.getObservacion());
                if (cl.getIdSector() != null) {
                    cobroWrapper.setSector(cl.getIdSector().getSector());
                }

                cobroWrapper.setTelefono(cl.getTelefono());
                if (cl.getIdconfiguracionpago() != null) {
                    cobroWrapper.setValor(cl.getIdconfiguracionpago().getValor());
                }

                listCobro.add(cobroWrapper);
            }
        }

        HashMap<Integer, Fila> mapaFilas = new HashMap<>();
        Workbook workbook = new SXSSFWorkbook(1000);
        Sheet sheet = workbook.createSheet("REPORTE_COBROS");

        int rownum = 0;
        int headerNum = 0;
        sheet.setColumnWidth(0, 1000);
        sheet.setColumnWidth(1, 2000);
        sheet.setColumnWidth(2, 9000);
        sheet.setColumnWidth(3, 6000);
        sheet.setColumnWidth(4, 9000);
        sheet.setColumnWidth(5, 5000);
        sheet.setColumnWidth(6, 5000);
        sheet.setColumnWidth(7, 5000);
        sheet.setColumnWidth(8, 7000);

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
            cell15.setCellValue("COBROS");
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
        celda1.setCellValue("CÓDIGO");
        celda1.setCellStyle(headerStyle);
        Cell celda2 = encabezados.createCell(headerNum++);
        celda2.setCellValue("NOMBRES");
        celda2.setCellStyle(headerStyle);
        Cell celda3 = encabezados.createCell(headerNum++);
        celda3.setCellValue("TELÉFONO");
        celda3.setCellStyle(headerStyle);
        Cell celda4 = encabezados.createCell(headerNum++);
        celda4.setCellValue("DIRECCIÓN");
        celda4.setCellStyle(headerStyle);
        Cell celda5 = encabezados.createCell(headerNum++);
        celda5.setCellValue("SECTOR");
        celda5.setCellStyle(headerStyle);
        Cell celda6 = encabezados.createCell(headerNum++);
        celda6.setCellValue("ÚLTIMO PAGO");
        celda6.setCellStyle(headerStyle);
        Cell celda7 = encabezados.createCell(headerNum++);
        celda7.setCellValue("CONFIG PAGO/ CUOTA");
        celda7.setCellStyle(headerStyle);
        Cell celda8 = encabezados.createCell(headerNum++);
        celda8.setCellValue("OBSERVACIONES");
        celda8.setCellStyle(headerStyle);
        int correlativo = 1;

        for (CobroWrapper reporte : listCobro) {
            if (!mapaFilas.containsKey(reporte.getIdpago())) {
                Fila fila = new Fila(sheet.createRow(rownum++));
                mapaFilas.put(reporte.getIdpago(), fila);

                Cell cell = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell.setCellValue(correlativo++);
                cell.setCellStyle(cellStyle);

                Cell cell1 = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell1.setCellValue(reporte.getCodigo());
                cell1.setCellStyle(cellStyle);

                if (reporte.getNombres() != null) {
                    Cell cell2 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell2.setCellValue(reporte.getNombres());
                    cell2.setCellStyle(cellStyle);
                } else {
                    Cell cell2 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell2.setCellValue("");
                    cell2.setCellStyle(cellStyle);
                }

                if (reporte.getTelefono() != null) {
                    Cell cell3 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell3.setCellValue(reporte.getTelefono());
                    cell3.setCellStyle(cellStyle);
                } else {
                    Cell cell3 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell3.setCellValue("");
                    cell3.setCellStyle(cellStyle);
                }

                if (reporte.getDireccion() != null) {
                    Cell cell4 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell4.setCellValue(reporte.getDireccion());
                    cell4.setCellStyle(cellStyleNumero);
                } else {
                    Cell cell4 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell4.setCellValue("");
                    cell4.setCellStyle(cellStyle);
                }

                if (reporte.getSector() != null) {
                    Cell cell5 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell5.setCellValue(reporte.getSector());
                    cell5.setCellStyle(cellStyleNumero);
                } else {
                    Cell cell5 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell5.setCellValue("");
                    cell5.setCellStyle(cellStyle);
                }

                if (reporte.getFechapago() != null) {
                    Cell cell6 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell6.setCellValue(reporte.getFechapago());
                    cell6.setCellStyle(cellStyle);
                } else {
                    Cell cell6 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell6.setCellValue("");
                    cell6.setCellStyle(cellStyle);
                }

                if (reporte.getValor() != null) {
                    Cell cell7 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell7.setCellValue(reporte.getValor());
                    cell7.setCellStyle(cellStyle);
                } else {
                    Cell cell7 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell7.setCellValue("");
                    cell7.setCellStyle(cellStyle);
                }

                if (reporte.getObservacion() != null) {
                    Cell cell8 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell8.setCellValue(reporte.getObservacion());
                    cell8.setCellStyle(cellStyle);
                } else {
                    Cell cell8 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell8.setCellValue("");
                    cell8.setCellStyle(cellStyle);
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

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Municipio> getListMunicipio() {
        return listMunicipio;
    }

    public void setListMunicipio(List<Municipio> listMunicipio) {
        this.listMunicipio = listMunicipio;
    }

    public Integer getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Integer idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public Integer getIdSector() {
        return idSector;
    }

    public void setIdSector(Integer idSector) {
        this.idSector = idSector;
    }

    public List<Sector> getListSector() {
        return listSector;
    }

    public void setListSector(List<Sector> listSector) {
        this.listSector = listSector;
    }

}
