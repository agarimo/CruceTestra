package enty;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import main.Regex;

/**
 *
 * @author Agarimo
 */
public class ProcesoManual {

    int id;
    String codigo;
    String fecha;
    List<String> all;
    List<String> valid;
    List<String> invalid;

    public ProcesoManual(int id,String codigo, String fecha, List<String> all) {
        this.id=id;
        this.codigo = codigo;
        this.fecha = fecha;
        this.all = all;
        this.valid = new ArrayList();
        this.invalid = new ArrayList();
        split();
    }

    private void split() {
        String aux;
        Iterator<String> it = all.iterator();

        while (it.hasNext()) {
            aux = it.next();

            if (isValid(aux)) {
                valid.add(aux);
            } else {
                invalid.add(aux);
            }
        }
    }

    private boolean isValid(String item) {
        if (Regex.buscar(item, Regex.FECHA)) {
            return Regex.buscar(item, Regex.DNI) || Regex.buscar(item, Regex.MATRICULA);
        } else {
            return false;
        }
    }

    public int getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getFecha() {
        return fecha;
    }

    public List<String> getValid() {
        return valid;
    }

    public void addToValid(String item) {
        invalid.remove(item);
        valid.add(item);
    }

    public List<String> getInvalid() {
        return invalid;
    }

    public int validCount() {
        return valid.size();
    }

    public int invalidCount() {
        return invalid.size();
    }
}
