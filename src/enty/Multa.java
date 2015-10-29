package enty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import main.Regex;
import main.Var;
import util.CalculaNif;
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
    String fechaM;
    Date fechaMulta;
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

    public String getFechaM() {
        return fechaM;
    }

    public void setFechaM(String fechaMulta) {
        this.fechaM = fechaMulta;
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

    public Date getFechaMulta() {
        return fechaMulta;
    }

    public void setFechaMulta(Date fechaMulta) {
        this.fechaMulta = fechaMulta;
    }

    public void setLineaQuery(String linea) {
        this.linea = linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
        splitLinea(linea);
    }

    @Override
    public String toString() {
        String separador = "|";
        return fechaPublicacion + separador
                + codigoBoletin + separador
                + boletin + separador
                + origen + separador
                + Dates.imprimeFecha(fechaMulta) + separador
                + expediente + separador
                + nif + separador
                + matricula + separador
                + linea;
    }

    private void splitLinea(String linea) {
        String[] split = linea.split(" ");

        this.expediente = split[0];
        this.fechaM = Regex.getFecha(linea);
        this.fechaMulta = setFecha(this.fechaM);
        this.matricula = Regex.getMatricula(linea);
        this.nif = Regex.getDni(linea);

        if (this.nif == null) {
            String patron = "[\\s][XYZ]{1}[0-9]{4,6}[TRWAGMYFPDXBNJZSQVHLCKE]{1}[\\s]";

            if (Regex.isBuscar(patron, linea)) {
                splitNie(linea);
            } else {
                splitNif(linea);
            }
        } else {
            String patron = "[0-9]{6,8}[TRWAGMYFPDXBNJZSQVHLCKE]{1}";

            if (Regex.isBuscar(patron, this.nif)) {
                if (this.nif.length() == 8) {
                    this.nif = this.nif.replace(this.nif, "0" + this.nif).trim();
                }
                if (this.nif.length() == 7) {
                    this.nif = this.nif.replace(this.nif, "00" + this.nif).trim();
                }
                if (this.nif.length() == 6) {
                    this.nif = this.nif.replace(this.nif, "000" + this.nif).trim();
                }
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
        CalculaNif cn = new CalculaNif();
        String patronNif = "[0-9]{8}";
        String patronNie = "[ABCDEFGHJKLMNPQRSUVW]{1}[0-9]{7}";
        String patronCif = "[XYZ]{1}[0-9]{7}";

        String[] spl = linea.split(this.fechaM);
        linea = spl[0];
        String[] split = linea.split(" ");

        String aux;
        this.nif="";

        for (int i = 1; i < split.length; i++) {
            aux = split[i];

            if (Regex.isBuscar("[0-9]{5}", aux)) {
                this.nif = aux;
            }
        }

        if (this.nif.length() == 8) {
            if (Regex.isBuscar(patronNie, this.nif)) {
                this.nif = cn.calcular(Regex.buscar(patronNie, this.nif));
            } else if (Regex.isBuscar(patronCif, this.nif)) {
                this.nif = cn.calcular(Regex.buscar(patronCif, this.nif));
            } else if (Regex.isBuscar(patronNif, this.nif)) {
                this.nif = cn.calcular(Regex.buscar(patronNif, this.nif));

                if (this.nif.length() == 8) {
                    this.nif = this.nif.replace(this.nif, "0" + this.nif).trim();
                }
                if (this.nif.length() == 7) {
                    this.nif = this.nif.replace(this.nif, "00" + this.nif).trim();
                }
                if (this.nif.length() == 6) {
                    this.nif = this.nif.replace(this.nif, "000" + this.nif).trim();
                }
            }
        }
    }

    private static Date setFecha(String fecha) {
        Iterator<String> it = Var.strucFecha.iterator();
        SimpleDateFormat formato;
        String aux;
        Date date = null;

        while (it.hasNext()) {
            aux = it.next();
            formato = new SimpleDateFormat(aux);
            formato.setLenient(false);

            try {
                date = formato.parse(fecha);
            } catch (ParseException ex) {
                //
            }
        }
        return date;
    }

    public static String SQLBuscar(Date fecha) {
        return "SELECT * FROM datagest.cruceTestra where fechaPublicacion=" + Varios.entrecomillar(Dates.imprimeFecha(fecha));
    }

    public String SQLCrear() {
        if (fechaMulta == null) {
            return "INSERT into datagest.cruceTestra (fechaPublicacion,codigoEdicto,nEdicto,origen,expediente,nif,matricula,linea) values("
                    + Varios.entrecomillar(this.fechaPublicacion) + ","
                    + Varios.entrecomillar(this.codigoBoletin) + ","
                    + Varios.entrecomillar(this.boletin) + ","
                    + Varios.entrecomillar(this.origen) + ","
                    + Varios.entrecomillar(this.expediente) + ","
                    + Varios.entrecomillar(this.nif) + ","
                    + Varios.entrecomillar(this.matricula) + ","
                    + Varios.entrecomillar(this.linea)
                    + ")";
        } else {
            return "INSERT into datagest.cruceTestra (fechaPublicacion,codigoEdicto,nEdicto,origen,expediente,fechaMulta,nif,matricula,linea) values("
                    + Varios.entrecomillar(this.fechaPublicacion) + ","
                    + Varios.entrecomillar(this.codigoBoletin) + ","
                    + Varios.entrecomillar(this.boletin) + ","
                    + Varios.entrecomillar(this.origen) + ","
                    + Varios.entrecomillar(this.expediente) + ","
                    + Varios.entrecomillar(Dates.imprimeFecha(this.fechaMulta)) + ","
                    + Varios.entrecomillar(this.nif) + ","
                    + Varios.entrecomillar(this.matricula) + ","
                    + Varios.entrecomillar(this.linea)
                    + ")";
        }
    }

}
