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
public class Archives {
    private List<MwsPrimary> mwsis;
    private String onRegex;
    private String toRegex;
    private String filter;
    private String filterstat;
    private String finalnameArchs;

    public List<MwsPrimary> getMwsis() {
        return mwsis;
    }

    public void setMwsis(List<MwsPrimary> mwsis) {
        this.mwsis = mwsis;
    }

    public String getOnRegex() {
        return onRegex;
    }

    public void setOnRegex(String onRegex) {
        this.onRegex = onRegex;
    }

    public String getToRegex() {
        return toRegex;
    }

    public void setToRegex(String toRegex) {
        this.toRegex = toRegex;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getFilterstat() {
        return filterstat;
    }

    public void setFilterstat(String filterstat) {
        this.filterstat = filterstat;
    }

    public String getFinalnameArchs() {
        return finalnameArchs;
    }

    public void setFinalnameArchs(String finalnameArchs) {
        this.finalnameArchs = finalnameArchs;
    }

    @Override
    public String toString() {
        return "Archives{" + "mwsis=" + mwsis + ", onRegex=" + onRegex + ", toRegex=" + toRegex + ", filter=" + filter + ", finalnameArchs=" + finalnameArchs + '}';
    }
    
}
