/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.connections;

import com.mycompany.foreach.utils.Constantes;
import com.mycompany.foreach.utils.Consultas;
import com.mycompany.foreach.utils.DerbyUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.controlsfx.control.Notifications;

/**
 *
 * @author 6_Delta
 */
public class CreateTables {

    public static void creaTablas(Connection conn) throws ClassNotFoundException {
        try {
            Statement st = conn.createStatement();
            st.execute(Consultas.CREATE_TABLE_ARCHIVOS);
            Notifications.create().title(Constantes.TITLE)
                    .text("La base de datos  se ha creado correctamente")
                    .showInformation();
        } catch (SQLException ex) {
            if(DerbyUtils.tableAlreadyExists(ex)) {
                Notifications.create()
                            .title(Constantes.TITLE)
                            .text("Las tablas están listas...")
                                .showInformation();
            }
        }
    }

}
