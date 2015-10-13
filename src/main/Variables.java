package main;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Conexion;

/**
 *
 * @author Agarimo
 */
public class Variables {

    public static Conexion con;
    public static File fichero;
    public static File temporal;

    public static void inicializar() {
        driver();
        setConexion();
        fichero = new File("data");
        temporal = new File("temp.txt");
        initFiles();
    }

    private static void initFiles() {
        if (!fichero.exists()) {
            fichero.mkdirs();
        }
        try { 
            temporal.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(Variables.class.getName()).log(Level.SEVERE, null, ex);
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
