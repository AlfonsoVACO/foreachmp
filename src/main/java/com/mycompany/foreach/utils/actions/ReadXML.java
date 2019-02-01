/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.utils.actions;

import com.mycompany.foreach.models.Linea;
import com.mycompany.foreach.models.Nodo;
import java.nio.file.Path;

/**
 *
 * @author 6_Delta
 */
public class ReadXML {
    
    private String log = "";
    private String logarchive = "";
    
    public String getLogs(){
        return this.log;
    }
    
    public String getFilesFail(){
        return this.logarchive;
    }

    Nodo leer(String a, Nodo nodo, Path archivo) {
        nodo.setNode(a.substring(a.indexOf("<nodeAlias>") + 11, a.indexOf("</nodeAlias>")));
        nodo.setRuntime(a.substring(a.indexOf("<runtimeComponentId>") + 20, a.indexOf("</runtimeComponentId>")));
        String res = a.substring(a.indexOf("<kpi>") + 5, a.length());
        String[] pr = res.split("<kpi>");
        if(pr.length >= 3){
            nodo.setStatus(pr[2].substring(pr[2].lastIndexOf("<runtimeStatus>") + 15, pr[2].indexOf("</runtimeStatus>")));
            String tmp = pr[2].substring(pr[2].lastIndexOf("<timeStamp>") + 11, pr[2].indexOf("</timeStamp>")).replace("T", " ");
            if(!tmp.contains("."))
                nodo.setTime(tmp.substring(0, 19));
            else
                nodo.setTime(tmp.substring(0, tmp.indexOf(".") - 3));
            pr[2] = pr[2].substring(0, pr[2].lastIndexOf("</kpi>"));
            for (String str : pr) {
                nodo.agregarLinea(descomponer(str, new Linea()));
            }
        }else{
            this.log = "Archivo sólo contiene menos de 3 KPI >> " + archivo.toString();
            logarchive = archivo.toString();
        }
        return nodo;
    }

    Linea descomponer(String str, Linea linea) {
        linea.setCriticalValue(Integer.parseInt(
                str.substring(str.indexOf("<criticalValue>")+15,
                        str.indexOf("</criticalValue>"))));
        linea.setDisplayName(
                str.substring(str.indexOf("<displayName>") + 13, 
                        str.indexOf("</displayName>")));
        String id = str.substring(
                str.indexOf("<id>") + 4, 
                str.indexOf("</id>"));
        if (id.contains(".")) {
            id = id.substring(id.lastIndexOf(".") + 1);
        }
        linea.setId(id);
        linea.setMarginalValue(Integer.parseInt(
                str.substring(str.indexOf("<marginalValue>") + 15, 
                        str.indexOf("</marginalValue>"))));
        linea.setMaxValue(Integer.parseInt(str.substring(
                str.indexOf("<maxValue>") + 10, 
                str.indexOf("</maxValue>"))));
        linea.setUnit(str.substring(str.indexOf("<unit>") + 6,
                str.indexOf("</unit>")));
        String vl = str.substring(str.indexOf("<value>") + 7,
                str.indexOf("</value>"));
        linea.setValue(Double.parseDouble(vl));
        return linea;
    }
}
