package enty;

/**
 *
 * @author Agarimo
 */
public enum TipoCruce {

    TESTRA(0),
    IDBL(1);

    private final int value;

    TipoCruce(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        switch (this) {
            case TESTRA:
                return "TESTRA";
            case IDBL:
                return "IDBL";
            default:
                throw new IllegalArgumentException();
        }
    }
}
