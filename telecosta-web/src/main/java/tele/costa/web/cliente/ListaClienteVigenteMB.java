package tele.costa.web.cliente;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
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
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import tele.costa.api.ejb.CatalogoBeanLocal;
import tele.costa.api.ejb.ClienteBeanLocal;
import tele.costa.api.ejb.PagosBeanLocal;
import tele.costa.api.entity.Cliente;
import tele.costa.api.entity.Configuracionpago;
import tele.costa.api.entity.Estadocliente;
import tele.costa.api.entity.Municipio;
import tele.costa.api.entity.Pago;
import tele.costa.api.entity.Usuariomunicipio;
import tele.costa.api.enums.EstadoClienteEnum;
import tele.costa.api.wrapper.CobroWrapper;
import telecosta.web.utils.JsfUtil;
import telecosta.web.utils.SesionUsuarioMB;

/**
 *
 * @author rcacacho
 */
@ManagedBean(name = "listaClienteVigenteMB")
@ViewScoped
public class ListaClienteVigenteMB implements Serializable {

    private static final Logger log = Logger.getLogger(ListaClienteMorosoMB.class);

    @EJB
    private ClienteBeanLocal clienteBean;
    @EJB
    private CatalogoBeanLocal catalogoBean;
    @EJB
    private PagosBeanLocal pagosBean;

    private List<Cliente> listCliente;
    private List<Cliente> listClientePago;
    private String nombre;
    private String codigo;
    private Integer idMunicipio;
    private String sector;
    private List<Municipio> listMunicipios;
    private String motivoCorte;
    private Cliente clienteSelected;
    private List<Configuracionpago> listConfiguracionPago;
    private String color;
    private final String LOGO = "logo.jpeg";
    private String realPath;
    private String dirSeparator;

    public ListaClienteVigenteMB() {
        listClientePago = new ArrayList<>();
    }

    @PostConstruct
    void cargarDatos() {
        if (SesionUsuarioMB.getRootUsuario()) {
            listCliente = clienteBean.ListClientesEstadoActivo();

            for (Cliente cc : listCliente) {
                Pago pp = new Pago();
                pp = pagosBean.findUltimoPago(cc.getIdcliente());
                if (pp != null) {
                    if (pp.getFechapago() != null) {
                        Date fechaInicio = new Date();
                        LocalDate fecha = fechaInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                        Integer anio = fecha.getYear();
                        Integer mes = fecha.getMonthValue();
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

                        switch (pp.getMes()) {
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

                        if ((mesLetra.equals(pp.getMes()) || mesLetraMa.equals(pp.getMes())) && anio.equals(pp.getAnio())) {
                            listClientePago.add(pp.getIdcliente());
                        } else if (mesNumero > mes && anio.equals(pp.getAnio())) {
                            listClientePago.add(pp.getIdcliente());
                        }
                    }
                }
            }
        } else {
            List<Usuariomunicipio> listUsuarioMun = catalogoBean.listUsuarioMunicipio(SesionUsuarioMB.getUserId());
            if (listUsuarioMun != null) {
                List<Integer> list = new ArrayList<>();
                for (Usuariomunicipio uu : listUsuarioMun) {
                    list.add(uu.getIdmunicipio().getIdmunicipio());
                }
                listCliente = clienteBean.ListClientesByListMunucipioEstadoActivo(list);

                for (Cliente cc : listCliente) {
                    Pago pp = new Pago();
                    pp = pagosBean.findUltimoPago(cc.getIdcliente());
                    if (pp != null) {
                        if (pp.getFechapago() != null) {
                            Date fechaInicio = new Date();
                            LocalDate fecha = fechaInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                            Integer anio = fecha.getYear();
                            Integer mes = fecha.getMonthValue();
                            String mesLetra = "";
                            String mesLetraMa = "";

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

                            if ((mesLetra.equals(pp.getMes()) || mesLetraMa.equals(pp.getMes())) && anio.equals(pp.getAnio())) {
                                listClientePago.add(pp.getIdcliente());
                            }
                        }
                    }
                }

            } else {
                listCliente = clienteBean.ListClientesByIdMunicipioEstadoActivo(SesionUsuarioMB.getIdMunicipio());

                for (Cliente cc : listCliente) {
                    Pago pp = new Pago();
                    pp = pagosBean.findUltimoPago(cc.getIdcliente());
                    if (pp != null) {
                        if (pp.getFechapago() != null) {
                            Date fechaInicio = new Date();
                            LocalDate fecha = fechaInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                            Integer anio = fecha.getYear();
                            Integer mes = fecha.getMonthValue();
                            String mesLetra = "";
                            String mesLetraMa = "";

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

                            if ((mesLetra.equals(pp.getMes()) || mesLetraMa.equals(pp.getMes())) && anio.equals(pp.getAnio())) {
                                listClientePago.add(pp.getIdcliente());
                            }
                        }
                    }
                }
            }
        }

        listMunicipios = catalogoBean.listMunicipioByIdDepartamento(1);
        listConfiguracionPago = catalogoBean.ListConfiguracionPago();
    }

    public void detalleCliente(Integer id) {
        JsfUtil.redirectTo("/clientes/detalle.xhtml?idCliente=" + id);
    }

    public String obtenerUltimoPago(Integer idcliente) {
        Pago response = pagosBean.findUltimoPago(idcliente);
        if (response != null) {

            if (response.getFechapago() != null) {
                Date fechaInicio = new Date();
                LocalDate startDate = response.getFechapago().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate endDate = fechaInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                ZoneId defaultZoneId = ZoneId.systemDefault();
                Integer count = 0;
                for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
                    count++;
                }
            }

            String a = new StringBuilder(response.getMes()).append("-").append(response.getAnio().toString()).toString();
            return a;
        } else {
            return null;
        }
    }

