package enty;

import java.util.Date;
import main.Regex;
import util.Dates;
import util.Varios;

/**
 *
 * @author Agarimo
 */
public class Multa {

    int id;
    String fechaPublicacion;
    String codigoBoletin;
    String boletin;
    String origen;
    String fechaMulta;
    String expediente;
    String nif;
    String matricula;
    String linea;

    public Multa() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigoBoletin() {
        return codigoBoletin;
    }

    public void setCodigoBoletin(String codigoBoletin) {
        this.codigoBoletin = codigoBoletin;
        String[] split = codigoBoletin.split("-");
        this.boletin = split[0];
        this.origen = split[1];
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public String getBoletin() {
        return boletin;
    }

    public String getOrigen() {
        return origen;
    }

    public String getFechaMulta() {
        return fechaMulta;
    }

    public void setFechaMulta(String fechaMulta) {
        this.fechaMulta = fechaMulta;
    }

    public String getExpediente() {
        return expediente;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public void setExpediente(String expediente) {
        this.expediente = expediente;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getLinea() {
        return linea;
    }
    
    public void setLineaQuery(String linea){
        this.linea=linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
        splitLinea(linea);
    }

    @Override
    public String toString() {
        String separador="|";
        return fechaPublicacion+separador+
                codigoBoletin+separador+
                boletin+separador+
                origen+separador+
                fechaMulta+separador+
                expediente+separador+
                nif+separador+
                matricula+separador+
                linea;
    }

    private void splitLinea(String linea) {
        String[] split = linea.split(" ");

        this.expediente = split[0];
        this.fechaMulta = Regex.getFecha(linea);
        this.matricula = Regex.getMatricula(linea);
        this.nif = Regex.getDni(linea);

        if (this.nif == null) {
            String patron = "[\\s][XYZ]{1}[0-9]{4,6}[TRWAGMYFPDXBNJZSQVHLCKE]{1}[\\s]";

            if (Regex.isBuscar(patron, linea)) {
                splitNie(linea);
            } else {
                splitNif(linea);
            }
        }
    }

    private void splitNie(String linea) {
        String nie;
        String patron = "[\\s][XYZ]{1}[0-9]{4,6}[TRWAGMYFPDXBNJZSQVHLCKE]{1}[\\s]";
        nie = Regex.buscar(patron, linea).trim();
        String sub = nie.substring(0, 1);

        if (nie.length() == 8) {
            this.nif = nie.replace(sub, sub + "0").trim();
        }
        if (nie.length() == 7) {
            this.nif = nie.replace(sub, sub + "00").trim();
        }
        if (nie.length() == 6) {
            this.nif = nie.replace(sub, sub + "000").trim();
        }
    }

    private void splitNif(String linea) {
        String patron = "[0-9]{7,8}";
        String[] splitFecha = linea.split(this.fechaMulta);
        String[] split = splitFecha[0].split(" ");
        StringBuilder sb = new StringBuilder();
        String aux;

        for (int i = 1; i < split.length; i++) {
            sb.append(split[i]);
        }

        aux = sb.toString();
        this.nif = Regex.buscar(patron, aux);
    }
    
    public static String SQLBuscar(Date fecha){
        return "SELECT * FROM datagest.cruceTestra where fechaPublicacion="+Varios.entrecomillar(Dates.imprimeFecha(fecha));
    }

    public String SQLCrear() {
        return "INSERT into datagest.cruceTestra (fechaPublicacion,codigoEdicto,nEdicto,origen,expediente,fechaMulta,nif,matricula,linea) values("
                + Varios.entrecomillar(this.fechaPublicacion) + ","
                + Varios.entrecomillar(this.codigoBoletin) + ","
                + Varios.entrecomillar(this.boletin) + ","
                + Varios.entrecomillar(this.origen) + ","
                + Varios.entrecomillar(this.expediente) + ","
                + Varios.entrecomillar(this.fechaMulta) + ","
                + Varios.entrecomillar(this.nif) + ","
                + Varios.entrecomillar(this.matricula) + ","
                + Varios.entrecomillar(this.linea)
                + ")";
    }

}
