package main;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.Dates;
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
        
        String a="NO CONSTA";
    }
}
