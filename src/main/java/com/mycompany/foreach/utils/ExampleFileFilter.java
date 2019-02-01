/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author 6_Delta
 */
public final class ExampleFileFilter extends FileFilter {
    private Map filters = null;
    private String description = null;
    private String fullDescription = null;
    private boolean useExtensionsInDescription = true;

    public ExampleFileFilter() {
        this.filters = new HashMap<>();
    }

    public ExampleFileFilter(String extension) {
        this(extension, null);
    }

    public ExampleFileFilter(String extension, String description) {
        this();
        if (extension != null) addExtension(extension);        
        if (description != null) setDescription(description);
    }

    public ExampleFileFilter(String[] filters) {
        this(filters, null);
    }

    public ExampleFileFilter(String[] filters, String description) {
        this();
        for (String filter : filters) 
            addExtension(filter);        
        if (description != null)  setDescription(description);
    }

    @Override
    public boolean accept(File f) {
        if (f != null) {
            if (f.isDirectory())
                return true;
            String extension = getExtension(f);
            if ((extension != null) && (this.filters.get(getExtension(f)) != null))
                return true;
        }
        return false;
    }

    public String getExtension(File f) {
        if (f != null) {
            String filename = f.getName();
            int i = filename.lastIndexOf('.');
            if ((i > 0) && (i < filename.length() - 1))
                return filename.substring(i + 1).toLowerCase();
        }
        return null;
    }

    public void addExtension(String extension) {
        if (this.filters == null)
            this.filters = new HashMap(5);
        this.filters.put(extension.toLowerCase(), this);
        this.fullDescription = null;
    }

    @Override
    public String getDescription() {
        if (this.fullDescription == null) {
            if ((this.description == null) || (isExtensionListInDescription())) {
                this.fullDescription = (this.description + " (");

                Iterator extensions = this.filters.keySet().iterator();
                if (extensions != null) {
                    this.fullDescription = (this.fullDescription + "." + (String) extensions.next());
                    while (extensions.hasNext()) {
                        this.fullDescription = (this.fullDescription + ", ." + (String) extensions.next());
                    }
                }
                this.fullDescription += ")";
            } else {
                this.fullDescription = this.description;
            }
        }
        return this.fullDescription;
    }

    public void setDescription(String description) {
        this.description = description;
        this.fullDescription = null;
    }

    public void setExtensionListInDescription(boolean b) {
        this.useExtensionsInDescription = b;
        this.fullDescription = null;
    }

    public boolean isExtensionListInDescription() {
        return this.useExtensionsInDescription;
    }
}
