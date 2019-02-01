/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.utils;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author 6_Delta
 */
public class Console {

    private static String log = "";

    public static void execute(String... command) {
        try {
            ProcessBuilder proceces = new ProcessBuilder(command);
            proceces.start();
            log = "";
        } catch (IOException ex) {
            Console.log = "Error: " + ex.getMessage()
                    + " | Comando >> " + getCommand(command);
        }
    }

    public static void executeFromFile(File dir, String... command) {
        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.directory(dir);
            pb.start();
            log = "";
        } catch (IOException ex) {
            Console.log = "Error: " + ex.getMessage()
                    + " | Comando >> " + getCommand(command);
        }
    }

    public static void executeWParams(String command, String[] params) {
        try {
            Process process = Runtime.getRuntime().exec(command, params, null);
            process.waitFor();
            log = "";
        } catch (IOException | InterruptedException ex) {
            Console.log = "Error: " + ex.getMessage()
                    + " | Comando >> " + getCommand(command, params);
        }
    }

    private static String getCommand(String[] command) {
        String salida = "";
        for (String item : command) {
            salida += " " + item;
        }
        return salida;
    }

    private static String getCommand(String command, String[] params) {
        String salida = command;
        for (String item : params) {
            salida += " " + item;
        }
        return salida;
    }

    public static String getLog() {
        return log;
    }

}
