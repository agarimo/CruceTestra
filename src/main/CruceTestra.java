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

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Variables.inicializar();
        testeo();
        System.exit(0);
//        launch(args);
    }

    public static void testeo() {
        String datos = null;

        try {
            Sql bd = new Sql(Variables.con);
            datos = bd.getString("SELECT datos FROM datagest.descarga where idDescarga="
                    + "(SELECT idDescarga from datagest.edicto where idEdicto='000000000509-410917')");
        } catch (SQLException ex) {
            Logger.getLogger(CruceTestra.class.getName()).log(Level.SEVERE, null, ex);
        }

        datos = limpiar(datos, "TTRAPU-60I08L-5ASD2E-F4EF43");
        System.out.println(datos);

    }

    private static String limpiar(String datos, String csv) {
        StringBuilder sb = new StringBuilder();
        String aux;
        boolean print = false;

        aux = datos.replace("CSV: " + csv, "");
        aux = aux.replace("Validar en: https://sede.dgt.gob.es/", "");

        String[] split = aux.split(System.lineSeparator());

        for (String split1 : split) {

            if (split1.contains("https://sede.dgt.gob.es")) {
                print = false;
            }

            if (print) {
                System.out.println(split1.trim());
                sb.append(split1.trim());
                sb.append(System.lineSeparator());
            }

            if (split1.contains("EXPEDIENTE SANCIONADO/A IDENTIF") || split1.contains("EXPEDIENTE DENUNCIADO/A IDENTIF")) {
                print = true;
            }
        }

        return sb.toString();
    }
}
