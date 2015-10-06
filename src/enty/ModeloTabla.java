package enty;

import java.util.Date;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import util.Dates;

/**
 *
 * @author Agarimo
 */
public class ModeloTabla {
    public SimpleStringProperty id = new SimpleStringProperty();
    public SimpleStringProperty fecha = new SimpleStringProperty();
    public SimpleStringProperty csv = new SimpleStringProperty();
    public SimpleStringProperty datos = new SimpleStringProperty();
    public SimpleIntegerProperty estado = new SimpleIntegerProperty();

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }
    
    public String getCsv(){
        return csv.get();
    }
    
    public void setCsv(String csv){
        this.csv.set(csv);
    }

    public String getDatos() {
        return datos.get();
    }

    public void setDatos(String datos) {
        this.datos.set(datos);
    }
    
    public String getFecha(){
        return fecha.get();
    }
    
    public void setFecha(Date fecha){
        this.fecha.set(Dates.imprimeFecha(fecha));
    }
    
    public int getEstado(){
        return this.estado.get();
    }
    
    public void setEstado(int estado){
        this.estado.set(estado);
    }
    
    
    
    
}
