/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.utils;

import com.mycompany.foreach.models.Mws82;
import com.mycompany.foreach.models.MwsPrimary;
import com.mycompany.foreach.utils.actions.Organize;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author 6_Delta
 */
public class FilesOperations {

    private static final List<String> logs = new ArrayList<>();
    private static final String[] timeStamp = {"Date", "Time"};
    private static Long[] fieldName;
    private static String s2;

    public static void writeFileJSON(File file, String content) throws IOException {
        try (BufferedWriter bufferedwriter = new BufferedWriter(new FileWriter(file))) {
            bufferedwriter.write(content, 0, content.length());
            bufferedwriter.newLine();
            bufferedwriter.close();
            if (FxDialogs.showConfirm("Configucación guardada",
                    "Es necesario cerrar la aplicación",
                    FxDialogs.YES).equals(FxDialogs.YES)) {
                System.exit(0);
            }
        }
    }

    public static List<String> readFileTxt(File archivocvs) { // lee rchivos generados por bat
        List<String> vecData = new ArrayList<>();
        try (BufferedReader bufferedreader = new BufferedReader(new FileReader(archivocvs))) {
            boolean loop = false;
            while (!loop) {
                String s1 = bufferedreader.readLine();
                if (s1 == null) {
                    loop = true;
                } else {
                    String str = "";

                    StringTokenizer stringtokenizer = new StringTokenizer(s1);
                    int ix = stringtokenizer.countTokens();
                    fieldName = new Long[ix];
                    if (ix == 14) {
                        timeStamp[0] = stringtokenizer.nextToken();
                        timeStamp[1] = stringtokenizer.nextToken();
                        ix -= 2;
                    } else if (ix == 15) {
                        timeStamp[0] = stringtokenizer.nextToken();
                        timeStamp[1] = stringtokenizer.nextToken();
                        str = stringtokenizer.nextToken();
                        ix -= 3;
                    }
                    boolean flag = false;
                    if (ix > 9) {
                        for (int i = 0; i < ix; i++) {
                            try {
                                str = stringtokenizer.nextToken();
                                fieldName[i] = Long.parseLong(str, 16);
                            } catch (NumberFormatException ne) {
                                flag = true;
                                fieldName[i] = 0L;
                            }
                        }
                    }
                    if ((!flag) && (ix > 9)) {
                        s2 = timeStamp[0] + "," + timeStamp[1];
                        for (int i = 0; i < ix; i++) {
                            s2 = s2 + "," + fieldName[i];
                        }
                        vecData.add(s2);
                    }
                }
            }
            bufferedreader.close();
        } catch (Exception exception) {
            logs.add("Error: " + exception.getMessage());
        }
        return vecData;
    }

    public List<String> getLogs() {
        return logs;
    }

    public static void writeCSVFile(Mws82 mwsod, List<String> vecList, String fname, int version) {
        try (BufferedWriter bufferedwriter = new BufferedWriter(new FileWriter(fname + ".csv"))) {

            List<MwsPrimary> itemprim = mwsod.getArchives().getMwsis()
                    .stream().filter(it -> it.getType().toLowerCase().contains("is"))
                    .collect(Collectors.toList());
            String head = setHeads(itemprim);
            bufferedwriter.write(head, 0, head.length());
            bufferedwriter.flush();

            Organize organize = new Organize(itemprim, vecList);
            List<String> tempvec = organize.getOrganize(version, mwsod.getArchives().getFilterstat());

            for (int i = 0; i < tempvec.size(); i++) {
                String str = tempvec.get(i);
                bufferedwriter.newLine();
                bufferedwriter.write(str, 0, str.length());
                bufferedwriter.flush();
            }
            bufferedwriter.newLine();
            bufferedwriter.close();
            FxDialogs.showInformation(Constantes.TITLE, "Archivo creado");
        } catch (IOException e) {
            Logger.getLogger(e.getMessage());
        }
    }

    private static String setHeads(List<MwsPrimary> itemprim) {
        StringBuilder stringbuild = new StringBuilder();
        itemprim.forEach((prim) -> {
            prim.getHeads().forEach((head) -> {
                stringbuild.append(head).append(",");
            });
        });
        return stringbuild.toString()
                .substring(0, stringbuild.toString().length() - 1);
    }
}