    public void suspenderCliente(Cliente cl) throws IOException {
        Estadocliente estado = catalogoBean.findEstadoCliente(EstadoClienteEnum.SUSPENDIDO.getId());
        cl.setIdestadocliente(estado);
        cl.setFechamodificacion(new Date());
        cl.setUsuariomodificacion(SesionUsuarioMB.getUserName());
        Cliente response = clienteBean.updateCliente(cl);
        if (response != null) {
            JsfUtil.addSuccessMessage("Cliente suspendido exitosamente");
        } else {
            JsfUtil.addErrorMessage("Ocurrio un al inactivar al cliente");
        }
    }

    public void dialogCorte(Cliente cl) {
        RequestContext.getCurrentInstance().execute("PF('dlgCorte').show()");
        motivoCorte = null;
        clienteSelected = cl;
    }

    public void cerrarDialog() {
        RequestContext.getCurrentInstance().execute("PF('dlgCorte').hide()");
    }

    public void corteCliente() throws IOException {
        Estadocliente estado = catalogoBean.findEstadoCliente(EstadoClienteEnum.CORTE.getId());
        clienteSelected.setIdestadocliente(estado);
        clienteSelected.setFechamodificacion(new Date());
        clienteSelected.setUsuariomodificacion(SesionUsuarioMB.getUserName());
        Cliente response = clienteBean.updateCliente(clienteSelected);
        if (response != null) {
            JsfUtil.addSuccessMessage("Cliente inactivado exitosamente");
            cargarDatos();
        } else {
            JsfUtil.addErrorMessage("Ocurrio un al inactivar al cliente");
        }
    }

    public void pagoCliente(Integer id) {
        JsfUtil.redirectTo("/pagos/registroCliente.xhtml?idCliente=" + id);
    }

    public void onRowEdit(RowEditEvent event) {
        Object value = event.getObject();
        Cliente tipo = (Cliente) value;

        if (tipo != null) {
            Cliente tt = clienteBean.updateCliente(tipo);
            JsfUtil.addSuccessMessage("Se actualizo el cliente exitosamente");
            cargarDatos();
        } else {
            JsfUtil.addErrorMessage("Sucedio un error al actualizar el registro");
        }
    }

    public void ticketCliente(Integer id) {
        JsfUtil.redirectTo("/atencion/registroCliente.xhtml?idcliente=" + id);
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

     public StreamedContent imprimirExcelCliente() throws IOException {
        StreamedContent content = null;
        List<Cliente> listaCliente = clienteBean.ListClientes();
        List<CobroWrapper> listCobro = new ArrayList<>();

        for (Cliente cl : listaCliente) {
            Pago pag = pagosBean.findUltimoPago(cl.getIdcliente());
            if (pag != null) {
                CobroWrapper cobroWrapper = new CobroWrapper();
                cobroWrapper.setCodigo(cl.getCodigo());
                cobroWrapper.setDireccion(cl.getDireccion());
                cobroWrapper.setFechapago(pag.getMes() + '-' + pag.getAnio());
                cobroWrapper.setIdpago(pag.getIdpago());
                cobroWrapper.setNombres(cl.getNombres());
                cobroWrapper.setObservacion(pag.getObservacion());
                cobroWrapper.setTiposervicio(cl.getTipocliente());

                if (cl.getIdSector() != null) {
                    cobroWrapper.setSector(cl.getSector());
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
        Sheet sheet = workbook.createSheet("REPORTE_CLIENTES");

        int rownum = 0;
        int headerNum = 0;
        sheet.setColumnWidth(0, 900);
        sheet.setColumnWidth(1, 6000);
        sheet.setColumnWidth(2, 9000);
        sheet.setColumnWidth(3, 6000);
        sheet.setColumnWidth(4, 11000);
        sheet.setColumnWidth(5, 5000);
        sheet.setColumnWidth(6, 5000);
        sheet.setColumnWidth(7, 3000);
        sheet.setColumnWidth(8, 4000);
        sheet.setColumnWidth(9, 11000);
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
            cell15.setCellValue("CLIENTES");
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
        celda8.setCellValue("TIPO DE SERVICIO");
        celda8.setCellStyle(headerStyle);
        Cell celda9 = encabezados.createCell(headerNum++);
        celda9.setCellValue("OBSERVACIONES");
        celda9.setCellStyle(headerStyle);
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

                if (reporte.getTiposervicio() != null) {
                    Cell cell8 = fila.getFila().createCell(fila.nextIndex().shortValue());
                    cell8.setCellValue(reporte.getTiposervicio());
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


    /*Metodos getters y setters*/
    public List<Cliente> getListCliente() {
        return listCliente;
    }

    public void setListCliente(List<Cliente> listCliente) {
        this.listCliente = listCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List<Municipio> getListMunicipios() {
        return listMunicipios;
    }

    public void setListMunicipios(List<Municipio> listMunicipios) {
        this.listMunicipios = listMunicipios;
    }

    public Integer getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Integer idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getMotivoCorte() {
        return motivoCorte;
    }

    public void setMotivoCorte(String motivoCorte) {
        this.motivoCorte = motivoCorte;
    }

    public List<Configuracionpago> getListConfiguracionPago() {
        return listConfiguracionPago;
    }

    public void setListConfiguracionPago(List<Configuracionpago> listConfiguracionPago) {
        this.listConfiguracionPago = listConfiguracionPago;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Cliente> getListClientePago() {
        return listClientePago;
    }

    public void setListClientePago(List<Cliente> listClientePago) {
        this.listClientePago = listClientePago;
    }

}
