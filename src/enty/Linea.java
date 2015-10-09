package enty;

import javafx.scene.paint.Color;
import main.Regex;

/**
 *
 * @author Agarimo
 */
public class Linea {

    boolean isValido;
    String linea;

    public Linea(String linea) {
        this.linea = linea;
        this.isValido = isValido(linea);
    }

    public boolean isIsValido() {
        return isValido;
    }

    public void setIsValido(boolean isValido) {
        this.isValido = isValido;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    @Override
    public String toString() {
        return linea.trim();
    }

    private boolean isValido(String item) {
        if (Regex.buscar(item, Regex.FECHA)) {
            return Regex.buscar(item, Regex.DNI) || Regex.buscar(item, Regex.MATRICULA);
        } else {
            return false;
        }
    }

}
