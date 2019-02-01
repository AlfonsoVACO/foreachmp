/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.models;

/**
 *
 * @author 6_Delta
 */
public class Linea {

    private int criticalValue;
    private String displayName;
    private String id;
    private int marginalValue;
    private int maxValue;
    private String unit;
    private double value;

    public int getCriticalValue() {
        return criticalValue;
    }

    public void setCriticalValue(int criticalValue) {
        this.criticalValue = criticalValue;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMarginalValue() {
        return marginalValue;
    }

    public void setMarginalValue(int marginalValue) {
        this.marginalValue = marginalValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
