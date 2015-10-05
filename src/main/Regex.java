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

    private static final  String[] fechas = {"[\\s]\\d{2}[/-]\\d{2}[/-]\\d{4}[\\s]",
                                             "[\\s]\\d{4}[/-]\\d{2}[/-]\\d{2}[\\s]",
                                             "NO CONSTA"};

    private static final  String[] identificacion = {"[0-9]{4,8}[A-Za-z]{1}",
                                                     "[A-Za-z]{1}[0-9]{8,9}",
                                                     "[A-Za-z]{1}[0-9]{8}[A-Za-z]{1}",
                                                     "[A-Za-z]{1}[0-9]{7}[A-Za-z]{1}",
                                                     "[A-Z]{1,3}[0-9]{6,8}",
                                                     "NO CONSTA"};

    private static final String[] matriculas = {"[0-9]{4}[\\s-]{0,1}[A-Z]{2,3}",
                                                "[A-Z]{1,2}[\\s-]{0,1}[0-9]{4,5}[\\s-]{0,1}[A-Z]{1,2}",
                                                "POKET BIKE",
                                                "EX",
                                                "CARECE"};

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
                    System.out.println("Fecha encontrada: " + getFecha(str));
                    existe = true;
                } else {
                    System.out.println("Fecha no encontrada: " + getFecha(str));
                }
                break;
            case 1:
                if (getDni(str) != null) {
                    existe = true;
                    System.out.println("Dni encontrado: " + getDni(str));
                } else {
                    System.out.println("Dni no encontrado: " + getDni(str));
                }
                break;
            case 2:
                if (getMatricula(str) != null) {
                    existe = true;
                    System.out.println("Matricula encontrado: " + getMatricula(str));
                } else {
                    System.out.println("Matricula no encontrado: " + getMatricula(str));
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
                aux = mt.group();
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
                System.out.println("Encontrado patrón: " + identificacion1);
                aux = mt.group();
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
                aux = mt.group();
                break;
            }
        }
        return aux;
    }
}
