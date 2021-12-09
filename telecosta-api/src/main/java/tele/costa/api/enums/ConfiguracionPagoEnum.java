package tele.costa.api.enums;

/**
 *
 * @author rcacacho
 */
public enum ConfiguracionPagoEnum {
    CABLE(1),
    INTERNET(2),
    CABLE_INTERNET(3);

    private final Integer id;

    private ConfiguracionPagoEnum(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
