package enty;

/**
 *
 * @author Agarimo
 */
public enum Estado {

    SIN_PROCESAR(0),
    PROCESADO(1),
    CON_ERRORES(2),
    SIN_MULTAS(3),
    ERROR_INSERCION(4),
    DESCARTADO(5);

    private final int value;

    Estado(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        switch (this) {
            case SIN_PROCESAR:
                return "SIN PROCESAR";
            case PROCESADO:
                return "PROCESADO";
            case CON_ERRORES:
                return "CON ERRORES";
            case SIN_MULTAS:
                return "SIN MULTAS";
            case ERROR_INSERCION:
                return "ERROR INSERCION";
            case DESCARTADO:
                return "DESCARTADO";
            default:
                throw new IllegalArgumentException();
        }
    }

}
