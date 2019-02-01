/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author 6_Delta
 */
public final class OpenFrameContents {

    private List<String> vecData = null;
    private String fname = null;
    private String s2 = null;    
    private Long[] fieldName = null;
    private final String[] timeStamp = {"Date", "Time"};

    public OpenFrameContents(String fname) throws Exception {
        this.fname = fname;
        readFile();
    }

    public List<String> getData() {
        return this.vecData;
    }

    private void readFile() {        
        try (BufferedReader bufferedreader = new BufferedReader(new FileReader(this.fname))){
            this.vecData = new ArrayList<>();
            s2 = new String();

            boolean loop = false;
            while (!loop) {
                String s1 = bufferedreader.readLine();
                if (s1 == null)
                    loop = true;
                 else {
                    String str = "";

                    StringTokenizer stringtokenizer = new StringTokenizer(s1);
                    int ix = stringtokenizer.countTokens();
                    this.fieldName = new Long[ix];
                    if (ix == 14) {
                        this.timeStamp[0] = stringtokenizer.nextToken();
                        this.timeStamp[1] = stringtokenizer.nextToken();
                        ix -= 2;
                    } else if (ix == 15) {
                        this.timeStamp[0] = stringtokenizer.nextToken();
                        this.timeStamp[1] = stringtokenizer.nextToken();
                        str = stringtokenizer.nextToken();
                        ix -= 3;
                    }
                    boolean flag = false;
                    if (ix > 9) {
                        for (int i = 0; i < ix; i++) {
                            try {
                                str = stringtokenizer.nextToken();
                                this.fieldName[i] = Long.parseLong(str, 16);
                            } catch (NumberFormatException ne) {
                                flag = true;
                                this.fieldName[i] = 0L;
                            }
                        }
                    }
                    if ((!flag) && (ix > 9)) {
                        s2 = this.timeStamp[0] + "," + this.timeStamp[1];
                        for (int i = 0; i < ix; i++)
                            s2 = s2 + "," + this.fieldName[i];
                        this.vecData.add(s2);
                    }
                }
            }
            bufferedreader.close();
        } catch (Exception exception) {
            System.out.println(exception.toString());
        }
        
        JOptionPane.showMessageDialog(null, "Carga completa", "For->Each", JOptionPane.INFORMATION_MESSAGE);
    }
}
