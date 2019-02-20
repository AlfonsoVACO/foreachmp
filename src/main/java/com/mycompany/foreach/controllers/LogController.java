/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.controllers;

import com.mycompany.foreach.connections.ConnectionLocal;
import com.mycompany.foreach.daos.DaoDataColumns;
import com.mycompany.foreach.models.GeneralInfo;
import com.mycompany.foreach.models.Mws82;
import com.mycompany.foreach.models.Mws98;
import com.mycompany.foreach.utils.constantes.Constantes;
import com.mycompany.foreach.utils.FilesOperations;
import com.mycompany.foreach.utils.FxDialogs;
import com.mycompany.foreach.models.Navegacion;
import com.mycompany.foreach.utils.Util;
import com.mycompany.foreach.utils.actions.FilesAndDirectory;
import com.mycompany.foreach.utils.actions.OperationsOD;
import com.mycompany.foreach.utils.actions.Temp;
import com.mycompany.foreach.utils.actions.XmlToRun;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author 6_Delta
 */
public class LogController implements Initializable {

    @FXML
    private HBox hboxBaju;
    @FXML
    private Button simpan, close;
    @FXML
    private AnchorPane paneTambah;
    @FXML
    private StackPane stackTambah;
    @FXML
    private ListView<String> logList;

    private final ObservableList<String> items = FXCollections.observableArrayList("Inicializando el proceso...");

    public File openFile = null;
    public static List<String> vecList = null;
    private String fname;
    private Navegacion nav;

    public static Mws98 mwsno;
    public static Mws82 mwsod;
    public static GeneralInfo gralinfo;
    public static int anioints = 0;
    public static List<File> archivesToClone;
    public static String mesGenera;
    private String nomenclatura;
    public static File workspace;
    private List<String> logs, wpaths;
    private List<File> archivostxt;
    private String cngstats, cngxml, pathp;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.nav = new Navegacion();
        this.logs = new ArrayList<>();
        try {
            logList.setItems(items);
            open();
        } catch (IOException ex) {
            items.add("Error: " + ex.getMessage());
        }
    }

    private void generateXML98() throws IOException {
        XmlToRun xmltorun = new XmlToRun(mwsno, gralinfo, workspace, nomenclatura);
        List<String> xml98 = xmltorun.executeXml();
        items.addAll(xml98);
    }

    private void generate82() throws IOException {
        OperationsOD operations = new OperationsOD(mwsod, workspace, nomenclatura);
        this.wpaths = operations.getWorksPaths();
        this.archivostxt = operations.getArchivostxt();
        items.addAll(operations.getLogs());
    }

    private void generateDirs() throws IOException {
        FilesAndDirectory filesn = new FilesAndDirectory(gralinfo);
        List<String> carpeta = filesn.generateDirectory(mesGenera, anioints);
        items.addAll(carpeta);
        this.nomenclatura = filesn.getNomenclatura();
        workspace = filesn.getWorkspace();
    }

    public void cloneArchives() throws IOException {
        for (File archiveclon : archivesToClone) {
            File nuevoarch = archiveclon.getName().contains("82")
                    ? Paths.get(workspace + "\\Monitoreo_Servicios-wM82_"
                            + this.nomenclatura + ".xlsx").toFile()
                    : Paths.get(workspace + "\\Monitoreo_Servicios-wM98_"
                            + this.nomenclatura + ".xlsx").toFile();
            this.logs.add("Clonando archivo: " + archiveclon.getName());
            Files.copy(archiveclon.toPath(), nuevoarch.toPath(), Constantes.COPY_OPTIONS);
        }
    }

    private void open() throws IOException {
        generateDirs();
        cloneArchives();
        generateXML98();
        generate82();
        createTemp();
        converterTxtToCSV();
        items.add("Fin del proceso...");
        logList.setItems(items);

        if (FxDialogs.showConfirm(Constantes.TITLE, "¿Desea guardar el log?",
                FxDialogs.YES, FxDialogs.NO).equals(FxDialogs.YES)) {
            StringBuilder stringbuilder = new StringBuilder();
            stringbuilder.append(workspace.toPath().toString()).append("\\");
            logList.getItems().forEach((str) -> {
                this.logs.add(str);
            });
            Util.makeFileNameds(this.logs,
                    Paths.get(stringbuilder.toString()), "Log_wM98", ".txt");
        }
    }

    private void converterTxtToCSV() {
        items.add("Conviriendo datos de txt a csv");
        this.archivostxt.stream().map((archivotxt) -> {
            List<String> data = FilesOperations.readFileTxt(archivotxt);
            int version = archivotxt.getName().contains("66") ? 0 : 1;
            FilesOperations.writeCSVFile(mwsod, data,
                    archivotxt.getPath()
                            .substring(0, archivotxt.getPath().length() - 18)
                            + File.separator
                            + nomenclatura
                            + Calendar.getInstance().getTimeInMillis(), version);
            return archivotxt;
        }).forEachOrdered((archivotxt) -> {
            items.add("Convirtiendo > " + archivotxt.getPath()
                    .substring(0, archivotxt.getPath().length() - 18) + File.separator
                    + nomenclatura + Calendar.getInstance().getTimeInMillis());
        });
    }

    private void createTemp() {
        cngstats = "\"extension\":\"" + mwsod.getExtension() + "\",\"urls\":[";
        String urlod = "";
        urlod = wpaths.stream()
                .map((path) -> "\"" + path.replace("\\", "\\\\") + "\\\\\",")
                .reduce(urlod, String::concat);
        cngstats += urlod.substring(0, urlod.length() - 1) + "]";
        cngxml = "\"extension\":\"" + mwsno.getExtension()
                + "\",\"url\":\"" + mwsno.getPath().toString().replace("\\", "\\\\") + "\\\\\"";
        pathp = workspace.toPath().toString() + "\\";
        try {
            Temp temporal = new Temp(cngstats, cngxml, new Date(), pathp);
            String salidatemp = temporal.createTemp();
            setFilesInDB(salidatemp);
        } catch (IOException ex) {
            items.add("Error al generar temporal: " + ex.getMessage());
        }
    }

    private void setFilesInDB(String path) {
        try {
            Connection conn = ConnectionLocal.getConnection();
            DaoDataColumns.setUrlJSON(conn, path);
        } catch (SQLException | ClassNotFoundException ex) {
            items.add("Error al guardar en BD: " + ex.getMessage());
        }
    }

}
