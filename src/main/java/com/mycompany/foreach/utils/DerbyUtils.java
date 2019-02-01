/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.utils;

import java.sql.SQLException;

/**
 *
 * @author 6_Delta
 */
public class DerbyUtils {
 public DerbyUtils() {
        
    }

    public static boolean tableAlreadyExists(SQLException e) {
        return e.getSQLState().equals("Carrera") || e.getSQLState().equals("Acesor") || e.getSQLState().equals("Alumno") || e.getSQLState().equals("Claves");
    }   
}
