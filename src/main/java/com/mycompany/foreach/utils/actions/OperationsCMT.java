/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.utils.actions;

import com.mycompany.foreach.models.GeneralInfo;
import com.mycompany.foreach.models.Mws82;
import com.mycompany.foreach.models.MwsArchive;
import com.mycompany.foreach.models.MwsPrimary;
import com.mycompany.foreach.utils.Constantes;
import com.mycompany.foreach.utils.FxDialogs;
import com.mycompany.foreach.utils.SendInfoCMT;
import com.mycompany.foreach.utils.Util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 6_Delta
 */
public class OperationsCMT extends SendInfoCMT {

    private final List<String> logs;
    private final Mws82 mwsod;
    private final GeneralInfo gralinfo;
    private final String fileconfig;
    private Path workspace;
    private final Path workspace66;
    private final Path workspace67;

    List<String> listamemoryn1;
    List<String> listamemoryn2;
    List<String> listathreadsn1;
    List<String> listathreadsn2;
    List<String> listacpun1;
    List<String> listacpun2;

    public OperationsCMT(Mws82 mwsod, GeneralInfo gralinfo, String workspace, String fileconfig) {
        super(Paths.get(fileconfig));

        this.fileconfig = fileconfig;
        this.workspace = Paths.get(workspace);
        this.logs = new ArrayList<>();
        this.mwsod = mwsod;
        this.gralinfo = gralinfo;
        this.workspace = Paths.get(workspace + "\\wM82\\");
        this.workspace66 = Paths.get(workspace + "\\wM82\\" + mwsod.getToPath().get(0));
        this.workspace67 = Paths.get(workspace + "\\wM82\\" + mwsod.getToPath().get(1));
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
    }

    private void setInitialice(String name, String type) {
        switch (type) {
            case "cpu":
                if (name.toLowerCase().contains("66")) {
                    this.listacpun1 = new ArrayList<>();
                } else {
                    this.listacpun2 = new ArrayList<>();
                }
                break;
            case "memory":
                if (name.toLowerCase().contains("66")) {
                    this.listamemoryn1 = new ArrayList<>();
                } else {
                    this.listamemoryn2 = new ArrayList<>();
                }
                break;
            case "thread":
                if (name.toLowerCase().contains("66")) {
                    this.listathreadsn1 = new ArrayList<>();
                } else {
                    this.listathreadsn2 = new ArrayList<>();
                }
                break;
        }
    }

    private void getMWS(MwsPrimary primary) {
        try {
            List<Path> lstfiltrados
                    = Util.filterContenido(Files.list(workspace), ".csv");
            if (lstfiltrados.size() <= 0) {
                FxDialogs.showWarning(Constantes.TITLE,
                        "No hay archivos, por favor agregalos");
            } else {
                for (Path path : lstfiltrados) { //CMT
                    String alias = primary.getAlias()
                            .get(path.getFileName().toFile()
                                    .getName().contains("66") ? 0 : 1);
                    if (path.getFileName().toFile().getName().toLowerCase().contains("cpu")) {
                        setInitialice(path.getFileName().toFile().getName(), "cpu");
                    } else if (path.getFileName().toFile().getName().toLowerCase().contains("memory")) {
                        setInitialice(path.getFileName().toFile().getName(), "memory");
                        readMemory(primary.getMemory(), path.toFile(), alias);
                    } else if (path.getFileName().toFile().getName().toLowerCase().contains("thread")) {
                        setInitialice(path.getFileName().toFile().getName(), "thread");
                    }
                }
            }
        } catch (IOException ex) {
            this.logs.add("Error: " + ex.getMessage());
        }
    }

    private void readMemory(MwsArchive archive, File archivo, String alias) throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(archivo));
        try {
            String line = br.readLine();
            while (null != line) {
                StringBuilder stringbuild = new StringBuilder();
                String[] fields = line.split(";");
                if (fields[0].contains("time")) {
                    stringbuild.append(getHeads(archive));
                } else {
                    stringbuild.append(getBody(archive, fields, alias));
                    //this.listacontn1.add(stringbuild.toString());
                }
                System.out.println(stringbuild.toString());
                line = br.readLine();
            }
        } catch (IOException ex) {
            this.logs.add("Error: " + ex.getMessage());
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                this.logs.add("Error: " + ex.getMessage());
            }
        }

    }

    private String getBody(MwsArchive archive, String[] fields, String alias) {
        StringBuilder stringbuild = new StringBuilder();
        stringbuild.append(alias).append(",");
        stringbuild.append(archive.getStatics().get(0)).append(",");
        stringbuild.append(fields[1]).append(",");
        stringbuild.append(fields[2]).append(",");
        stringbuild.append(archive.getStatics().get(1)).append(",");
        stringbuild.append(fields[0]).append(",");
        getDate(fields[0]);
        return stringbuild.toString();
    }

    private String getHeads(MwsArchive archive) {
        String heads = Arrays.toString(archive.getHeads().toArray());
        return heads.replace("[", "").replace("]", "");
    }

    private void getDate(String fecha) {
        long fechalng = Long.valueOf(fecha.replace(".", ""));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(fechalng);
        System.out.println(calendar.toString());
        System.out.println(getPattern(calendar.toString()));
    }

    private Date getPattern(String fecha) {
        Date fechas = null;
        try {
            SimpleDateFormat dateform = new SimpleDateFormat(mwsod.getArchives().getOnRegex());
            fechas = dateform.parse(fecha);
        } catch (ParseException ex) {
            Logger.getLogger(OperationsCMT.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fechas != null ? fechas : new Date();
    }

    private static boolean isInHour(String str) {
        String[] split = str.split(",");
        String formathhmm = split[1].substring(0, split[1].length() - 3);
        return formathhmm.matches(Constantes.REGEX_CMT);
    }
}
