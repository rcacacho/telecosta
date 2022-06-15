package tele.costa.api.enums;

/**
 *
 * @author rcacacho
 */
public enum TipoPagoEnum {
    COBRO(1),
    PAGO(2),
    MATERIALTVADICIONAL(3);

    private final Integer id;

    private TipoPagoEnum(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
