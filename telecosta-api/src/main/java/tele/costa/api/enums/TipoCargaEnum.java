package tele.costa.api.enums;

/**
 *
 * @author rcacacho
 */
public enum TipoCargaEnum {
    INGRESO(1),
    SALIDA(2),
    ENVIOS(3);

    private final Integer id;

    private TipoCargaEnum(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

}
