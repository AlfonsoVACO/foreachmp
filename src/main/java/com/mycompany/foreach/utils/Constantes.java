/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.utils;

import java.nio.file.CopyOption;
import java.nio.file.StandardCopyOption;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author 6_Delta
 */
public class Constantes {
    public static final String TITLE = "For-Each";
    public static final ObservableList LST_MESES = 
            FXCollections.observableArrayList("Enero","Febrero","Marzo",
                    "Abril","Mayo","Junio","Julio","Agosto","Septiembre",
                    "Octubre","Noviembre","Diciembre");
    public static final CopyOption[] COPY_OPTIONS = new CopyOption[] { 
        StandardCopyOption.REPLACE_EXISTING,
        StandardCopyOption.COPY_ATTRIBUTES };
    public static final String REGEX_STATS = ".*(:00).*";
    public static final String REGEX_CMT = ".*(:00:0).*";
    public static final String PATH_ARCHIVES = ".//src//main//resources//archives//";
    public static final String PATH_JSON = ".//src//main//resources//archives//jsonmain.json";
    
    public static final String NAME_BATSE = "executes66.bat";
    public static final String NAME_BATSI = "executes67.bat";
    
    public static final String STYLES = "/styles/styles.css";
    public static final String ARCHIVES_FAILS = "fails";
    public static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    
}
