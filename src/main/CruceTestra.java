package main;

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
    
    public static void testeo(){
        String a=" 53483776J ";
        String patron="[\\s][0-9]{4,8}[TRWAGMYFPDXBNJZSQVHLCKE]{1}[\\s]";
        
        String found=Regex.buscar(patron, a);
        
        System.out.println("|"+found+"|");
        
    }
}
