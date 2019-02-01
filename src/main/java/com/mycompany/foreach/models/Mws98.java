/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.models;

import java.nio.file.Path;

/**
 *
 * @author 6_Delta
 */
public class Mws98 {
    private String ip;
    private String user;
    private String pass;
    private Path path;
    private String extension;
    private String finalextension;
    private Path toPath;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public String getFinalextension() {
        return finalextension;
    }

    public void setFinalextension(String finalextension) {
        this.finalextension = finalextension;
    }

    public Path getToPath() {
        return toPath;
    }

    public void setToPath(Path toPath) {
        this.toPath = toPath;
    }

    @Override
    public String toString() {
        return "Mws98{" + "ip=" + ip + ", path=" + path + ", extension=" + extension + ", toPath=" + toPath + '}';
    }
    
}