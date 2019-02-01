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
public class MwsPrimary {
    private String type;
    private List<String> heads;
    private List<String> alias;
    private List<String> display;
    private MwsArchive memory;
    private MwsArchive cpu;
    private MwsArchive threads;
    
    public MwsPrimary(){}

    public MwsPrimary(String type, List<String> heads, List<String> alias, List<String> display, MwsArchive memory, MwsArchive cpu, MwsArchive threads) {
        this.type = type;
        this.heads = heads;
        this.alias = alias;
        this.display = display;
        this.memory = memory;
        this.cpu = cpu;
        this.threads = threads;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getHeads() {
        return heads;
    }

    public void setHeads(List<String> heads) {
        this.heads = heads;
    }

    public List<String> getAlias() {
        return alias;
    }

    public void setAlias(List<String> alias) {
        this.alias = alias;
    }

    public List<String> getDisplay() {
        return display;
    }

    public void setDisplay(List<String> display) {
        this.display = display;
    }

    public MwsArchive getMemory() {
        return memory;
    }

    public void setMemory(MwsArchive memory) {
        this.memory = memory;
    }

    public MwsArchive getCpu() {
        return cpu;
    }

    public void setCpu(MwsArchive cpu) {
        this.cpu = cpu;
    }

    public MwsArchive getThreads() {
        return threads;
    }

    public void setThreads(MwsArchive threads) {
        this.threads = threads;
    }

    @Override
    public String toString() {
        return "MwsPrimary{" + "type=" + type + ", heads=" + heads + ", alias=" + alias + ", display=" + display + ", memory=" + memory + ", cpu=" + cpu + ", threads=" + threads + '}';
    }

}
