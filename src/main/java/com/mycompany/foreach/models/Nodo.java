/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 6_Delta
 */
public class Nodo {

    private String runtime;
    private String node;
    private String status;
    private String time;
    private final List<Linea> lineas = new ArrayList<>();

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void agregarLinea(Linea linea) {
        this.lineas.add(linea);
    }

    public List<Linea> obtenerLineas() {
        return this.lineas;
    }

    public Linea obtenerLinea(int i) {
        return this.lineas.get(i);
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
