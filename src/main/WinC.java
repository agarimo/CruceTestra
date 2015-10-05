package main;

import enty.Descarga;
import enty.ModeloTabla;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import util.Dates;
import util.Sql;
import util.Varios;

/**
 *
 * @author Agarimo
 */
public class WinC implements Initializable {

    @FXML
    private DatePicker dpFecha;

    @FXML
    private TableView tabla;

    @FXML
    private TableColumn idCL;

    @FXML
    private TableColumn estadoCL;

    @FXML
    private Button btProcesar;

    @FXML
    private Button btArchivo;

    @FXML
    private Button btRefrescar;

    @FXML
    private Button btAbrirCarpeta;

    @FXML
    private ProgressIndicator piProgreso;

    @FXML
    private Label lbProgreso;

    ObservableList<ModeloTabla> listaTabla;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        iniciarTablaProcesar();
    }

    private void iniciarTablaProcesar() {
        idCL.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCL.setCellFactory(column -> {
            return new TableCell<ModeloTabla, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setAlignment(Pos.CENTER);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(item);
                    }
                }
            };
        });
        estadoCL.setCellValueFactory(new PropertyValueFactory<>("estado"));
        estadoCL.setCellFactory(column -> {
            return new TableCell<ModeloTabla, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setAlignment(Pos.CENTER);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {

                        switch (item) {
                            case 0:
                                setText("Sin procesar");
                                setTextFill(Color.BLACK);
                                break;

                            case 1:
                                setText("PDF generado");
                                setTextFill(Color.ORANGE);
                                break;

                            case 2:
                                setText("Listo para procesar");
                                setTextFill(Color.ORCHID);
                                break;

                            case 3:
                                setText("PROCESADO");
                                setTextFill(Color.GREEN);
                                break;

                            case 4:
                                setText("Error al procesar");
                                setTextFill(Color.ORANGERED);
                                break;
                        }
                    }
                }
            };
        });

        listaTabla = FXCollections.observableArrayList();
        tabla.setItems(listaTabla);
    }

    @FXML
    void cambioEnDatePicker(ActionEvent event) {
        try {
            Date fecha = Dates.asDate(dpFecha.getValue());

            if (fecha != null) {
                cargarDatos(listaBoe(Descarga.SQLBuscar(fecha)));
            }
        } catch (NullPointerException ex) {
            //
        }
    }

    void cargarDatos(List<Descarga> lista) {
        listaTabla.clear();
        List<ModeloTabla> listModelo = new ArrayList();
        Descarga aux;
        ModeloTabla mt;
        Iterator<Descarga> it = lista.iterator();

        while (it.hasNext()) {
            mt = new ModeloTabla();
            aux = it.next();
            mt.setId(aux.getId());
            mt.setCsv(aux.getCsv());
            mt.setDatos(aux.getDatos());
            mt.setFecha(aux.getFecha());
            listModelo.add(mt);
        }

        listaTabla.addAll(listModelo);
    }

    @FXML
    void procesarPdf(ActionEvent event) {
        ModeloTabla mt = (ModeloTabla) tabla.getSelectionModel().getSelectedItem();
        String datos;

        if (mt != null) {
            datos = mt.getDatos();

            datos = limpiar(datos, mt.getCsv());
            System.out.println(datos.trim());
            
        }
    }

    private String limpiar(String datos, String csv) {
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
                sb.append(split1.trim());
                sb.append(System.lineSeparator());
            }

            if (split1.contains("EXPEDIENTE SANCIONADO/A IDENTIF")
                    || split1.contains("EXPEDIENTE DENUNCIADO/A IDENTIF")) {
                print = true;
            }
        }

        return sb.toString();
    }

    @FXML
    void generarArchivo(ActionEvent event) {

    }

    @FXML
    void abrirCarpeta(ActionEvent event) {

    }

    @FXML
    void refrescar(ActionEvent event) {
        cambioEnDatePicker(event);
    }

    private List<Descarga> listaBoe(String query) {
        List<Descarga> list = new ArrayList();
        Sql bd;
        ResultSet rs;
        Descarga aux;

        try {
            bd = new Sql(Variables.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new Descarga();
                aux.setId(rs.getString("idEdicto"));
                aux.setFecha(rs.getDate("fecha"));
                aux.setCsv(rs.getString("csv"));
                aux.setDatos(rs.getString("datos"));
                list.add(aux);
            }
            rs.close();
            bd.close();
        } catch (SQLException ex) {
            Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
