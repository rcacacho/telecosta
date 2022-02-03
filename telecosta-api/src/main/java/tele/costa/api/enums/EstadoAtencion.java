package tele.costa.api.enums;

/**
 *
 * @author elfo_
 */
public enum EstadoAtencion {

    EN_PROCESO(1),
    FINALIZADA(0);

    private final Integer id;

    private EstadoAtencion(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

}
