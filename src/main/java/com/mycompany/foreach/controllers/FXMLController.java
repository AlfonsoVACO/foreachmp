package com.mycompany.foreach.controllers;

import com.mycompany.foreach.animacion.FadeInRightTransition;
import com.mycompany.foreach.animacion.FadeOutLeftTransition;
import com.mycompany.foreach.connections.ConnectionLocal;
import com.mycompany.foreach.connections.CreateTables;
import com.mycompany.foreach.daos.DaoDataColumns;
import com.mycompany.foreach.models.DataColumns;
import com.mycompany.foreach.models.GeneralInfo;
import com.mycompany.foreach.models.Mws82;
import com.mycompany.foreach.models.Mws98;
import com.mycompany.foreach.utils.Constantes;
import com.mycompany.foreach.utils.FxDialogs;
import com.mycompany.foreach.utils.Navegacion;
import com.mycompany.foreach.utils.Util;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;

public class FXMLController implements Initializable {

    @FXML
    private ListView<String> listViewBaju;
    @FXML
    private StackPane menuBaju, tambahBaju, loadStage, daftarListMenu;
    @FXML
    private AnchorPane editBaju;
    @FXML
    private TableView<DataColumns> tableBaju;
    @FXML
    private TableColumn<DataColumns, String> colPath, colDate, colDelete, colExecute, colCMT;
    @FXML
    private Label lblAdmin;
    @FXML
    private ComboBox cmbMesg;
    @FXML
    private TextField txtnamesclone;

    @FXML
    private final ObservableList<String> listView
            = FXCollections.observableArrayList("Fin de mes", "Configuración");
    public List<File> archivesToClone;

    public static Connection conn;
    public static String comboBaju, verificador, id_usuario, tambah = "";
    private ObservableList<DataColumns> listatempo;

    public static Mws98 mwsno;
    public static Mws82 mwsod;
    public static GeneralInfo gralinfo;
    public int anioints = 0;
    private Navegacion nav;
    private File workspace;

    private void columnBaju() {
        listViewBaju.setItems(listView);
    }

    public void setStageTambahBaju(Node node) {
        loadStage.getChildren().setAll(node);
    }

    public void setStageDaftarListBaju(Node node) {
        daftarListMenu.getChildren().setAll(node);
    }

    private void cargaComboMeses() {
        cmbMesg.setItems(Constantes.LST_MESES);
        cmbMesg.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                boolean insnio = true;
                while (insnio) {
                    String anio = FxDialogs.showTextInput(
                            Constantes.TITLE, "Año a realizar (yyyy)", "");
                    if (anio.length() == 4 || Util.stringIsNumber(anio)) {
                        anioints = Integer.parseInt(anio);
                        break;
                    }
                }
            }
        });

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        File pathdir = Paths.get(gralinfo.getInDir()).toFile();
        if (!pathdir.exists()) {
            StringBuilder strbuild = new StringBuilder();
            strbuild.append("El directorio de trabajo no existe, ");
            strbuild.append("debe de modificar el json y poner ");
            strbuild.append("un directorio válido en 'inDir'");
            FxDialogs.showInformation(Constantes.TITLE, strbuild.toString());
        }

        txtnamesclone.setEditable(false);
        cargaComboMeses();
        columnBaju();
        try {
            CargaTable();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tambahBaju.toBack();
        tambahBaju.setOpacity(0);
        editBaju.toBack();
        editBaju.setOpacity(0);

        menuBaju.setEffect(null);
        lblAdmin.setText(id_usuario);
        nav = new Navegacion();
        try {
            conn = ConnectionLocal.getConnection();
            CreateTables.creaTablas(conn);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(LoginController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void menuLateral(MouseEvent event) {
        switch (listViewBaju.getSelectionModel().getSelectedIndex()) {
            case 0: {
                menuBaju.toFront();
                menuBaju.setOpacity(100);
            }
            break;
            case 1: {
                daftarListMenu.toFront();
                daftarListMenu.setOpacity(100);
            }
            break;
        }
        listViewBaju.setOnKeyTyped((KeyEvent event1) -> {
            if (event1.getCharacter() != null) {
                switch (listViewBaju.getSelectionModel().getSelectedIndex()) {
                    case 0: {
                        menuBaju.toFront();
                        menuBaju.setOpacity(100);
                    }
                    break;
                    case 1: {
                        daftarListMenu.toFront();
                        daftarListMenu.setOpacity(100);
                    }
                    break;
                }
            }
        });
    }
    
    @FXML
    private void showinfo(MouseEvent event) 
            throws IOException, SQLException, ClassNotFoundException{
        if(event.getClickCount() == 2){
            DataColumns klik = tableBaju.getSelectionModel().getSelectedItems().get(0);
            new FadeInRightTransition(tambahBaju).play();
            menuBaju.setEffect(new GaussianBlur(10));
            InfoController.columnas = klik;
            Navegacion.loadStageTambahBaju(nav.getInfo());
            CargaTable();
        }
    }

    @FXML
    private void executeLDAO(ActionEvent event) {
        try {
            if (anioints != 0
                    && !archivesToClone.isEmpty()
                    && cmbMesg.getValue().toString() != null) {

                new FadeInRightTransition(tambahBaju).play();
                menuBaju.setEffect(new GaussianBlur(10));
                LogController.archivesToClone = archivesToClone;
                LogController.anioints = anioints;
                LogController.gralinfo = gralinfo;
                LogController.mwsno = mwsno;
                LogController.mwsod = mwsod;
                LogController.mesGenera = cmbMesg.getValue().toString();
                Navegacion.loadStageTambahBaju(nav.getLog());
                CargaTable();
                cleanVars();
            } else {
                FxDialogs.showError(Constantes.TITLE,
                        "No se cumplen requisitos para proceder");
            }
        } catch (IOException | SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    private void CargaTable() throws SQLException, ClassNotFoundException {
        conn = ConnectionLocal.getConnection();
        listatempo = FXCollections.observableArrayList();
        DaoDataColumns.LlenarInfoAll(conn, listatempo, mwsod, gralinfo);
        tableBaju.setItems(listatempo);
        colPath.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colExecute.setCellValueFactory(new PropertyValueFactory<>("execute"));
        colCMT.setCellValueFactory(new PropertyValueFactory<>("loadcmt"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("delete"));
    }

    @FXML
    private void btnClose(ActionEvent event) {
        menuBaju.setEffect(null);
        new FadeOutLeftTransition(tambahBaju).play();
    }

    @FXML
    private void clonaArch(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Buscar archivos a clonar");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("XLS", "*.xls"),
                new FileChooser.ExtensionFilter("XLSX", "*.xlsx")
        );
        List<File> archives = fileChooser.showOpenMultipleDialog(null);
        if (archives != null && archives.isEmpty()) {
            FxDialogs.showInformation(Constantes.TITLE, "No hay archivos a clonar");
            cleanVars();
        } else {
            setStringToText(archives);
        }
    }

    private void cleanVars() {
        archivesToClone = new ArrayList<>();
        txtnamesclone.setText("");
    }

    private void setStringToText(List<File> archives) {
        archivesToClone = archives;
        String stringarchives = "";
        stringarchives = archivesToClone.stream().map((file) -> file.getName() + "|").reduce(stringarchives, String::concat);
        txtnamesclone.setText(stringarchives.substring(0, stringarchives.length() - 1));
    }
}
