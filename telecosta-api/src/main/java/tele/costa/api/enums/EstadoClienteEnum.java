package tele.costa.api.enums;

/**
 *
 * @author elfo_
 */
public enum EstadoClienteEnum {

    ACTIVO(1),
    SUSPENDIDO(2),
    CORTE(3);

    private final Integer id;

    private EstadoClienteEnum(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

}
