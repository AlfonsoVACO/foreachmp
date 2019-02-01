/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.models;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 * @author 6_Delta
 */
public class PrimaryDir {
    private Date inDate;;
    private Path inDir;
    private String clone;
    private String nameDir;
    private String nameArch;
    private List<String> extras = Arrays.asList("wM82","wM98");

    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public Path getInDir() {
        return inDir;
    }

    public void setInDir(Path inDir) {
        this.inDir = inDir;
    }

    public String getClone() {
        return clone;
    }

    public void setClone(String clone) {
        this.clone = clone;
    }

    public String getNameDir() {
        return nameDir;
    }

    public void setNameDir(String nameDir) {
        this.nameDir = nameDir;
    }

    public String getNameArch() {
        return nameArch;
    }

    public void setNameArch(String nameArch) {
        this.nameArch = nameArch;
    }

    public List<String> getExtras() {
        return extras;
    }

    public void setExtras(List<String> extras) {
        this.extras = extras;
    }
    
    
}
