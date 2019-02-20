/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.connections;

import com.mycompany.foreach.utils.constantes.Constantes;
import com.mycompany.foreach.utils.constantes.Consultas;
import com.mycompany.foreach.utils.DerbyUtils;
import com.mycompany.foreach.utils.FxDialogs;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author 6_Delta
 */
public class CreateTables {

    public static void creaTablas(Connection conn) throws ClassNotFoundException {
        try {
            Statement st = conn.createStatement();
            st.execute(Consultas.CREATE_TABLE_ARCHIVOS);
            FxDialogs.showInformation(Constantes.TITLE, "La base de datos  se ha creado correctamente");
        } catch (SQLException ex) {
            if(DerbyUtils.tableAlreadyExists(ex)) {
                FxDialogs.showException(Constantes.TITLE, "Las tablas están listas...", ex);
            }
        }
    }

}
