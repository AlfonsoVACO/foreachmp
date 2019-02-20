/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.daos;

import com.mycompany.foreach.connections.ConnectionLocal;
import com.mycompany.foreach.models.DataColumns;
import com.mycompany.foreach.utils.constantes.Consultas;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.ObservableList;

/**
 *
 * @author 6_Delta
 */
public class DaoDataColumns {

    public static void LlenarInfoAll(Connection connection, 
            ObservableList<DataColumns> lista) throws SQLException {
        
        Statement instruccion = connection.createStatement();
        try (ResultSet rs = instruccion.executeQuery(Consultas.SELECT_ARCHIVOS_EXISTENCIA_ALL)) {
            while (rs.next()) {
                lista.add(
                        new DataColumns(
                                rs.getInt("idfile"),
                                rs.getString("url"),
                                rs.getString("fecha")
                        )
                );
            }
        }
        
    }

    public static void deleteDetail( int id) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionLocal.getConnection();
        try (PreparedStatement delete = connection.prepareStatement(Consultas.DELETE_ARCHIVOS)) {
            delete.setInt(1, id);
            delete.execute();
            delete.close();
        }
    }

    public static void setUrlJSON(Connection connection, String url) throws SQLException {
        java.util.Date fecha = new java.util.Date();
        try (PreparedStatement insert = connection.prepareStatement(Consultas.INSERT_ARCHIVOS)) {
            insert.setString(1, url);
            insert.setDate(2, new Date(fecha.getTime()));
            insert.execute();
        }
    }   
}
