/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.models;

import java.nio.file.Path;
import java.util.List;

/**
 *
 * @author 6_Delta
 */
public class JSONTemp {

    private String date;
    private Path path;
    private Stats stats;
    private Xml xml;

    public JSONTemp() {

    }

    public JSONTemp(String date, Path path, Stats stats, Xml xml) {
        this.date = date;
        this.path = path;
        this.stats = stats;
        this.xml = xml;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public Xml getXml() {
        return xml;
    }

    public void setXml(Xml xml) {
        this.xml = xml;
    }

    public static class Stats {

        private String extension;
        private List<Path> urls;

        public Stats(String extension, List<Path> urls) {
            this.extension = extension;
            this.urls = urls;
        }

        public String getExtension() {
            return extension;
        }

        public void setExtension(String extension) {
            this.extension = extension;
        }

        public List<Path> getUrls() {
            return urls;
        }

        public void setUrls(List<Path> urls) {
            this.urls = urls;
        }

        @Override
        public String toString() {
            return "Stats{" + "extension=" + extension + ", urls=" + urls + '}';
        }
    }

    public static class Xml {

        private String extension;
        private Path url;

        public Xml(String extension, Path url) {
            this.extension = extension;
            this.url = url;
        }

        public String getExtension() {
            return extension;
        }

        public void setExtension(String extension) {
            this.extension = extension;
        }

        public Path getUrl() {
            return url;
        }

        public void setUrl(Path url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "Xml{" + "extension=" + extension + ", url=" + url + '}';
        }
    }

    @Override
    public String toString() {
        return "JSONTemp{" + "date=" + date + ", path=" + path + ", stats=" + stats + ", xml=" + xml + '}';
    }
}
