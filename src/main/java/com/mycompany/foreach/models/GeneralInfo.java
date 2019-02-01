/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.models;

import java.util.List;

/**
 *
 * @author 6_Delta
 */
public class GeneralInfo {
    
    private String inDir;
    private List<String> extras;

    public GeneralInfo(String inDir, List<String> extras) {
        this.inDir = inDir;
        this.extras = extras;
    }
    
    public GeneralInfo(){}

    public String getInDir() {
        return inDir;
    }

    public void setInDir(String inDir) {
        this.inDir = inDir;
    }

    public List<String> getExtras() {
        return extras;
    }

    public void setExtras(List<String> extras) {
        this.extras = extras;
    }

    @Override
    public String toString() {
        return "GeneralInfo{" + "inDir=" + inDir + ", extras=" + extras + '}';
    }
}
