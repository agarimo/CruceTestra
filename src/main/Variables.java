package main;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Conexion;

/**
 *
 * @author Agarimo
 */
public class Variables {

    public static Conexion con;
    public static File ficheroPdf;
    public static File ficheroTxt;

    public static void inicializar() {
        driver();
        setConexion();
        ficheroPdf = new File(new File("data"), "pdfData");
        ficheroTxt = new File(new File("data"), "txtData");
        initFiles();
    }

    private static void initFiles() {
        if (!ficheroPdf.exists()) {
            ficheroPdf.mkdirs();
        }
        if (!ficheroTxt.exists()) {
            ficheroTxt.mkdirs();
        }
    }

    private static void driver() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Variables.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void setConexion() {
        con = new Conexion();
//        con.setDireccion("oficina.redcedeco.net");
        con.setDireccion("192.168.1.40");
//        con.setDireccion("localhost");
        con.setUsuario("admin");
        con.setPass("IkuinenK@@m.s84");
        con.setPuerto("3306");
    }
}
