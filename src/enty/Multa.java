package enty;

import main.Regex;

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

    public void setLinea(String linea) {
        this.linea = linea;
        splitLinea(linea);
    }

    private void splitLinea(String linea) {
        String[] split = linea.split(" ");

        this.expediente = split[0];
        this.fechaMulta = Regex.getFecha(linea);
        this.matricula = Regex.getMatricula(linea);
        this.nif = Regex.getDni(linea);
        
        if(this.nif==null){
            splitNif(linea);
        }
    }
    
    private void splitNif(String linea){
        String patron="[0-9]{7,8}";
        String[] splitFecha = linea.split(this.fechaMulta);
        String[] split =splitFecha[0].split(" ");
        StringBuilder sb = new StringBuilder();
        String aux;
        
        for (int i = 1; i < split.length; i++) {
            sb.append(split[i]);
        }
        
        aux=sb.toString();
        this.nif=Regex.buscar(patron, aux);
    }

}
