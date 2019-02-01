/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.models;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author 6_Delta
 */
public class Mws82 {
    private List<String> ips;
    private String user;
    private String pass;
    private Path path;
    private String extension;
    private List<String> toPath;
    private List<String> commands = Arrays.asList("cd ","TYPE *.log > ","cd ", "TYPE *.log > ");
    private Archives archives;

    public List<String> getIps() {
        return ips;
    }

    public void setIps(List<String> ips) {
        this.ips = ips;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public List<String> getToPath() {
        return toPath;
    }

    public void setToPath(List<String> toPath) {
        this.toPath = toPath;
    }

    public List<String> getCommands() {
        return commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

    public Archives getArchives() {
        return archives;
    }

    public void setArchives(Archives archives) {
        this.archives = archives;
    }

    @Override
    public String toString() {
        return "Mws82{" + "ips=" + ips + ", user=" + user + ", pass=" + pass + ", path=" + path + ", extension=" + extension + ", toPath=" + toPath + ", commands=" + commands + ", archives=" + archives + '}';
    }
    
}
