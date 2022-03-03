package tele.costa.web.caja;

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
import tele.costa.api.ejb.CajaBeanLocal;
import tele.costa.api.ejb.CatalogoBeanLocal;
import tele.costa.api.entity.Cajaagencia;
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
    private final String LOGO = "logo.jpeg";
    private String realPath;
    private String dirSeparator;

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
                if (idSectorPago != null) {
                    nombreReporte = "rptCajaAgenciaFechasSector";
                } else {
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
            } else {
                nombreReporte = "rptCajaAgenciaSector";
            }

            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = servletContext.getRealPath("/");

            String nombreArchivo = "CajaAgencia.pdf";
            HashMap parametros = new HashMap();
            parametros.put("IMAGE", "logo.jpeg");
            parametros.put("DIRECTORIO", realPath + File.separator + "resources" + File.separator + "images" + File.separator);
            parametros.put("USUARIO", SesionUsuarioMB.getUserName());

            if (fechaInicio != null && fechaFin != null) {
                parametros.put("FECHA_INICIO", fe);
                parametros.put("FECHA_FIN", fe2);
            }

            if (idSectorPago != null) {
                parametros.put("ID_SECTOR_PAGO", idSectorPago);
            }

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

    public StreamedContent imprimirExcelCompra() throws IOException {
        StreamedContent content = null;
        List<Cajaagencia> listaCaja = null;

        if (fechaInicio != null && fechaFin != null && idSectorPago != null) {
            listaCaja = cajaBean.listCajaByFechasAndSector(fechaInicio, fechaFin, idSectorPago);
        } else if (fechaInicio != null && fechaFin != null) {
            listaCaja = cajaBean.listCajaByFechas(fechaInicio, fechaFin);
        } else if (idSectorPago != null) {
            listaCaja = cajaBean.listCajaBySector(idSectorPago);
        }

        HashMap<Integer, Fila> mapaFilas = new HashMap<>();
        Workbook workbook = new SXSSFWorkbook(1000);
        Sheet sheet = workbook.createSheet("REPORTE_CAJA");

        int rownum = 0;
        int headerNum = 0;
        sheet.setColumnWidth(0, 900);
        sheet.setColumnWidth(1, 5000);
        sheet.setColumnWidth(2, 9000);
        sheet.setColumnWidth(3, 9000);
        sheet.setColumnWidth(4, 4000);
        sheet.setColumnWidth(5, 4000);
        sheet.setColumnWidth(6, 3000);
        sheet.setColumnWidth(7, 3000);
        sheet.setColumnWidth(8, 3000);
        sheet.setColumnWidth(9, 7000);
        sheet.setColumnWidth(10, 7000);
        sheet.setColumnWidth(11, 12000);

        CellStyle headerStyle = workbook.createCellStyle();
        XSSFColor color = new XSSFColor(new java.awt.Color(0, 56, 123));
        ((XSSFCellStyle) headerStyle).setFillForegroundColor(color);
        headerStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);

        CellStyle headerStyleBlanco = workbook.createCellStyle();
        XSSFColor colorBla = new XSSFColor(new java.awt.Color(255, 250, 250));
        ((XSSFCellStyle) headerStyle).setFillForegroundColor(colorBla);
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
            cell15.setCellValue("CAJA AGENCIA");
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
        celda0.setCellStyle(headerStyleBlanco);
        Cell celda1 = encabezados.createCell(headerNum++);
        celda1.setCellValue("Fecha");
        celda1.setCellStyle(headerStyleBlanco);
        Cell celda2 = encabezados.createCell(headerNum++);
        celda2.setCellValue("Municipio");
        celda2.setCellStyle(headerStyleBlanco);
        Cell celda3 = encabezados.createCell(headerNum++);
        celda3.setCellValue("Sector");
        celda3.setCellStyle(headerStyleBlanco);
        Cell celda4 = encabezados.createCell(headerNum++);
        celda4.setCellValue("Del");
        celda4.setCellStyle(headerStyleBlanco);
        Cell celda5 = encabezados.createCell(headerNum++);
        celda5.setCellValue("Al");
        celda5.setCellStyle(headerStyleBlanco);
        Cell celda6 = encabezados.createCell(headerNum++);
        celda6.setCellValue("Ingreso");
        celda6.setCellStyle(headerStyleBlanco);
        Cell celda7 = encabezados.createCell(headerNum++);
        celda7.setCellValue("Egreso");
        celda7.setCellStyle(headerStyleBlanco);
        Cell celda8 = encabezados.createCell(headerNum++);
        celda8.setCellValue("Saldo");
        celda8.setCellStyle(headerStyleBlanco);
        Cell celda9 = encabezados.createCell(headerNum++);
        celda9.setCellValue("Forma");
        celda9.setCellStyle(headerStyleBlanco);
        Cell celda10 = encabezados.createCell(headerNum++);
        celda10.setCellValue("No. Documento");
        celda10.setCellStyle(headerStyleBlanco);
        Cell celda11 = encabezados.createCell(headerNum++);
        celda11.setCellValue("Observaciones");
        celda11.setCellStyle(headerStyleBlanco);
        int correlativo = 1;

        for (Cajaagencia reporte : listaCaja) {
            if (!mapaFilas.containsKey(reporte.getIdcajaagencia())) {
                Fila fila = new Fila(sheet.createRow(rownum++));
                mapaFilas.put(reporte.getIdcajaagencia(), fila);

                Cell cell = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell.setCellValue(correlativo++);
                cell.setCellStyle(cellStyle);

                Cell cell1 = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell1.setCellValue(reporte.getFechacreacion());
                cell1.setCellStyle(cellStyleFecha);

                Cell cell2 = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell2.setCellValue(reporte.getIdsectorpago().getIdmunicipio().getMunicipio());
                cell2.setCellStyle(cellStyle);

                Cell cell3 = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell3.setCellValue(reporte.getIdsectorpago().getNombre());
                cell3.setCellStyle(cellStyle);

                Cell cell4 = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell4.setCellValue(reporte.getCorrelativodel());
                cell4.setCellStyle(cellStyle);

                Cell cell5 = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell5.setCellValue(reporte.getCorrelativoal());
                cell5.setCellStyle(cellStyle);

                Cell cell6 = fila.getFila().createCell(fila.nextIndex().shortValue());
                cell6.setCellValue(reporte.getIngreso());
                cell6.setCellStyle(cellStyleNumero);

                if (reporte.getEgreso() != null) {
                    Cell cell7 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell7.setCellValue(reporte.getEgreso());
                    cell7.setCellStyle(cellStyleNumero);
                } else {
                    Cell cell7 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell7.setCellValue("");
                    cell7.setCellStyle(cellStyle);
                }

                if (reporte.getSaldo() != null) {
                    Cell cell8 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell8.setCellValue(reporte.getSaldo());
                    cell8.setCellStyle(cellStyleNumero);
                } else {
                    Cell cell8 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell8.setCellValue("");
                    cell8.setCellStyle(cellStyle);
                }

                if (reporte.getForma() != null) {
                    Cell cell9 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell9.setCellValue(reporte.getForma());
                    cell9.setCellStyle(cellStyle);
                } else {
                    Cell cell9 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell9.setCellValue("");
                    cell9.setCellStyle(cellStyle);
                }

                if (reporte.getNodocumento() != null) {
                    Cell cell10 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell10.setCellValue(reporte.getNodocumento());
                    cell10.setCellStyle(cellStyle);
                } else {
                    Cell cell10 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell10.setCellValue("");
                    cell10.setCellStyle(cellStyle);
                }

                if (reporte.getObservaciones() != null) {
                    Cell cell11 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell11.setCellValue(reporte.getObservaciones());
                    cell11.setCellStyle(cellStyle);
                } else {
                    Cell cell11 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell11.setCellValue("");
                    cell11.setCellStyle(cellStyle);
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

    public String getOutputDir() {
        return String.format("%sresources%sreports%sgenerated%s", getRealPath(), getDirSeparator(), getDirSeparator(), getDirSeparator());
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
