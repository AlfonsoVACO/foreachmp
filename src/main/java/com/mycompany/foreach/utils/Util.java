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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import org.controlsfx.control.Notifications;

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
        try (BufferedWriter bufferedwritter = new BufferedWriter(
                new FileWriter(new File(stringbuilder.toString())))) {
            for (String line : lstfiles) {
                bufferedwritter.write(line, 0, line.length());
                bufferedwritter.newLine();
            }
            bufferedwritter.close();
        }
    }

    public static void makeFileFails(List<String> lstfiles, GeneralInfo mwsno) throws IOException {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(mwsno.getInDir())
                .append(mwsno.getExtras().get(1))
                .append("\\")
                .append(Constantes.ARCHIVES_FAILS)
                .append("_")
                .append(getDateString())
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
        SimpleDateFormat simpledate
                = new SimpleDateFormat(Constantes.FORMAT_YYYY_MM_DD);
        simpledate.format(new Date());
        return simpledate.toString();
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
            Notifications
                    .create()
                    .title("Error")
                    .text(ex.getMessage())
                    .showError();
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
    
    public static Date date(long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.getTime();
    }
    
}
