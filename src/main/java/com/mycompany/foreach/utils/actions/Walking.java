package com.mycompany.foreach.utils.actions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

/**
 *
 * @author 6_Delta
 */
public class Walking {

    public List<String> execute(String palabra, List<String> listaextends, Path start, boolean[] config)
            throws IOException {
        final ArrayList<String> listaencuentra = new ArrayList<>();
        Files.walkFileTree(start, new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if ((file != null) && (!file.getParent()
                        .toString()
                        .contains("System Volume Information"))) {
                    listaextends.forEach(it -> {
                        if ((file.toString().endsWith("." + (String) it))
                                && (leerFichero(file, palabra, config))) {
                            listaencuentra.add(file.toString());
                        }
                    });
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }
        });

        return listaencuentra;
    }

    private boolean leerFichero(Path fichero, String cadena, boolean[] config) {
        try {
            BufferedReader file = new BufferedReader(new FileReader(fichero.toFile()));
            try {
                String slinea;
                while ((slinea = file.readLine()) != null) {
                    if (config[0]) {
                        if (slinea.contains(cadena)) {
                            return true;
                        }
                    } else if (config[1]) {
                        if (slinea.toLowerCase().contains(cadena)) {
                            return true;
                        }
                    } else {
                        if (slinea.toUpperCase().contains(cadena)) {
                            return true;
                        }
                    }
                }
            } catch (IOException localThrowable1) {

            } finally {
                if (file != null) {
                    file.close();
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
}
