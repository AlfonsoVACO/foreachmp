/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author 6_Delta
 */
public class ConnectionLocal {
    
    private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String DBNAME = ".\\DB\\ForEach.DB";
    private static final String CONNECTIONURL = "jdbc:derby:" + DBNAME + ";create=true" ;
    private static Connection conn = null;
    
    public static Connection connect() throws SQLException{
        try{
            Class.forName(DRIVER);
        } catch(java.lang.ClassNotFoundException e) {
        }
        conn = DriverManager.getConnection(CONNECTIONURL);
        return conn;
    }
    public static Connection getConnection() throws SQLException, ClassNotFoundException{
        if(conn !=null && !conn.isClosed())
            return conn;
        connect();
        return conn;

    }
}
