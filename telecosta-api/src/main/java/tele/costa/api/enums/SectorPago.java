package tele.costa.api.enums;

/**
 *
 * @author rcacacho
 */
public enum SectorPago {
    CTN1(1),
    CTN2(2),
    MLN1(3),
    MLN2(4),
    MLN3(5),
    MLN4(6),
    MLN5(7),
    SNP1(8),
    SNP2(9),
    SNR1(10),
    ERD1(11);

    private final Integer id;

    private SectorPago(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
    
}
