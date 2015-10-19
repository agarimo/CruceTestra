package enty;

import java.util.List;

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
        return listaIdbl.contains(aux);
    }
}
