package main;

import enty.Cruce;
import enty.Descarga;
import enty.Estado;
import enty.ModeloCruce;
import enty.ModeloTabla;
import enty.Multa;
import enty.ProcesoCruce;
import enty.ProcesoManual;
import enty.TipoCruce;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import util.Dates;
import util.Files;

/**
 *
 * @author Agarimo
 */
public class WinC implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="FXML Variables">
    @FXML
    private AnchorPane panelPrincipal;

    @FXML
    private AnchorPane panelManual;

    @FXML
    private AnchorPane panelEspera;

    @FXML
    private AnchorPane panelEditar;

    @FXML
    private AnchorPane panelCruce;

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
    private Button btEditarBoletin;

    @FXML
    private Button btArchivo;

    @FXML
    private Button btCruce;

    @FXML
    private Button btAbrirCarpeta;

    @FXML
    private ProgressIndicator piProgreso;

    @FXML
    private Label lbProgreso;

    @FXML
    private ListView lvManual;

    @FXML
    private Button btManualP;

    @FXML
    private Label lbTotal;

    @FXML
    private Label lbProcesados;

    @FXML
    private Label lbErrores;

    @FXML
    private Label lbValid;

    @FXML
    private Label lbInvalid;

    @FXML
    private TextArea textArea;
    
    @FXML
    private Label lbCountDatagest;
    
    @FXML
    private Label lbCountIdbl;
    
    @FXML
    private Label lbLineDatagest;
    
    @FXML
    private Label lbLineIdbl;
    
    @FXML
    private TableView tvCruce;
    
    @FXML
    private TableColumn expedienteCCL;
    
    @FXML
    private TableColumn nifCCL;
    
    @FXML
    private TableColumn matriculaCCL;
    
    @FXML
    private Button btIniciaCruce;
    
    @FXML
    private Button btGeneraArchivoCruce;
    
    @FXML
    private Button btEliminarCruce;
    
    @FXML
    private ProgressIndicator piProgresoCruce;
    
    @FXML
    private Label lbProgresoCruce;
    
    @FXML
    private Label lbProcesoCruce;
    
    @FXML
    private AnchorPane panelBotonesCruce;
    
    @FXML
    private AnchorPane panelProgresoCruce;

    //</editor-fold>
    private ProcesoManual procesoManual;
    private ProcesoCruce procesoCruce;

    ObservableList<ModeloTabla> listaTabla;
    ObservableList<String> listaManual;
    ObservableList<ModeloCruce> listaCruce;

    private final int PANEL_PRINCIPAL = 1;
    private final int PANEL_MANUAL = 2;
    private final int PANEL_ESPERA = 3;
    private final int PANEL_EDITAR = 4;
    private final int PANEL_CRUCE = 5;
    
    private final int PANEL_CRUCE_BOTONES=1;
    private final int PANEL_CRUCE_PROGRESO=2;

    private int CONTADOR_TOTAL = 0;
    private int CONTADOR_PROCESADOS = 0;
    private int CONTADOR_ERRORES = 0;

    private boolean isProcesandoM;
    private boolean isEditandoB;
    private boolean isCruzando;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        iniciarTablaProcesar();
        iniciarListaManual();
        iniciarTablaCruce();
        mostrarPanel(0);
        mostrarPanelCruce(this.PANEL_CRUCE_BOTONES);
        isProcesandoM = false;
        isEditandoB = false;
        isCruzando = false;
        btCruce.setDisable(true);
    }

    public void mostrarPanel(int a) {

        switch (a) {
            case 0:
                panelPrincipal.setVisible(false);
                panelManual.setVisible(false);
                panelEspera.setVisible(false);
                panelEditar.setVisible(false);
                panelCruce.setVisible(false);
                break;
            case 1:
                panelPrincipal.setVisible(true);
                panelManual.setVisible(false);
                panelEspera.setVisible(false);
                panelEditar.setVisible(false);
                panelCruce.setVisible(false);
                break;
            case 2:
                panelPrincipal.setVisible(false);
                panelManual.setVisible(true);
                panelEspera.setVisible(false);
                panelEditar.setVisible(false);
                panelCruce.setVisible(false);
                break;
            case 3:
                panelPrincipal.setVisible(false);
                panelManual.setVisible(false);
                panelEspera.setVisible(true);
                panelEditar.setVisible(false);
                panelCruce.setVisible(false);
                break;
            case 4:
                panelPrincipal.setVisible(false);
                panelManual.setVisible(false);
                panelEspera.setVisible(false);
                panelEditar.setVisible(true);
                panelCruce.setVisible(false);
                break;
            case 5:
                panelPrincipal.setVisible(false);
                panelManual.setVisible(false);
                panelEspera.setVisible(false);
                panelEditar.setVisible(false);
                panelCruce.setVisible(true);
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
                                setText(Estado.SIN_PROCESAR.toString());
                                setTextFill(Color.BLACK);
                                break;

                            case 1:
                                setText(Estado.PROCESADO.toString());
                                setTextFill(Color.GREEN);
                                break;

                            case 2:
                                setText(Estado.CON_ERRORES.toString());
                                setTextFill(Color.RED);
                                break;

                            case 3:
                                setText(Estado.SIN_MULTAS.toString());
                                setTextFill(Color.ORANGE);
                                break;

                            case 4:
                                setText(Estado.ERROR_INSERCION.toString());
                                setTextFill(Color.ORCHID);
                                break;

                            case 5:
                                setText(Estado.DESCARTADO.toString());
                                setTextFill(Color.ORANGE);
                        }
                    }
                }
            };
        });

        listaTabla = FXCollections.observableArrayList();
        tabla.setItems(listaTabla);
    }

    private void iniciarListaManual() {
        lvManual.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> p) {
                ListCell<String> cell = new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        this.setAlignment(Pos.CENTER_LEFT);

                        if (item == null || empty) {
                            setText("");
                            setStyle("-fx-background-color:  #f2f2f2");
                        } else {
                            setText(item);
                            setTextFill(Color.RED);
                            setStyle("");
                        }
                    }
                };

                return cell;
            }
        });

        listaManual = FXCollections.observableArrayList();
        lvManual.setItems(listaManual);
    }
    
    private void iniciarTablaCruce(){
        expedienteCCL.setCellValueFactory(new PropertyValueFactory<>("expediente"));
        expedienteCCL.setCellFactory(column -> {
            return new TableCell<ModeloCruce, String>() {
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
        nifCCL.setCellValueFactory(new PropertyValueFactory<>("nif"));
        nifCCL.setCellFactory(column -> {
            return new TableCell<ModeloCruce, String>() {
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
        matriculaCCL.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        matriculaCCL.setCellFactory(column -> {
            return new TableCell<ModeloCruce, String>() {
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

        listaCruce = FXCollections.observableArrayList();
        tvCruce.setItems(listaCruce);
    }

    @FXML
    void cambioEnDatePicker(ActionEvent event) {
        try {
            Date fecha = Dates.asDate(dpFecha.getValue());

            if (fecha != null) {

                Thread a = new Thread(() -> {
                    List<Descarga> aux;

                    Platform.runLater(() -> {
                        dpFecha.setDisable(true);
                        piProgreso.setVisible(true);
                        piProgreso.setProgress(-1);
                        lbProgreso.setVisible(true);
                        lbProgreso.setText("CARGANDO BOLETINES");
                        mostrarPanel(this.PANEL_ESPERA);
                    });

                    aux = Sql.listaBoe(Descarga.SQLBuscar(fecha));

                    Platform.runLater(() -> {
                        dpFecha.setDisable(false);
                        piProgreso.setProgress(1);
                        piProgreso.setVisible(false);
                        lbProgreso.setText("");
                        lbProgreso.setVisible(false);
                        cargarDatos(aux);
                        FXCollections.sort(listaTabla);
                        mostrarPanel(this.PANEL_PRINCIPAL);
                        btCruce.setDisable(comprobarBoletines());
                    });
                });
                a.start();
            }

        } catch (NullPointerException ex) {
            //
        }
    }

    void cargarDatos(List<Descarga> lista) {
        this.CONTADOR_TOTAL = 0;
        this.CONTADOR_PROCESADOS = 0;
        this.CONTADOR_ERRORES = 0;
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
            CONTADOR_TOTAL++;

            if (aux.getEstado() == 1) {
                CONTADOR_PROCESADOS++;
            }

            if (aux.getEstado() > 1) {
                CONTADOR_ERRORES++;
            }
        }

        listaTabla.addAll(listModelo);
        lbTotal.setText(Integer.toString(CONTADOR_TOTAL));
        lbProcesados.setText(Integer.toString(CONTADOR_PROCESADOS));
        lbErrores.setText(Integer.toString(CONTADOR_ERRORES));
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
                lbProgreso.setText("PROCESANDO BOLETINES");
                mostrarPanel(this.PANEL_ESPERA);
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
                datos = cribaMultas(datos).trim();

                if (datos.contains("*error*")) {
                    Sql.setEstadoDescarga(mt.getId(), Estado.CON_ERRORES);
                } else {
                    List<Multa> listado = splitMultas(mt, datos);

                    if (!listado.isEmpty()) {

                        Platform.runLater(() -> {
                            int contadour = contador + 1;
                            piProgreso.setProgress(-1);
                            lbProgreso.setText("INSERTANDO " + contadour + " de " + total);
                        });

                        if (Sql.insertMultas(listado)) {
                            Sql.setEstadoDescarga(mt.getId(), Estado.PROCESADO);
                        } else {
                            Sql.setEstadoDescarga(mt.getId(), Estado.ERROR_INSERCION);
                        }
                    } else {
                        Sql.setEstadoDescarga(mt.getId(), Estado.SIN_MULTAS);
                    }
                }
            }

            Platform.runLater(() -> {
                piProgreso.setProgress(1);
                piProgreso.setVisible(false);
                lbProgreso.setText("");
                lbProgreso.setVisible(false);
                btProcesar.setDisable(false);
                mostrarPanel(this.PANEL_PRINCIPAL);
                cambioEnDatePicker(new ActionEvent());
            });
        });
        a.start();
    }

    @FXML
    void procesarManual(ActionEvent event) {
        if (isProcesandoM) {
            mostrarPanel(this.PANEL_PRINCIPAL);
            btProcesarM.setText("Procesar Manualmente");
            dpFecha.setDisable(false);
            btProcesar.setDisable(false);
            btArchivo.setDisable(false);
            btEditarBoletin.setDisable(false);
            btAbrirCarpeta.setDisable(false);
            btCruce.setDisable(false);
        } else {
            listaManual.clear();
            ModeloTabla mt = (ModeloTabla) tabla.getSelectionModel().getSelectedItem();

            if (mt != null) {
                mostrarPanel(this.PANEL_MANUAL);
                btProcesarM.setText("VOLVER");
                dpFecha.setDisable(true);
                btProcesar.setDisable(true);
                btArchivo.setDisable(true);
                btEditarBoletin.setDisable(true);
                btAbrirCarpeta.setDisable(true);
                btCruce.setDisable(true);

                cargarProcesarManual(mt);

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ERROR");
                alert.setHeaderText("SELECCIONE UN ELEMENTO");
                alert.setContentText("Debes seleccionar un elemento para continuar");
                alert.showAndWait();
            }
        }
        isProcesandoM = !isProcesandoM;
    }

    void cargarProcesarManual(ModeloTabla mt) {
        String datos;
        String[] split;
        datos = mt.getDatos();
        datos = limpiar(datos, mt.getCsv());
        split = datos.split(System.lineSeparator());

        List lista = new ArrayList();
        lista.addAll(Arrays.asList(split));
        procesoManual = new ProcesoManual(mt.getId(), mt.getCodigo(), mt.getFecha(), lista);

        listaManual.addAll(procesoManual.getInvalid());
        lbValid.setText(Integer.toString(procesoManual.validCount()));
        lbInvalid.setText(Integer.toString(procesoManual.invalidCount()));
    }

    @FXML
    void validarLinea(ActionEvent event) {
        String item = (String) lvManual.getSelectionModel().getSelectedItem();
        procesoManual.addToValid(item);
        refreshProcesarManual();
    }

    void refreshProcesarManual() {
        listaManual.clear();
        listaManual.addAll(procesoManual.getInvalid());
        lbValid.setText(Integer.toString(procesoManual.validCount()));
        lbInvalid.setText(Integer.toString(procesoManual.invalidCount()));
    }

    @FXML
    void botonProcesarManual(ActionEvent event) {

        Thread a = new Thread(() -> {

            Platform.runLater(() -> {
                piProgreso.setVisible(true);
                piProgreso.setProgress(-1);
                lbProgreso.setVisible(true);
                lbProgreso.setText("PROCESANDO BOLETIN");
                mostrarPanel(this.PANEL_ESPERA);
            });

            Sql bd;
            Multa multa;
            List<String> lista = procesoManual.getValid();
            List<Multa> list = new ArrayList();

            for (String aux : lista) {
                multa = new Multa();
                multa.setCodigoBoletin(procesoManual.getCodigo());
                multa.setFechaPublicacion(procesoManual.getFecha());
                multa.setLinea(aux);
                list.add(multa);
            }

            try {
                bd = new Sql(Variables.con);

                bd.ejecutar("DELETE FROM datagest.cruceTestra where codigoEdicto=" + procesoManual.getCodigo());

                for (int i = 0; i < list.size(); i++) {
                    final int contador = i;
                    final int total = list.size();

                    Platform.runLater(() -> {
                        int contadour = contador + 1;
                        double counter = contador + 1;
                        double toutal = total;
                        lbProgreso.setText("INSERTANDO " + contadour + " de " + total);
                        piProgreso.setProgress(counter / toutal);
                    });

                    multa = list.get(i);
                    bd.ejecutar(multa.SQLCrear());
                }

                bd.close();
                Sql.setEstadoDescarga(procesoManual.getId(), Estado.PROCESADO);

            } catch (SQLException ex) {
                Sql.setEstadoDescarga(procesoManual.getId(), Estado.ERROR_INSERCION);
                Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
            }

            Platform.runLater(() -> {
                piProgreso.setProgress(1);
                piProgreso.setVisible(false);
                lbProgreso.setText("");
                lbProgreso.setVisible(false);
                procesarManual(new ActionEvent());
                cambioEnDatePicker(new ActionEvent());
            });

        });
        a.start();
    }

    private List<Multa> splitMultas(ModeloTabla aux, String datos) {
        List<Multa> list = new ArrayList();
        Multa multa;
        String[] split = datos.split(System.lineSeparator());
        
        System.out.println(aux.getCodigo());

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
        aux = aux.replace("\n", System.lineSeparator());

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
        return sb.toString().trim();
    }

    private String cribaMultas(String datos) {
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
    void verBoletin(ActionEvent event) {
        ModeloTabla mt = (ModeloTabla) tabla.getSelectionModel().getSelectedItem();

        if (mt != null) {
            Files.escribeArchivo(Variables.temporal, mt.getDatos());
            try {
                Desktop.getDesktop().browse(Variables.temporal.toURI());
            } catch (IOException ex) {
                Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    void eliminarBoletin(ActionEvent event) {
        ModeloTabla mt = (ModeloTabla) tabla.getSelectionModel().getSelectedItem();

        if (mt != null) {
            Sql.setEstadoDescarga(mt.getId(), Estado.DESCARTADO);
            cambioEnDatePicker(new ActionEvent());
        }
    }

    @FXML
    void generarArchivo(ActionEvent event) {
        try {
            Date fecha = Dates.asDate(dpFecha.getValue());

            if (fecha != null) {
                File archivo = new File(Variables.fichero, Dates.imprimeFecha(fecha) + ".txt");
                archivo.createNewFile();
                generarArchivo(fecha, archivo);
            }

        } catch (NullPointerException | IOException ex) {
            Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void generarArchivo(Date fecha, File aux) {
        Thread a = new Thread(() -> {

            Platform.runLater(() -> {
                btArchivo.setDisable(true);
                piProgreso.setVisible(true);
                piProgreso.setProgress(-1);
                lbProgreso.setVisible(true);
                lbProgreso.setText("GENERANDO ARCHIVO");
                mostrarPanel(this.PANEL_ESPERA);
            });

            Multa multa;
            StringBuilder sb = new StringBuilder();
            List<Multa> list = Sql.listaMulta(Multa.SQLBuscar(fecha));
            Iterator<Multa> it = list.iterator();

            while (it.hasNext()) {
                multa = it.next();
                sb.append(multa);
                sb.append(System.lineSeparator());
            }

            Files.escribeArchivo(aux, sb.toString().trim());

            Platform.runLater(() -> {
                piProgreso.setProgress(1);
                piProgreso.setVisible(false);
                lbProgreso.setText("");
                lbProgreso.setVisible(false);
                btArchivo.setDisable(false);
                mostrarPanel(this.PANEL_PRINCIPAL);
            });
        });
        a.start();
    }

    @FXML
    void abrirCarpeta(ActionEvent event) {
        try {
            Desktop.getDesktop().browse(Variables.fichero.toURI());
        } catch (IOException ex) {
            Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void editarBoletin(ActionEvent event) {

        if (isEditandoB) {
            volverEditar(new ActionEvent());
            btEditarBoletin.setText("Editar Boletín");
            dpFecha.setDisable(false);
            btProcesar.setDisable(false);
            btArchivo.setDisable(false);
            btProcesarM.setDisable(false);
            btAbrirCarpeta.setDisable(false);
            btCruce.setDisable(false);
        } else {
            ModeloTabla mt = (ModeloTabla) tabla.getSelectionModel().getSelectedItem();

            if (mt != null) {
                mostrarPanel(this.PANEL_EDITAR);
                btEditarBoletin.setText("VOLVER");
                dpFecha.setDisable(true);
                btProcesar.setDisable(true);
                btArchivo.setDisable(true);
                btProcesarM.setDisable(true);
                btAbrirCarpeta.setDisable(true);
                btCruce.setDisable(true);

                textArea.setText(mt.getDatos().replace("\n", System.lineSeparator()));
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ERROR");
                alert.setHeaderText("SELECCIONE UN ELEMENTO");
                alert.setContentText("Debes seleccionar un elemento para continuar");
                alert.showAndWait();
            }
        }
        isEditandoB = !isEditandoB;
    }

    @FXML
    void guardarBoletin(ActionEvent event) {
        String aux;
        StringBuilder datos = new StringBuilder();
        ModeloTabla mt = (ModeloTabla) tabla.getSelectionModel().getSelectedItem();

        Iterator it = textArea.getParagraphs().iterator();

        while (it.hasNext()) {
            aux = (String) it.next().toString();
            datos.append(aux);
            datos.append(System.lineSeparator());
        }

        if (Sql.guardarBoletin(mt.getCodigo(), datos.toString())) {
            volverEditar(new ActionEvent());
            cambioEnDatePicker(new ActionEvent());
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR");
            alert.setHeaderText("ERROR SQL");
            alert.setContentText("Se ha producido un error en la BBDD");
            alert.showAndWait();

            volverEditar(new ActionEvent());
        }
    }

    @FXML
    void volverEditar(ActionEvent event) {
        textArea.setText("");
        mostrarPanel(this.PANEL_PRINCIPAL);
    }

    private boolean comprobarBoletines(){
        ModeloTabla mt;
        Iterator<ModeloTabla> it =listaTabla.iterator();
        
        while(it.hasNext()){
            mt=it.next();
            
            if(mt.getEstado()!=Estado.PROCESADO.getValue()){
                return true;
            }
        }
        return false;
    }
    
    @FXML
    void procesoCruce(ActionEvent event) {
        if (isCruzando) {
            mostrarPanel(this.PANEL_PRINCIPAL);
            btCruce.setText("Cruzar Multas");
            dpFecha.setDisable(false);
            btProcesar.setDisable(false);
            btArchivo.setDisable(false);
            btEditarBoletin.setDisable(false);
            btAbrirCarpeta.setDisable(false);
            btProcesarM.setDisable(false);
        } else {
            Date fecha = Dates.asDate(dpFecha.getValue());
            mostrarPanel(this.PANEL_CRUCE);
            btCruce.setText("VOLVER");
            dpFecha.setDisable(true);
            btProcesar.setDisable(true);
            btArchivo.setDisable(true);
            btEditarBoletin.setDisable(true);
            btAbrirCarpeta.setDisable(true);
            btProcesarM.setDisable(true);

            iniciaCruce(fecha);
        }
        isCruzando = !isCruzando;
    }
    
    void mostrarPanelCruce(int a){
        
        switch(a){
            case 1:
                panelBotonesCruce.setVisible(true);
                panelProgresoCruce.setVisible(false);
                break;
            case 2:
                panelBotonesCruce.setVisible(false);
                panelProgresoCruce.setVisible(true);
                break;
        }
    }
    
    void iniciaCruce(Date fecha){
        listaCruce.clear();
        Thread a = new Thread(() -> {

            Platform.runLater(() -> {
                mostrarPanelCruce(this.PANEL_CRUCE_PROGRESO);
                piProgresoCruce.setProgress(-1);
                lbProcesoCruce.setText("CARGANDO DATOS");
                lbProgresoCruce.setText("La operacion puede tardar varios minutos");
                lbProgresoCruce.setWrapText(true);
                lbProgresoCruce.setAlignment(Pos.CENTER);
                lbCountDatagest.setText("...Cargando...");
                lbCountIdbl.setText("...Cargando...");
            });

            this.procesoCruce = new ProcesoCruce();
            this.procesoCruce.setListaTestra(Sql.listaCruce(TipoCruce.TESTRA, fecha));
            this.procesoCruce.setListaIdbl(Sql.listaCruce(TipoCruce.IDBL, fecha));
            

            Platform.runLater(() -> {
                mostrarPanelCruce(this.PANEL_CRUCE_BOTONES);
                piProgresoCruce.setProgress(0);
                lbProcesoCruce.setText("");
                lbProgresoCruce.setText("");
                lbCountDatagest.setText(Integer.toString(this.procesoCruce.getTotalTestra()));
                lbCountIdbl.setText(Integer.toString(this.procesoCruce.getTotalIdbl()));
            });
        });
        a.start();
    }
    
    void cargaDatosCruce(List<ModeloCruce> list){
  
    }
    
    void addDatosCruce(Cruce testra, Cruce idbl){
        ModeloCruce mc = new ModeloCruce();
        
        mc.setId(testra.getId());
        mc.setExpediente(testra.getExpediente());
        mc.setNif(testra.getNif());
        mc.setMatricula(testra.getMatricula());
        mc.setLinea(testra.getLinea());
        
    }
    
    @FXML
    void botonIniciaCruce(ActionEvent event){
        int existen=0;
        int noexisten=0;
        Cruce aux;
        Iterator<Cruce> it = this.procesoCruce.getListaTestra().iterator();
        
        System.out.println("iniciando");
        
        while(it.hasNext()){
            aux=it.next();
            
            if(this.procesoCruce.cruzarMulta(aux)){
//                System.out.println("Existe: "+aux.getLinea());
                existen++;
            }else{
                System.err.println("No existe: "+aux.getExpediente()+" "+aux.getNif()+" "+aux.getLinea());
                noexisten++;
            }
        }
        System.out.println("fin");
        System.out.println("Existen: "+existen);
        System.out.println("No existen: "+noexisten);
    }
    
    @FXML
    void botonGeneraArchivoCruce(ActionEvent event){
        
    }
    
    @FXML
    void botonEliminarCruce(ActionEvent event){
        
    }
    
}
