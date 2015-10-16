package enty;

import java.util.Date;
import java.util.List;
import main.Sql;

/**
 *
 * @author Agarimo
 */
public class ProcesoCruce {

    int totalTestra;
    int totalIdbl;

    List<Cruce> listaTestra;
    List<Cruce> listaIdbl;

    public ProcesoCruce() {

    }

    public void setListaTestra(List<Cruce> listaTestra) {
        this.listaTestra = listaTestra;
        totalTestra = listaTestra.size();
    }

    public void setListaIdbl(List<Cruce> listaIdbl) {
        this.listaIdbl = listaIdbl;
        totalIdbl = listaIdbl.size();
    }

    public int getTotalTestra() {
        return totalTestra;
    }

    public int getTotalIdbl() {
        return totalIdbl;
    }

    public List<Cruce> getListaTestra() {
        return listaTestra;
    }

    public boolean cruzarMulta(Cruce aux) {

        return true;
    }
}
