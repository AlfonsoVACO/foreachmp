/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.utils.constantes;

/**
 *
 * @author 6_Delta
 */
public class Consultas {
    public static final String SELECT_ARCHIVOS_EXISTENCIA = "SELECT url, fecha FROM files";
    public static final String SELECT_ARCHIVOS_EXISTENCIA_ALL = "SELECT idfile, url, fecha FROM files";
    public static final String INSERT_ARCHIVOS = "insert into files ( url, fecha) VALUES (?, ?)";
    public static final String DELETE_ARCHIVOS = "delete from files where idfile = ?";
    public static final String CREATE_TABLE_ARCHIVOS = "CREATE TABLE files ( idfile INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), url varchar (250) NOT NULL, fecha date NOT NULL, CONSTRAINT pk_idfile PRIMARY KEY (idfile))";
}
