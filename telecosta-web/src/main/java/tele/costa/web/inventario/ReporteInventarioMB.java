package tele.costa.web.inventario;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import tele.costa.api.ejb.CatalogoBeanLocal;
import tele.costa.api.ejb.InsumoBeanLocal;
import tele.costa.api.entity.Agencia;
import tele.costa.api.entity.Bitacorainventario;
import tele.costa.api.entity.Inventario;
import telecosta.web.utils.JasperUtil;
import telecosta.web.utils.JsfUtil;
import telecosta.web.utils.ReporteJasper;
import telecosta.web.utils.SesionUsuarioMB;

/**
 *
 * @author elfo_
 */
@ManagedBean(name = "reporteInventarioMB")
@ViewScoped

public class ReporteInventarioMB implements Serializable {

    private static final Logger log = Logger.getLogger(ReporteInventarioMB.class);

    @Resource(lookup = "jdbc/telecosta")
    private DataSource dataSource;

    @EJB
    private InsumoBeanLocal bodegaBeanLocal;
    @EJB
    private CatalogoBeanLocal catalogoBean;

    private String tipoCarga;
    private String mes;
    private Agencia idAgencia;
    private Integer idAgenciaEnvio;
    private Integer anio;
    private Boolean mostrarIngreso;
    private Boolean mostrarIngresoFechaInicio;
    private Boolean mostrarIngresoFechaFin;
    private Boolean mostrarIngresoInsumo;
    private Boolean mostrarSalida;
    private Boolean mostrarSalidaDocumento;
    private Boolean mostrarTraslado;
    private List<Agencia> listAgencia;
    private String numeroDocumentoIngreso;
    private Date fechaInicioIngreso;
    private Date fechaFinIngreso;
    private String insumoIngreso;
    private String insumoSalida;
    private String numeroDocumentoSalida;
    private Date fechaInicioSalida;
    private Date fechaFinSalida;
    private Date fechaInicioEnvio;
    private Date fechaFinEnvio;
    private final String LOGO = "logo.jpeg";
    private String realPath;
    private String dirSeparator;
    private Boolean mostrarSalidaFechaInicio;
    private Boolean mostrarSalidaFechaFin;
    private Boolean mostrarTrasladoFechaInicio;
    private Boolean mostrarTrasladoFechaFin;
    private Integer idAgenciaGeneral;
    private Date fechaInicioGeneral;
    private Date fechaFinGeneral;
    private Integer idAgenciaTecnico;
    private Date fechaInicioTecnico;
    private Date fechaFinTecnico;

    public ReporteInventarioMB() {
        mostrarIngreso = Boolean.FALSE;
        mostrarSalida = Boolean.FALSE;
        mostrarTraslado = Boolean.FALSE;
        mostrarIngresoFechaInicio = Boolean.FALSE;
        mostrarIngresoFechaFin = Boolean.FALSE;
        mostrarIngresoInsumo = Boolean.FALSE;
        mostrarSalidaDocumento = Boolean.FALSE;
        mostrarSalidaFechaInicio = Boolean.FALSE;
        mostrarSalidaFechaFin = Boolean.FALSE;
        mostrarTrasladoFechaInicio = Boolean.FALSE;
        mostrarTrasladoFechaFin = Boolean.FALSE;
    }

    @PostConstruct
    void cargarDatos() {
        listAgencia = catalogoBean.listAgencias();
    }

    public void cargarIngreso() {
        if (tipoCarga.equals("Ingreso")) {
            if (idAgencia != null) {
                mostrarIngresoFechaInicio = Boolean.TRUE;
            }

            if (mes != null) {
                mostrarIngresoFechaFin = Boolean.TRUE;
            }

            if (anio != null) {
                mostrarIngresoInsumo = Boolean.TRUE;
            }

            mostrarIngreso = Boolean.TRUE;
        } else {
            mostrarIngreso = Boolean.FALSE;
            mostrarIngresoInsumo = Boolean.FALSE;
            mostrarIngresoFechaFin = Boolean.FALSE;
            mostrarIngresoFechaInicio = Boolean.FALSE;
        }

        if (tipoCarga.equals("Salida")) {

            if (idAgencia != null) {
                mostrarSalidaDocumento = Boolean.TRUE;
            }

            if (mes != null) {
                mostrarSalidaFechaInicio = Boolean.TRUE;
            }

            if (anio != null) {
                mostrarSalidaFechaFin = Boolean.TRUE;
            }

            mostrarSalida = Boolean.TRUE;

        } else {
            mostrarSalida = Boolean.FALSE;
            mostrarSalidaFechaFin = Boolean.FALSE;
            mostrarSalidaFechaInicio = Boolean.FALSE;
            mostrarSalidaDocumento = Boolean.FALSE;
        }

        if (tipoCarga.equals("Envio")) {

            if (idAgencia != null) {
                mostrarTrasladoFechaInicio = Boolean.TRUE;
            }

            if (mes != null) {
                mostrarTrasladoFechaFin = Boolean.TRUE;
            }

            mostrarTraslado = Boolean.TRUE;
        } else {
            mostrarTraslado = Boolean.FALSE;
            mostrarTrasladoFechaInicio = Boolean.FALSE;
            mostrarTrasladoFechaFin = Boolean.FALSE;
        }
    }

