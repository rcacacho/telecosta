package tele.costa.web.cliente;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import static org.apache.poi.ss.util.NumberToTextConverter.toText;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import tele.costa.api.ejb.ClienteBeanLocal;
import tele.costa.api.entity.Cliente;
import telecosta.web.utils.FileUtil;
import telecosta.web.utils.JsfUtil;

/**
 *
 * @author elfo_
 */
@ManagedBean(name = "listaClienteMB")
@ViewScoped
public class ListaClienteMB implements Serializable {

    private static final Logger log = Logger.getLogger(ListaClienteMB.class);

    @EJB
    private ClienteBeanLocal clienteBean;

    private List<Cliente> listCliente;
    private String nombre;
    private String cui;
    private UploadedFile file;

    public ListaClienteMB() {
    }

    @PostConstruct
    void cargarDatos() {
        listCliente = clienteBean.ListClientes();
    }

    public void buscarCliente() {
        if (nombre != null) {
            List<Cliente> response = clienteBean.ListClientesByNombre(nombre);
            if (response != null) {
                listCliente = response;
            } else {
                listCliente = new ArrayList<>();
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else if (cui != null) {
            List<Cliente> response = clienteBean.ListClientesByCui(cui);
            if (response.size() > 0) {
                listCliente = response;
            } else {
                JsfUtil.addErrorMessage("No se encontraron datos");
            }
        } else {
            JsfUtil.addErrorMessage("No se encontraron datos");
        }
    }

    public void limpiarCampos() {
        nombre = null;
        cui = null;
        cargarDatos();
    }

    public void detalleCliente(Integer id) {
        JsfUtil.redirectTo("/clientes/detalle.xhtml?idCliente=" + id);
    }

    public void handleMainFileUpload(FileUploadEvent event) throws IOException {
        file = event.getFile();
        String nombreArchivo = file.getFileName();
        cargarDatosArchivo(file.getInputstream());
    }

    public void cargarDatosArchivo(InputStream inputStream) throws IOException {
        Integer totalRegistros = 0;
        Integer totalExitosos = 0;
        Integer totalFilas = 0;
        BigDecimal montoTotalExitoso = new BigDecimal("0.00");
        //List<NpDetalleCargaMasiva> listaDetalle = new ArrayList();

        XSSFWorkbook book = new XSSFWorkbook(inputStream);
        // Obtener medinte indice la hoja que se leera
        XSSFSheet sheet = book.getSheetAt(0);
        // Obteniendo todas las filas de la hoja leida
        Iterator<Row> rowIterator = sheet.iterator();

        // CRendo un StringBuilder donde almacenaremos los datos del 
        // Excel temporalmente para mostrarlo en consola
        StringBuilder sb = new StringBuilder();

        Row row;
        // Recorreremos cada fila de la hoja hasta llegar al final
        while (rowIterator.hasNext()) {
            row = rowIterator.next();
            // Obteniendo las celdas de cada fila para obtener valores
            Iterator<Cell> cellIterator = row.cellIterator();
            Cell cell;
            //Recorrienco las celdas de la fila obtenida
            while (cellIterator.hasNext()) {
                // Obteniendo el valor de la celda para ser almacenada en nuestro buffer
                cell = cellIterator.next();
                // Agregando a nuestro buffer los valores leidos 
                // de las celdas incluyendo la cabecera
                // Haremos una pequeña validacion para saber si la columna que 
                // estamos leyendo esta en la posicion cero, si es la primera no agregaremos formato
                // de lo contrario rellenaremos con espacios la cadena pare verlo de forma ordenada.
                if (cell.getColumnIndex() == 0) {
                    sb.append(cell.toString()).append("\t");
                } else {
                    sb.append(String.format("%-30s", cell.toString())).append("\t");
                }
                System.out.println(sb.toString());
            }

        }
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

    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

}
