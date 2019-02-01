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
public class MwsArchive {

    private List<String> heads;
    private List<String> statics;

    public MwsArchive() {
    }

    public MwsArchive(List<String> heads, List<String> statics) {
        this.heads = heads;
        this.statics = statics;
    }

    public List<String> getHeads() {
        return heads;
    }

    public void setHeads(List<String> heads) {
        this.heads = heads;
    }

    public List<String> getStatics() {
        return statics;
    }

    public void setStatics(List<String> statics) {
        this.statics = statics;
    }

    @Override
    public String toString() {
        return "MwsArchive{" + "heads=" + heads + ", statics=" + statics + '}';
    }
    
}
