package main;

import enty.Multa;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        String a = "000000000001-120402";
        String b = "A58818501";
        String c = "7000434908 LEX ET GAUDIUM SL B98160435 LLERENA 17/06/15 0901DZK 160 LSV ART:9BI1 9BI 1 0 R";
        String d = "156838/2014 PAEZ MELENDES JUANA MARIA 24296712 TREVELEZ 26-08-2014 0674DMM 600,00 LSV 65.5.J";
        
        String linea= d;
        
        Multa multa= new Multa();
        
        multa.setCodigoBoletin(a);
        multa.setFechaPublicacion("la fecha");
        multa.setLinea(linea);
        
        System.out.println("Boletin: "+multa.getBoletin());
        System.out.println("Origen : "+multa.getOrigen());
        System.out.println("Expediente :"+multa.getExpediente());
        System.out.println("Nif : "+multa.getNif());
        System.out.println("Matricula :"+multa.getMatricula());
        System.out.println("Fecha multa: "+multa.getFechaMulta());
        System.out.println("Linea : "+multa.getLinea());
        

    }
}
