package tele.costa.api.enums;

/**
 *
 * @author rcacacho
 */
public enum TipoAtencion {
    TRABAJO(1),
    SUSPENSION(2),
    CORTE(3),
    NUEVO_SERVICIO(4);

    private final Integer id;

    private TipoAtencion(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
