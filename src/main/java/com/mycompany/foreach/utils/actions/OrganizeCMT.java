/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.utils.actions;

import com.mycompany.foreach.models.Mws82;
import com.mycompany.foreach.models.MwsArchive;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.poi.ss.usermodel.DateUtil;

/**
 *
 * @author 6_Delta
 */
public class OrganizeCMT {

    private final List<String> listamemoryn1;
    private final List<String> listamemoryn2;
    private final List<String> listathreadsn1;
    private final List<String> listathreadsn2;
    private final List<String> listacpun1;
    private final List<String> listacpun2;
    private final List<String> logs;
    private final Mws82 mwsod;

    OrganizeCMT(Mws82 mwsod) {
        this.logs = new ArrayList<>();
        this.listamemoryn1 = new ArrayList<>();
        this.listamemoryn2 = new ArrayList<>();
        this.listathreadsn1 = new ArrayList<>();
        this.listathreadsn2 = new ArrayList<>();
        this.listacpun1 = new ArrayList<>();
        this.listacpun2 = new ArrayList<>();
        this.mwsod = mwsod;
    }

    public List<String> getLogs() {
        return this.logs;
    }

    public List<String> getListamemoryn1() {
        return this.listamemoryn1;
    }

    public List<String> getListamemoryn2() {
        return this.listamemoryn2;
    }

    public List<String> getListathreadsn1() {
        return this.listathreadsn1;
    }

    public List<String> getListathreadsn2() {
        return this.listathreadsn2;
    }

    public List<String> getListacpun1() {
        return listacpun1;
    }

    public List<String> getListacpun2() {
        return listacpun2;
    }

    public void readCPU(MwsArchive archive, File archivo, String alias, int nodo) throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(archivo));
        try {
            String line = br.readLine();
            while (null != line) {
                String lineread = "";
                String[] fields = line.split(",");
                if (!fields[0].contains("time")) {
                    String body = getBodyC(archive, fields, alias);
                    if (!body.equals("")) {
                        lineread = body;
                    }
                }
                setListCPU(nodo, lineread);
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

    public void readThread(MwsArchive archive, File archivo, String alias, int nodo) throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(archivo));
        try {
            String line = br.readLine();
            while (null != line) {
                String lineread = "";
                String[] fields = line.split(",");
                if (!fields[0].contains("time")) {
                    String body = getBodyT(archive, fields, alias);
                    if (!body.equals("")) {
                        lineread = body;
                    }
                }
                setListThread(nodo, lineread);
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

    public void readMemory(MwsArchive archive, File archivo, String alias, int nodo) throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(archivo));
        try {
            String line = br.readLine();
            while (null != line) {
                String lineread = "";
                String[] fields = line.split(",");
                if (fields[0].contains("time")) {
                    lineread = getHeads(archive);
                } else {
                    String body = getBodyM(archive, fields, alias);
                    if (!body.equals("")) {
                        lineread = body;
                    }
                }
                setListMemory(nodo, lineread);
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

    private void setListCPU(int nodo, String lineread) {
        if (!lineread.equals("")) {
            if (nodo == 0 && !lineread.equals("")) {
                listacpun1.add(lineread);
            } else {
                listacpun2.add(lineread);
            }
        }
    }

    private void setListThread(int nodo, String lineread) {
        if (!lineread.equals("")) {
            if (nodo == 0 && !lineread.equals("")) {
                listathreadsn1.add(lineread);
            } else {
                listathreadsn2.add(lineread);
            }
        }
    }

    private void setListMemory(int nodo, String lineread) {
        if (!lineread.equals("")) {
            if (nodo == 0 && !lineread.equals("")) {
                listamemoryn1.add(lineread);
            } else {
                listamemoryn2.add(lineread);
            }
        }
    }

    private String getBodyM(MwsArchive archive, String[] fields, String alias) {
        String valuef = "", values = "", valuet = "";
        for (int i = 0; i < fields.length; i++) {
            switch (i) {
                case 0:
                    valuef = fields[i];
                    break;
                case 1:
                    values = fields[i];
                    break;
                case 2:
                    valuet = fields[i];
                    break;
            }
        }
        String datefromc = getDate(valuef);
        if (isInRange(datefromc)) {

            StringBuilder stringbuild = new StringBuilder();
            stringbuild.append(alias).append(",");
            stringbuild.append(archive.getStatics().get(0)).append(",");
            stringbuild.append(values).append(",");
            stringbuild.append(valuet).append(",");
            stringbuild.append(archive.getStatics().get(1)).append(",");
            stringbuild.append(datefromc);
            return stringbuild.toString();
        }
        return "";
    }
    
    public String getBodyC(MwsArchive archive, String[] fields, String alias) {
        String datefromc = getDate(fields[0]);
        if (isInRange(datefromc)) {
            StringBuilder stringbuild = new StringBuilder();
            stringbuild.append(alias).append(",");
            stringbuild.append(archive.getStatics().get(0)).append(",");
            stringbuild.append(fields[1]).append(",");
            stringbuild.append("").append(",");
            stringbuild.append(archive.getStatics().get(1)).append(",");
            stringbuild.append(datefromc);
            return stringbuild.toString();
        }
        return "";
    }

    public String getBodyT(MwsArchive archive, String[] fields, String alias) {
        String datefromc = getDate(fields[0]);
        if (isInRange(datefromc)) {
            StringBuilder stringbuild = new StringBuilder();
            stringbuild.append(alias).append(",");
            stringbuild.append(archive.getStatics().get(0)).append(",");
            stringbuild.append(fields[1]).append(",");
            stringbuild.append(fields[2]).append(",");
            stringbuild.append("").append(",");
            stringbuild.append(datefromc);
            return stringbuild.toString();
        }
        return "";
    }

    private String getHeads(MwsArchive archive) {
        String heads = Arrays.toString(archive.getHeads().toArray());
        return heads.replace("[", "").replace("]", "");
    }

    private boolean isInRange(String date) {
        return date.matches(this.mwsod.getArchives().getFilter());
    }

    private String getDate(String fecha) {
        if (!fecha.toLowerCase().contains("time")) {
            Date fechadate = DateUtil.getJavaDate(Double.parseDouble(fecha));
            SimpleDateFormat simpledatef = new SimpleDateFormat(mwsod.getArchives().getOnRegex());
            return simpledatef.format(fechadate);
        }
        return "";
    }
}
