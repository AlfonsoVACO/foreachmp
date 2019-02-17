/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.utils.actions;

import com.mycompany.foreach.models.MwsPrimary;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 6_Delta
 */
public class Organize {

    private final List<String> lstcsv;
    private final List<String> memory;
    private final List<String> threads;
    private final List<String> main;
    private final List<MwsPrimary> mwsod;

    public Organize(List<MwsPrimary> mwsod, List<String> lstcsv) {
        this.lstcsv = lstcsv;
        this.mwsod = mwsod;
        this.memory = new ArrayList<>();
        this.threads = new ArrayList<>();
        this.main = new ArrayList<>();
    }

    public List<String> getOrganize(int typenode, String regex) {
        List<String> tempvec = filterByCols(this.lstcsv);
        for (int i = 0; i < tempvec.size(); i++) {
            String str = tempvec.get(i);
            if (isInHour(str, regex)) {
                this.mwsod.forEach((itemprim) -> {
                    itemprim.getDisplay().forEach((display) -> {
                        if (display.toLowerCase().contains("memory")) {
                            this.memory.add(setJMemory(str, itemprim.getAlias().get(typenode), display));
                        } else {
                            this.threads.add(setJThreads(str, itemprim.getAlias().get(typenode), display));
                        }
                    });
                });
            }
        }
        this.main.addAll(this.memory);
        this.main.addAll(this.threads);
        return this.main;
    }

    private String setJMemory(String cadenasplit, String typenode, String display) {
        StringBuilder stringbuild = new StringBuilder();
        String[] splitcadena = cadenasplit.split(",");
        stringbuild
                .append(typenode).append(",")
                .append(display).append(",")
                .append("").append(",")
                .append(splitcadena[2].trim()).append(",")
                .append(splitcadena[3].trim()).append(",")
                .append(splitcadena[0].trim())
                .append(" ").append(splitcadena[1].trim());
        return stringbuild.toString();
    }

    private String setJThreads(String cadenasplit, String typenode, String display) {
        StringBuilder stringbuild = new StringBuilder();
        String[] splitcadena = cadenasplit.split(",");
        stringbuild
                .append(typenode).append(",")
                .append(display).append(",")
                .append(splitcadena[4].trim()).append(",")
                .append(splitcadena[5].trim()).append(",")
                .append("").append(",")
                .append(splitcadena[0].trim())
                .append(" ").append(splitcadena[1].trim());
        return stringbuild.toString();
    }

    private static boolean isInHour(String str, String regex) {
        String[] split = str.split(",");
        String formathhmm = split[1].substring(0, split[1].length() - 3);
        return formathhmm.matches(regex);
    }

    private List<String> filterByCols(List<String> lstDatos) {
        List<String> lstDatosfiltrado = new ArrayList<>();
        for (String dato : lstDatos) {
            String[] split = dato.split(",");
            String salida = "";
            for (int i = 0; i < split.length; i++) {
                if (i != 6) {
                    salida += split[i] + ",";
                } else {
                    break;
                }
            }
            String finalSalida = salida.substring(0, salida.length() - 1);
            lstDatosfiltrado.add(finalSalida);
        }
        return lstDatosfiltrado.isEmpty() ? new ArrayList<>() : lstDatosfiltrado;
    }
}
