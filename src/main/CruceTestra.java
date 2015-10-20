package main;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.Sql;

/**
 *
 * @author Agarimo
 */
public class CruceTestra extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Win.fxml"));

        Scene scene = new Scene(root);

        stage.setMinHeight(380);
        stage.setMinWidth(900);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Var.inicializar();
//        testeo();
//        System.exit(0);
        launch(args);
    }

    public static void testeo() {
        String datos = null;

        try {
            Sql bd = new Sql(Var.con);
            datos = bd.getString("SELECT datos FROM datagest.descarga where idDescarga="
                    + "(SELECT idDescarga from datagest.edicto where idEdicto='000000007492-280796')");
        } catch (SQLException ex) {
            Logger.getLogger(CruceTestra.class.getName()).log(Level.SEVERE, null, ex);
        }

        datos = limpiar(datos, "TTRAPU-70U9NC-4D1M1F-20B21E");
//        System.out.println(datos);

    }
    
    private static String limpiar(String datos, String csv){
        StringBuilder sb = new StringBuilder();
        String aux;
        boolean print = false;

        aux = datos.replace("CSV: " + csv, "");
        aux = aux.replace("Validar en: https://sede.dgt.gob.es/", "");
        aux= aux.replace("\n", System.lineSeparator());
        
        String[] split = aux.split(System.lineSeparator());

        System.out.println(split.length);
        
        int a=1;
        for (String split1 : split) {

            System.out.println("Linea "+a+": "+split1);
            a++;
            
            if (split1.contains("https://sede.dgt.gob.es")) {
                print = false;
            }

            if (print) {
                System.err.println(split1);
                sb.append(split1.trim());
                sb.append(System.lineSeparator());
            }

            if (split1.contains("EXPEDIENTE SANCIONADO/A IDENTIF")
                    || split1.contains("EXPEDIENTE DENUNCIADO/A IDENTIF")) {
                print = true;
            }
        }
//
        return sb.toString().trim();
    }
}
