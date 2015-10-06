package enty;

import java.util.Date;
import util.Dates;
import util.Varios;

/**
 *
 * @author Agarimo
 */
public class Descarga {

    private String id;
    private String csv;
    private String datos;
    private Date fecha;
    private int estado;

    public Descarga() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCsv() {
        return csv;
    }

    public void setCsv(String csv) {
        this.csv = csv;
    }

    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public static String SQLBuscar(Date fecha) {
        return "SELECT b.idEdicto, a.fecha,a.csv,a.datos,a.estadoCruce FROM datagest.descarga a "
                + "left join datagest.edicto b on a.idDescarga=b.idDescarga "
                + "where fecha=" + Varios.entrecomillar(Dates.imprimeFecha(fecha));
    }
}
