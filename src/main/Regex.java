package main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Agárimo
 */
public class Regex {

    public static int FECHA = 0;
    public static int DNI = 1;
    public static int MATRICULA = 2;

    private static Pattern pt;
    private static Matcher mt;

    private static final String[] fechas = {
        "[\\s][\\d]{2}[/-][\\d]{1,2}[/-][\\d]{4}[\\s]",
        "[\\s][\\d]{4}[/-][\\d]{1,2}[/-][\\d]{2}[\\s]",
        "[\\s][\\d]{2}[/-][\\d]{1,2}[/-][\\d]{2}[\\s]",
        "[\\s][\\d]{1,2}[/-][\\d]{1,2}[/-]20[\\d]{2}[\\s]",
        "[\\s]NO CONSTA[\\s]"
    };

    private static final String[] identificacion = {
        "[\\s][0-9]{6,8}[TRWAGMYFPDXBNJZSQVHLCKE]{1}[\\s]",
        "[\\s][XYZ]{1}[0-9]{7}[TRWAGMYFPDXBNJZSQVHLCKE]{1}[\\s]",
        "[\\s][ABCDEFGHJKLMNPQRSUVW]{1}[0-9]{8}[\\s]",
        "[\\s][ABCDEFGHJKLMNPQRSUVW]{1}[0-9]{7}[JABCDEFGHI]{1}[\\s]",
        "[0-9]{6,8}[TRWAGMYFPDXBNJZSQVHLCKE]{1}[\\s]"
    };

    private static final String[] matriculas = {
        "[\\s][0-9]{4}[\\s-]{0,1}[A-Z]{3}[\\s]",
        "[\\s][A-Z]{1,2}[\\s-]{0,1}[0-9]{4}[\\s-]{0,1}[A-Z]{1,2}[\\s]",
        "[\\s][CEPRST]{1}[\\s-]{0,1}[0-9]{4}[\\s-]{0,1}[A-Z]{3}[\\s]"
    };
    
    public static boolean isBuscar(String patron, String str){
        pt = Pattern.compile(patron);
        mt = pt.matcher(str);

        return mt.find();
    }
    
    public static String buscar(String patron, String str) {
        String aux;
        pt = Pattern.compile(patron);
        mt = pt.matcher(str);

        if (mt.find()) {
            aux = mt.group();
        } else {
            aux = null;
        }
        return aux;
    }

    public static boolean buscar(String str, int tipo) {
        boolean existe = false;

        switch (tipo) {
            case 0:
                if (getFecha(str) != null) {
                    existe = true;
                }
                
                break;
            case 1:
                if (getDni(str) != null) {
                    existe = true;
                }
                
                break;
            case 2:
                if (getMatricula(str) != null) {
                    existe = true;
                }
                
                break;
        }
        return existe;
    }

    public static String getFecha(String str) {
        String aux = null;
        for (String fecha : fechas) {
            pt = Pattern.compile(fecha);
            mt = pt.matcher(str);
            if (mt.find()) {
                aux = mt.group().trim();
                break;
            }
        }
        return aux;
    }

    public static String getDni(String str) {
        String aux = null;

        for (String identificacion1 : identificacion) {
            pt = Pattern.compile(identificacion1);
            mt = pt.matcher(str);
            if (mt.find()) {
                aux = mt.group().trim();
                break;
            }
        }
        
        return aux;
    }

    public static String getMatricula(String str) {
        String aux = null;

        for (String matricula : matriculas) {
            pt = Pattern.compile(matricula);
            mt = pt.matcher(str);
            if (mt.find()) {
                aux = mt.group().trim();
                break;
            }
        }
        return aux;
    }
}
