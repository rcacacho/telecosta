package telecosta.web.utils;

import java.math.BigDecimal;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author rcacacho
 */
public class Fila {

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