    public void cargarAgencia() {
        if (tipoCarga.equals("Ingreso")) {
            mostrarIngreso = Boolean.TRUE;
        } else {
            mostrarIngreso = Boolean.FALSE;
        }

        if (tipoCarga.equals("Salida")) {
            mostrarSalida = Boolean.TRUE;
        } else {
            mostrarSalida = Boolean.FALSE;
        }

        if (tipoCarga.equals("Envio")) {
            mostrarTraslado = Boolean.TRUE;
        } else {
            mostrarTraslado = Boolean.FALSE;
        }
    }

    public StreamedContent imprimirReporteIngresoPdf() {
        try {
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = servletContext.getRealPath("/");
            String nombreReporte = "rptIngreso";
            String nombreArchivo = "Bodaga_Ingreso.pdf";

            HashMap parametros = new HashMap();
            parametros.put("IMAGE", "logo.jpeg");
            parametros.put("DIRECTORIO", realPath + File.separator + "resources" + File.separator + "images" + File.separator);
            parametros.put("USUARIO", SesionUsuarioMB.getUserName());
            parametros.put("AGENCIA", idAgencia.getAgencia());
            parametros.put("ANIO", anio);
            parametros.put("MES", mes);
            parametros.put("DOCUMENTO", numeroDocumentoIngreso);
            parametros.put("FECHA_INICIO", fechaInicioIngreso);
            parametros.put("FECHA_FIN", fechaFinIngreso);
            parametros.put("INSUMO", insumoIngreso);

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

    public StreamedContent imprimirReporteIngresoExcel() throws FileNotFoundException, IOException {
        StreamedContent content = null;
        List<Bitacorainventario> listBitacora = null;
        Integer id = 0;
        if (tipoCarga.equals("Ingreso")) {
            id = 1;
        } else if (tipoCarga.equals("Salida")) {
            id = 2;
        } else if (tipoCarga.equals("Envio")) {
            id = 3;
        }

        if (fechaInicioIngreso != null && fechaFinIngreso != null && numeroDocumentoIngreso != null) {
            List<Bitacorainventario> response = bodegaBeanLocal.listBitacoraByFechaInicioAndFechaFinAndDocumento(fechaInicioIngreso, fechaFinIngreso, numeroDocumentoIngreso, id);
            if (response != null) {
                listBitacora = response;
            } else {
                listBitacora = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (fechaInicioIngreso != null && fechaFinIngreso != null && insumoIngreso != null) {
            List<Bitacorainventario> response = bodegaBeanLocal.listBitacoraByFechaInicioAndFechaFinAndInsumo(fechaInicioIngreso, fechaFinIngreso, insumoIngreso, id);
            if (response != null) {
                listBitacora = response;
            } else {
                listBitacora = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (fechaInicioIngreso != null && fechaFinIngreso != null) {
            List<Bitacorainventario> response = bodegaBeanLocal.listBitacoraByFechaInicioAndFechaFin(fechaInicioIngreso, fechaFinIngreso, id);
            if (response != null) {
                listBitacora = response;
            } else {
                listBitacora = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (fechaInicioIngreso != null) {
            List<Bitacorainventario> response = bodegaBeanLocal.listBitacoraByFechaInicio(fechaInicioIngreso, id);
            if (response != null) {
                listBitacora = response;
            } else {
                listBitacora = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (fechaFinIngreso != null) {
            List<Bitacorainventario> response = bodegaBeanLocal.listBitacoraByFechaFin(fechaFinIngreso, id);
            if (response != null) {
                listBitacora = response;
            } else {
                listBitacora = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (insumoIngreso != null) {
            List<Bitacorainventario> response = bodegaBeanLocal.listBitacoraByInsumo(insumoIngreso, id);
            if (response != null) {
                listBitacora = response;
            } else {
                listBitacora = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (numeroDocumentoIngreso != null) {
            List<Bitacorainventario> response = bodegaBeanLocal.listBitacoraByDocumento(numeroDocumentoIngreso, id);
            if (response != null) {
                listBitacora = response;
            } else {
                listBitacora = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else {
            JsfUtil.addErrorMessage("No se encontraron datos");
            return null;
        }

        HashMap<Integer, Fila> mapaFilas = new HashMap<>();
        Workbook workbook = new SXSSFWorkbook(1000);
        Sheet sheet = workbook.createSheet("REPORTE_INGRESO_BODEGA");

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
        fontTitulo.setFontHeightInPoints((short) 16);
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
            cell15.setCellValue("BODEGA");
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
        celda2.setCellValue("FECHA");
        celda2.setCellStyle(headerStyle);
        Cell celda3 = encabezados.createCell(headerNum++);
        celda3.setCellValue("DESCRIPCIÓN");
        celda3.setCellStyle(headerStyle);
        Cell celda4 = encabezados.createCell(headerNum++);
        celda4.setCellValue("CANTIDAD");
        celda4.setCellStyle(headerStyle);
        Cell celda5 = encabezados.createCell(headerNum++);
        celda5.setCellValue("P. UNITARIO");
        celda5.setCellStyle(headerStyle);
        Cell celda6 = encabezados.createCell(headerNum++);
        celda6.setCellValue("TOTAL");
        celda6.setCellStyle(headerStyle);
        Cell celda7 = encabezados.createCell(headerNum++);
        celda7.setCellValue("PROVEEDOR");
        celda7.setCellStyle(headerStyle);
        Cell celda8 = encabezados.createCell(headerNum++);
        celda8.setCellValue("DOCUMENTO");
        celda8.setCellStyle(headerStyle);
        int correlativo = 1;

        for (Bitacorainventario reporte : listBitacora) {
            if (!mapaFilas.containsKey(reporte.getIdbitacorainventario())) {
                Fila fila = new Fila(sheet.createRow(rownum++));
                mapaFilas.put(reporte.getIdbitacorainventario(), fila);

                Cell cell = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell.setCellValue(correlativo++);
                cell.setCellStyle(cellStyle);

                Cell cell1 = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell1.setCellValue(reporte.getCodigo());
                cell1.setCellStyle(cellStyle);

                Cell cell2 = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell2.setCellValue(reporte.getFecha());
                cell2.setCellStyle(cellStyleFecha);

                Cell cell3 = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell3.setCellValue(reporte.getDescripcion());
                cell3.setCellStyle(cellStyle);

                if (reporte.getCantidad() != null) {
                    Cell cell4 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell4.setCellValue(reporte.getCantidad());
                    cell4.setCellStyle(cellStyleNumero);
                } else {
                    Cell cell4 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell4.setCellValue("");
                    cell4.setCellStyle(cellStyle);
                }

                if (reporte.getPreciounitario() != null) {
                    Cell cell5 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell5.setCellValue(reporte.getPreciounitario());
                    cell5.setCellStyle(cellStyleNumero);
                } else {
                    Cell cell5 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell5.setCellValue("");
                    cell5.setCellStyle(cellStyle);
                }

                if (reporte.getTotal() != null) {
                    Cell cell6 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell6.setCellValue(reporte.getTotal());
                    cell6.setCellStyle(cellStyleNumero);
                } else {
                    Cell cell6 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell6.setCellValue("");
                    cell6.setCellStyle(cellStyle);
                }

                if (reporte.getProveedor() != null) {
                    Cell cell7 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell7.setCellValue(reporte.getProveedor());
                    cell7.setCellStyle(cellStyle);
                } else {
                    Cell cell7 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell7.setCellValue("");
                    cell7.setCellStyle(cellStyle);
                }

                if (reporte.getDocumento() != null) {
                    Cell cell8 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell8.setCellValue(reporte.getDocumento());
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

    public StreamedContent imprimirReporteSalidaPdf() {
        try {
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = servletContext.getRealPath("/");
            String nombreReporte = "rptSalidas";
            String nombreArchivo = "Bodaga_Salida.pdf";

            HashMap parametros = new HashMap();
            parametros.put("IMAGE", "logo.jpeg");
            parametros.put("DIRECTORIO", realPath + File.separator + "resources" + File.separator + "images" + File.separator);
            parametros.put("USUARIO", SesionUsuarioMB.getUserName());
            parametros.put("AGENCIA", idAgencia.getAgencia());
            parametros.put("ANIO", anio);
            parametros.put("MES", mes);
            parametros.put("DOCUMENTO", numeroDocumentoSalida);
            parametros.put("FECHA_INICIO", fechaInicioSalida);
            parametros.put("FECHA_FIN", fechaFinSalida);
            parametros.put("INSUMO", insumoSalida);

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

    public StreamedContent imprimirReporteSalidaExcel() throws FileNotFoundException, IOException {
        StreamedContent content = null;
        List<Bitacorainventario> listBitacora = null;
        Integer id = 0;
        if (tipoCarga.equals("Ingreso")) {
            id = 1;
        } else if (tipoCarga.equals("Salida")) {
            id = 2;
        } else if (tipoCarga.equals("Envio")) {
            id = 3;
        }

        if (fechaInicioSalida != null && fechaFinSalida != null && numeroDocumentoSalida != null) {
            List<Bitacorainventario> response = bodegaBeanLocal.listBitacoraByFechaInicioAndFechaFinAndDocumento(fechaInicioSalida, fechaFinSalida, numeroDocumentoSalida, id);
            if (response != null) {
                listBitacora = response;
            } else {
                listBitacora = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (fechaInicioSalida != null && fechaFinSalida != null && insumoSalida != null) {
            List<Bitacorainventario> response = bodegaBeanLocal.listBitacoraByFechaInicioAndFechaFinAndInsumo(fechaInicioSalida, fechaFinSalida, insumoSalida, id);
            if (response != null) {
                listBitacora = response;
            } else {
                listBitacora = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (fechaInicioSalida != null && fechaFinSalida != null) {
            List<Bitacorainventario> response = bodegaBeanLocal.listBitacoraByFechaInicioAndFechaFin(fechaInicioSalida, fechaFinSalida, id);
            if (response != null) {
                listBitacora = response;
            } else {
                listBitacora = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (fechaInicioSalida != null) {
            List<Bitacorainventario> response = bodegaBeanLocal.listBitacoraByFechaInicio(fechaInicioSalida, id);
            if (response != null) {
                listBitacora = response;
            } else {
                listBitacora = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (fechaFinSalida != null) {
            List<Bitacorainventario> response = bodegaBeanLocal.listBitacoraByFechaFin(fechaFinSalida, id);
            if (response != null) {
                listBitacora = response;
            } else {
                listBitacora = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (insumoSalida != null) {
            List<Bitacorainventario> response = bodegaBeanLocal.listBitacoraByInsumo(insumoSalida, id);
            if (response != null) {
                listBitacora = response;
            } else {
                listBitacora = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (numeroDocumentoSalida != null) {
            List<Bitacorainventario> response = bodegaBeanLocal.listBitacoraByDocumento(numeroDocumentoSalida, id);
            if (response != null) {
                listBitacora = response;
            } else {
                listBitacora = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else {
            JsfUtil.addErrorMessage("No se encontraron datos");
            return null;
        }

        HashMap<Integer, Fila> mapaFilas = new HashMap<>();
        Workbook workbook = new SXSSFWorkbook(1000);
        Sheet sheet = workbook.createSheet("REPORTE_SALIDA_BODEGA");

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
        fontTitulo.setFontHeightInPoints((short) 16);
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
            cell15.setCellValue("BODEGA");
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
        celda2.setCellValue("FECHA");
        celda2.setCellStyle(headerStyle);
        Cell celda3 = encabezados.createCell(headerNum++);
        celda3.setCellValue("DESCRIPCIÓN");
        celda3.setCellStyle(headerStyle);
        Cell celda4 = encabezados.createCell(headerNum++);
        celda4.setCellValue("CANTIDAD");
        celda4.setCellStyle(headerStyle);
        Cell celda5 = encabezados.createCell(headerNum++);
        celda5.setCellValue("P. UNITARIO");
        celda5.setCellStyle(headerStyle);
        Cell celda6 = encabezados.createCell(headerNum++);
        celda6.setCellValue("TOTAL");
        celda6.setCellStyle(headerStyle);
        Cell celda7 = encabezados.createCell(headerNum++);
        celda7.setCellValue("RESPONSABLE");
        celda7.setCellStyle(headerStyle);
        Cell celda8 = encabezados.createCell(headerNum++);
        celda8.setCellValue("SECTOR");
        celda8.setCellStyle(headerStyle);
        Cell celda9 = encabezados.createCell(headerNum++);
        celda9.setCellValue("DOCUMENTO");
        celda9.setCellStyle(headerStyle);
        int correlativo = 1;

        for (Bitacorainventario reporte : listBitacora) {
            if (!mapaFilas.containsKey(reporte.getIdbitacorainventario())) {
                Fila fila = new Fila(sheet.createRow(rownum++));
                mapaFilas.put(reporte.getIdbitacorainventario(), fila);

                Cell cell = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell.setCellValue(correlativo++);
                cell.setCellStyle(cellStyle);

                Cell cell1 = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell1.setCellValue(reporte.getCodigo());
                cell1.setCellStyle(cellStyle);

                Cell cell2 = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell2.setCellValue(reporte.getFecha());
                cell2.setCellStyle(cellStyleFecha);

                Cell cell3 = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell3.setCellValue(reporte.getDescripcion());
                cell3.setCellStyle(cellStyle);

                if (reporte.getCantidad() != null) {
                    Cell cell4 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell4.setCellValue(reporte.getCantidad());
                    cell4.setCellStyle(cellStyleNumero);
                } else {
                    Cell cell4 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell4.setCellValue("");
                    cell4.setCellStyle(cellStyle);
                }

                if (reporte.getPreciounitario() != null) {
                    Cell cell5 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell5.setCellValue(reporte.getPreciounitario());
                    cell5.setCellStyle(cellStyleNumero);
                } else {
                    Cell cell5 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell5.setCellValue("");
                    cell5.setCellStyle(cellStyle);
                }

                if (reporte.getTotal() != null) {
                    Cell cell6 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell6.setCellValue(reporte.getTotal());
                    cell6.setCellStyle(cellStyleNumero);
                } else {
                    Cell cell6 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell6.setCellValue("");
                    cell6.setCellStyle(cellStyle);
                }

                if (reporte.getResponsable() != null) {
                    Cell cell7 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell7.setCellValue(reporte.getResponsable());
                    cell7.setCellStyle(cellStyle);
                } else {
                    Cell cell7 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell7.setCellValue("");
                    cell7.setCellStyle(cellStyle);
                }

                if (reporte.getSector() != null) {
                    Cell cell8 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell8.setCellValue(reporte.getSector());
                    cell8.setCellStyle(cellStyle);
                } else {
                    Cell cell8 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell8.setCellValue("");
                    cell8.setCellStyle(cellStyle);
                }

                if (reporte.getDocumento() != null) {
                    Cell cell9 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell9.setCellValue(reporte.getDocumento());
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

    public StreamedContent imprimirReporteEnvioPdf() {
        try {
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = servletContext.getRealPath("/");
            String nombreReporte = "rptEnvios";
            String nombreArchivo = "Bodaga_Envio.pdf";

            HashMap parametros = new HashMap();
            parametros.put("IMAGE", "logo.jpeg");
            parametros.put("DIRECTORIO", realPath + File.separator + "resources" + File.separator + "images" + File.separator);
            parametros.put("USUARIO", SesionUsuarioMB.getUserName());
            parametros.put("AGENCIA", idAgencia.getAgencia());
            parametros.put("ANIO", anio);
            parametros.put("MES", mes);
            parametros.put("FECHA_INICIO", fechaInicioEnvio);
            parametros.put("FECHA_FIN", fechaFinEnvio);

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

    public StreamedContent imprimirReporteEnvioExcel() throws IOException {
        StreamedContent content = null;
        List<Bitacorainventario> listBitacora = null;
        Integer id = 0;
        if (tipoCarga.equals("Ingreso")) {
            id = 1;
        } else if (tipoCarga.equals("Salida")) {
            id = 2;
        } else if (tipoCarga.equals("Envio")) {
            id = 3;
        }

        if (fechaInicioIngreso != null && fechaFinIngreso != null) {
            List<Bitacorainventario> response = bodegaBeanLocal.listBitacoraByFechaInicioAndFechaFin(fechaInicioIngreso, fechaFinIngreso, id);
            if (response != null) {
                listBitacora = response;
            } else {
                listBitacora = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (fechaInicioIngreso != null) {
            List<Bitacorainventario> response = bodegaBeanLocal.listBitacoraByFechaInicio(fechaInicioIngreso, id);
            if (response != null) {
                listBitacora = response;
            } else {
                listBitacora = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (fechaFinIngreso != null) {
            List<Bitacorainventario> response = bodegaBeanLocal.listBitacoraByFechaFin(fechaFinIngreso, id);
            if (response != null) {
                listBitacora = response;
            } else {
                listBitacora = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else {
            JsfUtil.addErrorMessage("No se encontraron datos");
            return null;
        }

        HashMap<Integer, Fila> mapaFilas = new HashMap<>();
        Workbook workbook = new SXSSFWorkbook(1000);
        Sheet sheet = workbook.createSheet("REPORTE_INGRESO_BODEGA");

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
        fontTitulo.setFontHeightInPoints((short) 16);
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
            cell15.setCellValue("BODEGA");
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
        celda2.setCellValue("FECHA");
        celda2.setCellStyle(headerStyle);
        Cell celda3 = encabezados.createCell(headerNum++);
        celda3.setCellValue("DESCRIPCIÓN");
        celda3.setCellStyle(headerStyle);
        Cell celda4 = encabezados.createCell(headerNum++);
        celda4.setCellValue("CANTIDAD");
        celda4.setCellStyle(headerStyle);
        Cell celda5 = encabezados.createCell(headerNum++);
        celda5.setCellValue("P. UNITARIO");
        celda5.setCellStyle(headerStyle);
        Cell celda6 = encabezados.createCell(headerNum++);
        celda6.setCellValue("TOTAL");
        celda6.setCellStyle(headerStyle);
        Cell celda7 = encabezados.createCell(headerNum++);
        celda7.setCellValue("DESTINO");
        celda7.setCellStyle(headerStyle);
        int correlativo = 1;

        for (Bitacorainventario reporte : listBitacora) {
            if (!mapaFilas.containsKey(reporte.getIdbitacorainventario())) {
                Fila fila = new Fila(sheet.createRow(rownum++));
                mapaFilas.put(reporte.getIdbitacorainventario(), fila);

                Cell cell = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell.setCellValue(correlativo++);
                cell.setCellStyle(cellStyle);

                Cell cell1 = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell1.setCellValue(reporte.getCodigo());
                cell1.setCellStyle(cellStyle);

                Cell cell2 = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell2.setCellValue(reporte.getFecha());
                cell2.setCellStyle(cellStyleFecha);

                Cell cell3 = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell3.setCellValue(reporte.getDescripcion());
                cell3.setCellStyle(cellStyle);

                if (reporte.getCantidad() != null) {
                    Cell cell4 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell4.setCellValue(reporte.getCantidad());
                    cell4.setCellStyle(cellStyleNumero);
                } else {
                    Cell cell4 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell4.setCellValue("");
                    cell4.setCellStyle(cellStyle);
                }

                if (reporte.getPreciounitario() != null) {
                    Cell cell5 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell5.setCellValue(reporte.getPreciounitario());
                    cell5.setCellStyle(cellStyleNumero);
                } else {
                    Cell cell5 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell5.setCellValue("");
                    cell5.setCellStyle(cellStyle);
                }

                if (reporte.getTotal() != null) {
                    Cell cell6 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell6.setCellValue(reporte.getTotal());
                    cell6.setCellStyle(cellStyleNumero);
                } else {
                    Cell cell6 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell6.setCellValue("");
                    cell6.setCellStyle(cellStyle);
                }

                if (reporte.getIdagencia() != null) {
                    Cell cell7 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell7.setCellValue(reporte.getIdagencia().getAgencia());
                    cell7.setCellStyle(cellStyle);
                } else {
                    Cell cell7 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell7.setCellValue("");
                    cell7.setCellStyle(cellStyle);
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

    public StreamedContent imprimirReporteGeneralPdf() {
        try {
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = servletContext.getRealPath("/");
            String nombreReporte = "rptBodegaGeneral";
            String nombreArchivo = "Bodaga_Envio.pdf";

            HashMap parametros = new HashMap();
            parametros.put("IMAGE", "logo.jpeg");
            parametros.put("DIRECTORIO", realPath + File.separator + "resources" + File.separator + "images" + File.separator);
            parametros.put("USUARIO", SesionUsuarioMB.getUserName());
            parametros.put("ID_AGENCIA", idAgenciaGeneral);
            parametros.put("FECHA_INICIO", fechaInicioGeneral);
            parametros.put("FECHA_FIN", fechaFinGeneral);

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

    public StreamedContent imprimirReporteGeneralExcel() throws IOException {
        StreamedContent content = null;
        List<Inventario> listInventario = null;

        if (fechaInicioGeneral != null && fechaFinGeneral != null && idAgenciaGeneral > 0) {
            List<Inventario> response = bodegaBeanLocal.listInsumoByFechaInicioAndFechaFinAndIdAgencia(fechaInicioGeneral, fechaFinGeneral, idAgenciaGeneral);
            if (response != null) {
                listInventario = response;
            } else {
                listInventario = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (fechaInicioIngreso != null && fechaFinGeneral != null) {
            List<Inventario> response = bodegaBeanLocal.listInsumoByFechaInicioAndFechaFin(fechaInicioIngreso, fechaFinGeneral);
            if (response != null) {
                listInventario = response;
            } else {
                listInventario = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (fechaInicioGeneral != null) {
            List<Inventario> response = bodegaBeanLocal.listInsumoByFechaInicio(fechaInicioGeneral);
            if (response != null) {
                listInventario = response;
            } else {
                listInventario = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (fechaFinGeneral != null) {
            List<Inventario> response = bodegaBeanLocal.listInsumoByFechaInicio(fechaFinGeneral);
            if (response != null) {
                listInventario = response;
            } else {
                listInventario = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else {
            JsfUtil.addErrorMessage("No se encontraron datos");
            return null;
        }

        HashMap<Integer, Fila> mapaFilas = new HashMap<>();
        Workbook workbook = new SXSSFWorkbook(1000);
        Sheet sheet = workbook.createSheet("REPORTE_INGRESO_BODEGA");

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
        fontTitulo.setFontHeightInPoints((short) 16);
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
            cell15.setCellValue("BODEGA");
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
        celda2.setCellValue("DESCRIPCIÓN");
        celda2.setCellStyle(headerStyle);
        Cell celda3 = encabezados.createCell(headerNum++);
        celda3.setCellValue("SALDO INICIAL");
        celda3.setCellStyle(headerStyle);
        Cell celda4 = encabezados.createCell(headerNum++);
        celda4.setCellValue("ENTRADAS");
        celda4.setCellStyle(headerStyle);
        Cell celda5 = encabezados.createCell(headerNum++);
        celda5.setCellValue("SALIDAS");
        celda5.setCellStyle(headerStyle);
        Cell celda6 = encabezados.createCell(headerNum++);
        celda6.setCellValue("EXISTENCIA");
        celda6.setCellStyle(headerStyle);
        Cell celda7 = encabezados.createCell(headerNum++);
        celda7.setCellValue("MONTO");
        celda7.setCellStyle(headerStyle);
        int correlativo = 1;

        for (Inventario reporte : listInventario) {
            if (!mapaFilas.containsKey(reporte.getIdinventario())) {
                Fila fila = new Fila(sheet.createRow(rownum++));
                mapaFilas.put(reporte.getIdinventario(), fila);

                Cell cell = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell.setCellValue(correlativo++);
                cell.setCellStyle(cellStyle);

                Cell cell1 = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell1.setCellValue(reporte.getIdinsumo().getCodigo());
                cell1.setCellStyle(cellStyle);

                Cell cell2 = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell2.setCellValue(reporte.getIdinsumo().getDescripcion());
                cell2.setCellStyle(cellStyle);

                Cell cell3 = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell3.setCellValue(reporte.getSaldoinicial());
                cell3.setCellStyle(cellStyleNumero);

                if (reporte.getEntradas() != null) {
                    Cell cell4 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell4.setCellValue(reporte.getEntradas());
                    cell4.setCellStyle(cellStyleNumero);
                } else {
                    Cell cell4 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell4.setCellValue("");
                    cell4.setCellStyle(cellStyleNumero);
                }

                if (reporte.getSalidas() != null) {
                    Cell cell5 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell5.setCellValue(reporte.getSalidas());
                    cell5.setCellStyle(cellStyleNumero);
                } else {
                    Cell cell5 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell5.setCellValue("");
                    cell5.setCellStyle(cellStyle);
                }

                Cell cell6 = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell6.setCellValue(reporte.getExistencia());
                cell6.setCellStyle(cellStyleNumero);

                Cell cell7 = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell7.setCellValue(reporte.getTotal());
                cell7.setCellStyle(cellStyleNumero);

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

    public StreamedContent imprimirReporteTecnicoPdf() {
        try {
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = servletContext.getRealPath("/");
            String nombreReporte = "rptTecnicoBodega";
            String nombreArchivo = "tecnico.pdf";

            HashMap parametros = new HashMap();
            parametros.put("IMAGE", "logo.jpeg");
            parametros.put("DIRECTORIO", realPath + File.separator + "resources" + File.separator + "images" + File.separator);
            parametros.put("USUARIO", SesionUsuarioMB.getUserName());
            parametros.put("ID_AGENCIA", idAgenciaTecnico);
            parametros.put("FECHA_INICIO", fechaInicioTecnico);
            parametros.put("FECHA_FIN", fechaFinTecnico);

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

    public StreamedContent imprimirReporteTecnicoExcel() throws IOException {
        StreamedContent content = null;
        List<Inventario> listInventario = null;

        if (fechaInicioTecnico != null && fechaFinTecnico != null && idAgenciaTecnico > 0) {
            List<Inventario> response = bodegaBeanLocal.listInsumoTecnicoByFechaInicioAndFechaFinAndIdAgencia(fechaInicioTecnico, fechaFinTecnico, idAgenciaTecnico);
            if (response != null) {
                listInventario = response;
            } else {
                listInventario = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (fechaInicioTecnico != null && fechaFinGeneral != null) {
            List<Inventario> response = bodegaBeanLocal.listInsumoByFechaInicioAndFechaFin(fechaInicioTecnico, fechaFinTecnico);
            if (response != null) {
                listInventario = response;
            } else {
                listInventario = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (fechaInicioTecnico != null) {
            List<Inventario> response = bodegaBeanLocal.listInsumoByFechaInicio(fechaInicioTecnico);
            if (response != null) {
                listInventario = response;
            } else {
                listInventario = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (fechaFinTecnico != null) {
            List<Inventario> response = bodegaBeanLocal.listInsumoTecnicoByFechaInicio(fechaFinTecnico);
            if (response != null) {
                listInventario = response;
            } else {
                listInventario = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (idAgenciaTecnico != null) {
            List<Inventario> response = bodegaBeanLocal.listInsumoTecnicoByIdAgencia(idAgenciaTecnico);
            if (response != null) {
                listInventario = response;
            } else {
                listInventario = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else {
            JsfUtil.addErrorMessage("No se encontraron datos");
            return null;
        }

        HashMap<Integer, Fila> mapaFilas = new HashMap<>();
        Workbook workbook = new SXSSFWorkbook(1000);
        Sheet sheet = workbook.createSheet("REPORTE_INGRESO_BODEGA");

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
        fontTitulo.setFontHeightInPoints((short) 16);
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
            cell15.setCellValue("BODEGA");
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
        celda2.setCellValue("DESCRIPCIÓN");
        celda2.setCellStyle(headerStyle);
        Cell celda3 = encabezados.createCell(headerNum++);
        celda3.setCellValue("CANTIDAD ENTREGADA");
        celda3.setCellStyle(headerStyle);
        Cell celda4 = encabezados.createCell(headerNum++);
        celda4.setCellValue("PRECIO UNITARIO");
        celda4.setCellStyle(headerStyle);
        Cell celda5 = encabezados.createCell(headerNum++);
        celda5.setCellValue("RESPONSABLE");
        celda5.setCellStyle(headerStyle);
        Cell celda6 = encabezados.createCell(headerNum++);
        celda6.setCellValue("RUTA");
        celda6.setCellStyle(headerStyle);
        Cell celda7 = encabezados.createCell(headerNum++);
        celda7.setCellValue("AGENCIA");
        celda7.setCellStyle(headerStyle);
        int correlativo = 1;

        for (Inventario reporte : listInventario) {
            if (!mapaFilas.containsKey(reporte.getIdinventario())) {
                Fila fila = new Fila(sheet.createRow(rownum++));
                mapaFilas.put(reporte.getIdinventario(), fila);

                Cell cell = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell.setCellValue(correlativo++);
                cell.setCellStyle(cellStyle);

                Cell cell1 = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell1.setCellValue(reporte.getIdinsumo().getCodigo());
                cell1.setCellStyle(cellStyle);

                Cell cell2 = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell2.setCellValue(reporte.getIdinsumo().getDescripcion());
                cell2.setCellStyle(cellStyle);

                Cell cell3 = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell3.setCellValue(reporte.getSalidas());
                cell3.setCellStyle(cellStyle);

                Cell cell4 = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell4.setCellValue(reporte.getPrecio());
                cell4.setCellStyle(cellStyleNumero);

                if (reporte.getResponsable() != null) {
                    Cell cell5 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell5.setCellValue(reporte.getResponsable());
                    cell5.setCellStyle(cellStyle);
                } else {
                    Cell cell5 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell5.setCellValue("");
                    cell5.setCellStyle(cellStyle);
                }

                Cell cell6 = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell6.setCellValue(reporte.getIdruta().getRuta());
                cell6.setCellStyle(cellStyle);

                Cell cell7 = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell7.setCellValue(reporte.getIdagencia().getAgencia());
                cell7.setCellStyle(cellStyle);

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

    public void limpiarCampos() {
        idAgencia = null;
        tipoCarga = null;
        mes = null;
        anio = null;
        mostrarIngreso = Boolean.FALSE;
        mostrarSalida = Boolean.FALSE;
        mostrarTraslado = Boolean.FALSE;
        mostrarIngresoFechaInicio = Boolean.FALSE;
        mostrarIngresoFechaFin = Boolean.FALSE;
        mostrarIngresoInsumo = Boolean.FALSE;
        mostrarSalidaDocumento = Boolean.FALSE;
        mostrarSalidaFechaInicio = Boolean.FALSE;
        mostrarSalidaFechaFin = Boolean.FALSE;
        mostrarTrasladoFechaInicio = Boolean.FALSE;
        mostrarTrasladoFechaFin = Boolean.FALSE;
    }

    /*Metodos getters y setters*/
    public String getTipoCarga() {
        return tipoCarga;
    }

    public void setTipoCarga(String tipoCarga) {
        this.tipoCarga = tipoCarga;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Agencia getIdAgencia() {
        return idAgencia;
    }

    public void setIdAgencia(Agencia idAgencia) {
        this.idAgencia = idAgencia;
    }

    public Integer getIdAgenciaGeneral() {
        return idAgenciaGeneral;
    }

    public void setIdAgenciaGeneral(Integer idAgenciaGeneral) {
        this.idAgenciaGeneral = idAgenciaGeneral;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Boolean getMostrarIngreso() {
        return mostrarIngreso;
    }

    public void setMostrarIngreso(Boolean mostrarIngreso) {
        this.mostrarIngreso = mostrarIngreso;
    }

    public Boolean getMostrarSalida() {
        return mostrarSalida;
    }

    public void setMostrarSalida(Boolean mostrarSalida) {
        this.mostrarSalida = mostrarSalida;
    }

    public Boolean getMostrarTraslado() {
        return mostrarTraslado;
    }

    public void setMostrarTraslado(Boolean mostrarTraslado) {
        this.mostrarTraslado = mostrarTraslado;
    }

    public List<Agencia> getListAgencia() {
        return listAgencia;
    }

    public void setListAgencia(List<Agencia> listAgencia) {
        this.listAgencia = listAgencia;
    }

    public Boolean getMostrarIngresoFechaInicio() {
        return mostrarIngresoFechaInicio;
    }

    public void setMostrarIngresoFechaInicio(Boolean mostrarIngresoFechaInicio) {
        this.mostrarIngresoFechaInicio = mostrarIngresoFechaInicio;
    }

    public Boolean getMostrarIngresoFechaFin() {
        return mostrarIngresoFechaFin;
    }

    public void setMostrarIngresoFechaFin(Boolean mostrarIngresoFechaFin) {
        this.mostrarIngresoFechaFin = mostrarIngresoFechaFin;
    }

    public Boolean getMostrarIngresoInsumo() {
        return mostrarIngresoInsumo;
    }

    public void setMostrarIngresoInsumo(Boolean mostrarIngresoInsumo) {
        this.mostrarIngresoInsumo = mostrarIngresoInsumo;
    }

    public String getNumeroDocumentoIngreso() {
        return numeroDocumentoIngreso;
    }

    public void setNumeroDocumentoIngreso(String numeroDocumentoIngreso) {
        this.numeroDocumentoIngreso = numeroDocumentoIngreso;
    }

    public Date getFechaInicioIngreso() {
        return fechaInicioIngreso;
    }

    public void setFechaInicioIngreso(Date fechaInicioIngreso) {
        this.fechaInicioIngreso = fechaInicioIngreso;
    }

    public Date getFechaFinIngreso() {
        return fechaFinIngreso;
    }

    public void setFechaFinIngreso(Date fechaFinIngreso) {
        this.fechaFinIngreso = fechaFinIngreso;
    }

    public String getInsumoIngreso() {
        return insumoIngreso;
    }

    public void setInsumoIngreso(String insumoIngreso) {
        this.insumoIngreso = insumoIngreso;
    }

    public String getInsumoSalida() {
        return insumoSalida;
    }

    public void setInsumoSalida(String insumoSalida) {
        this.insumoSalida = insumoSalida;
    }

    public String getNumeroDocumentoSalida() {
        return numeroDocumentoSalida;
    }

    public void setNumeroDocumentoSalida(String numeroDocumentoSalida) {
        this.numeroDocumentoSalida = numeroDocumentoSalida;
    }

    public Date getFechaInicioSalida() {
        return fechaInicioSalida;
    }

    public void setFechaInicioSalida(Date fechaInicioSalida) {
        this.fechaInicioSalida = fechaInicioSalida;
    }

    public Date getFechaFinSalida() {
        return fechaFinSalida;
    }

    public void setFechaFinSalida(Date fechaFinSalida) {
        this.fechaFinSalida = fechaFinSalida;
    }

    public Date getFechaInicioEnvio() {
        return fechaInicioEnvio;
    }

    public void setFechaInicioEnvio(Date fechaInicioEnvio) {
        this.fechaInicioEnvio = fechaInicioEnvio;
    }

    public Date getFechaFinEnvio() {
        return fechaFinEnvio;
    }

    public void setFechaFinEnvio(Date fechaFinEnvio) {
        this.fechaFinEnvio = fechaFinEnvio;
    }

    public Integer getIdAgenciaEnvio() {
        return idAgenciaEnvio;
    }

    public void setIdAgenciaEnvio(Integer idAgenciaEnvio) {
        this.idAgenciaEnvio = idAgenciaEnvio;
    }

    public Boolean getMostrarSalidaDocumento() {
        return mostrarSalidaDocumento;
    }

    public void setMostrarSalidaDocumento(Boolean mostrarSalidaDocumento) {
        this.mostrarSalidaDocumento = mostrarSalidaDocumento;
    }

    public Boolean getMostrarSalidaFechaInicio() {
        return mostrarSalidaFechaInicio;
    }

    public void setMostrarSalidaFechaInicio(Boolean mostrarSalidaFechaInicio) {
        this.mostrarSalidaFechaInicio = mostrarSalidaFechaInicio;
    }

    public Boolean getMostrarSalidaFechaFin() {
        return mostrarSalidaFechaFin;
    }

    public void setMostrarSalidaFechaFin(Boolean mostrarSalidaFechaFin) {
        this.mostrarSalidaFechaFin = mostrarSalidaFechaFin;
    }

    public Boolean getMostrarTrasladoFechaInicio() {
        return mostrarTrasladoFechaInicio;
    }

    public void setMostrarTrasladoFechaInicio(Boolean mostrarTrasladoFechaInicio) {
        this.mostrarTrasladoFechaInicio = mostrarTrasladoFechaInicio;
    }

    public Boolean getMostrarTrasladoFechaFin() {
        return mostrarTrasladoFechaFin;
    }

    public void setMostrarTrasladoFechaFin(Boolean mostrarTrasladoFechaFin) {
        this.mostrarTrasladoFechaFin = mostrarTrasladoFechaFin;
    }

    public Date getFechaInicioGeneral() {
        return fechaInicioGeneral;
    }

    public void setFechaInicioGeneral(Date fechaInicioGeneral) {
        this.fechaInicioGeneral = fechaInicioGeneral;
    }

    public Date getFechaFinGeneral() {
        return fechaFinGeneral;
    }

    public void setFechaFinGeneral(Date fechaFinGeneral) {
        this.fechaFinGeneral = fechaFinGeneral;
    }

    public Integer getIdAgenciaTecnico() {
        return idAgenciaTecnico;
    }

    public void setIdAgenciaTecnico(Integer idAgenciaTecnico) {
        this.idAgenciaTecnico = idAgenciaTecnico;
    }

    public Date getFechaInicioTecnico() {
        return fechaInicioTecnico;
    }

    public void setFechaInicioTecnico(Date fechaInicioTecnico) {
        this.fechaInicioTecnico = fechaInicioTecnico;
    }

    public Date getFechaFinTecnico() {
        return fechaFinTecnico;
    }

    public void setFechaFinTecnico(Date fechaFinTecnico) {
        this.fechaFinTecnico = fechaFinTecnico;
    }

}
