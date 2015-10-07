package main;

import enty.Descarga;
import enty.ModeloTabla;
import enty.Multa;
import java.net.URL;
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
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import util.Dates;
import util.Sql;

/**
 *
 * @author Agarimo
 */
public class WinC implements Initializable {

    @FXML
    private StackPane panel;

    @FXML
    private AnchorPane panelPrincipal;

    @FXML
    private AnchorPane panelManual;

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
    private Button btProcesarM;

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
    private final int PANEL_PRINCIPAL = 1;
    private final int PANEL_MANUAL = 2;
    private boolean isProcesandoM;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        iniciarTablaProcesar();
        mostrarPanel(PANEL_PRINCIPAL);
        isProcesandoM = false;
    }

    public void mostrarPanel(int a) {

        switch (a) {
            case 1:
                panelPrincipal.setVisible(true);
                panelManual.setVisible(false);
                break;
            case 2:
                panelPrincipal.setVisible(false);
                panelManual.setVisible(true);
                break;
        }
    }

    private void iniciarTablaProcesar() {
        idCL.setCellValueFactory(new PropertyValueFactory<>("codigo"));
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
                                setText("PROCESADO");
                                setTextFill(Color.GREEN);
                                break;

                            case 2:
                                setText("CON ERRORES");
                                setTextFill(Color.RED);
                                break;

                            case 3:
                                setText("SIN MULTAS");
                                setTextFill(Color.ORANGE);
                                break;

                            case 4:
                                setText(" ");
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
            mt.setCodigo(aux.getCodigo());
            mt.setCsv(aux.getCsv());
            mt.setDatos(aux.getDatos());
            mt.setFecha(aux.getFecha());
            mt.setEstado(aux.getEstado());
            listModelo.add(mt);
        }

        listaTabla.addAll(listModelo);
    }

    private List<ModeloTabla> getBoletinesToProcess() {
        ModeloTabla mt;
        List<ModeloTabla> list = new ArrayList();
        Iterator<ModeloTabla> it = listaTabla.iterator();

        while (it.hasNext()) {
            mt = it.next();
            if (mt.getEstado() == 0) {
                list.add(mt);
            }
        }

        return list;
    }

    @FXML
    void procesar(ActionEvent event) {
        Thread a = new Thread(() -> {

            Platform.runLater(() -> {
                btProcesar.setDisable(true);
                piProgreso.setVisible(true);
                piProgreso.setProgress(0);
                lbProgreso.setVisible(true);
                lbProgreso.setText("");
            });

            String datos;
            ModeloTabla mt;
            List list = getBoletinesToProcess();

            for (int i = 0; i < list.size(); i++) {
                final int contador = i;
                final int total = list.size();
                Platform.runLater(() -> {
                    int contadour = contador + 1;
                    double counter = contador + 1;
                    double toutal = total;
                    lbProgreso.setText("PROCESANDO " + contadour + " de " + total);
                    piProgreso.setProgress(counter / toutal);
                });
                mt = (ModeloTabla) list.get(i);
                datos = mt.getDatos();
                datos = limpiar(datos, mt.getCsv());
                datos = selectMultas(datos).trim();

                if (datos.contains("*error*")) {
                    setEstadoDescarga(mt.getId(), 2);
                } else {
                    List<Multa> listado = splitMultas(mt, datos);

                    if (!listado.isEmpty()) {
                        if (insertMultas(listado)) {
                            setEstadoDescarga(mt.getId(), 1);
                        }
                    } else {
                        setEstadoDescarga(mt.getId(), 3);
                    }
                }
            }

            Platform.runLater(() -> {
                piProgreso.setProgress(1);
                piProgreso.setVisible(false);
                lbProgreso.setText("");
                lbProgreso.setVisible(false);
                btProcesar.setDisable(false);

                cambioEnDatePicker(new ActionEvent());
            });
        });
        a.start();
    }

    @FXML
    void procesarManual(ActionEvent event) {
        if (isProcesandoM) {
            mostrarPanel(this.PANEL_PRINCIPAL);
        } else {
            mostrarPanel(this.PANEL_MANUAL);
        }
        isProcesandoM = !isProcesandoM;
    }

    private boolean insertMultas(List<Multa> list) {
        Sql bd;
        Multa aux;
        Iterator<Multa> it = list.iterator();

        try {
            bd = new Sql(Variables.con);

            while (it.hasNext()) {
                aux = it.next();
                bd.ejecutar(aux.SQLCrear());
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    private List<Multa> splitMultas(ModeloTabla aux, String datos) {
        List<Multa> list = new ArrayList();
        Multa multa;
        String[] split = datos.split(System.lineSeparator());

        for (String split1 : split) {
            if (!split1.equals("")) {
                multa = new Multa();
                multa.setCodigoBoletin(aux.getCodigo());
                multa.setFechaPublicacion(aux.getFecha());
                multa.setLinea(split1);
                list.add(multa);
            }
        }
        return list;
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

    private String selectMultas(String datos) {
        StringBuilder sb = new StringBuilder();
        String[] split = datos.split(System.lineSeparator());

        for (String split1 : split) {
            if (Regex.buscar(split1, Regex.FECHA)) {
                if (Regex.buscar(split1, Regex.DNI) || Regex.buscar(split1, Regex.MATRICULA)) {
                    sb.append(split1);
                    sb.append(System.lineSeparator());
                } else {
                    sb.append("*error*");
                    sb.append(System.lineSeparator());
                }
            } else {
                if (Regex.buscar(split1, Regex.DNI) || Regex.buscar(split1, Regex.MATRICULA)) {
                    sb.append(split1);
                    sb.append(System.lineSeparator());
                }
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
                aux.setId(rs.getInt("idDescarga"));
                aux.setCodigo(rs.getString("idEdicto"));
                aux.setFecha(rs.getDate("fecha"));
                aux.setCsv(rs.getString("csv"));
                aux.setDatos(rs.getString("datos"));
                aux.setEstado(rs.getInt("estadoCruce"));
                list.add(aux);
            }
            rs.close();
            bd.close();
        } catch (SQLException ex) {
            Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    private void setEstadoDescarga(int id, int estado) {
        Sql bd;
        String query = "UPDATE datagest.descarga SET estadoCruce=" + estado + " WHERE idDescarga=" + id;

        try {
            bd = new Sql(Variables.con);
            bd.ejecutar(query);
            bd.close();
        } catch (SQLException ex) {
            Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
