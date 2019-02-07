/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.utils.actions;

import com.mycompany.foreach.models.GeneralInfo;
import com.mycompany.foreach.models.Mws82;
import com.mycompany.foreach.models.MwsPrimary;
import com.mycompany.foreach.utils.Constantes;
import com.mycompany.foreach.utils.FxDialogs;
import com.mycompany.foreach.utils.SendInfoCMT;
import com.mycompany.foreach.utils.Util;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 6_Delta
 */
public class OperationsCMT extends SendInfoCMT {

    private final List<String> logs;
    private final Mws82 mwsod;
    private Path workspace;

    private List<String> listamemoryn1;
    private List<String> listamemoryn2;
    private List<String> listathreadsn1;
    private List<String> listathreadsn2;
    private List<String> listacpun1;
    private List<String> listacpun2;

    private final List<String> nodo66;
    private final List<String> nodo67;

    public OperationsCMT(Mws82 mwsod, GeneralInfo gralinfo, String workspace, String fileconfig) {
        super(Paths.get(fileconfig));

        this.nodo66 = new ArrayList<>();
        this.nodo67 = new ArrayList<>();
        this.workspace = Paths.get(workspace);
        this.logs = new ArrayList<>();
        this.mwsod = mwsod;
        this.workspace = Paths.get(workspace + "\\wM82\\");
        this.listamemoryn1 = new ArrayList<>();
        this.listamemoryn2 = new ArrayList<>();
        this.listathreadsn1 = new ArrayList<>();
        this.listathreadsn2 = new ArrayList<>();
        this.listacpun1 = new ArrayList<>();
        this.listacpun2 = new ArrayList<>();
        
        getPaths();
    }

    public List<String> getLogs() {
        return this.logs;
    }

    private void getPaths() {
        mwsod.getArchives().getMwsis().forEach((primary) -> {
            if (primary.getType().toLowerCase().equals("mws")) {
                getMWS(primary);
            }
        });
        setUnifica();
        createFiles();
    }

    private void setUnifica() {
        this.nodo66.addAll(this.listamemoryn1);
        this.nodo66.addAll(this.listacpun1);
        this.nodo66.addAll(this.listathreadsn1);
        this.nodo67.addAll(this.listamemoryn2);
        this.nodo67.addAll(this.listacpun2);
        this.nodo67.addAll(this.listathreadsn2);
    }    

    private void getMWS(MwsPrimary primary) {
        OrganizeCMT organize = new OrganizeCMT();
        try {
            List<Path> lstfiltrados
                    = Util.filterContenido(Files.list(workspace), ".csv");
            if (lstfiltrados.size() <= 0) {
                FxDialogs.showWarning(Constantes.TITLE,
                        "No hay archivos, por favor agregalos");
            } else {
                for (Path path : lstfiltrados) {
                    String namefile = path.getFileName().toFile().getName();
                    String alias = primary.getAlias()
                            .get(namefile.contains("66") ? 0 : 1);
                    int nodo = namefile.toLowerCase().contains("66")
                            || namefile.contains("1") ? 0 : 1;

                    if (namefile.toLowerCase().contains("cpu")) {
                        organize.readCPU(primary.getCpu(), path.toFile(), alias, nodo);
                    } else if (namefile.toLowerCase().contains("memory")) {
                        organize.readMemory(primary.getMemory(), path.toFile(), alias, nodo);
                    } else if (namefile.toLowerCase().contains("thread")) {
                        organize.readThread(primary.getThreads(), path.toFile(), alias, nodo);
                    }
                }
                if (!organize.getLogs().isEmpty()) {
                    this.logs.addAll(organize.getLogs());
                }
                setToLists(organize);
            }
        } catch (IOException ex) {
            this.logs.add("Error: " + ex.getMessage());
        }
    }

    private void setToLists(OrganizeCMT organize) {
        this.listamemoryn1 = organize.getListamemoryn1();
        this.listamemoryn2 = organize.getListamemoryn2();
        this.listathreadsn1 = organize.getListathreadsn1();
        this.listathreadsn2 = organize.getListathreadsn2();
        this.listacpun1 = organize.getListacpun1();
        this.listacpun2 = organize.getListacpun2();
    }
    
    private void createFiles(){
        try {
            Path workspacemws = Paths.get(this.workspace.toString() + "\\MWS-82");
            if(!workspacemws.toFile().exists()){
                workspacemws.toFile().mkdir();
            }
            Util.makeFileNameds(this.nodo66, workspacemws, "MWS-Nodo66", ".csv");
            Util.makeFileNameds(this.nodo67, workspacemws, "MWS-Nodo67", ".csv");
        } catch (IOException ex) {
            this.logs.add(ex.getMessage());
        }
    }

}
