/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.utils;

import com.mycompany.foreach.models.GeneralInfo;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

/**
 *
 * @author 6_Delta
 */
public class Util {

    public static boolean stringIsNumber(String cadena) {
        boolean resultado;
        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }
        return resultado;
    }

    public static void editFile(List<String> lines, String archive) throws IOException {
        try (BufferedWriter bufferedwritter = new BufferedWriter(
                new FileWriter(new File(archive))
        )) {
            for (String line : lines) {
                bufferedwritter.write(line, 0, line.length());
                bufferedwritter.newLine();
            }
            bufferedwritter.close();
        }
    }

    public static void writeInFile(InputStream in, OutputStream out) throws IOException {
        byte[] arrab = new byte[1024];
        int numbyts = 0;
        while ((numbyts = in.read(arrab)) != -1) {
            out.write(arrab, 0, numbyts);
        }
    }

    public static void makeFileNameds(List<String> lstfiles, Path path,
            String namefile, String extension) throws IOException {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(path.toString())
                .append("\\")
                .append(namefile)
                .append(extension);
        if (!Paths.get(stringbuilder.toString()).toFile().exists()) {
            try (BufferedWriter bufferedwritter = new BufferedWriter(
                    new FileWriter(new File(stringbuilder.toString())))) {
                for (String line : lstfiles) {
                    bufferedwritter.write(line, 0, line.length());
                    bufferedwritter.newLine();
                }
                bufferedwritter.close();
            }
        }
    }

    public static void makeFileFails(List<String> lstfiles, GeneralInfo mwsno,
            String nomenclatura, int numfile) throws IOException {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(mwsno.getInDir())
                .append(getMonth(nomenclatura))
                .append("\\")
                .append(mwsno.getExtras().get(1))
                .append("\\")
                .append(Constantes.ARCHIVES_FAILS)
                .append("_")
                .append(getDateString())
                .append(numfile)
                .append(".txt");
        System.out.println("File: " + stringbuilder.toString());
        try (BufferedWriter bufferedwritter = new BufferedWriter(
                new FileWriter(new File(stringbuilder.toString())))) {
            for (String line : lstfiles) {
                bufferedwritter.write(line, 0, line.length());
                bufferedwritter.newLine();
            }
            bufferedwritter.close();
        }
    }

    public static String getDateString() {
        String fechastr = null;
        try {
            Date fecha = new Date();
            SimpleDateFormat simpledate
                    = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.US);
            simpledate.parse(fecha.toLocaleString());
            fechastr = simpledate.toString();
        } catch (ParseException ex) {
            FxDialogs.showException("Error", ex.getMessage(), ex);
        }
        return fechastr != null ? fechastr : "";
    }

    public static Path getRutas(String... str) {
        StringBuilder stringbuilder = new StringBuilder();
        for (String itemstr : str) {
            stringbuilder.append(itemstr).append("\\");
        }
        return Paths.get(stringbuilder.toString());
    }

    public static void removeFile(String file) {
        try {
            if (Files.deleteIfExists(Paths.get(file))) {
                FxDialogs.showInformation(Constantes.TITLE, "Json eliminado");
            }
        } catch (IOException ex) {
            FxDialogs.showException("Error", ex.getMessage(), ex);
        }
    }

    public static List<Path> filterContenido(Stream<Path> contenido, String extension) {
        List<Path> lstnewPath = new ArrayList<>();
        Iterator<Path> iterator = contenido.iterator();
        while (iterator.hasNext()) {
            Path pathtemp = iterator.next();
            if (pathtemp.getFileName().toString().endsWith(extension)) {
                lstnewPath.add(pathtemp);
            }
        }
        return lstnewPath.isEmpty() ? new ArrayList<>() : lstnewPath;
    }

    public static Date date(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.getTime();
    }

    private static String getMonth(String format) {
        String getting = format.substring(0, 3);
        String rest = format.substring(format.indexOf("-"), format.length());
        switch (getting.toLowerCase()) {
            case "ene":
                return "Enero" + rest;
            case "feb":
                return "Febrero" + rest;
            case "mar":
                return "Marzo" + rest;
            case "abr":
                return "Abril" + rest;
            case "may":
                return "Mayo" + rest;
            case "jun":
                return "Junio" + rest;
            case "jul":
                return "Julio" + rest;
            case "ago":
                return "Agosto" + rest;
            case "sep":
                return "Septiembre" + rest;
            case "oct":
                return "Octubre" + rest;
            case "nov":
                return "Noviembre" + rest;
            case "dic":
                return "Diciembre" + rest;
        }
        return "";
    }

}
