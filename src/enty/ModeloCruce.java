package enty;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Agarimo
 */
public class ModeloCruce {

    SimpleIntegerProperty id = new SimpleIntegerProperty();
    SimpleStringProperty expediente = new SimpleStringProperty();
    SimpleStringProperty nif = new SimpleStringProperty();
    SimpleStringProperty matricula = new SimpleStringProperty();
    SimpleStringProperty linea = new SimpleStringProperty();

    public int getId() {
        return id.get();
    }

    public void setId(int idTestra) {
        this.id.set(idTestra);
    }

    public String getExpediente() {
        return expediente.get();
    }

    public void setExpediente(String expediente) {
        this.expediente.set(expediente);
    }

    public String getNif() {
        return nif.get();
    }

    public void setNif(String nif) {
        this.nif.set(nif);
    }

    public String getMatricula() {
        return matricula.get();
    }

    public void setMatricula(String matricula) {
        this.matricula.set(matricula);
    }

    public String getLinea() {
        return linea.get();
    }

    public void setLinea(String lineaTestra) {
        this.linea.set(lineaTestra);
    }
}
