package tele.costa.api.enums;

/**
 *
 * @author rcacacho
 */
public enum TipoCarga {

    INGRESO(1),
    SALIDA(2),
    ENVIO(3);

    private final Integer id;

    private TipoCarga(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

}
